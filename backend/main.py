from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field
from concurrent.futures import ThreadPoolExecutor, as_completed
import httpx
from openai import OpenAI
from dotenv import load_dotenv
from pathlib import Path
import json
import logging
import os
import re
import threading
import time

from prompt_ladder import (
    build_production_user_prompt,
    build_prompt_variant,
    is_ladder_variant,
)

BASE_DIR = Path(__file__).resolve().parent
load_dotenv(BASE_DIR / ".env")

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("vehtrust.backend")

app = FastAPI(title="VehTrust AI Backend", version="1.0.0")

MODEL = os.getenv("OPENAI_MODEL", "gpt-4o-mini")
OPENAI_TEMPERATURE = float(os.getenv("OPENAI_TEMPERATURE", "0.2"))
# 可选：兼容 OpenAI 协议的第三方网关，例如 DeepSeek 填 https://api.deepseek.com
OPENAI_BASE_URL = os.getenv("OPENAI_BASE_URL", "").strip() or None


def _parse_api_keys() -> list[str]:
    """OPENAI_API_KEYS=key1,key2 优先；否则使用 OPENAI_API_KEY 单条。"""
    raw = os.getenv("OPENAI_API_KEYS", "").strip()
    if raw:
        keys = [k.strip() for k in raw.split(",") if k.strip()]
        if keys:
            return keys
    single = os.getenv("OPENAI_API_KEY", "").strip()
    return [single] if single else []


API_KEYS = _parse_api_keys()
# 单 Key 默认并发 3；多 Key 未显式配置时按 Key 数放大（仍受上限约束，避免打爆限流）
_env_batch = os.getenv("OPENAI_BATCH_CONCURRENCY", "").strip()
if _env_batch:
    BATCH_CONCURRENCY = max(1, min(int(_env_batch), 32))
else:
    BATCH_CONCURRENCY = min(3 * max(1, len(API_KEYS)), 16)


def _make_openai_clients(keys: list[str]) -> list[OpenAI]:
    clients: list[OpenAI] = []
    for key in keys:
        kwargs: dict = {
            "api_key": key,
            # 每 Key 独立连接池，避免多线程共用一个 httpx 客户端的边角问题
            "http_client": httpx.Client(trust_env=False, timeout=60.0),
        }
        if OPENAI_BASE_URL:
            kwargs["base_url"] = OPENAI_BASE_URL
        clients.append(OpenAI(**kwargs))
    return clients


OPENAI_CLIENTS = _make_openai_clients(API_KEYS)
# 兼容旧代码与 /health：首 Key 是否存在
OPENAI_API_KEY = API_KEYS[0] if API_KEYS else ""
client = OPENAI_CLIENTS[0] if OPENAI_CLIENTS else None

if not API_KEYS:
    logger.warning(
        "OPENAI_API_KEY / OPENAI_API_KEYS 未设置：请在 %s 中配置，否则 /api/accident/analyze 将返回 500",
        BASE_DIR / ".env",
    )
elif len(API_KEYS) > 1:
    logger.info("已加载 %s 个 API Key，批量默认并发=%s（可用 OPENAI_BATCH_CONCURRENCY 覆盖）", len(API_KEYS), BATCH_CONCURRENCY)


class ResponsibilityPayload(BaseModel):
    driverFactor: int
    systemFactor: int
    environmentFactor: int
    conclusion: str
    reasons: list[str] = Field(default_factory=list)


class EnvironmentPayload(BaseModel):
    weather: str
    road: str
    obstacle: str
    laneMarking: str


class DecisionTracePayload(BaseModel):
    sensorInput: str
    perception: str
    planning: str
    control: str


class TelemetryPayload(BaseModel):
    tMs: int
    speedKph: float
    axMS2: float
    brake: int
    steerDeg: float


