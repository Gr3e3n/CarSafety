#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
全量区块链测试数据集构建（含真实大模型复盘回复）

两种模式（二选一或组合）：

1) 默认【不调后端】— 推荐
   合并仓库内已有 cloud_experiment_results_*.json（约 1750 条，均为历史 API 跑出的模型回复）
   + experiment_samples_realistic_50.json 中的原始事故输入。

2) 【调后端】— 仅当需要「全新」模型回复时
   先启动 backend（uvicorn + .env 里 OPENAI_API_KEY），再加 --call-backend。
   会请求 POST /api/accident/analyze/batch，消耗大模型额度。

用法:
  python blockchain_test_export/build_full_blockchain_dataset.py
  python blockchain_test_export/build_full_blockchain_dataset.py --also-generate 500 --call-backend --backend-url http://127.0.0.1:8080
"""

from __future__ import annotations

import argparse
import csv
import json
import sys
import urllib.error
import urllib.request
from datetime import datetime
from pathlib import Path
from typing import Any, Dict, List, Optional

_ROOT = Path(__file__).resolve().parent.parent
sys.path.insert(0, str(_ROOT / "backend"))

SAMPLES_PATH = _ROOT / "backend" / "experiment_samples_realistic_50.json"
AI_FIELD_KEYS = [
    "summary",
    "rootCause",
    "comprehensiveAnalysis",
    "scenarioReconstruction",
    "confidenceStatement",
    "evidencePoints",
    "suggestions",
    "modelHint",
    "rawText",
]


def find_cloud_result_files() -> List[Path]:
    files = sorted(_ROOT.rglob("cloud_experiment_results_*.json"))
    # 排除明显重复目录时可在此过滤；当前全部合并以最大化条数
    return files


def load_samples_by_event_id() -> Dict[str, Dict[str, Any]]:
    if not SAMPLES_PATH.exists():
        return {}
    samples = json.loads(SAMPLES_PATH.read_text(encoding="utf-8"))
    return {s["eventId"]: s for s in samples if s.get("eventId")}


def sample_to_analyze_payload(sample: Dict[str, Any], group: str = "C", ablation: str = "D0") -> Dict[str, Any]:
    """转为 backend AccidentAnalyzeRequest JSON。"""
    payload = {
        "eventId": sample["eventId"],
        "eventType": sample["eventType"],
        "timeMillis": sample["timeMillis"],
        "location": sample["location"],
        "summary": sample["summary"],
        "triggerReasons": sample.get("triggerReasons", []),
        "severity": sample["severity"],
        "autoDrivingState": sample["autoDrivingState"],
        "telemetry": sample.get("telemetry", []),
        "responsibility": sample["responsibility"],
        "environment": sample.get("environment"),
        "decisionTrace": sample.get("decisionTrace"),
        "derivedSignals": sample.get("derivedSignals"),
        "experimentGroup": group,
        "ablationMode": ablation,
    }
    return payload


def merge_record(
    cloud_entry: Dict[str, Any],
    sample: Optional[Dict[str, Any]],
    source_file: str,
) -> Dict[str, Any]:
    event_id = cloud_entry.get("eventId", "")
    raw = cloud_entry.get("rawResponse") or {}
    ai_data = raw.get("data") or {}
    meta = raw.get("meta") or {}
    group = cloud_entry.get("experimentGroup") or raw.get("experimentGroup") or "C"
    ablation = cloud_entry.get("ablationMode") or raw.get("ablationMode") or "D0"

    # 唯一键：同 eventId 在不同实验组/文件会出现多次
    record_id = f"{event_id}__{group}__{ablation}__{cloud_entry.get('requestIndex', 0)}"

    base = sample or {}
    resp = base.get("responsibility") or {}
    env = base.get("environment") or {}
    dec = base.get("decisionTrace") or {}

    record: Dict[str, Any] = {
        "recordId": record_id,
        "eventId": event_id,
        "experimentGroup": group,
        "ablationMode": ablation,
        "sourceFile": source_file,
        "apiKeySlot": cloud_entry.get("apiKeySlot"),
        "modelSuccess": bool(raw.get("success")),
        # ── 原始事故输入（来自 50 条金样本或 cloud 同 id）──
        "originalAccident": {
            "eventType": base.get("eventType"),
            "scenarioId": base.get("scenarioId"),
            "scenarioName": base.get("scenarioName"),
            "timeMillis": base.get("timeMillis"),
            "location": base.get("location"),
            "summary": base.get("summary"),
            "triggerReasons": base.get("triggerReasons"),
            "severity": base.get("severity"),
            "autoDrivingState": base.get("autoDrivingState"),
            "telemetry": base.get("telemetry"),
        },
        "environment": env,
        "decisionTrace": dec,
        "derivedSignals": base.get("derivedSignals"),
        "responsibility": resp,
        # ── 大模型复盘输出（真实 API 历史结果）──
        "aiAnalysis": {
            k: ai_data.get(k, "" if k != "evidencePoints" and k != "suggestions" else [])
            for k in AI_FIELD_KEYS
        },
        "aiMeta": {
            "latency_ms": meta.get("latency_ms"),
            "prompt_tokens": meta.get("prompt_tokens"),
            "completion_tokens": meta.get("completion_tokens"),
            "total_tokens": meta.get("total_tokens"),
            "model": meta.get("model"),
            "retry_count": meta.get("retry_count"),
        },
    }
    if not raw.get("success"):
        record["aiError"] = raw.get("error") or raw.get("detail") or "unknown"
    return record


def to_blockchain_upload(record: Dict[str, Any], device_id: str) -> Dict[str, Any]:
    """链码 map[string]string：全部转字符串，字段尽量齐全。"""
    resp = record.get("responsibility") or {}
    env = record.get("environment") or {}
    dec = record.get("decisionTrace") or {}
    ai = record.get("aiAnalysis") or {}
    orig = record.get("originalAccident") or {}
    derived = record.get("derivedSignals") or {}

    def s(v: Any) -> str:
        if v is None:
            return ""
        if isinstance(v, (dict, list)):
            return json.dumps(v, ensure_ascii=False)
        return str(v)

    data = {
        "recordId": record["recordId"],
        "eventId": record["eventId"],
        "experimentGroup": record.get("experimentGroup", ""),
        "ablationMode": record.get("ablationMode", ""),
        "timeMillis": s(orig.get("timeMillis")),
        "location": s(orig.get("location")),
        "summary": s(ai.get("summary") or orig.get("summary")),
        "driverFactor": s(resp.get("driverFactor")),
        "systemFactor": s(resp.get("systemFactor")),
        "envFactor": s(resp.get("environmentFactor")),
        "conclusion": s(resp.get("conclusion")),
        "eventType": s(orig.get("eventType")),
        "severity": s(orig.get("severity")),
        "autoDrivingState": s(orig.get("autoDrivingState")),
        "scenarioName": s(orig.get("scenarioName")),
        "weather": s(env.get("weather")),
        "road": s(env.get("road")),
        "obstacle": s(env.get("obstacle")),
        "laneMarking": s(env.get("laneMarking")),
        "sensorInput": s(dec.get("sensorInput")),
        "perception": s(dec.get("perception")),
        "planning": s(dec.get("planning")),
        "control": s(dec.get("control")),
        "responsibilityReasons": s(resp.get("reasons")),
        "telemetryJson": s(orig.get("telemetry")),
        "derivedSignalsJson": s(derived),
        "rootCause": s(ai.get("rootCause")),
        "comprehensiveAnalysis": s(ai.get("comprehensiveAnalysis")),
        "scenarioReconstruction": s(ai.get("scenarioReconstruction")),
        "confidenceStatement": s(ai.get("confidenceStatement")),
        "evidencePoints": s(ai.get("evidencePoints")),
        "suggestions": s(ai.get("suggestions")),
        "modelHint": s(ai.get("modelHint")),
        "rawText": s(ai.get("rawText")),
        "aiModel": s((record.get("aiMeta") or {}).get("model")),
        "aiTotalTokens": s((record.get("aiMeta") or {}).get("total_tokens")),
        "sourceFile": s(record.get("sourceFile")),
    }
    return {"deviceId": device_id, "data": data}


def _ai_text_len(record: Dict[str, Any]) -> int:
    ai = record.get("aiAnalysis") or {}
    total = 0
    for k in ("comprehensiveAnalysis", "rawText", "scenarioReconstruction", "summary"):
        v = ai.get(k, "")
        if isinstance(v, str):
            total += len(v.strip())
    return total


def _is_full_ai_record(record: Dict[str, Any]) -> bool:
    if not record.get("modelSuccess"):
        return False
    ai = record.get("aiAnalysis") or {}
    ca = ai.get("comprehensiveAnalysis", "")
    rt = ai.get("rawText", "")
    if isinstance(ca, str) and len(ca.strip()) >= 80:
        return True
    if isinstance(rt, str) and len(rt.strip()) >= 80:
        return True
    return False


def _group_priority(record: Dict[str, Any]) -> int:
    g = (record.get("experimentGroup") or "").upper()
    if g == "C" or g == "FINAL":
        return 0
    if g.startswith("P") or g == "BASELINE":
        return 1
    if g == "B":
        return 2
    if g == "A":
        return 4
    return 3


def filter_and_limit_records(
    records: List[Dict[str, Any]],
    limit: int,
    require_full_ai: bool,
) -> List[Dict[str, Any]]:
    if require_full_ai:
        records = [r for r in records if _is_full_ai_record(r)]
    # C/P/BASELINE 长文复盘优先；同一事故在不同实验组可出现多条（共 1750 条池子）
    records.sort(key=lambda r: (_group_priority(r), -_ai_text_len(r)))
    if limit <= 0:
        return records
    return records[:limit]


def collect_from_cloud_files() -> List[Dict[str, Any]]:
    samples_map = load_samples_by_event_id()
    records: List[Dict[str, Any]] = []
    for path in find_cloud_result_files():
        rel = str(path.relative_to(_ROOT))
        try:
            entries = json.loads(path.read_text(encoding="utf-8"))
        except Exception:
            continue
        if not isinstance(entries, list):
            continue
        for entry in entries:
            eid = entry.get("eventId", "")
            sample = samples_map.get(eid)
            records.append(merge_record(entry, sample, rel))
    return records


def call_backend_batch(
    payloads: List[Dict[str, Any]],
    backend_url: str,
    timeout_sec: int = 600,
) -> List[Dict[str, Any]]:
    url = backend_url.rstrip("/") + "/api/accident/analyze/batch"
    body = json.dumps(payloads, ensure_ascii=False).encode("utf-8")
    req = urllib.request.Request(
        url,
        data=body,
        headers={"Content-Type": "application/json; charset=utf-8"},
        method="POST",
    )
    with urllib.request.urlopen(req, timeout=timeout_sec) as resp:
        result = json.loads(resp.read().decode("utf-8"))
    items = result.get("results") or []
    samples_map = {p["eventId"]: p for p in payloads}
    out: List[Dict[str, Any]] = []
    for entry in items:
        eid = entry.get("eventId", "")
        sample = samples_map.get(eid)
        out.append(merge_record(entry, sample, "backend_live_batch"))
    return out


def generate_extra_samples(count: int, seed: int) -> List[Dict[str, Any]]:
    from generate_experiment_samples import SCENARIO_NAMES, TYPE_PLAN, generate_sample  # noqa: E402
    import random

    random.seed(seed)
    type_cycle: List[str] = []
    for etype, n in TYPE_PLAN:
        type_cycle.extend([etype] * max(1, n))
    payloads = []
    for i in range(1, count + 1):
        etype = type_cycle[(i - 1) % len(type_cycle)]
        names = SCENARIO_NAMES[etype]
        name = names[(i - 1) % len(names)]
        raw = generate_sample(i, etype, name)
        dt = datetime.now().strftime("%Y%m%d")
        raw["eventId"] = f"LIVE-{dt}-{i:04d}"
        payloads.append(sample_to_analyze_payload(raw))
    return payloads


def write_csv_summary(path: Path, records: List[Dict[str, Any]]) -> None:
    fields = [
        "recordId",
        "eventId",
        "experimentGroup",
        "ablationMode",
        "driverFactor",
        "systemFactor",
        "environmentFactor",
        "aiModel",
        "aiSummaryPreview",
        "sourceFile",
    ]
    with path.open("w", encoding="utf-8-sig", newline="") as f:
        w = csv.DictWriter(f, fieldnames=fields)
        w.writeheader()
        for r in records:
            resp = r.get("responsibility") or {}
            ai = r.get("aiAnalysis") or {}
            preview = str(ai.get("summary", "")).replace("\n", " ")[:120]
            w.writerow(
                {
                    "recordId": r["recordId"],
                    "eventId": r["eventId"],
                    "experimentGroup": r.get("experimentGroup"),
                    "ablationMode": r.get("ablationMode"),
                    "driverFactor": resp.get("driverFactor"),
                    "systemFactor": resp.get("systemFactor"),
                    "environmentFactor": resp.get("environmentFactor"),
                    "aiModel": (r.get("aiMeta") or {}).get("model"),
                    "aiSummaryPreview": preview,
                    "sourceFile": r.get("sourceFile"),
                }
            )


def main() -> None:
    parser = argparse.ArgumentParser(description="全量数据集：原始事故 + 责任 + 大模型复盘 + 上链体")
    parser.add_argument("--device-id", default="VEHTRUST_001")
    parser.add_argument("--out-dir", type=Path, default=None)
    parser.add_argument(
        "--also-generate",
        type=int,
        default=0,
        help="额外生成 N 条并（可选）调后端拿模型回复",
    )
    parser.add_argument(
        "--call-backend",
        action="store_true",
        help="对 --also-generate 的样本调用 /api/accident/analyze/batch（需 backend+API Key）",
    )
    parser.add_argument("--backend-url", default="http://127.0.0.1:8080")
    parser.add_argument("--seed", type=int, default=20260522)
    parser.add_argument(
        "--limit",
        type=int,
        default=0,
        help="只导出 N 条（默认 0=全部）。配合 --require-full-ai 可导出 500 条完整大模型复盘",
    )
    parser.add_argument(
        "--require-full-ai",
        action="store_true",
        help="仅保留 comprehensiveAnalysis 或 rawText 足够长的真实大模型回复",
    )
    args = parser.parse_args()

    ts = datetime.now().strftime("%Y%m%d_%H%M%S")
    suffix = f"{args.limit}_ai" if args.limit and args.require_full_ai else (str(args.limit) if args.limit else "full")
    out_dir = args.out_dir or (_ROOT / "blockchain_test_export" / "output" / f"dataset_{suffix}_{ts}")
    out_dir.mkdir(parents=True, exist_ok=True)

    print("正在合并仓库内历史大模型实验结果（无需调后端）…")
    records = collect_from_cloud_files()
    print(f"  已合并 {len(records)} 条（含 aiAnalysis 全字段）")

    if args.also_generate > 0:
        print(f"正在生成额外 {args.also_generate} 条输入样本…")
        payloads = generate_extra_samples(args.also_generate, args.seed)
        if args.call_backend:
            print(f"正在调用后端 {args.backend_url} …（需 OPENAI_API_KEY）")
            try:
                live = call_backend_batch(payloads, args.backend_url)
                records.extend(live)
                print(f"  后端返回 {len(live)} 条")
            except urllib.error.URLError as e:
                print("后端调用失败:", e)
                print("提示: cd backend && uvicorn main:app --host 0.0.0.0 --port 8080")
                sys.exit(1)
        else:
            # 仅输入，无模型回复
            for p in payloads:
                records.append(
                    merge_record(
                        {
                            "eventId": p["eventId"],
                            "experimentGroup": p.get("experimentGroup", "C"),
                            "ablationMode": p.get("ablationMode", "D0"),
                            "requestIndex": 0,
                            "rawResponse": {"success": False, "error": "no_backend_call"},
                        },
                        p,
                        "generated_without_ai",
                    )
                )
            print(f"  已追加 {len(payloads)} 条（无模型回复；加 --call-backend 可拉取 AI）")

    if args.require_full_ai or args.limit:
        before = len(records)
        records = filter_and_limit_records(records, args.limit, args.require_full_ai)
        print(
            f"  筛选后 {len(records)} 条"
            + (f"（从 {before} 条中选取，require_full_ai={args.require_full_ai}）" if before != len(records) else "")
        )

    uploads = [to_blockchain_upload(r, args.device_id) for r in records]
    n = len(records)

    full_path = out_dir / f"dataset_full_{n}.json"
    upload_json = out_dir / f"blockchain_upload_{n}.json"
    jsonl_path = out_dir / f"blockchain_upload_{n}.jsonl"
    csv_path = out_dir / f"dataset_summary_{n}.csv"

    full_path.write_text(json.dumps(records, ensure_ascii=False, indent=2), encoding="utf-8")
    upload_json.write_text(json.dumps(uploads, ensure_ascii=False, indent=2), encoding="utf-8")
    with jsonl_path.open("w", encoding="utf-8") as f:
        for u in uploads:
            f.write(json.dumps(u, ensure_ascii=False) + "\n")
    write_csv_summary(csv_path, records)

    readme = out_dir / "README.txt"
    readme.write_text(
        "\n".join(
            [
                "VehTrust 全量区块链测试包（含大模型复盘）",
                f"条数: {n}",
                f"deviceId: {args.device_id}",
                "",
                "数据来源:",
                "  - 默认: 仓库 experiment_lab 下 cloud_experiment_results_*.json（真实历史 API 输出）",
                "  - 原始事故/责任/环境: experiment_samples_realistic_50.json 按 eventId 对齐",
                "",
                "是否要调后端:",
                "  - 本包默认 【不需要】，模型回复已来自历史实验 JSON",
                "  - 仅当需要全新回复时: 启动 backend + --call-backend --also-generate N",
                "",
                "文件:",
                f"  {full_path.name}  — 完整记录（originalAccident/responsibility/aiAnalysis/aiMeta）",
                f"  {upload_json.name} — 上链 POST 数组（含 rootCause/comprehensiveAnalysis/rawText 等）",
                f"  {jsonl_path.name} — 每行一条 POST",
                f"  {csv_path.name} — 摘要表",
                "",
                "批量上链:",
                f"  powershell -File ..\\batch_upload.ps1 -Jsonl {jsonl_path.name}",
            ]
        ),
        encoding="utf-8",
    )

    success_ai = sum(1 for r in records if r.get("modelSuccess"))
    print(f"\n输出目录: {out_dir}")
    print(f"  完整: {full_path.name}")
    print(f"  上链 JSONL: {jsonl_path.name}")
    print(f"  含成功模型回复: {success_ai} / {n}")


if __name__ == "__main__":
    main()
