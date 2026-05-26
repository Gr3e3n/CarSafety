#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
模拟事故 → 喂给大模型复盘 → 导出结果（区块链测试用）

流程：
  1. 用 backend/generate_experiment_samples.py 同套逻辑随机生成 N 条事故
     （8 类事故、遥测、环境、责任比例、决策链）
  2. 调用 backend 大模型分析（与 App「AI 事故分析」同一套 Prompt）
  3. 保存 cloud_experiment_results_<组>_<消融>_<时间>.json
  4. 可选导出 blockchain_upload_*.jsonl

【推荐】CLI 模式（不用先起 uvicorn，直接读 backend/.env 调 OpenAI）：
  cd D:\\Users\\Desktop\\sax_0522_1
  python blockchain_test_export/run_simulate_and_analyze.py --count 500

HTTP 模式（先另开终端启动 backend）：
  cd backend
  python -m uvicorn main:app --host 0.0.0.0 --port 8080

  python blockchain_test_export/run_simulate_and_analyze.py --count 500 --mode http
"""

from __future__ import annotations

import argparse
import json
import random
import sys
import time
from concurrent.futures import ThreadPoolExecutor, as_completed
from datetime import datetime, timezone
from pathlib import Path
from typing import Any, Dict, List

ROOT = Path(__file__).resolve().parents[1]
BACKEND = ROOT / "backend"
sys.path.insert(0, str(BACKEND))

from generate_experiment_samples import (  # noqa: E402
    SCENARIO_NAMES,
    TYPE_PLAN,
    generate_sample,
)


def utc_stamp() -> str:
    return datetime.now(timezone.utc).strftime("%Y%m%d_%H%M%SZ")


def generate_simulated_accidents(count: int, seed: int) -> List[Dict[str, Any]]:
    random.seed(seed)
    type_cycle: List[str] = []
    for etype, n in TYPE_PLAN:
        type_cycle.extend([etype] * max(1, n))
    samples: List[Dict[str, Any]] = []
    dt = datetime.now().strftime("%Y%m%d")
    for i in range(1, count + 1):
        etype = type_cycle[(i - 1) % len(type_cycle)]
        names = SCENARIO_NAMES[etype]
        scenario = names[(i - 1) % len(names)]
        if count > len(names):
            scenario = f"{scenario}（模拟#{i}）"
        s = generate_sample(i, etype, scenario)
        s["eventId"] = f"SIM-{dt}-{i:04d}"
        s["scenarioId"] = f"SIM-{etype}-{i:04d}"
        s["experimentGroup"] = "C"
        s["derivedSignals"] = s.get("derivedSignals") or {
            "reactionTimeMs": random.randint(420, 1400),
            "brakeRiseTimeMs": random.randint(280, 650),
            "aebDelayMs": random.randint(-1200, -200),
            "ttcAtBrakeSeconds": round(random.uniform(0.8, 3.2), 2),
            "brakeEffective": random.random() > 0.4,
            "maxSteerLast2sDeg": round(random.uniform(1.5, 9.0), 1),
            "driverTakeoverSummary": "模拟数据",
            "riskPredictionSummary": f"端侧风险模型：{etype}",
        }
        samples.append(s)
    return samples


def make_payload(sample: Dict[str, Any], group: str, ablation: str) -> Dict[str, Any]:
    p = dict(sample)
    p["experimentGroup"] = group
    p["ablationMode"] = ablation
    return p


def wrap_result(index: int, payload: Dict[str, Any], raw: Dict[str, Any], slot: int) -> Dict[str, Any]:
    return {
        "requestIndex": index,
        "eventId": payload.get("eventId", ""),
        "experimentGroup": payload.get("experimentGroup", "C").upper(),
        "ablationMode": payload.get("ablationMode", "D0").upper(),
        "apiKeySlot": slot,
        "rawResponse": raw,
    }


def run_cli_batch(
    samples: List[Dict[str, Any]],
    group: str,
    ablation: str,
    concurrency: int,
) -> List[Dict[str, Any]]:
    from main import BATCH_CONCURRENCY, OPENAI_CLIENTS, AccidentAnalyzeRequest, _analyze_payload  # noqa: WPS433

    if not OPENAI_CLIENTS:
        raise RuntimeError(
            "未配置 OPENAI_API_KEY。请在 backend 目录创建 .env，参考 backend/OPENAI使用说明.md"
        )

    workers = concurrency or BATCH_CONCURRENCY
    n_keys = len(OPENAI_CLIENTS)
    payloads = [AccidentAnalyzeRequest(**make_payload(s, group, ablation)) for s in samples]
    results: List[Dict[str, Any] | None] = [None] * len(payloads)

    def run_one(index: int, payload: AccidentAnalyzeRequest) -> Dict[str, Any]:
        client = OPENAI_CLIENTS[index % n_keys]
        try:
            raw = _analyze_payload(payload, client)
            return wrap_result(index, payload.model_dump(), raw, index % n_keys)
        except Exception as exc:
            return wrap_result(
                index,
                payload.model_dump(),
                {"success": False, "error": str(exc)},
                index % n_keys,
            )

    print(f"  CLI 调模型: {len(payloads)} 条 | 并发={workers} | Key数={n_keys}")
    with ThreadPoolExecutor(max_workers=workers) as executor:
        futures = {executor.submit(run_one, i, p): i for i, p in enumerate(payloads)}
        done = 0
        for future in as_completed(futures):
            idx = futures[future]
            results[idx] = future.result()
            done += 1
            if done % 10 == 0 or done == len(payloads):
                ok = sum(
                    1
                    for r in results
                    if r and (r.get("rawResponse") or {}).get("success")
                )
                print(f"  进度 {done}/{len(payloads)}（已成功 {ok}）")

    return [r for r in results if r is not None]


def run_http_batch(
    samples: List[Dict[str, Any]],
    group: str,
    ablation: str,
    base_url: str,
    timeout: float,
) -> List[Dict[str, Any]]:
    import httpx

    payloads = [make_payload(s, group, ablation) for s in samples]
    url = base_url.rstrip("/") + "/api/accident/analyze/batch"
    with httpx.Client(timeout=timeout, trust_env=False) as client:
        health = client.get(base_url.rstrip("/") + "/health")
        health.raise_for_status()
        info = health.json()
        if not info.get("openai_configured"):
            raise RuntimeError("后端 /health 显示 openai_configured=false，请检查 backend/.env")
        print(f"  后端 OK: model={info.get('model')} concurrency={info.get('batch_concurrency')}")
        resp = client.post(url, json=payloads)
        resp.raise_for_status()
        body = resp.json()
    return body.get("results") or []


def export_blockchain(samples: List[Dict[str, Any]], results: List[Dict[str, Any]], out_dir: Path, device_id: str) -> None:
    """合并模拟输入 + 模型输出 → 上链 JSONL。"""
    sys.path.insert(0, str(ROOT / "blockchain_test_export"))
    from build_full_blockchain_dataset import merge_record, to_blockchain_upload  # noqa: WPS433

    by_id = {s["eventId"]: s for s in samples}
    records = []
    for entry in results:
        eid = entry.get("eventId", "")
        records.append(merge_record(entry, by_id.get(eid), "simulate_live"))
    uploads = [to_blockchain_upload(r, device_id) for r in records]
    jsonl = out_dir / f"blockchain_upload_{len(uploads)}.jsonl"
    full = out_dir / f"dataset_full_{len(records)}.json"
    full.write_text(json.dumps(records, ensure_ascii=False, indent=2), encoding="utf-8")
    with jsonl.open("w", encoding="utf-8") as f:
        for u in uploads:
            f.write(json.dumps(u, ensure_ascii=False) + "\n")
    print(f"  区块链导出: {full.name} , {jsonl.name}")


def main() -> int:
    ap = argparse.ArgumentParser(description="模拟事故并调用大模型复盘")
    ap.add_argument("--count", type=int, default=50, help="生成事故条数，例如 500")
    ap.add_argument("--seed", type=int, default=20260523)
    ap.add_argument("--group", default="C", help="实验组，生产复盘用 C")
    ap.add_argument("--ablation", default="D0", help="消融，完整 Prompt 用 D0")
    ap.add_argument("--mode", choices=["cli", "http"], default="cli")
    ap.add_argument("--base-url", default="http://127.0.0.1:8080")
    ap.add_argument("--concurrency", type=int, default=0)
    ap.add_argument("--http-timeout", type=float, default=900.0)
    ap.add_argument("--out-dir", type=Path, default=None)
    ap.add_argument("--device-id", default="VEHTRUST_001")
    ap.add_argument("--skip-ai", action="store_true", help="只生成模拟事故，不调大模型")
    ap.add_argument("--no-blockchain-export", action="store_true")
    args = ap.parse_args()

    out_dir = args.out_dir or (ROOT / "blockchain_test_export" / "output" / f"simulate_{args.count}_{utc_stamp()}")
    out_dir.mkdir(parents=True, exist_ok=True)

    print(f"=== 1/3 模拟生成 {args.count} 条事故（8 类）===")
    samples = generate_simulated_accidents(args.count, args.seed)
    sim_path = out_dir / f"simulated_accidents_{args.count}.json"
    sim_path.write_text(json.dumps(samples, ensure_ascii=False, indent=2), encoding="utf-8")
    from collections import Counter

    print(f"  已保存: {sim_path}")
    print(f"  类型分布: {dict(Counter(s['eventType'] for s in samples))}")

    if args.skip_ai:
        print("  --skip-ai：跳过大模型，结束。")
        return 0

    group = args.group.upper()
    ablation = args.ablation.upper()
    print(f"\n=== 2/3 调用大模型复盘（组={group} 消融={ablation} 模式={args.mode}）===")
    print("  请确认 backend/.env 已配置 OPENAI_API_KEY（及可选 OPENAI_BASE_URL、OPENAI_MODEL）")
    t0 = time.perf_counter()
    try:
        if args.mode == "cli":
            results = run_cli_batch(samples, group, ablation, args.concurrency)
        else:
            results = run_http_batch(samples, group, ablation, args.base_url, args.http_timeout)
    except Exception as exc:
        print("失败:", exc, file=sys.stderr)
        if args.mode == "cli":
            print("提示: 检查 backend/.env ；或改用 --mode http 并先启动 uvicorn", file=sys.stderr)
        else:
            print("提示: cd backend && python -m uvicorn main:app --host 0.0.0.0 --port 8080", file=sys.stderr)
        return 1

    ok = sum(1 for r in results if (r.get("rawResponse") or {}).get("success"))
    result_path = out_dir / f"cloud_experiment_results_{group}_{ablation}_{utc_stamp()}.json"
    result_path.write_text(json.dumps(results, ensure_ascii=False, indent=2), encoding="utf-8")
    print(f"  完成: 成功 {ok}/{len(results)} | 耗时 {time.perf_counter() - t0:.1f}s")
    print(f"  模型结果: {result_path}")

    if not args.no_blockchain_export:
        print("\n=== 3/3 导出区块链上链包 ===")
        export_blockchain(samples, results, out_dir, args.device_id)

    print(f"\n全部输出目录: {out_dir.resolve()}")
    return 0 if ok == len(results) else 2


if __name__ == "__main__":
    raise SystemExit(main())
