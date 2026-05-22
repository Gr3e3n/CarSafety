# -*- coding: utf-8 -*-
"""
提示词工程阶梯实验：Baseline → P1 → … → P6 → Final（累积叠加）。

P 系列各组使用相同全量结构化输入，仅 Prompt 策略逐层增加。
Final 应与 main.build_prompt_c(ablation=D0) + C 组 system 等价。
"""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Protocol

BASE_DIR = Path(__file__).resolve().parent
FEWSHOT_PATH = BASE_DIR / "prompt_fewshot_example.json"

LADDER_VARIANTS = ["BASELINE", "P1", "P2", "P3", "P4", "P5", "P6", "FINAL"]

LADDER_ORDER = ["BASELINE", "P1", "P2", "P3", "P4", "P5", "P6", "FINAL"]

PROMPT_TYPE_LABELS: dict[str, str] = {
    "BASELINE": "普通Prompt",
    "P1": "Role Prompt",
    "P2": "Structured Prompt",
    "P3": "CoT Prompt",
    "P4": "Few-shot Prompt",
    "P5": "可信性约束 Prompt",
    "P6": "增量语义 Prompt",
    "FINAL": "完整版 Prompt",
}

SYSTEM_GENERIC = "你是一个通用助手，请根据用户提供的事故数据生成复盘结果。"

SYSTEM_ROLE = (
    "你是资深车载事故分析专家。请严格基于输入数据进行专业、谨慎、可解释的因果分析，"
    "不要虚构事实，输出结构化 JSON。若发现后文与前文语义重复，"
    "必须优先改写为新增证据、新增视角或新增不确定性说明。"
)

_JSON_FIELDS = (
    "summary, rootCause, comprehensiveAnalysis, scenarioReconstruction, "
    "confidenceStatement, evidencePoints, suggestions, modelHint, rawText"
)


class _PayloadLike(Protocol):
    eventId: str
    eventType: str
    timeMillis: int
    location: str
    summary: str
    triggerReasons: list[str]
    severity: str
    autoDrivingState: str
    telemetry: list[Any]
    responsibility: Any
    environment: Any
    decisionTrace: Any
    derivedSignals: Any


def is_ladder_variant(group: str) -> bool:
    return group.upper().strip() in LADDER_VARIANTS


def normalize_variant(group: str) -> str:
    g = group.upper().strip()
    if g == "FINAL":
        return "FINAL"
    if g in LADDER_VARIANTS:
        return g
    raise ValueError(f"未知提示词阶梯组别: {group}")


def _telemetry_lines(payload: _PayloadLike) -> str:
    return "\n".join(
        f"t={point.tMs}ms, speed={point.speedKph:.2f}km/h, ax={point.axMS2:.2f}m/s², "
        f"brake={point.brake}%, steer={point.steerDeg:.2f}°"
        for point in payload.telemetry[:20]
    )