class DerivedSignalsPayload(BaseModel):
    """对齐实验说明 §4.3：端侧派生的 TTC、AEB、制动与接管等摘要（可选）。"""

    reactionTimeMs: int | None = None
    brakeRiseTimeMs: int | None = None
    aebDelayMs: int | None = None
    ttcAtBrakeSeconds: float | None = None
    brakeEffective: bool | None = None
    maxSteerLast2sDeg: float | None = None
    driverTakeoverSummary: str | None = None
    riskPredictionSummary: str | None = None


class AccidentAnalyzeRequest(BaseModel):
    eventId: str
    eventType: str  # 支持实验事故类型: 驾驶员反应不足 / AEB触发延迟或未触发 / TTC过低导致碰撞风险 / 驾驶员接管不足 / 环境因素干扰 / 多因素共同作用 / COLLISION / AUTOPILOT_FAULT
    timeMillis: int
    location: str
    summary: str
    triggerReasons: list[str] = Field(default_factory=list)
    severity: str
    autoDrivingState: str
    telemetry: list[TelemetryPayload] = Field(default_factory=list)
    responsibility: ResponsibilityPayload
    environment: EnvironmentPayload | None = None
    decisionTrace: DecisionTracePayload | None = None
    derivedSignals: DerivedSignalsPayload | None = None
    experimentGroup: str = Field(
        default="C",
        description="A/B/C=系统方法对比; BASELINE/P1～P6/FINAL=提示词工程阶梯（固定全量输入）",
    )
    ablationMode: str = Field(
        default="D0",
        description="消融 D0=完整 D1=去掉责任块 D2=去掉环境 D3=去掉决策链 D4=弱化结构化提示（仅 C 组）",
    )


def _telemetry_lines(payload: AccidentAnalyzeRequest) -> str:
    return "\n".join(
        f"t={point.tMs}ms, speed={point.speedKph:.2f}km/h, ax={point.axMS2:.2f}m/s², brake={point.brake}%, steer={point.steerDeg:.2f}°"
        for point in payload.telemetry[:20]
    )


def _structured_output_rules() -> str:
    from prompt_ladder import full_output_rules

    return full_output_rules()


def build_prompt_c(payload: AccidentAnalyzeRequest) -> str:
    """C组：本项目结构化方法；按 ablationMode 裁剪输入块（对齐说明文档 §9）。"""
    ablation = payload.ablationMode.upper().strip() or "D0"
    telemetry_summary = _telemetry_lines(payload)
    derived_json = (
        payload.derivedSignals.model_dump_json(exclude_none=True) if payload.derivedSignals else "null"
    )

    event_block = f"""
【事件】
id={payload.eventId}
type={payload.eventType}
time={payload.timeMillis}
location={payload.location}
summary={payload.summary}
triggers={'、'.join(payload.triggerReasons)}
severity={payload.severity}
autoDrivingState={payload.autoDrivingState}
""".strip()

    if ablation == "D4":
        return f"""
请根据以下「普通描述 + 遥测摘要 + 派生指标」生成复盘 JSON（字段仍为 summary, rootCause, comprehensiveAnalysis, scenarioReconstruction, confidenceStatement, evidencePoints, suggestions, modelHint, rawText）。
说明：本题为消融实验 D4，不提供完整结构化提示词；请仍严格基于下列已给信息，不要编造未出现的数据。

{event_block}

【事故前关键遥测】
{telemetry_summary}

【端侧派生信号（TTC/AEB/制动/接管/严重度摘要）】
{derived_json}
只输出 JSON。
""".strip()

    if ablation == "D0":
        return build_production_user_prompt(payload)

    resp_block = f"""
【责任分析】
conclusion={payload.responsibility.conclusion}
driver={payload.responsibility.driverFactor}%
system={payload.responsibility.systemFactor}%
environment={payload.responsibility.environmentFactor}%
reasons={' | '.join(payload.responsibility.reasons)}
""".strip()

    env_block = f"【环境】\n{payload.environment.model_dump_json() if payload.environment else 'null'}"
    trace_block = f"【决策链】\n{payload.decisionTrace.model_dump_json() if payload.decisionTrace else 'null'}"
    tele_block = f"【事故前关键遥测】\n{telemetry_summary}"
    derived_block = f"【端侧派生信号（TTC/AEB/制动/接管/严重度摘要）】\n{derived_json}"

    parts_rules = [_structured_output_rules(), event_block]
    if ablation != "D1":
        parts_rules.append(resp_block)
    if ablation != "D2":
        parts_rules.append(env_block)
    if ablation != "D3":
        parts_rules.append(trace_block)
    parts_rules.append(tele_block)
    parts_rules.append(derived_block)
    return "\n\n".join(parts_rules).strip()


