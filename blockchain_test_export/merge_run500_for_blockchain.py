#!/usr/bin/env python3
"""
合并 500 条模拟样本 + 大模型复盘 JSON + 端侧严重度推断，导出单一上链包。

用法（在项目根目录）:
  python blockchain_test_export/merge_run500_for_blockchain.py ^
    --samples backend/experiment_samples_500.json ^
    --results blockchain_test_export/output/run_500_new/cloud_experiment_results_C_D0_20260522_190247Z.json ^
    --out-dir blockchain_test_export/output/run_500_new
"""
from __future__ import annotations

import argparse
import json
import sys
from datetime import datetime, timezone
from pathlib import Path
from typing import Any, Dict, List, Mapping

_SCRIPT_DIR = Path(__file__).resolve().parent
if str(_SCRIPT_DIR) not in sys.path:
    sys.path.insert(0, str(_SCRIPT_DIR))

from collision_severity_local import predict_collision_severity
from severity_public import to_public_severity

AI_FIELD_KEYS = [
    "summary",
    "rootCause",
    "comprehensiveAnalysis",
    "scenarioReconstruction",
    "confidenceStatement",
    "evidencePoints",
    "suggestions",
    "rawText",
]


def _s(v: Any) -> str:
    if v is None:
        return ""
    if isinstance(v, (dict, list)):
        return json.dumps(v, ensure_ascii=False)
    return str(v)


def _public_derived_signals(derived: Any) -> Any:
    if not isinstance(derived, dict):
        return derived
    out = dict(derived)
    rps = out.get("riskPredictionSummary")
    if isinstance(rps, str) and any(k in rps for k in ("模型", "端侧", "置信", "COLLISION")):
        out["riskPredictionSummary"] = "事故风险偏高，建议结合现场证据进一步复核。"
    return out


def merge_record(
    sample: Mapping[str, Any],
    cloud_entry: Mapping[str, Any],
    source_file: str,
) -> Dict[str, Any]:
    event_id = cloud_entry.get("eventId") or sample.get("eventId")
    raw = cloud_entry.get("rawResponse") or {}
    meta = raw.get("meta") or {}
    ai_data = raw.get("data") or {}
    group = cloud_entry.get("experimentGroup") or raw.get("experimentGroup") or "C"
    ablation = cloud_entry.get("ablationMode") or raw.get("ablationMode") or "D0"
    record_id = f"{event_id}__{group}__{ablation}__{cloud_entry.get('requestIndex', 0)}"
    severity = to_public_severity(predict_collision_severity(sample))

    return {
        "recordId": record_id,
        "eventId": event_id,
        "modelSuccess": bool(raw.get("success")),
        "originalAccident": {
            "eventType": sample.get("eventType"),
            "scenarioId": sample.get("scenarioId"),
            "scenarioName": sample.get("scenarioName"),
            "timeMillis": sample.get("timeMillis"),
            "location": sample.get("location"),
            "summary": sample.get("summary"),
            "triggerReasons": sample.get("triggerReasons"),
            "severityLabel": sample.get("severity"),
            "autoDrivingState": sample.get("autoDrivingState"),
            "telemetry": sample.get("telemetry"),
        },
        "environment": sample.get("environment") or {},
        "decisionTrace": sample.get("decisionTrace") or {},
        "derivedSignals": sample.get("derivedSignals") or {},
        "responsibility": sample.get("responsibility") or {},
        "severityAnalysis": severity,
        "aiAnalysis": {
            k: ai_data.get(
                k, "" if k not in ("evidencePoints", "suggestions") else []
            )
            for k in AI_FIELD_KEYS
        },
        **(
            {"aiError": raw.get("error") or raw.get("detail") or "unknown"}
            if not raw.get("success")
            else {}
        ),
    }