def shared_input_blocks(payload: _PayloadLike) -> str:
    """P 系列固定全量结构化输入（与 C 组 D0 数据块一致）。"""
    derived_json = (
        payload.derivedSignals.model_dump_json(exclude_none=True)
        if payload.derivedSignals is not None and hasattr(payload.derivedSignals, "model_dump_json")
        else "null"
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

    resp = payload.responsibility
    resp_block = f"""
【责任分析】
conclusion={resp.conclusion}
driver={resp.driverFactor}%
system={resp.systemFactor}%
environment={resp.environmentFactor}%
reasons={' | '.join(resp.reasons)}
""".strip()

    env_block = f"【环境】\n{payload.environment.model_dump_json() if payload.environment else 'null'}"
    trace_block = f"【决策链】\n{payload.decisionTrace.model_dump_json() if payload.decisionTrace else 'null'}"
    tele_block = f"【事故前关键遥测】\n{_telemetry_lines(payload)}"
    derived_block = f"【端侧派生信号（TTC/AEB/制动/接管/严重度摘要）】\n{derived_json}"

    return "\n\n".join([event_block, resp_block, env_block, trace_block, tele_block, derived_block]).strip()


def _baseline_instruction() -> str:
    return f"请根据以下事故数据生成复盘 JSON，字段固定为：{_JSON_FIELDS}。只输出 JSON，不要 markdown。"


def _structured_rules_core() -> str:
    """Structured Prompt（P2+）：字段 1～9，不含 rawText 增量规则。"""
    return """
输出要求：
1) 只输出 JSON，不要 markdown，不要代码块。
2) 严格基于输入数据，不得虚构未提供的证据或结论。
3) summary：1 段，80~160 字，概括事件本质与主要风险。
4) rootCause：1 段，60~120 字，明确“主因 + 次因”。
5) comprehensiveAnalysis：2~3 段，分别从驾驶、系统、环境角度解释因果链。
6) scenarioReconstruction：1~2 段，按时间顺序复盘风险形成与碰撞过程。
7) confidenceStatement：1 段，说明置信度（高/中/低）及不确定性来源。
8) evidencePoints：3~6 条，简短证据点列表；须覆盖输入 reasons 关键证据。
9) suggestions：3~5 条，可执行改进建议或补充取证建议。
""".strip()


def _cot_rules() -> str:
    """CoT Prompt（P3+）：在 JSON 字段内体现分步推理。"""
    return """
推理要求（Chain-of-Thought，写入 JSON 字段内，不要输出 JSON 以外的文字）：
- comprehensiveAnalysis 必须按“感知→决策→控制→结果”分步写清因果链，每步引用输入中的具体证据。
- scenarioReconstruction 必须按时间阶段（如 T-3s / T-1s / T0）逐步叙述，体现先后关系。
- rootCause 在综合上述分步推理后再归纳，不得跳步下结论。
""".strip()


def _fewshot_block() -> str:
    """Few-shot Prompt（P4+）：合成示范，非测试集样本。"""
    if not FEWSHOT_PATH.is_file():
        return ""
    data = json.loads(FEWSHOT_PATH.read_text(encoding="utf-8"))
    example = data.get("output_example") or {}
    return f"""
【Few-shot 示范（合成样本，仅作格式与推理深度参考，勿照搬内容）】
输入摘要：{data.get('input_summary', '')}
输出 JSON 示范：
{json.dumps(example, ensure_ascii=False, indent=2)}
""".strip()


def _trust_rules() -> str:
    """可信性约束（P5+）。"""
    return """
可信性约束：
- 只能使用输入中已出现的事实、数值与标签，不得编造传感器读数、法规结论或未提供的第三方信息。
- 必须填写 confidenceStatement，明确置信度等级及不确定性来源。
- 若某关键证据缺失，须在 suggestions 或 confidenceStatement 中说明“信息不足 + 建议补采的数据”，不得猜测填补。
""".strip()


def _incremental_rawtext_rules() -> str:
    """增量语义 Prompt（P6+ / 创新点）。"""
    return """
rawText 增量语义要求：
10) rawText：3~5 段“增量叙述”，每段都必须提供前文未出现的新信息。
11) rawText 必须优先覆盖以下维度中未被前文覆盖的项：
   - 时间线细节（具体到阶段/先后关系）
   - 证据链闭环（某证据如何支持某结论）
   - 反事实或替代解释（若关键条件变化，结果可能如何变化）
   - 待补充取证点（缺失什么数据会影响结论）
12) rawText 严禁复述 summary/rootCause/comprehensiveAnalysis/scenarioReconstruction 的句子或近义改写。
13) 若输入证据不足，rawText 只写“信息不足点 + 建议补采数据”等内容，不要重复已有判断。
""".strip()


def _full_structured_header() -> str:
    return f"请基于以下事故数据生成“结构化专家报告”JSON，字段固定为：{_JSON_FIELDS}。"


def _variant_level(variant: str) -> int:
    v = normalize_variant(variant)
    if v == "FINAL":
        return len(LADDER_ORDER) - 1
    return LADDER_ORDER.index(v)


def full_output_rules() -> str:
    """与 main._structured_output_rules 一致，供 C 组 D0 与 FINAL 共用。"""
    return """
请基于以下事故数据生成“结构化专家报告”JSON，字段固定为：
summary, rootCause, comprehensiveAnalysis, scenarioReconstruction, confidenceStatement, evidencePoints, suggestions, modelHint, rawText。

输出要求：
1) 只输出 JSON，不要 markdown，不要代码块。
2) 严格基于输入数据，不得虚构未提供的证据或结论。
3) summary：1 段，80~160 字，概括事件本质与主要风险。
4) rootCause：1 段，60~120 字，明确“主因 + 次因”。
5) comprehensiveAnalysis：2~3 段，分别从驾驶、系统、环境角度解释因果链。
6) scenarioReconstruction：1~2 段，按时间顺序复盘风险形成与碰撞过程。
7) confidenceStatement：1 段，说明置信度（高/中/低）及不确定性来源。
8) evidencePoints：3~6 条，简短证据点列表；必须逐条覆盖输入【责任分析】reasons 中的关键证据（保留原标签如【反应时间】【TTC】【AEB】及对应数值），不得遗漏。
9) suggestions：3~5 条，可执行改进建议或补充取证建议。
9.1) rootCause 的主责任方向须与输入 responsibility 中百分比最高的一项一致（驾驶员/系统/环境），并在 comprehensiveAnalysis 中解释因果链。
9.2) evidencePoints 与 comprehensiveAnalysis 须引用【端侧派生信号】中的 TTC、AEB、制动、接管等关键指标（若输入已提供）。
10) rawText：3~5 段“增量叙述”，每段都必须提供前文未出现的新信息。
11) rawText 必须优先覆盖以下维度中未被前文覆盖的项：
   - 时间线细节（具体到阶段/先后关系）
   - 证据链闭环（某证据如何支持某结论）
   - 反事实或替代解释（若关键条件变化，结果可能如何变化）
   - 待补充取证点（缺失什么数据会影响结论）
12) rawText 严禁复述 summary/rootCause/comprehensiveAnalysis/scenarioReconstruction 的句子或近义改写。
13) 若输入证据不足，rawText 只写“信息不足点 + 建议补采数据”等内容，不要重复已有判断。
""".strip()


def build_production_user_prompt(payload: _PayloadLike) -> str:
    """生产版 user prompt（= C 组 D0 = FINAL）。"""
    return "\n\n".join([full_output_rules(), shared_input_blocks(payload)]).strip()


def build_prompt_variant(variant: str, payload: _PayloadLike) -> tuple[str, str]:
    """
    累积式构建 user_prompt 与 system_prompt。
    FINAL 与 build_production_user_prompt 等价（= C 组 D0）。
    """
    v = normalize_variant(variant)
    if v == "FINAL":
        return build_production_user_prompt(payload), SYSTEM_ROLE

    level = _variant_level(v)
    input_blocks = shared_input_blocks(payload)

    parts: list[str] = [_baseline_instruction()]
    if level >= _variant_level("P2"):
        parts[0] = _full_structured_header()

    if level >= _variant_level("P2"):
        parts.append(_structured_rules_core())
    if level >= _variant_level("P3"):
        parts.append(_cot_rules())
    if level >= _variant_level("P4"):
        fs = _fewshot_block()
        if fs:
            parts.append(fs)
    if level >= _variant_level("P5"):
        parts.append(_trust_rules())
    if level >= _variant_level("P6"):
        parts.append(_incremental_rawtext_rules())

    parts.append(input_blocks)
    parts.append("只输出 JSON。")

    user_prompt = "\n\n".join(p for p in parts if p).strip()
    system_prompt = SYSTEM_ROLE if level >= _variant_level("P1") else SYSTEM_GENERIC
    return user_prompt, system_prompt


def build_final_user_prompt(payload: _PayloadLike) -> str:
    """与 build_prompt_c(D0) 对齐的 user prompt（供等价性校验）。"""
    return build_production_user_prompt(payload)
