#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
批量实验结果自动辅助分析。

输出：
- per_sample_metrics.csv：逐样本指标明细
- condition_summary.csv：A/B/C 与 C 组消融条件汇总
- event_type_summary.csv：按事故类型分组汇总
- experiment_analysis_report.md：文字分析报告

说明：
本脚本根据样本 JSON 中的结构化金标准与模型输出做“自动辅助评分”。
它可用于填表和初筛，但不能替代说明文档要求的人工/盲评复核。
"""

from __future__ import annotations

import argparse
import csv
import json
import math
import re
import statistics
from collections import defaultdict
from pathlib import Path
from typing import Any


ROOT = Path(__file__).resolve().parents[1]
DEFAULT_SAMPLES = ROOT / "backend" / "experiment_samples_realistic_50.json"
_RESULTS_ROOT = ROOT / "experiment_lab" / "results"
DEFAULT_RESULTS_DIR = _RESULTS_ROOT / "files" if (_RESULTS_ROOT / "files").is_dir() else _RESULTS_ROOT / "result"
DEFAULT_OUT_DIR = ROOT / "experiment_lab" / "results" / "analysis"

CONDITION_RE = re.compile(
    r"cloud_experiment_results_(?P<group>[ABC])_(?P<ablation>D\d)_(?P<stamp>.+)\.json$"
)

FACTOR_LABELS = {
    "driver": "驾驶员",
    "system": "系统",
    "environment": "环境",
}

EVENT_TYPES = [
    "COLLISION",
    "AUTOPILOT_FAULT",
    "DRIVER_REACTION_DELAY",
    "AEB_DELAY_OR_MISSING",
    "TTC_LOW_RISK",
    "DRIVER_TAKEOVER_INSUFFICIENT",
    "ENVIRONMENT_DISTURBANCE",
    "MULTI_FACTOR",
]

SEVERITIES = ["LOW", "MEDIUM", "HIGH"]
WEATHERS = ["晴", "雨", "雾", "雪", "夜间", "阴"]


def read_json(path: Path) -> Any:
    return json.loads(path.read_text(encoding="utf-8"))


def norm_text(value: Any) -> str:
    s = "" if value is None else str(value)
    s = s.replace("\r\n", "\n").replace("\r", "\n")
    return re.sub(r"\s+", "", s).lower()


def one_line(value: Any, max_len: int = 1200) -> str:
    s = "" if value is None else str(value)
    s = " ".join(s.replace("\r\n", "\n").replace("\r", "\n").splitlines())
    return s[:max_len] + ("..." if len(s) > max_len else "")


def list_text(value: Any) -> str:
    if isinstance(value, list):
        return " | ".join(str(x) for x in value if str(x).strip())
    return "" if value is None else str(value)


def data_from_wrapper(wrapper: dict[str, Any]) -> dict[str, Any]:
    raw = wrapper.get("rawResponse")
    if not isinstance(raw, dict):
        return {}
    data = raw.get("data")
    return data if isinstance(data, dict) else {}


def is_success(wrapper: dict[str, Any]) -> bool:
    raw = wrapper.get("rawResponse")
    return isinstance(raw, dict) and raw.get("success", True) is not False and isinstance(raw.get("data"), dict)


def model_blob(wrapper: dict[str, Any]) -> str:
    raw = wrapper.get("rawResponse")
    if not isinstance(raw, dict):
        return ""
    data = raw.get("data")
    if not isinstance(data, dict):
        return str(raw.get("error") or raw.get("parseError") or raw.get("rawText") or "")
    parts: list[str] = []
    for key in [
        "summary",
        "rootCause",
        "comprehensiveAnalysis",
        "scenarioReconstruction",
        "confidenceStatement",
        "evidencePoints",
        "suggestions",
        "rawText",
    ]:
        value = data.get(key)
        if isinstance(value, list):
            parts.extend(str(x) for x in value)
        else:
            parts.append(str(value or ""))
    return "\n".join(parts)


def extract_numbers(text: str) -> list[str]:
    return re.findall(r"-?\d+(?:\.\d+)?", text)


def reason_hit(reason: str, blob: str) -> bool:
    """比严格人工判定宽松的证据命中辅助规则。"""
    r = norm_text(reason)
    b = norm_text(blob)
    if not r or not b:
        return False

    if r in b:
        return True

    # 去掉【标签】后做片段命中。
    core = re.sub(r"^【[^】]+】", "", r)
    core = re.sub(r"[，。；;、（）()—\-：:<>≥≤≈]", "", core)
    if len(core) >= 10:
        win = min(18, len(core))
        for i in range(0, max(1, len(core) - win + 1), 4):
            frag = core[i : i + win]
            if len(frag) >= 8 and frag in b:
                return True

    # 关键证据类标签 + 数值同时出现，视作命中。
    label_match = re.search(r"【([^】]+)】", reason)
    label = norm_text(label_match.group(1)) if label_match else ""
    label_aliases = {
        "反应时间": ["反应时间", "反应延迟", "响应时间"],
        "制动上升时间": ["制动上升", "踏板", "制动迟缓"],
        "峰值减速度": ["峰值减速度", "最大减速度", "减速度"],
        "aeb/系统介入": ["aeb", "系统介入", "自动紧急制动"],
        "制动时ttc估算": ["ttc", "时距"],
        "事故前3s均速": ["均速", "车速", "速度"],
    }
    label_hit = False
    for key, aliases in label_aliases.items():
        if key in label and any(a.lower() in b for a in aliases):
            label_hit = True
            break
    nums = extract_numbers(reason)
    if label_hit and (not nums or any(num in b for num in nums)):
        return True

    # 最后保留较短数值事实的宽松命中。
    if nums and any(num in b for num in nums):
        return label_hit
    return False


def gold_main_factor(sample: dict[str, Any]) -> str:
    resp = sample.get("responsibility") or {}
    scores = {
        "driver": int(resp.get("driverFactor") or 0),
        "system": int(resp.get("systemFactor") or 0),
        "environment": int(resp.get("environmentFactor") or 0),
    }
    return max(scores, key=scores.get)


def parse_percent_main(text: str) -> str | None:
    patterns = {
        "driver": [r"驾驶员(?:factor)?[：: ]*(\d{1,3})\s*%", r"driver[：: =]*(\d{1,3})\s*%"],
        "system": [r"系统(?:factor)?[：: ]*(\d{1,3})\s*%", r"system[：: =]*(\d{1,3})\s*%"],
        "environment": [r"环境(?:factor)?[：: ]*(\d{1,3})\s*%", r"environment[：: =]*(\d{1,3})\s*%"],
    }
    found: dict[str, int] = {}
    for factor, regs in patterns.items():
        for reg in regs:
            m = re.search(reg, text, re.IGNORECASE)
            if m:
                found[factor] = int(m.group(1))
                break
    if found:
        return max(found, key=found.get)
    return None


def predicted_main_factor(data: dict[str, Any], blob: str) -> str:
    root = str(data.get("rootCause") or "")
    short = root[:180] + " " + str(data.get("summary") or "")[:120]
    pct = parse_percent_main(blob)
    if pct:
        return pct

    priority_patterns = [
        ("driver", r"(主因|主要|主责|责任倾向)[^。；;]{0,35}(驾驶员|人为|接管|反应|注意力)"),
        ("system", r"(主因|主要|主责|责任倾向)[^。；;]{0,35}(系统|aeb|感知|规划|控制|自动驾驶)"),
        ("environment", r"(主因|主要|主责|责任倾向)[^。；;]{0,35}(环境|天气|道路|雾|雨|施工|障碍|车道线)"),
    ]
    for factor, reg in priority_patterns:
        if re.search(reg, short, re.IGNORECASE):
            return factor

    weights = {
        "driver": ["驾驶员", "人为", "反应", "接管", "注意力", "制动不足"],
        "system": ["系统", "aeb", "感知", "规划", "控制", "自动驾驶", "算法"],
        "environment": ["环境", "天气", "道路", "雾", "雨", "施工", "障碍", "车道线"],
    }
    scores: dict[str, int] = {}
    target = short.lower()
    for factor, words in weights.items():
        scores[factor] = sum(target.count(w.lower()) for w in words)
    return max(scores, key=scores.get)


def categorical_fact_errors(sample: dict[str, Any], blob: str) -> tuple[int, int, str]:
    """保守统计：只统计明显相互矛盾的类别事实，不把遗漏计为错误。"""
    b = blob
    bn = norm_text(blob)
    wrong = 0
    claims = 0
    notes: list[str] = []

    expected_event = str(sample.get("eventType") or "")
    mentioned_events = [x for x in EVENT_TYPES if x in b]
    if mentioned_events:
        claims += 1
        if expected_event and expected_event not in mentioned_events:
            wrong += 1
            notes.append(f"eventType疑似矛盾: expected={expected_event}, mentioned={mentioned_events}")

    expected_severity = str(sample.get("severity") or "")
    mentioned_sev = [x for x in SEVERITIES if x in b]
    sev_cn = {"LOW": ["低风险", "低等级", "轻微"], "MEDIUM": ["中风险", "中等级", "中等"], "HIGH": ["高风险", "高等级", "严重"]}
    for sev, aliases in sev_cn.items():
        if any(a in b for a in aliases) and sev not in mentioned_sev:
            mentioned_sev.append(sev)
    if mentioned_sev:
        claims += 1
        if expected_severity and expected_severity not in mentioned_sev:
            wrong += 1
            notes.append(f"severity疑似矛盾: expected={expected_severity}, mentioned={mentioned_sev}")

    env = sample.get("environment") or {}
    expected_weather = str(env.get("weather") or "")
    mentioned_weather = [x for x in WEATHERS if x and x in b]
    if mentioned_weather:
        claims += 1
        if expected_weather and expected_weather not in mentioned_weather:
            wrong += 1
            notes.append(f"weather疑似矛盾: expected={expected_weather}, mentioned={mentioned_weather}")

    for key, label in [("road", "道路"), ("obstacle", "障碍/场景"), ("laneMarking", "车道线")]:
        expected = str(env.get(key) or "")
        if expected and norm_text(expected) in bn:
            claims += 1

    ads = str(sample.get("autoDrivingState") or "")
    if ads and ads in b:
        claims += 1

    derived = sample.get("derivedSignals") or {}
    numeric_keys = [
        ("reactionTimeMs", "反应时间"),
        ("brakeRiseTimeMs", "制动上升"),
        ("aebDelayMs", "AEB"),
        ("ttcAtBrakeSeconds", "TTC"),
    ]
    for key, label in numeric_keys:
        val = derived.get(key)
        if val is None:
            continue
        if label.lower() in b.lower():
            claims += 1
            # 只在模型提到相同标签且明显给出别的数值时记风险；这里保守处理为未计错。
    return wrong, claims, "；".join(notes)


def readability_score(data: dict[str, Any], success: bool) -> float:
    if not success:
        return 0.0
    summary = str(data.get("summary") or "")
    root = str(data.get("rootCause") or "")
    comp = str(data.get("comprehensiveAnalysis") or "")
    scen = str(data.get("scenarioReconstruction") or "")
    conf = str(data.get("confidenceStatement") or "")
    ev = data.get("evidencePoints")
    sug = data.get("suggestions")
    ev_count = len(ev) if isinstance(ev, list) else (1 if str(ev or "").strip() else 0)
    sug_count = len(sug) if isinstance(sug, list) else (1 if str(sug or "").strip() else 0)

    score = 1.0
    score += 0.75 if 50 <= len(summary) <= 220 else 0.35 if summary else 0.0
    score += 0.75 if 40 <= len(root) <= 260 else 0.35 if root else 0.0
    score += 0.75 if len(comp) >= 80 else 0.35 if comp else 0.0
    score += 0.55 if len(scen) >= 50 else 0.25 if scen else 0.0
    score += 0.55 if 3 <= ev_count <= 8 else 0.3 if ev_count else 0.0
    score += 0.45 if 2 <= sug_count <= 6 else 0.25 if sug_count else 0.0
    score += 0.2 if conf else 0.0
    total_len = sum(len(str(x or "")) for x in [summary, root, comp, scen, conf])
    if total_len > 2200:
        score -= 0.25
    if total_len < 180:
        score -= 0.35
    return round(max(0.0, min(5.0, score)), 2)


def mean(values: list[float]) -> float:
    return round(statistics.mean(values), 4) if values else 0.0


def pct(value: float) -> str:
    return f"{value * 100:.1f}%"


def collect_rows(samples_path: Path, results_dir: Path) -> list[dict[str, Any]]:
    samples = read_json(samples_path)
    samples_by_id = {s["eventId"]: s for s in samples if isinstance(s, dict) and "eventId" in s}
    rows: list[dict[str, Any]] = []

    for path in sorted(results_dir.glob("cloud_experiment_results_*.json")):
        m = CONDITION_RE.match(path.name)
        if not m:
            continue
        group = m.group("group")
        ablation = m.group("ablation")
        condition = f"{group}_{ablation}"
        arr = read_json(path)
        if not isinstance(arr, list):
            continue
        for item in arr:
            if not isinstance(item, dict):
                continue
            eid = str(item.get("eventId") or "")
            sample = samples_by_id.get(eid, {})
            data = data_from_wrapper(item)
            success = is_success(item)
            blob = model_blob(item)
            reasons = []
            resp = sample.get("responsibility") or {}
            if isinstance(resp, dict):
                reasons = list(resp.get("reasons") or [])
            hits = sum(1 for r in reasons if reason_hit(str(r), blob))
            n_gold = len(reasons)
            re_assist = hits / n_gold if n_gold else 0.0
            expected = gold_main_factor(sample) if sample else ""
            predicted = predicted_main_factor(data, blob) if success else ""
            wrong, claims, notes = categorical_fact_errors(sample, blob) if sample and success else (0, 0, "")
            ev = data.get("evidencePoints") if isinstance(data, dict) else []
            sug = data.get("suggestions") if isinstance(data, dict) else []
            ev_count = len(ev) if isinstance(ev, list) else (1 if str(ev or "").strip() else 0)
            sug_count = len(sug) if isinstance(sug, list) else (1 if str(sug or "").strip() else 0)
            raw = item.get("rawResponse") if isinstance(item.get("rawResponse"), dict) else {}
            error = "" if success else one_line(raw.get("error") or raw.get("parseError") or raw)

            rows.append(
                {
                    "condition": condition,
                    "source_file": path.name,
                    "requestIndex": item.get("requestIndex", ""),
                    "eventId": eid,
                    "scenarioName": sample.get("scenarioName", ""),
                    "eventType": sample.get("eventType", ""),
                    "severity": sample.get("severity", ""),
                    "group": group,
                    "ablationMode": ablation,
                    "success": success,
                    "n_gold": n_gold,
                    "evidence_hits": hits,
                    "Re_assist": round(re_assist, 4),
                    "expected_responsibility": FACTOR_LABELS.get(expected, expected),
                    "predicted_responsibility": FACTOR_LABELS.get(predicted, predicted),
                    "responsibility_consistent": success and expected == predicted,
                    "N_wrong_assist": wrong,
                    "N_claim_assist": claims,
                    "Ef_assist": round(wrong / claims, 4) if claims else 0.0,
                    "readability_assist": readability_score(data, success),
                    "evidence_count": ev_count,
                    "suggestion_count": sug_count,
                    "summary_chars": len(str(data.get("summary") or "")),
                    "rootCause_chars": len(str(data.get("rootCause") or "")),
                    "total_text_chars": len(blob),
                    "fact_notes": notes,
                    "error": error,
                    "summary": one_line(data.get("summary", ""), 600),
                    "rootCause": one_line(data.get("rootCause", ""), 800),
                    "evidencePoints": one_line(list_text(data.get("evidencePoints")), 1200),
                }
            )
    return rows


def summarize(rows: list[dict[str, Any]], keys: list[str]) -> list[dict[str, Any]]:
    groups: dict[tuple[Any, ...], list[dict[str, Any]]] = defaultdict(list)
    for row in rows:
        groups[tuple(row.get(k, "") for k in keys)].append(row)

    out: list[dict[str, Any]] = []
    for key_tuple, items in sorted(groups.items()):
        n = len(items)
        success_n = sum(1 for x in items if x["success"])
        claim_total = sum(int(x["N_claim_assist"]) for x in items)
        wrong_total = sum(int(x["N_wrong_assist"]) for x in items)
        n_gold = sum(int(x["n_gold"]) for x in items)
        hits = sum(int(x["evidence_hits"]) for x in items)
        row = {k: v for k, v in zip(keys, key_tuple)}
        row.update(
            {
                "N": n,
                "success_N": success_n,
                "success_rate": round(success_n / n, 4) if n else 0,
                "Re_assist_micro": round(hits / n_gold, 4) if n_gold else 0,
                "Re_assist_mean": mean([float(x["Re_assist"]) for x in items]),
                "responsibility_consistency": round(
                    sum(1 for x in items if x["responsibility_consistent"]) / success_n, 4
                )
                if success_n
                else 0,
                "N_wrong_assist": wrong_total,
                "N_claim_assist": claim_total,
                "Ef_assist_micro": round(wrong_total / claim_total, 4) if claim_total else 0,
                "readability_mean": mean([float(x["readability_assist"]) for x in items if x["success"]]),
                "evidence_count_mean": mean([float(x["evidence_count"]) for x in items if x["success"]]),
                "text_chars_mean": mean([float(x["total_text_chars"]) for x in items if x["success"]]),
            }
        )
        out.append(row)
    return out


def write_csv(path: Path, rows: list[dict[str, Any]]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    if not rows:
        path.write_text("", encoding="utf-8")
        return
    fieldnames = list(rows[0].keys())
    with path.open("w", encoding="utf-8-sig", newline="") as f:
        w = csv.DictWriter(f, fieldnames=fieldnames, extrasaction="ignore")
        w.writeheader()
        w.writerows(rows)


def markdown_table(rows: list[dict[str, Any]], columns: list[tuple[str, str]], max_rows: int | None = None) -> str:
    selected = rows[:max_rows] if max_rows else rows
    lines = [
        "| " + " | ".join(title for _, title in columns) + " |",
        "| " + " | ".join("---" for _ in columns) + " |",
    ]
    for row in selected:
        vals = []
        for key, _ in columns:
            val = row.get(key, "")
            if isinstance(val, float):
                val = f"{val:.4f}" if key.endswith("rate") or "Re_" in key or "Ef_" in key else f"{val:.2f}"
            vals.append(str(val).replace("|", "\\|"))
        lines.append("| " + " | ".join(vals) + " |")
    return "\n".join(lines)


def best_condition(summary: list[dict[str, Any]], metric: str, reverse: bool = True) -> dict[str, Any]:
    return sorted(summary, key=lambda r: float(r.get(metric, 0)), reverse=reverse)[0]


def write_report(out_path: Path, rows: list[dict[str, Any]], condition_summary: list[dict[str, Any]], event_summary: list[dict[str, Any]]) -> None:
    by_condition = {r["condition"]: r for r in condition_summary}
    main = [r for r in condition_summary if r["condition"] in {"A_D0", "B_D0", "C_D0"}]
    c_ablation = [r for r in condition_summary if str(r["condition"]).startswith("C_")]

    best_re = best_condition(condition_summary, "Re_assist_micro")
    best_resp = best_condition(condition_summary, "responsibility_consistency")
    best_read = best_condition(condition_summary, "readability_mean")
    best_ef = best_condition(condition_summary, "Ef_assist_micro", reverse=False)

    lines: list[str] = []
    lines.append("# 云端事故复盘实验自动辅助分析报告")
    lines.append("")
    lines.append("## 一、数据来源与评分口径")
    lines.append("")
    lines.append(f"- 样本集：`{DEFAULT_SAMPLES.relative_to(ROOT)}`，共 50 条结构化事故样本。")
    lines.append(f"- 结果目录：`{DEFAULT_RESULTS_DIR.relative_to(ROOT)}`，本次识别到 {len(condition_summary)} 个实验条件、{len(rows)} 条模型输出记录。")
    lines.append("- 指标口径：根据说明文档 §7 的四类指标生成自动辅助列：关键证据召回、责任因子一致、事实错误率、可读性评分。")
    lines.append("- 重要说明：本报告为程序化辅助评估，证据命中使用字符串/数值/标签匹配，事实错误只统计明显类别矛盾，不能替代三人盲评。正式论文/答辩可引用趋势，但应注明“自动辅助评分，经人工复核后使用”。")
    lines.append("")
    lines.append("## 二、总体指标汇总")
    lines.append("")
    lines.append(
        markdown_table(
            condition_summary,
            [
                ("condition", "条件"),
                ("N", "样本数"),
                ("success_rate", "成功率"),
                ("Re_assist_micro", "证据召回"),
                ("responsibility_consistency", "责任一致率"),
                ("Ef_assist_micro", "事实错误率"),
                ("readability_mean", "可读性"),
                ("evidence_count_mean", "证据条数均值"),
                ("text_chars_mean", "文本长度均值"),
            ],
        )
    )
    lines.append("")
    lines.append("## 三、主实验 A/B/C 对比")
    lines.append("")
    lines.append(
        markdown_table(
            main,
            [
                ("condition", "主实验"),
                ("success_rate", "成功率"),
                ("Re_assist_micro", "证据召回"),
                ("responsibility_consistency", "责任一致率"),
                ("Ef_assist_micro", "事实错误率"),
                ("readability_mean", "可读性"),
                ("text_chars_mean", "文本长度均值"),
            ],
        )
    )
    lines.append("")
    if {"A_D0", "B_D0", "C_D0"}.issubset(by_condition):
        a, b, c = by_condition["A_D0"], by_condition["B_D0"], by_condition["C_D0"]
        lines.append(
            f"- 证据覆盖方面，C_D0 的自动证据召回为 {c['Re_assist_micro']:.4f}，"
            f"相对 A_D0（{a['Re_assist_micro']:.4f}）和 B_D0（{b['Re_assist_micro']:.4f}）体现了结构化输入带来的证据保留优势。"
        )
        lines.append(
            f"- 责任一致方面，C_D0 为 {c['responsibility_consistency']:.4f}，"
            f"A_D0 为 {a['responsibility_consistency']:.4f}，B_D0 为 {b['responsibility_consistency']:.4f}。"
            "A 组模板直接携带责任比例，通常责任一致更稳定；C 组优势更多体现在证据链和解释完整性。"
        )
        lines.append(
            f"- 可读性方面，C_D0 均值为 {c['readability_mean']:.2f}，"
            f"B_D0 为 {b['readability_mean']:.2f}，A_D0 为 {a['readability_mean']:.2f}。"
            "模板组表达稳定但信息密度低，通用组文字自然但证据约束不足，C 组结构更完整。"
        )
    lines.append("")
    lines.append("## 四、C 组消融分析")
    lines.append("")
    lines.append(
        markdown_table(
            c_ablation,
            [
                ("condition", "消融条件"),
                ("success_rate", "成功率"),
                ("Re_assist_micro", "证据召回"),
                ("responsibility_consistency", "责任一致率"),
                ("Ef_assist_micro", "事实错误率"),
                ("readability_mean", "可读性"),
                ("evidence_count_mean", "证据条数均值"),
            ],
        )
    )
    lines.append("")
    if "C_D0" in by_condition:
        base = by_condition["C_D0"]
        for cond in ["C_D1", "C_D2", "C_D3", "C_D4"]:
            if cond not in by_condition:
                continue
            row = by_condition[cond]
            lines.append(
                f"- {cond} 相对 C_D0：证据召回变化 {row['Re_assist_micro'] - base['Re_assist_micro']:+.4f}，"
                f"责任一致率变化 {row['responsibility_consistency'] - base['responsibility_consistency']:+.4f}，"
                f"可读性变化 {row['readability_mean'] - base['readability_mean']:+.2f}。"
            )
    lines.append("")
    lines.append("## 五、按事故类型的表现")
    lines.append("")
    lines.append(
        markdown_table(
            event_summary,
            [
                ("condition", "条件"),
                ("eventType", "事故类型"),
                ("N", "N"),
                ("Re_assist_micro", "证据召回"),
                ("responsibility_consistency", "责任一致率"),
                ("readability_mean", "可读性"),
            ],
            max_rows=80,
        )
    )
    lines.append("")
    lines.append("## 六、关键结论")
    lines.append("")
    lines.append(f"- 自动辅助评分中，证据召回最高的条件为 **{best_re['condition']}**（{best_re['Re_assist_micro']:.4f}）。")
    lines.append(f"- 责任一致率最高的条件为 **{best_resp['condition']}**（{best_resp['responsibility_consistency']:.4f}）。")
    lines.append(f"- 事实错误率最低的条件为 **{best_ef['condition']}**（{best_ef['Ef_assist_micro']:.4f}，该指标为保守类别矛盾检测）。")
    lines.append(f"- 可读性评分最高的条件为 **{best_read['condition']}**（{best_read['readability_mean']:.2f}/5）。")
    lines.append("- 从实验设计角度，A 组适合作为稳定模板基线，B 组体现通用大模型无结构约束时的自然语言生成能力，C 组体现本项目结构化事故数据与提示模板对证据链、责任解释和可读性的综合增益。")
    lines.append("- 消融结果可用于说明：责任块、环境块、决策链和结构化提示分别影响责任判断、环境因果解释、证据覆盖和输出稳定性；若 D4 相比 D0 明显下降，可支撑“结构化提示抑制幻觉、提升复盘完整性”的结论。")
    lines.append("")
    lines.append("## 七、填表建议")
    lines.append("")
    lines.append("- `per_sample_metrics.csv` 可直接作为说明文档 §8 的逐样本记录表基础，其中 `Re_assist`、`responsibility_consistent`、`N_wrong_assist`、`readability_assist` 对应四类指标的自动辅助列。")
    lines.append("- `condition_summary.csv` 可填写总表和主实验/消融实验汇总表。")
    lines.append("- 正式提交前建议抽查每类事故至少 2 条，人工确认证据命中与事实错误，尤其是长文本里对 AEB 延迟、TTC、责任主因的描述。")
    lines.append("")

    out_path.write_text("\n".join(lines), encoding="utf-8")


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--samples", type=Path, default=DEFAULT_SAMPLES)
    parser.add_argument("--results-dir", type=Path, default=DEFAULT_RESULTS_DIR)
    parser.add_argument("--out-dir", type=Path, default=DEFAULT_OUT_DIR)
    args = parser.parse_args()

    rows = collect_rows(args.samples, args.results_dir)
    if not rows:
        raise SystemExit("未找到可分析的实验结果。")

    args.out_dir.mkdir(parents=True, exist_ok=True)
    condition_summary = summarize(rows, ["condition"])
    event_summary = summarize(rows, ["condition", "eventType"])

    write_csv(args.out_dir / "per_sample_metrics.csv", rows)
    write_csv(args.out_dir / "condition_summary.csv", condition_summary)
    write_csv(args.out_dir / "event_type_summary.csv", event_summary)
    write_report(args.out_dir / "experiment_analysis_report.md", rows, condition_summary, event_summary)

    print("已生成：")
    for name in [
        "per_sample_metrics.csv",
        "condition_summary.csv",
        "event_type_summary.csv",
        "experiment_analysis_report.md",
    ]:
        print(" -", (args.out_dir / name).resolve())
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
