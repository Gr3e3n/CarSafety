#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
PC 端一键提示词实验：A/B/C 系统方法 + Baseline/P1～Final 阶梯 × 50 条 → JSON → 报告。

用法：
  python experiment_lab/run_prompt_experiment.py
  python experiment_lab/run_prompt_experiment.py --groups-method
  python experiment_lab/run_prompt_experiment.py --groups-prompt
  python experiment_lab/run_prompt_experiment.py --analyze-only --results-dir experiment_lab/results/run_xxx
"""

from __future__ import annotations

import argparse
import json
import sys
import time
from concurrent.futures import ThreadPoolExecutor, as_completed
from datetime import datetime, timezone
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
BACKEND_DIR = ROOT / "backend"
DEFAULT_SAMPLES = BACKEND_DIR / "experiment_samples_realistic_50.json"
DEFAULT_RESULTS_ROOT = ROOT / "experiment_lab" / "results"

GROUPS_METHOD = "A,B,C"
GROUPS_PROMPT = "BASELINE,P1,P2,P3,P4,P5,P6"
GROUPS_ALL = f"{GROUPS_METHOD},{GROUPS_PROMPT}"


def utc_stamp() -> str:
    return datetime.now(timezone.utc).strftime("%Y%m%d_%H%M%SZ")


def load_samples(path: Path) -> list[dict[str, Any]]:
    data = json.loads(path.read_text(encoding="utf-8"))
    if not isinstance(data, list):
        raise ValueError("样本 JSON 根应为数组")
    return [s for s in data if isinstance(s, dict) and s.get("eventId")]


def existing_group_file(results_dir: Path, group: str, ablation: str) -> Path | None:
    pattern = f"cloud_experiment_results_{group}_{ablation}_*.json"
    files = sorted(results_dir.glob(pattern), key=lambda p: p.stat().st_mtime, reverse=True)
    return files[0] if files else None


def make_payload(sample: dict[str, Any], group: str, ablation: str) -> dict[str, Any]:
    payload = dict(sample)
    payload["experimentGroup"] = group
    payload["ablationMode"] = ablation
    return payload


def _progress_bar(total: int, desc: str):
    try:
        from tqdm import tqdm

        return tqdm(total=total, desc=desc, unit="条", dynamic_ncols=True)
    except ImportError:
        return None


def _print_progress(done: int, total: int, ok: int, desc: str) -> None:
    width = 40
    filled = int(width * done / max(total, 1))
    bar = "█" * filled + "░" * (width - filled)
    print(f"\r{desc} |{bar}| {done}/{total} 成功{ok}", end="", flush=True)
    if done >= total:
        print()


def wrap_result(index: int, payload: dict[str, Any], raw_response: dict[str, Any], api_key_slot: int = 0) -> dict[str, Any]:
    return {
        "requestIndex": index,
        "eventId": payload.get("eventId", ""),
        "experimentGroup": payload.get("experimentGroup", "").upper(),
        "ablationMode": payload.get("ablationMode", "D0").upper(),
        "apiKeySlot": api_key_slot,
        "rawResponse": raw_response,
    }


def run_cli_single_requests(
    samples: list[dict[str, Any]],
    group: str,
    ablation: str,
    workers: int,
) -> list[dict[str, Any]]:
    """N 次独立分析（每条样本一次 _analyze_payload），并发 + 进度条。"""
    sys.path.insert(0, str(BACKEND_DIR))
    from main import BATCH_CONCURRENCY, OPENAI_CLIENTS, AccidentAnalyzeRequest, _analyze_payload  # noqa: WPS433

    if not OPENAI_CLIENTS:
        raise RuntimeError("OPENAI_API_KEY 未配置，请在 backend/.env 中设置")

    n_workers = workers or BATCH_CONCURRENCY
    n_keys = len(OPENAI_CLIENTS)
    payloads = [AccidentAnalyzeRequest(**make_payload(s, group, ablation)) for s in samples]
    results: list[dict[str, Any] | None] = [None] * len(payloads)
    total = len(payloads)
    desc = f"{group}_{ablation}"
    pbar = _progress_bar(total, desc)

    def run_one(index: int, payload: AccidentAnalyzeRequest) -> dict[str, Any]:
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

    done = 0
    ok_count = 0
    with ThreadPoolExecutor(max_workers=n_workers) as executor:
        futures = {executor.submit(run_one, i, p): i for i, p in enumerate(payloads)}
        for future in as_completed(futures):
            idx = futures[future]
            row = future.result()
            results[idx] = row
            done += 1
            if row.get("rawResponse", {}).get("success"):
                ok_count += 1
            if pbar is not None:
                pbar.update(1)
                pbar.set_postfix(ok=ok_count, fail=done - ok_count)
            else:
                _print_progress(done, total, ok_count, desc)
    if pbar is not None:
        pbar.close()
    return [r for r in results if r is not None]


def run_cli_group(
    samples: list[dict[str, Any]],
    group: str,
    ablation: str,
    concurrency: int,
) -> list[dict[str, Any]]:
    sys.path.insert(0, str(BACKEND_DIR))
    from main import (  # noqa: WPS433
        BATCH_CONCURRENCY,
        OPENAI_CLIENTS,
        AccidentAnalyzeRequest,
        _analyze_payload,
    )

    if not OPENAI_CLIENTS:
        raise RuntimeError("OPENAI_API_KEY 未配置，请在 backend/.env 中设置")

    workers = concurrency or BATCH_CONCURRENCY
    n_keys = len(OPENAI_CLIENTS)
    payloads = [AccidentAnalyzeRequest(**make_payload(s, group, ablation)) for s in samples]
    results: list[dict[str, Any] | None] = [None] * len(payloads)

    def run_one(index: int, payload: AccidentAnalyzeRequest) -> dict[str, Any]:
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

    executor = ThreadPoolExecutor(max_workers=workers)
    futures: dict[Any, int] = {}
    try:
        futures = {executor.submit(run_one, i, p): i for i, p in enumerate(payloads)}
        done = 0
        total = len(payloads)
        for future in as_completed(futures):
            idx = futures[future]
            results[idx] = future.result()
            done += 1
            if done % 5 == 0 or done == total:
                print(f"  [{group}_{ablation}] 进度 {done}/{total}")
    except KeyboardInterrupt:
        print(f"\n  [{group}_{ablation}] 收到中断信号，正在取消未完成任务…", flush=True)
        for future in futures:
            future.cancel()
        executor.shutdown(wait=False, cancel_futures=True)
        raise
    else:
        executor.shutdown(wait=True)

    return [r for r in results if r is not None]


def run_http_single_requests(
    samples: list[dict[str, Any]],
    group: str,
    ablation: str,
    base_url: str,
    timeout: float,
    workers: int,
) -> list[dict[str, Any]]:
    """
    与 App 一致：共 N 次 HTTP 请求，每次 POST /api/accident/analyze + 单条事故 JSON
    （非 /analyze/batch 一次塞 500 条）。默认多线程并发，带进度条。
    """
    import httpx

    base = base_url.rstrip("/")
    url = base + "/api/accident/analyze"
    bodies = [make_payload(s, group, ablation) for s in samples]
    total = len(bodies)
    results: list[dict[str, Any] | None] = [None] * total

    with httpx.Client(timeout=timeout, trust_env=False) as client:
        health = client.get(base + "/health")
        health.raise_for_status()
        info = health.json()
        if not info.get("openai_configured"):
            raise RuntimeError("后端未配置 OPENAI_API_KEY")
        n_workers = workers or int(info.get("batch_concurrency") or 4)
        print(
            f"  后端 OK: model={info.get('model')} | "
            f"{total} 个独立请求 → POST {url} | 并发={n_workers}"
        )

    desc = f"{group}_{ablation}"
    pbar = _progress_bar(total, desc)
    done = 0
    ok_count = 0

    def post_one(index: int, body: dict[str, Any]) -> dict[str, Any]:
        with httpx.Client(timeout=timeout, trust_env=False) as client:
            try:
                resp = client.post(url, json=body)
                resp.raise_for_status()
                raw = resp.json()
            except Exception as exc:
                raw = {"success": False, "error": str(exc)}
        return wrap_result(index, body, raw, 0)

    with ThreadPoolExecutor(max_workers=n_workers) as executor:
        futures = {executor.submit(post_one, i, b): i for i, b in enumerate(bodies)}
        for future in as_completed(futures):
            idx = futures[future]
            row = future.result()
            results[idx] = row
            done += 1
            if row.get("rawResponse", {}).get("success"):
                ok_count += 1
            if pbar is not None:
                pbar.update(1)
                pbar.set_postfix(ok=ok_count, fail=done - ok_count)
            else:
                _print_progress(done, total, ok_count, desc)
    if pbar is not None:
        pbar.close()
    return [r for r in results if r is not None]


def run_http_group(
    samples: list[dict[str, Any]],
    group: str,
    ablation: str,
    base_url: str,
    timeout: float,
) -> list[dict[str, Any]]:
    import httpx

    payloads = [make_payload(s, group, ablation) for s in samples]
    url = base_url.rstrip("/") + "/api/accident/analyze/batch"
    with httpx.Client(timeout=timeout, trust_env=False) as client:
        health = client.get(base_url.rstrip("/") + "/health")
        health.raise_for_status()
        info = health.json()
        if not info.get("openai_configured"):
            raise RuntimeError("后端未配置 OPENAI_API_KEY")
        print(f"  后端 OK: model={info.get('model')} concurrency={info.get('batch_concurrency')}")
        resp = client.post(url, json=payloads)
        resp.raise_for_status()
        body = resp.json()
    results = body.get("results") or []
    if len(results) != len(samples):
        print(f"  警告: 返回 {len(results)} 条，期望 {len(samples)} 条", file=sys.stderr)
    return results


def save_results(results: list[dict[str, Any]], out_dir: Path, group: str, ablation: str) -> Path:
    out_dir.mkdir(parents=True, exist_ok=True)
    out_file = out_dir / f"cloud_experiment_results_{group}_{ablation}_{utc_stamp()}.json"
    out_file.write_text(json.dumps(results, ensure_ascii=False, indent=2), encoding="utf-8")
    return out_file


def run_analysis(samples: Path, results_dir: Path, out_dir: Path) -> None:
    lab_dir = Path(__file__).resolve().parent
    if str(lab_dir) not in sys.path:
        sys.path.insert(0, str(lab_dir))
    from analyze_experiment_results import main as analyze_main  # noqa: WPS433

    argv = [
        "analyze_experiment_results.py",
        "--samples", str(samples),
        "--results-dir", str(results_dir),
        "--out-dir", str(out_dir),
    ]
    old_argv = sys.argv
    sys.argv = argv
    try:
        rc = analyze_main()
    finally:
        sys.argv = old_argv
    if rc != 0:
        raise RuntimeError(f"分析脚本退出码 {rc}")


def main() -> int:
    ap = argparse.ArgumentParser(description="PC 一键提示词工程实验（A/B/C + 阶梯 Prompt）")
    ap.add_argument("--mode", choices=["cli", "http"], default="http", help="http=经 uvicorn（与 App 一致）；cli=直连 OpenAI")
    ap.add_argument(
        "--batch-endpoint",
        action="store_true",
        help="使用 POST /api/accident/analyze/batch（一次提交全部样本）。默认：N 次独立 /analyze 请求",
    )
    ap.add_argument(
        "--sequential",
        action="store_true",
        help="（已废弃，等同默认）保留兼容",
    )
    ap.add_argument("--base-url", default="http://127.0.0.1:8080", help="HTTP 模式后端地址")
    ap.add_argument("--groups", default=GROUPS_ALL, help=f"实验组别，默认全量 10 组: {GROUPS_ALL}")
    ap.add_argument("--groups-method", action="store_true", help=f"仅跑系统方法对比: {GROUPS_METHOD}")
    ap.add_argument("--groups-prompt", action="store_true", help=f"仅跑提示词阶梯: {GROUPS_PROMPT}")
    ap.add_argument("--ablation", default="D0", help="消融模式")
    ap.add_argument("--samples", type=Path, default=DEFAULT_SAMPLES)
    ap.add_argument("--out-dir", type=Path, default=None, help="结果输出目录（默认 run_<timestamp>）")
    ap.add_argument(
        "--workers",
        "--concurrency",
        dest="workers",
        type=int,
        default=0,
        help="独立请求并发数，0=读后端 health 的 batch_concurrency",
    )
    ap.add_argument("--http-timeout", type=float, default=600.0)
    ap.add_argument("--skip-existing", action="store_true", help="目录已有同组 JSON 则跳过 API")
    ap.add_argument("--analyze-only", action="store_true", help="仅分析已有 JSON，不调 API")
    ap.add_argument("--skip-analysis", action="store_true", help="跑完 API 后不生成 analysis 统计报告")
    ap.add_argument("--results-dir", type=Path, default=None, help="--analyze-only 时指定结果目录")
    args = ap.parse_args()

    if args.groups_method:
        args.groups = GROUPS_METHOD
    elif args.groups_prompt:
        args.groups = GROUPS_PROMPT

    if args.analyze_only:
        results_dir = args.results_dir or args.out_dir or (DEFAULT_RESULTS_ROOT / "result")
        if not results_dir.is_dir():
            print("结果目录不存在:", results_dir, file=sys.stderr)
            return 1
        analysis_dir = results_dir / "analysis" if (results_dir / "analysis").is_dir() else results_dir.parent / "analysis"
        if args.out_dir:
            analysis_dir = args.out_dir / "analysis"
        analysis_dir.mkdir(parents=True, exist_ok=True)
        print("仅分析模式，结果目录:", results_dir.resolve())
        run_analysis(args.samples, results_dir, analysis_dir)
        return 0

    if not args.samples.is_file():
        print("样本文件不存在:", args.samples, file=sys.stderr)
        return 1

    out_dir = args.out_dir or (DEFAULT_RESULTS_ROOT / f"run_{utc_stamp()}")
    out_dir.mkdir(parents=True, exist_ok=True)
    groups = [g.strip().upper() for g in args.groups.split(",") if g.strip()]
    ablation = args.ablation.upper().strip() or "D0"

    samples = load_samples(args.samples)
    print(f"样本: {len(samples)} 条 | 组别: {groups} | 消融: {ablation}")
    print(f"输出目录: {out_dir.resolve()}")
    use_batch = args.batch_endpoint and not args.sequential
    api_style = "POST /analyze/batch（1 次）" if use_batch else f"POST /analyze ×{len(samples)}（独立请求）"
    print(f"模式: {args.mode} | {api_style} | workers={args.workers or '自动'}")

    saved_files: list[Path] = []
    t_all = time.perf_counter()

    try:
        for group in groups:
            if args.skip_existing:
                existing = existing_group_file(out_dir, group, ablation)
                if existing:
                    print(f"[{group}_{ablation}] 跳过（已存在 {existing.name}）")
                    saved_files.append(existing)
                    continue

            print(f"\n=== 开始 {group}_{ablation} ===")
            t0 = time.perf_counter()
            if args.mode == "cli":
                if use_batch:
                    print("  提示: CLI 无 /analyze/batch，仍按独立请求并发调模型", file=sys.stderr)
                results = run_cli_single_requests(samples, group, ablation, args.workers)
            else:
                results = (
                    run_http_group(samples, group, ablation, args.base_url, args.http_timeout)
                    if use_batch
                    else run_http_single_requests(
                        samples, group, ablation, args.base_url, args.http_timeout, args.workers
                    )
                )
            out_file = save_results(results, out_dir, group, ablation)
            ok = sum(1 for r in results if isinstance(r.get("rawResponse"), dict) and r["rawResponse"].get("success"))
            elapsed = time.perf_counter() - t0
            print(f"[{group}_{ablation}] 完成: 成功 {ok}/{len(results)} | 耗时 {elapsed:.1f}s | {out_file.name}")
            saved_files.append(out_file)
    except KeyboardInterrupt:
        print("\n\n=== 用户中断（Ctrl+C）===", file=sys.stderr)
        print(f"已完成组别已保存在: {out_dir.resolve()}", file=sys.stderr)
        if saved_files:
            print("已保存:", file=sys.stderr)
            for f in saved_files:
                print(f"  - {f.name}", file=sys.stderr)
        print("\n续跑示例（跳过已有 JSON，从中断处继续）:", file=sys.stderr)
        groups_arg = "--groups-method" if args.groups == GROUPS_METHOD else (
            "--groups-prompt" if args.groups == GROUPS_PROMPT else f'--groups "{args.groups}"'
        )
        print(
            f'  python experiment_lab/run_prompt_experiment.py {groups_arg} '
            f'--out-dir "{out_dir}" --skip-existing',
            file=sys.stderr,
        )
        return 130

    if not args.skip_analysis:
        analysis_dir = out_dir / "analysis"
        print(f"\n=== 自动分析 ===")
        run_analysis(args.samples, out_dir, analysis_dir)
    else:
        print("\n已跳过 analysis 统计（--skip-analysis）")

    total_elapsed = time.perf_counter() - t_all
    print(f"\n全部完成，总耗时 {total_elapsed:.1f}s")
    print("JSON 文件:")
    for f in saved_files:
        print(" -", f.resolve())
    if not args.skip_analysis:
        analysis_dir = (args.out_dir or (DEFAULT_RESULTS_ROOT / "result")) / "analysis"
        print("分析报告:")
        for name in [
            "unified_experiment_summary.csv",
            "full_prompt_comparison_report.md",
            "full_prompt_comparison.csv",
            "metric_leaderboard.csv",
            "prompt_ladder_report.md",
            "prompt_experiment_report.md",
            "experiment_analysis_report.md",
            "condition_summary.csv",
            "efficiency_summary.csv",
            "per_sample_metrics.csv",
        ]:
            p = analysis_dir / name
            if p.is_file():
                print(" -", p.resolve())
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