def build_prompt_a(payload: AccidentAnalyzeRequest) -> str:
    """A组：模板生成 - 固定规则拼接，无需复杂推理"""
    return f"""请严格按模板生成事故复盘JSON，字段：summary, rootCause, comprehensiveAnalysis, scenarioReconstruction, confidenceStatement, evidencePoints, suggestions, modelHint, rawText。
仅使用固定模板填充：
summary: 事故{ payload.eventType }发生于{ payload.location }，严重程度{ payload.severity }。
rootCause: 主要责任驾驶员{ payload.responsibility.driverFactor }%，系统{ payload.responsibility.systemFactor }%，环境{ payload.responsibility.environmentFactor }%。
证据点从输入直接提取。
只输出JSON。"""


def build_prompt_b(payload: AccidentAnalyzeRequest) -> str:
    """B组：通用大模型 - 仅输入简要描述和基础状态，不提供结构化分析结果"""
    return f"""请基于以下简要事故描述生成复盘报告JSON。
仅提供：事件类型{ payload.eventType }，摘要{ payload.summary }，自动驾驶状态{ payload.autoDrivingState }，严重程度{ payload.severity }。
不要使用任何结构化责任分析、环境或决策链数据。
生成summary, rootCause等字段，输出JSON。"""


@app.get("/health")
def health():
    return {
        "ok": True,
        "model": MODEL,
        "openai_configured": bool(API_KEYS),
        "openai_key_count": len(API_KEYS),
        "api_base_url": OPENAI_BASE_URL or "default (https://api.openai.com/v1)",
        "batch_concurrency": BATCH_CONCURRENCY,
        "temperature": OPENAI_TEMPERATURE,
    }


def _normalize_model_json_text(s: str) -> str:
    s = (s or "").strip()
    if s.startswith("\ufeff"):
        s = s.lstrip("\ufeff")
    return s.replace("\r\n", "\n").replace("\r", "\n").strip()


def _try_repair_json_commas(text: str) -> str:
    """去掉对象/数组末尾多余逗号（模型偶发 `,}` / `,]`）。"""
    t = text
    for _ in range(16):
        nxt = re.sub(r",(\s*})", r"\1", t)
        nxt = re.sub(r",(\s*])", r"\1", nxt)
        if nxt == t:
            break
        t = nxt
    return t


def _parse_llm_json_object(content: str) -> dict:
    """解析模型返回的 JSON 对象；偶发夹杂 markdown 或非 JSON 前缀时尽量兜底。"""
    s = _normalize_model_json_text(content)
    if not s:
        raise ValueError("模型返回内容为空")

    if s.startswith("```"):
        rest = s[3:].lstrip()
        if rest.lower().startswith("json"):
            rest = rest[4:].lstrip()
        fence = rest.rfind("```")
        s = rest[:fence].strip() if fence != -1 else rest.strip()

    candidates: list[str] = [s]
    start = s.find("{")
    if start >= 0:
        depth = 0
        for i in range(start, len(s)):
            if s[i] == "{":
                depth += 1
            elif s[i] == "}":
                depth -= 1
                if depth == 0:
                    chunk = s[start : i + 1]
                    if chunk != s:
                        candidates.append(chunk)
                    break

    last_err: Exception | None = None
    for cand in candidates:
        repaired = _try_repair_json_commas(cand)
        variants = [cand]
        if repaired != cand:
            variants.append(repaired)
        for variant in variants:
            try:
                obj = json.loads(variant)
            except json.JSONDecodeError as exc:
                last_err = exc
                continue
            if not isinstance(obj, dict):
                raise ValueError(f"模型 JSON 根须为 object，实际为 {type(obj).__name__}")
            return obj
    msg = str(last_err) if last_err else "unknown"
    snippet = s[:400] + ("…" if len(s) > 400 else "")
    raise ValueError(f"JSON 解析失败: {msg}; 片段: {snippet}") from last_err