def to_blockchain_upload(record: Dict[str, Any], device_id: str) -> Dict[str, Any]:
    resp = record.get("responsibility") or {}
    env = record.get("environment") or {}
    dec = record.get("decisionTrace") or {}
    ai = record.get("aiAnalysis") or {}
    orig = record.get("originalAccident") or {}
    derived = record.get("derivedSignals") or {}
    sev = record.get("severityAnalysis") or {}

    data = {
        "recordId": record["recordId"],
        "eventId": record["eventId"],
        "timeMillis": _s(orig.get("timeMillis")),
        "location": _s(orig.get("location")),
        "summary": _s(ai.get("summary") or orig.get("summary")),
        "driverFactor": _s(resp.get("driverFactor")),
        "systemFactor": _s(resp.get("systemFactor")),
        "envFactor": _s(resp.get("environmentFactor")),
        "conclusion": _s(resp.get("conclusion")),
        "eventType": _s(orig.get("eventType")),
        "severityLabel": _s(orig.get("severityLabel")),
        "predictedSeverity": _s(sev.get("predictedSeverity")),
        "severityScore": _s(sev.get("severityScore")),
        "severitySummary": _s(sev.get("summary")),
        "severityHighlights": _s(sev.get("highlights")),
        "autoDrivingState": _s(orig.get("autoDrivingState")),
        "scenarioName": _s(orig.get("scenarioName")),
        "weather": _s(env.get("weather")),
        "road": _s(env.get("road")),
        "obstacle": _s(env.get("obstacle")),
        "laneMarking": _s(env.get("laneMarking")),
        "sensorInput": _s(dec.get("sensorInput")),
        "perception": _s(dec.get("perception")),
        "planning": _s(dec.get("planning")),
        "control": _s(dec.get("control")),
        "responsibilityReasons": _s(resp.get("reasons")),
        "telemetryJson": _s(orig.get("telemetry")),
        "derivedSignalsJson": _s(_public_derived_signals(derived)),
        "rootCause": _s(ai.get("rootCause")),
        "comprehensiveAnalysis": _s(ai.get("comprehensiveAnalysis")),
        "scenarioReconstruction": _s(ai.get("scenarioReconstruction")),
        "confidenceStatement": _s(ai.get("confidenceStatement")),
        "evidencePoints": _s(ai.get("evidencePoints")),
        "suggestions": _s(ai.get("suggestions")),
        "rawText": _s(ai.get("rawText")),
    }
    return {"deviceId": device_id, "data": data}


def main() -> None:
    parser = argparse.ArgumentParser(description="合并样本+AI+严重度，导出上链包")
    parser.add_argument("--samples", required=True, help="experiment_samples_500.json")
    parser.add_argument("--results", required=True, help="cloud_experiment_results_*.json")
    parser.add_argument("--out-dir", required=True, help="输出目录")
    parser.add_argument("--device-id", default="veh-trust-lab-500", help="链上 deviceId")
    args = parser.parse_args()

    samples_path = Path(args.samples)
    results_path = Path(args.results)
    out_dir = Path(args.out_dir)
    out_dir.mkdir(parents=True, exist_ok=True)

    with samples_path.open(encoding="utf-8") as f:
        samples_list = json.load(f)
    sample_by_id = {s["eventId"]: s for s in samples_list}

    with results_path.open(encoding="utf-8") as f:
        cloud_list = json.load(f)

    records: List[Dict[str, Any]] = []
    missing: List[str] = []
    for entry in cloud_list:
        eid = entry.get("eventId")
        sample = sample_by_id.get(eid)
        if not sample:
            missing.append(str(eid))
            continue
        records.append(
            merge_record(sample, entry, results_path.name)
        )

    records.sort(key=lambda r: r.get("eventId", ""))
    uploads = [to_blockchain_upload(r, args.device_id) for r in records]

    ts = datetime.now(timezone.utc).strftime("%Y%m%d_%H%M%SZ")
    full_path = out_dir / f"blockchain_full_records_500_{ts}.json"
    upload_json_path = out_dir / f"blockchain_upload_500_{ts}.json"
    upload_jsonl_path = out_dir / f"blockchain_upload_500_{ts}.jsonl"
    readme_path = out_dir / f"blockchain_bundle_README_{ts}.txt"

    bundle = {
        "generatedAt": ts,
        "count": len(records),
        "samplesFile": str(samples_path),
        "resultsFile": str(results_path),
        "deviceId": args.device_id,
        "records": records,
        "blockchainUploads": uploads,
    }
    with full_path.open("w", encoding="utf-8") as f:
        json.dump(bundle, f, ensure_ascii=False, indent=2)

    with upload_json_path.open("w", encoding="utf-8") as f:
        json.dump(uploads, f, ensure_ascii=False, indent=2)

    with upload_jsonl_path.open("w", encoding="utf-8") as f:
        for item in uploads:
            f.write(json.dumps(item, ensure_ascii=False) + "\n")

    readme_path.write_text(
        "\n".join(
            [
                f"生成时间(UTC): {ts}",
                f"完整合并包: {full_path.name} ({len(records)} 条，含原始+AI+严重度)",
                f"上链数组 JSON: {upload_json_path.name}",
                f"上链 JSONL（逐条 invoke）: {upload_jsonl_path.name}",
                f"deviceId: {args.device_id}",
                f"缺失样本 eventId: {len(missing)}",
            ]
        ),
        encoding="utf-8",
    )

    print(f"合并完成: {len(records)} 条")
    print(f"  完整包: {full_path}")
    print(f"  上链 JSON: {upload_json_path}")
    print(f"  上链 JSONL: {upload_jsonl_path}")
    if missing:
        print(f"  警告: {len(missing)} 条无对应样本")


if __name__ == "__main__":
    main()
