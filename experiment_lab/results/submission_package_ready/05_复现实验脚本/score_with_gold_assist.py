#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
将「样本 JSON（含金标准 reasons）」与「App 批量结果 JSON」按 eventId 对齐，
输出 CSV：含模型摘要字段 + **辅助**证据命中（非说明文档最终 R_e）。

辅助规则：对每条 gold reason，取长度>=8 的滑动窗口子串（步长 4），若任一子串
出现在模型拼接文本中则该 reason 计为命中 1 次。此规则偏宽松，仅用于初筛。
"""

from __future__ import annotations

import argparse
import csv
import json
import sys
from pathlib import Path


def _model_blob(raw: dict) -> str:
    if not isinstance(raw, dict):
        return ""
    data = raw.get("data")
    if not isinstance(data, dict):
        return str(raw.get("rawText") or raw.get("parseError") or "")
    parts = [
        str(data.get("summary", "")),
        str(data.get("rootCause", "")),
    ]
    ev = data.get("evidencePoints")
    if isinstance(ev, list):
        parts.extend(str(x) for x in ev)
    else:
        parts.append(str(ev))
    return "\n".join(parts)


def _reason_hit(reason: str, blob: str) -> bool:
    r = reason.strip().replace("\r\n", "\n")
    if len(r) < 8:
        return r in blob if r else False
    win = 24
    step = 4
    for i in range(0, max(1, len(r) - win + 1), step):
        frag = r[i : i + win].strip()
        if len(frag) >= 8 and frag in blob:
            return True
    return r in blob


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("samples_json", type=Path, help="experiment_samples_realistic_50.json")
    ap.add_argument("batch_json", type=Path, help="cloud_experiment_results_*.json")
    ap.add_argument("out_csv", type=Path, nargs="?", help="输出 CSV")
    args = ap.parse_args()
    gold_path, batch_path = args.samples_json, args.batch_json
    if not gold_path.is_file() or not batch_path.is_file():
        print("样本或批量文件不存在", file=sys.stderr)
        return 1
    out_csv = args.out_csv or batch_path.with_suffix(".assist.csv")

    gold_list = json.loads(gold_path.read_text(encoding="utf-8"))
    gold_by_id = {g["eventId"]: g for g in gold_list if isinstance(g, dict) and "eventId" in g}

    batch = json.loads(batch_path.read_text(encoding="utf-8"))
    if not isinstance(batch, list):
        print("批量 JSON 根应为数组", file=sys.stderr)
        return 1

    rows: list[dict[str, object]] = []
    for w in batch:
        if not isinstance(w, dict):
            continue
        eid = w.get("eventId", "")
        raw = w.get("rawResponse")
        blob = _model_blob(raw) if isinstance(raw, dict) else ""
        g = gold_by_id.get(eid)
        reasons: list[str] = []
        if g and isinstance(g.get("responsibility"), dict):
            reasons = list(g["responsibility"].get("reasons") or [])
        n_gold = len(reasons)
        hits = sum(1 for rs in reasons if _reason_hit(rs, blob)) if n_gold else 0
        re_assist = round(hits / n_gold, 4) if n_gold else ""
        data = raw.get("data") if isinstance(raw, dict) else {}
        summary = str(data.get("summary", "")) if isinstance(data, dict) else ""
        rows.append(
            {
                "requestIndex": w.get("requestIndex", ""),
                "eventId": eid,
                "experimentGroup": w.get("experimentGroup", ""),
                "ablationMode": w.get("ablationMode", ""),
                "n_gold_reasons": n_gold,
                "assist_hits": hits,
                "Re_assist_only": re_assist,
                "gold_missing": "是" if g is None else "否",
                "summary_one_line": summary.replace("\n", " ")[:500],
            }
        )

    if not rows:
        print("批量结果中无有效条目", file=sys.stderr)
        return 1

    fieldnames = list(rows[0].keys())
    with out_csv.open("w", encoding="utf-8-sig", newline="") as f:
        w = csv.DictWriter(f, fieldnames=fieldnames)
        w.writeheader()
        for r in rows:
            w.writerow(r)
    print("已写入:", out_csv.resolve(), "行数:", len(rows))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