def _analyze_payload(payload: AccidentAnalyzeRequest, openai_client: OpenAI) -> dict:
    group = payload.experimentGroup.upper().strip()
    prompt_variant = group if is_ladder_variant(group) else None

    if group == "A":
        prompt = build_prompt_a(payload)
        system_content = "你是一个固定模板填充助手，只按规则生成结构化JSON，不要添加额外推理。"
    elif group == "B":
        prompt = build_prompt_b(payload)
        system_content = "你是通用大模型助手，仅基于简要描述生成事故复盘，不要使用结构化输入。"
    elif is_ladder_variant(group):
        prompt, system_content = build_prompt_variant(group, payload)
    else:  # C 及默认
        group = "C"
        prompt = build_prompt_c(payload)
        system_content = (
            "你是资深车载事故分析专家。请严格基于输入数据进行专业、谨慎、可解释的因果分析，"
            "不要虚构事实，输出结构化 JSON。若发现后文与前文语义重复，"
            "必须优先改写为新增证据、新增视角或新增不确定性说明。"
        )

    strict_suffix = (
        "\n\n[硬性要求] 只输出一个 JSON 对象；键名与字符串一律用 ASCII 双引号；"
        "字符串内的双引号、反斜杠、换行必须按 JSON 转义；不要用 markdown 代码块。"
    )
    snippet_chars = max(1000, min(int(os.getenv("OPENAI_JSON_RETRY_SNIPPET_CHARS", "8000")), 32000))

    messages_first: list[dict[str, str]] = [
        {"role": "system", "content": system_content},
        {"role": "user", "content": prompt},
    ]

    last_content = "{}"
    data: dict | None = None
    retry_count = 0
    total_latency_ms = 0.0
    prompt_tokens: int | None = None
    completion_tokens: int | None = None
    total_tokens: int | None = None

    for attempt in range(2):
        if attempt == 0:
            messages = messages_first
            temp = OPENAI_TEMPERATURE
        else:
            bad = (last_content or "")[:snippet_chars]
            messages = [
                {"role": "system", "content": system_content + strict_suffix},
                {"role": "user", "content": prompt},
                {"role": "assistant", "content": bad},
                {
                    "role": "user",
                    "content": (
                        "以上 assistant 内容不是合法 JSON。请输出**修正后的完整** JSON，"
                        "仅一个对象；字符串内双引号必须写成 \\\"；不要 markdown、不要解释。"
                    ),
                },
            ]
            temp = 0.0

        t0 = time.perf_counter()
        completion = openai_client.chat.completions.create(
            model=MODEL,
            temperature=temp,
            response_format={"type": "json_object"},
            messages=messages,
        )
        total_latency_ms += (time.perf_counter() - t0) * 1000.0
        usage = getattr(completion, "usage", None)
        if usage is not None:
            prompt_tokens = (prompt_tokens or 0) + int(getattr(usage, "prompt_tokens", 0) or 0)
            completion_tokens = (completion_tokens or 0) + int(getattr(usage, "completion_tokens", 0) or 0)
            total_tokens = (total_tokens or 0) + int(getattr(usage, "total_tokens", 0) or 0)
        last_content = completion.choices[0].message.content or "{}"
        try:
            data = _parse_llm_json_object(last_content)
        except ValueError as exc:
            if attempt == 0:
                retry_count += 1
                logger.warning(
                    "JSON 解析失败，将重试 1 次 API: eventId=%s | %s",
                    payload.eventId,
                    exc,
                )
                continue
            raise ValueError(f"{exc}（重试后仍失败）") from exc
        if attempt == 1:
            logger.info("JSON 解析在重试后成功: eventId=%s", payload.eventId)
        break

    if data is None:
        raise ValueError("模型未返回可解析的 JSON")

    return {
        "success": True,
        "experimentGroup": group,
        "ablationMode": payload.ablationMode.upper().strip() or "D0",
        "meta": {
            "latency_ms": round(total_latency_ms, 1),
            "prompt_tokens": prompt_tokens,
            "completion_tokens": completion_tokens,
            "total_tokens": total_tokens,
            "retry_count": retry_count,
            "prompt_chars": len(prompt),
            "model": MODEL,
            "promptVariant": prompt_variant,
        },
        "data": {
            "summary": data.get("summary", "未返回事故摘要"),
            "rootCause": data.get("rootCause", "未返回根因判断"),
            "comprehensiveAnalysis": data.get("comprehensiveAnalysis", ""),
            "scenarioReconstruction": data.get("scenarioReconstruction", ""),
            "confidenceStatement": data.get("confidenceStatement", ""),
            "evidencePoints": data.get("evidencePoints", []),
            "suggestions": data.get("suggestions", []),
            "modelHint": f"模型说明：{MODEL} (Group {group})",
            "rawText": data.get("rawText", last_content),
        },
    }


