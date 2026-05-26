#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
VehTrust 区块链批量上链测试数据生成器（独立脚本，不修改 App 业务代码）

基于 backend/generate_experiment_samples.py 与 Android BlockchainApi 上链字段对齐，
批量生成：完整事故记录 + 责任比例 + 环境/决策链 + 复盘全文 + 可直接 POST 的上链 JSON。

用法:
  python blockchain_test_export/generate_blockchain_bulk_data.py
  python blockchain_test_export/generate_blockchain_bulk_data.py --count 500
  python blockchain_test_export/generate_blockchain_bulk_data.py --count 200 --device-id VEHTRUST_001

输出目录（默认）: blockchain_test_export/output/<timestamp>/
"""

from __future__ import annotations

import argparse
import csv
import json
import random
import sys
from collections import Counter
from datetime import datetime, timezone
from pathlib import Path
from typing import Any, Dict, List

# 复用 backend 样本生成逻辑（与 App / 实验样本同源）
_ROOT = Path(__file__).resolve().parent.parent
sys.path.insert(0, str(_ROOT / "backend"))
from generate_experiment_samples import (  # noqa: E402
    SCENARIO_NAMES,
    TYPE_PLAN,
    generate_sample,
)


def build_comprehensive_analysis(sample: Dict[str, Any]) -> str:
    """生成与云端实验报告风格一致的事故复盘全文。"""
    env = sample["environment"]
    resp = sample["responsibility"]
    dec = sample["decisionTrace"]
    sig = sample.get("derivedSignals", {})
    tele = sample["telemetry"]
    tele_lines = [
        f"t={p['tMs']}ms, v={p['speedKph']}km/h, ax={p['axMS2']}m/s², "
        f"brake={p['brake']}%, steer={p['steerDeg']}°"
        for p in tele[:8]
    ]

    return "\n".join(
        [
            f"【事故复盘综合报告】{sample['eventId']}",
            "",
            "一、事故概述",
            f"类型：{sample['eventType']}（{sample.get('scenarioName', '')}）",
            f"等级：{sample['severity']} · 智驾状态：{sample['autoDrivingState']}",
            f"时间戳：{sample['timeMillis']} · 地点：{sample['location']}",
            sample["summary"],
            f"触发依据：{'；'.join(sample['triggerReasons'])}",
            "",
            "二、环境与道路场景",
            f"天气：{env['weather']}；道路形态：{env['road']}",
            f"关键障碍/交互：{env['obstacle']}；车道线：{env['laneMarking']}",
            "",
            "三、责任界定（人-车-环境）",
            f"驾驶员责任占比：{resp['driverFactor']}%",
            f"车辆/系统责任占比：{resp['systemFactor']}%",
            f"环境因素责任占比：{resp['environmentFactor']}%",
            f"结论：{resp['conclusion']}",
            "量化依据：",
            *[f"  · {r}" for r in resp["reasons"]],
            "",
            "四、关键派生信号（对齐端侧 ResponsibilityAnalyzer）",
            f"  · 反应时间：{sig.get('reactionTimeMs', '—')} ms",
            f"  · 制动上升时间：{sig.get('brakeRiseTimeMs', '—')} ms",
            f"  · AEB 介入时刻：{sig.get('aebDelayMs', '—')} ms（相对事故时刻）",
            f"  · 制动时 TTC：{sig.get('ttcAtBrakeSeconds', '—')} s",
            f"  · 制动有效性：{'有效' if sig.get('brakeEffective') else '不足'}",
            f"  · 接管摘要：{sig.get('driverTakeoverSummary', '—')}",
            "",
            "五、感知-规划-控制决策链",
            f"  · 传感输入：{dec['sensorInput']}",
            f"  · 感知融合：{dec['perception']}",
            f"  · 规划决策：{dec['planning']}",
            f"  · 控制执行：{dec['control']}",
            "",
            "六、事故前关键遥测（节选）",
            *[f"  · {line}" for line in tele_lines],
            "",
            "七、复盘结论与链上存证建议",
            "本次复盘基于 EDR 前后窗口遥测、责任分析器输出与环境/决策链快照生成。"
            "建议将 eventId、责任占比、结论摘要与 comprehensiveAnalysis 全文摘要哈希一并上链，"
            "便于保险定损、司法鉴定与跨机构溯源比对。",
            f"端侧风险提示：{sig.get('riskPredictionSummary', '—')}",
        ]
    )


def enrich_sample(sample: Dict[str, Any]) -> Dict[str, Any]:
    """附加复盘全文与原始事故结构化块（供同学侧扩展解析）。"""
    out = dict(sample)
    out["comprehensiveAnalysis"] = build_comprehensive_analysis(sample)
    out["originalAccident"] = {
        "eventId": sample["eventId"],
        "eventType": sample["eventType"],
        "scenarioName": sample.get("scenarioName"),
        "timeMillis": sample["timeMillis"],
        "location": sample["location"],
        "summary": sample["summary"],
        "severity": sample["severity"],
        "autoDrivingState": sample["autoDrivingState"],
        "triggerReasons": sample["triggerReasons"],
        "telemetry": sample["telemetry"],
    }
    return out


def to_blockchain_upload(sample: Dict[str, Any], device_id: str) -> Dict[str, Any]:
    """
    对齐 app/.../BlockchainApi.kt 的 POST 体，并在 data 中扩展字段（链码存 map[string]string，
    Go 网关会整包 JSON 序列化上链，便于测试方读取完整复盘）。
    """
    resp = sample["responsibility"]
    env = sample["environment"]
    dec = sample["decisionTrace"]

    def s(v: Any) -> str:
        if v is None:
            return ""
        if isinstance(v, (dict, list)):
            return json.dumps(v, ensure_ascii=False)
        return str(v)

    data = {
        # ── App 现有上链字段（字符串）──
        "eventId": sample["eventId"],
        "timeMillis": str(sample["timeMillis"]),
        "location": sample["location"],
        "summary": sample["summary"],
        "driverFactor": str(resp["driverFactor"]),
        "systemFactor": str(resp["systemFactor"]),
        "envFactor": str(resp["environmentFactor"]),
        "conclusion": resp["conclusion"],
        # ── 扩展字段（测试用，不影响 App）──
        "eventType": sample["eventType"],
        "severity": sample["severity"],
        "autoDrivingState": sample["autoDrivingState"],
        "scenarioName": sample.get("scenarioName", ""),
        "weather": env["weather"],
        "road": env["road"],
        "obstacle": env["obstacle"],
        "laneMarking": env["laneMarking"],
        "sensorInput": dec["sensorInput"],
        "perception": dec["perception"],
        "planning": dec["planning"],
        "control": dec["control"],
        "responsibilityReasons": s(resp["reasons"]),
        "telemetryJson": s(sample["telemetry"]),
        "comprehensiveAnalysis": sample["comprehensiveAnalysis"],
        "analysisHashHint": sample["eventId"] + "_" + str(sample["timeMillis"]),
    }
    return {"deviceId": device_id, "data": data}


def generate_bulk(count: int, seed: int) -> List[Dict[str, Any]]:
    random.seed(seed)
    type_cycle: List[str] = []
    for etype, n in TYPE_PLAN:
        type_cycle.extend([etype] * max(1, n))
    samples: List[Dict[str, Any]] = []
    for i in range(1, count + 1):
        etype = type_cycle[(i - 1) % len(type_cycle)]
        names = SCENARIO_NAMES[etype]
        base_name = names[(i - 1) % len(names)]
        scenario_name = base_name if count <= 50 else f"{base_name}（变体{i % 97 + 1}）"
        raw = generate_sample(i, etype, scenario_name)
        # 避免 ID 与 50 条实验样本冲突
        dt = datetime.now(timezone.utc).strftime("%Y%m%d")
        raw["eventId"] = f"CHAIN-{dt}-{i:04d}"
        raw["scenarioId"] = f"CHAIN-{etype}-{i:04d}"
        samples.append(enrich_sample(raw))
    return samples


def write_csv_flat(path: Path, samples: List[Dict[str, Any]]) -> None:
    fields = [
        "eventId",
        "eventType",
        "severity",
        "location",
        "timeMillis",
        "driverFactor",
        "systemFactor",
        "environmentFactor",
        "weather",
        "road",
        "obstacle",
        "conclusion",
        "summary",
        "comprehensiveAnalysis_preview",
    ]
    with path.open("w", encoding="utf-8-sig", newline="") as f:
        w = csv.DictWriter(f, fieldnames=fields)
        w.writeheader()
        for s in samples:
            resp = s["responsibility"]
            env = s["environment"]
            preview = s["comprehensiveAnalysis"].replace("\n", " ")[:200]
            w.writerow(
                {
                    "eventId": s["eventId"],
                    "eventType": s["eventType"],
                    "severity": s["severity"],
                    "location": s["location"],
                    "timeMillis": s["timeMillis"],
                    "driverFactor": resp["driverFactor"],
                    "systemFactor": resp["systemFactor"],
                    "environmentFactor": resp["environmentFactor"],
                    "weather": env["weather"],
                    "road": env["road"],
                    "obstacle": env["obstacle"],
                    "conclusion": resp["conclusion"],
                    "summary": s["summary"],
                    "comprehensiveAnalysis_preview": preview,
                }
            )


def main() -> None:
    parser = argparse.ArgumentParser(description="生成区块链批量上链测试数据")
    parser.add_argument("--count", type=int, default=300, help="生成条数，默认 300")
    parser.add_argument("--seed", type=int, default=20260522, help="随机种子")
    parser.add_argument("--device-id", default="VEHTRUST_001", help="上链 deviceId")
    parser.add_argument(
        "--out-dir",
        type=Path,
        default=None,
        help="输出目录，默认 blockchain_test_export/output/<时间戳>",
    )
    args = parser.parse_args()

    ts = datetime.now().strftime("%Y%m%d_%H%M%S")
    out_dir = args.out_dir or (_ROOT / "blockchain_test_export" / "output" / ts)
    out_dir.mkdir(parents=True, exist_ok=True)

    samples = generate_bulk(args.count, args.seed)
    uploads = [to_blockchain_upload(s, args.device_id) for s in samples]

    full_path = out_dir / f"accidents_full_{args.count}.json"
    upload_json_path = out_dir / f"blockchain_upload_{args.count}.json"
    jsonl_path = out_dir / f"blockchain_upload_{args.count}.jsonl"
    csv_path = out_dir / f"accidents_summary_{args.count}.csv"
    readme_path = out_dir / "README_使用说明.txt"

    full_path.write_text(json.dumps(samples, ensure_ascii=False, indent=2), encoding="utf-8")
    upload_json_path.write_text(json.dumps(uploads, ensure_ascii=False, indent=2), encoding="utf-8")
    with jsonl_path.open("w", encoding="utf-8") as f:
        for u in uploads:
            f.write(json.dumps(u, ensure_ascii=False) + "\n")
    write_csv_flat(csv_path, samples)

    readme_path.write_text(
        "\n".join(
            [
                "VehTrust 区块链批量测试数据",
                f"生成时间: {ts}",
                f"条数: {args.count}",
                f"deviceId: {args.device_id}",
                "",
                "文件说明:",
                f"  1. {full_path.name} — 完整事故（遥测/环境/责任/复盘全文）",
                f"  2. {upload_json_path.name} — 上链 POST 数组（与 App BlockchainApi 兼容并扩展字段）",
                f"  3. {jsonl_path.name} — 每行一条 POST JSON，适合脚本批量 curl",
                f"  4. {csv_path.name} — 表格预览（Excel 可打开）",
                "",
                "单条上链示例（网关默认 http://192.168.119.128:8080/upload）:",
                '  curl -X POST http://192.168.119.128:8080/upload ^',
                '    -H "Content-Type: application/json" ^',
                f'    -d @"{jsonl_path.name}"  REM 需取 jsonl 中单行',
                "",
                "批量上链: 在本目录执行",
                "  powershell -File ..\\batch_upload.ps1 -Jsonl .\\" + jsonl_path.name,
                "",
                "类型分布: " + str(dict(Counter(s["eventType"] for s in samples))),
            ]
        ),
        encoding="utf-8",
    )

    print(f"已生成 {args.count} 条 -> {out_dir}")
    print("  完整事故:", full_path.name)
    print("  上链 JSON:", upload_json_path.name)
    print("  上链 JSONL:", jsonl_path.name)
    print("  汇总 CSV:", csv_path.name)
    print("类型分布:", dict(Counter(s["eventType"] for s in samples)))


if __name__ == "__main__":
    main()
