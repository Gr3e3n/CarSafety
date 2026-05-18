#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""单次 cloud_experiment_results_*.json 健康度摘要（条数、成功率、字段长度）。"""

from __future__ import annotations

import argparse
import json
import statistics
import sys
from pathlib import Path


def main() -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("batch_json", type=Path)
    args = ap.parse_args()
    p = args.batch_json
    if not p.is_file():
        print("找不到文件:", p, file=sys.stderr)
        return 1
    arr = json.loads(p.read_text(encoding="utf-8"))
    if not isinstance(arr, list):
        print("根应为数组", file=sys.stderr)
        return 1
    n = len(arr)
    ok = 0
    lens: list[int] = []
    for w in arr:
        if not isinstance(w, dict):
            continue
        raw = w.get("rawResponse")
        if isinstance(raw, dict) and raw.get("success", True) and isinstance(raw.get("data"), dict):
            ok += 1
            data = raw["data"]
            ev = data.get("evidencePoints") or []
            if isinstance(ev, list):
                blob = " ".join(str(x) for x in ev)
            else:
                blob = str(ev)
            lens.append(len(blob))
    print("文件:", p.resolve())
    print("条数:", n)
    print("解析成功(粗):", ok, "/", n)
    if lens:
        print("evidencePoints 总字符数 — min/mean/max:", min(lens), round(statistics.mean(lens), 1), max(lens))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