@app.post("/api/accident/analyze")
def analyze_accident(payload: AccidentAnalyzeRequest):
    if not API_KEYS or not OPENAI_CLIENTS:
        raise HTTPException(status_code=500, detail="OPENAI_API_KEY 未配置")

    try:
        return _analyze_payload(payload, OPENAI_CLIENTS[0])
    except Exception as exc:
        logger.error("POST /api/accident/analyze 失败: %s", exc, exc_info=True)
        raise HTTPException(status_code=500, detail=str(exc)) from exc


@app.post("/api/accident/analyze/batch")
def analyze_accident_batch(payloads: list[AccidentAnalyzeRequest]):
    if not API_KEYS or not OPENAI_CLIENTS:
        raise HTTPException(status_code=500, detail="OPENAI_API_KEY 未配置")
    if not payloads:
        return {"success": True, "count": 0, "results": []}

    total = len(payloads)
    n_keys = len(OPENAI_CLIENTS)
    results: list[dict | None] = [None] * total
    progress_lock = threading.Lock()
    completed = 0

    def run_one(index: int, payload: AccidentAnalyzeRequest) -> dict:
        # 按样本序号轮询 Key，分散到不同账号的配额
        oa_client = OPENAI_CLIENTS[index % n_keys]
        try:
            return {
                "requestIndex": index,
                "eventId": payload.eventId,
                "experimentGroup": payload.experimentGroup.upper(),
                "ablationMode": payload.ablationMode.upper().strip() or "D0",
                "apiKeySlot": index % n_keys,
                "rawResponse": _analyze_payload(payload, oa_client),
            }
        except Exception as exc:
            logger.error("批量分析第 %s 条失败: %s", index, exc, exc_info=True)
            return {
                "requestIndex": index,
                "eventId": payload.eventId,
                "experimentGroup": payload.experimentGroup.upper(),
                "ablationMode": payload.ablationMode.upper().strip() or "D0",
                "apiKeySlot": index % n_keys,
                "rawResponse": {
                    "success": False,
                    "error": str(exc),
                },
            }

    with ThreadPoolExecutor(max_workers=BATCH_CONCURRENCY) as executor:
        future_map = {
            executor.submit(run_one, index, payload): index
            for index, payload in enumerate(payloads)
        }
        for future in as_completed(future_map):
            index = future_map[future]
            results[index] = future.result()
            with progress_lock:
                completed += 1
                logger.info(
                    "批量分析进度 %s/%s（并发=%s，Key 数=%s）",
                    completed,
                    total,
                    BATCH_CONCURRENCY,
                    n_keys,
                )

    return {
        "success": True,
        "count": total,
        "concurrency": BATCH_CONCURRENCY,
        "api_key_slots": n_keys,
        "results": results,
    }
