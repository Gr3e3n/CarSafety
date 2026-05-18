#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
将 App「批量跑 50 条」导出的 cloud_experiment_results_*.json 转为 CSV，便于用 Excel / WPS 做表。

用法（在 backend 目录或任意路径）:
  python export_batch_results_to_csv.py 路径/到/cloud_experiment_results_20260101_120000.json 输出.csv

说明:
  - 自动从每条记录的 rawResponse 里抽取 summary、rootCause、evidencePoints 等，方便粘贴到《实验结果记录》模板对应列旁对照。
  - R_e、责任一致、N_wrong、可读性 等需对照「金标准」人工或半人工填写，本脚本不自动判分（避免无金标准时误报）。
"""

from __future__ import annotations

import argparse
import csv
import json
import sys
from pathlib import Path


def _one_line(s: str, max_len: int = 8000) -> str:
    if not s:
        return ""
    t = " ".join(s.replace("\r\n", "\n").splitlines())
    return t[:max_len] + ("…" if len(t) > max_len else "")


def extract_row(wrapper: dict) -> dict:
    idx = wrapper.get("requestIndex", "")
    eid = wrapper.get("eventId", "")
    grp = wrapper.get("experimentGroup", "")
    abl = wrapper.get("ablationMode", "")
    raw = wrapper.get("rawResponse")
    if not isinstance(raw, dict):
        return {
            "requestIndex": idx,
            "eventId": eid,
            "experimentGroup": grp,
            "ablationMode": abl,
            "success": False,
            "summary": "",
            "rootCause": "",
            "evidencePoints": "",
            "modelHint": "",
            "error": _one_line(str(raw)),
        }
    ok = raw.get("success", True)
    data = raw.get("data") if isinstance(raw.get("data"), dict) else {}
    ev = data.get("evidencePoints") or []
    if isinstance(ev, list):
        ev_str = " | ".join(str(x) for x in ev if str(x).strip())
    else:
        ev_str = str(ev)
    err = raw.get("parseError") or raw.get("detail") or ""
    if raw.get("message"):
        err = (err + " " + str(raw.get("message"))).strip()
    return {
        "requestIndex": idx,
        "eventId": eid,
        "experimentGroup": grp,
        "ablationMode": abl,
        "success": ok,
        "summary": _one_line(str(data.get("summary", ""))),
        "rootCause": _one_line(str(data.get("rootCause", ""))),
        "evidencePoints": _one_line(ev_str, max_len=4000),
        "modelHint": _one_line(str(data.get("modelHint", ""))),
        "error": _one_line(str(err)),
    }


def main() -> int:
    ap = argparse.ArgumentParser(description="批量实验 JSON → CSV（便于 Excel 填表）")
    ap.add_argument("input_json", type=Path, help="cloud_experiment_results_*.json 路径")
    ap.add_argument("output_csv", type=Path, nargs="?", help="输出 CSV（默认同名 .csv）")
    args = ap.parse_args()
    inp = args.input_json
    if not inp.is_file():
        print("找不到文件:", inp, file=sys.stderr)
        return 1
    outp = args.output_csv
    if outp is None:
        outp = inp.with_suffix(".csv")

    text = inp.read_text(encoding="utf-8")
    arr = json.loads(text)
    if not isinstance(arr, list):
        print("JSON 根应为数组", file=sys.stderr)
        return 1

    fieldnames = [
        "requestIndex",
        "eventId",
        "experimentGroup",
        "ablationMode",
        "success",
        "summary",
        "rootCause",
        "evidencePoints",
        "modelHint",
        "error",
        "Re_人工填",
        "责任一致_人工填",
        "N_wrong_人工填",
        "N_claim_人工填",
        "可读性_人工填",
        "备注",
    ]

    with outp.open("w", encoding="utf-8-sig", newline="") as f:
        w = csv.DictWriter(f, fieldnames=fieldnames, extrasaction="ignore")
        w.writeheader()
        for item in arr:
            if not isinstance(item, dict):
                continue
            row = extract_row(item)
            row["Re_人工填"] = ""
            row["责任一致_人工填"] = ""
            row["N_wrong_人工填"] = ""
            row["N_claim_人工填"] = ""
            row["可读性_人工填"] = ""
            row["备注"] = ""
            w.writerow({**row, "success": "是" if row["success"] else "否"})

    print("已写入:", outp.resolve())
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
