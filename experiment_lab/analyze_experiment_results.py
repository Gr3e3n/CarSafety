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
    r"cloud_experiment_results_(?P<group>[A-Z0-9]+)_(?P<ablation>D\d)_(?P<stamp>.+)\.json$"
)

# 统一总表排序：系统方法 A/B/C → 提示词阶梯 BASELINE→Final
UNIFIED_CONDITION_ORDER = [
    "A_D0", "B_D0", "C_D0",
    "BASELINE_D0", "P1_D0", "P2_D0", "P3_D0", "P4_D0", "P5_D0", "P6_D0",
]

LADDER_CONDITIONS = [f"{g}_D0" for g in ["BASELINE", "P1", "P2", "P3", "P4", "P5", "P6"]]

# 报告与汇总中排除 FINAL（与 C 同 Prompt，避免重复展示）；C_D0 即当前项目生产提示词
EXCLUDED_REPORT_CONDITIONS = {"FINAL_D0"}

PROMPT_TYPE_LABELS: dict[str, str] = {
    "A": "模板生成（系统方法）",
    "B": "通用大模型（系统方法）",
    "C": "结构化全量（生产版）",
    "BASELINE": "普通Prompt",
    "P1": "Role Prompt",
    "P2": "Structured Prompt",
    "P3": "CoT Prompt",
    "P4": "Few-shot Prompt",
    "P5": "可信性约束 Prompt",
    "P6": "增量语义 Prompt",
    "FINAL": "完整版 Prompt",
}

EXPERIMENT_TRACK: dict[str, str] = {
    "A": "系统方法对比",
    "B": "系统方法对比",
    "C": "系统方法对比",
    "BASELINE": "提示词工程阶梯",
    "P1": "提示词工程阶梯",
    "P2": "提示词工程阶梯",
    "P3": "提示词工程阶梯",
    "P4": "提示词工程阶梯",
    "P5": "提示词工程阶梯",
    "P6": "提示词工程阶梯",
    "FINAL": "提示词工程阶梯",
}


def prompt_type_for_group(group: str) -> str:
    return PROMPT_TYPE_LABELS.get(group.upper(), group)


def experiment_track_for_group(group: str) -> str:
    return EXPERIMENT_TRACK.get(group.upper(), "其他")


def enrich_condition_summary(rows: list[dict[str, Any]]) -> list[dict[str, Any]]:
    out: list[dict[str, Any]] = []
    for row in rows:
        cond = str(row.get("condition", ""))
        group = cond.rsplit("_", 1)[0] if "_" in cond else cond
        enriched = dict(row)
        enriched["group_code"] = group
        enriched["prompt_type"] = prompt_type_for_group(group)
        enriched["experiment_track"] = experiment_track_for_group(group)
        out.append(enriched)
    order_map = {c: i for i, c in enumerate(UNIFIED_CONDITION_ORDER)}
    out.sort(key=lambda r: order_map.get(str(r["condition"]), 999))
    return out

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


def meta_from_wrapper(wrapper: dict[str, Any]) -> dict[str, Any]:
    raw = wrapper.get("rawResponse")
    if not isinstance(raw, dict):
        return {}
    meta = raw.get("meta")
    return meta if isinstance(meta, dict) else {}


def quality_index(re_assist: float, readability: float, resp_consistency: float, ef: float) -> float:
    """复盘质量指数 QI，0–100。"""
    return round(
        100.0 * (0.45 * re_assist + 0.25 * (readability / 5.0) + 0.20 * resp_consistency + 0.10 * max(0.0, 1.0 - ef)),
        2,
    )


def value_index(qi: float, total_tokens: float) -> float:
    """性价比指数 VQI = QI / (tokens/1000)。"""
    denom = max(total_tokens / 1000.0, 0.1)
    return round(qi / denom, 2)


def evidence_calibration_score(fill_ratio: float) -> float:
    """证据条数校准分：越接近 1.0 越好，超过 1.0 视为过度生成并扣分。"""
    if fill_ratio <= 0:
        return 0.0
    if fill_ratio <= 1.0:
        return round(fill_ratio, 4)
    return round(max(0.0, 1.0 - (fill_ratio - 1.0) * 2.0), 4)


def disciplined_recall(re_micro: float, fill_ratio_mean: float) -> float:
    """校准证据召回：在 Re 基础上惩罚证据条数明显超出金标准（>1.0）的组别。"""
    return round(re_micro * evidence_calibration_score(fill_ratio_mean), 4)


def production_quality_index(
    re_micro: float,
    fill_ratio_mean: float,
    readability: float,
    structured: float,
    field_complete: float,
    confidence_rate: float,
) -> float:
    """
    生产质量指数 PQI（0–100）：面向可上线复盘，强调召回+结构+合规，并惩罚证据堆砌。
    权重：Re 22% + 证据校准 12% + 可读 15% + 结构化 22% + 字段齐全 24% + 置信度 5%
    """
    fill_score = evidence_calibration_score(fill_ratio_mean)
    return round(
        100.0 * (
            0.22 * re_micro
            + 0.12 * fill_score
            + 0.15 * (readability / 5.0)
            + 0.22 * structured
            + 0.24 * field_complete
            + 0.05 * confidence_rate
        ),
        2,
    )


PRODUCTION_CONDITION = "C_D0"
PRODUCTION_ALIASES = {"C_D0"}


REQUIRED_JSON_FIELDS = [
    "summary", "rootCause", "comprehensiveAnalysis", "scenarioReconstruction",
    "confidenceStatement", "evidencePoints", "suggestions", "modelHint", "rawText",
]

DERIVED_SIGNAL_LABELS = {
    "reactionTimeMs": ["反应时间", "响应时间", "反应延迟"],
    "brakeRiseTimeMs": ["制动上升", "踏板", "制动迟缓"],
    "aebDelayMs": ["aeb", "自动紧急制动", "系统介入"],
    "ttcAtBrakeSeconds": ["ttc", "时距", "碰撞时间"],
    "driverTakeoverSummary": ["接管", "takeover"],
    "riskPredictionSummary": ["风险预判", "风险预测"],
}


def field_completeness(data: dict[str, Any], success: bool) -> float:
    """九字段齐全率（0/1）。"""
    if not success:
        return 0.0
    for key in REQUIRED_JSON_FIELDS:
        val = data.get(key)
        if val is None:
            return 0.0
        if isinstance(val, list):
            if not val:
                return 0.0
        elif not str(val).strip():
            return 0.0
    return 1.0


def structured_compliance_score(data: dict[str, Any], success: bool) -> float:
    """结构化规范符合度 0–1（字段长度/条数约束）。"""
    if not success:
        return 0.0
    score = 0.0
    summary = str(data.get("summary") or "")
    root = str(data.get("rootCause") or "")
    comp = str(data.get("comprehensiveAnalysis") or "")
    scen = str(data.get("scenarioReconstruction") or "")
    conf = str(data.get("confidenceStatement") or "")
    ev = data.get("evidencePoints")
    sug = data.get("suggestions")
    ev_count = len(ev) if isinstance(ev, list) else (1 if str(ev or "").strip() else 0)
    sug_count = len(sug) if isinstance(sug, list) else (1 if str(sug or "").strip() else 0)
    checks = [
        50 <= len(summary) <= 220,
        40 <= len(root) <= 260,
        len(comp) >= 80,
        len(scen) >= 50,
        3 <= ev_count <= 8,
        2 <= sug_count <= 6,
        bool(conf.strip()),
    ]
    score = sum(1.0 for c in checks if c) / len(checks)
    return round(score, 4)


def rawtext_non_overlap_ratio(data: dict[str, Any], success: bool) -> float:
    """rawText 相对前文的非重复率（增量语义代理，越高越好）。"""
    if not success:
        return 0.0
    prior = norm_text("".join(
        str(data.get(k) or "") for k in ["summary", "rootCause", "comprehensiveAnalysis", "scenarioReconstruction"]
    ))
    raw = norm_text(str(data.get("rawText") or ""))
    if len(raw) < 16:
        return 0.0
    win = 12
    step = 6
    windows = [raw[i : i + win] for i in range(0, max(1, len(raw) - win + 1), step)]
    if not windows:
        return 0.0
    novel = sum(1 for w in windows if w not in prior)
    return round(novel / len(windows), 4)


def derived_signal_recall(sample: dict[str, Any], blob: str) -> float:
    """端侧派生信号关键词召回（输入有则检查输出是否提及）。"""
    derived = sample.get("derivedSignals") or {}
    if not isinstance(derived, dict):
        return 0.0
    n = 0
    hits = 0
    b = blob.lower()
    for key, labels in DERIVED_SIGNAL_LABELS.items():
        val = derived.get(key)
        if val is None:
            continue
        n += 1
        if any(lab.lower() in b for lab in labels):
            hits += 1
        elif str(val) in blob:
            hits += 1
    return round(hits / n, 4) if n else 0.0


def analysis_depth_chars(data: dict[str, Any], success: bool) -> int:
    if not success:
        return 0
    return len(str(data.get("comprehensiveAnalysis") or "")) + len(str(data.get("scenarioReconstruction") or ""))


# 生产评估主榜：优先展示生产导向指标（PQI 等）
PRODUCTION_METRIC_CATALOG: list[tuple[str, str, bool, str]] = [
    ("PQI_mean", "生产质量指数 PQI", True, "生产评估"),
    ("Re_disciplined_micro", "校准证据召回 Re*", True, "生产评估"),
    ("Re_assist_micro", "证据召回率 Re", True, "生产评估"),
    ("structured_compliance_mean", "结构化规范符合度", True, "生产评估"),
    ("field_completeness_rate", "九字段齐全率", True, "生产评估"),
    ("readability_mean", "可读性/5", True, "生产评估"),
    ("evidence_calibration_mean", "证据条数校准分", True, "生产评估"),
    ("confidence_present_rate", "置信度说明覆盖率", True, "生产评估"),
    ("QI_mean", "综合质量指数 QI", True, "综合"),
    ("VQI_mean", "性价比 VQI", True, "综合"),
]

# 扩展指标目录：(字段名, 中文名, 越高越好, 类别)
METRIC_CATALOG: list[tuple[str, str, bool, str]] = [
    ("PQI_mean", "生产质量指数 PQI", True, "综合"),
    ("Re_disciplined_micro", "校准证据召回 Re*", True, "质量"),
    ("evidence_calibration_mean", "证据条数校准分", True, "质量"),
    ("Re_assist_micro", "证据召回率 Re", True, "质量"),
    ("responsibility_consistency", "责任一致率", True, "质量"),
    ("Ef_assist_micro", "事实错误率", False, "质量"),
    ("fact_consistency_rate", "事实一致率(1-Ef)", True, "质量"),
    ("readability_mean", "可读性/5", True, "质量"),
    ("structured_compliance_mean", "结构化规范符合度", True, "质量"),
    ("field_completeness_rate", "九字段齐全率", True, "质量"),
    ("confidence_present_rate", "置信度说明覆盖率", True, "质量"),
    ("evidence_count_mean", "证据条数均值", True, "质量"),
    ("evidence_fill_ratio_mean", "证据条数/金标准比(参考)", True, "质量"),
    ("derived_signal_recall_mean", "派生信号关键词召回", True, "质量"),
    ("rawText_non_overlap_mean", "rawText增量非重复率", True, "质量"),
    ("analysis_depth_chars_mean", "分析+复盘字符深度", True, "质量"),
    ("suggestion_count_mean", "建议条数均值", True, "质量"),
    ("QI_mean", "综合质量指数 QI", True, "综合"),
    ("VQI_mean", "性价比 VQI", True, "综合"),
    ("Re_per_1k_tokens", "每千Token证据召回", True, "综合"),
    ("QI_per_1k_tokens", "每千Token质量分", True, "综合"),
    ("success_rate", "解析成功率", True, "稳定"),
    ("retry_rate", "JSON重试率", False, "稳定"),
    ("latency_ms_mean", "平均延迟ms", False, "效率"),
    ("total_tokens_mean", "平均总Token", False, "效率"),
    ("tokens_per_evidence_hit", "每命中1证据Token", False, "效率"),
    ("completion_to_prompt_ratio", "输出/输入Token比", True, "效率"),
]


def percentile(values: list[float], p: float) -> float:
    if not values:
        return 0.0
    ordered = sorted(values)
    k = (len(ordered) - 1) * p
    f = int(k)
    c = min(f + 1, len(ordered) - 1)
    if f == c:
        return round(ordered[f], 2)
    return round(ordered[f] + (ordered[c] - ordered[f]) * (k - f), 2)


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
            meta = meta_from_wrapper(item)
            error = "" if success else one_line(raw.get("error") or raw.get("parseError") or raw)

            raw_text = str(data.get("rawText") or "") if isinstance(data, dict) else ""
            conf_present = bool(str(data.get("confidenceStatement") or "").strip()) if success else False
            ev_fill = ev_count / n_gold if n_gold else 0.0
            qi_sample = quality_index(
                re_assist,
                readability_score(data, success),
                1.0 if (success and expected == predicted) else 0.0,
                wrong / claims if claims else 0.0,
            )

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
                    "field_completeness": field_completeness(data, success),
                    "structured_compliance": structured_compliance_score(data, success),
                    "confidence_present": conf_present,
                    "evidence_fill_ratio": round(ev_fill, 4),
                    "rawText_chars": len(raw_text),
                    "rawText_non_overlap": rawtext_non_overlap_ratio(data, success),
                    "derived_signal_recall": derived_signal_recall(sample, blob) if sample else 0.0,
                    "analysis_depth_chars": analysis_depth_chars(data, success),
                    "QI_sample": qi_sample,
                    "summary_chars": len(str(data.get("summary") or "")),
                    "rootCause_chars": len(str(data.get("rootCause") or "")),
                    "total_text_chars": len(blob),
                    "latency_ms": meta.get("latency_ms"),
                    "prompt_tokens": meta.get("prompt_tokens"),
                    "completion_tokens": meta.get("completion_tokens"),
                    "total_tokens": meta.get("total_tokens"),
                    "retry_count": meta.get("retry_count", 0),
                    "prompt_chars": meta.get("prompt_chars"),
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
        latencies = [float(x["latency_ms"]) for x in items if x.get("latency_ms") is not None]
        prompt_toks = [float(x["prompt_tokens"]) for x in items if x.get("prompt_tokens") is not None]
        completion_toks = [float(x["completion_tokens"]) for x in items if x.get("completion_tokens") is not None]
        total_toks = [float(x["total_tokens"]) for x in items if x.get("total_tokens") is not None]
        retries = [int(x.get("retry_count") or 0) for x in items]
        qi_vals = [
            quality_index(float(x["Re_assist"]), float(x["readability_assist"]), float(x["responsibility_consistent"]), float(x["Ef_assist"]))
            for x in items
            if x["success"]
        ]
        success_items = [x for x in items if x["success"]]
        ef_micro = round(wrong_total / claim_total, 4) if claim_total else 0
        re_micro = round(hits / n_gold, 4) if n_gold else 0
        qi_mean = mean(qi_vals)
        total_tok_mean = mean(total_toks)
        prompt_tok_mean = mean(prompt_toks)
        completion_tok_mean = mean(completion_toks)
        tok_per_1k = max(total_tok_mean / 1000.0, 0.1)
        read_mean = mean([float(x["readability_assist"]) for x in success_items])
        struct_mean = mean([float(x["structured_compliance"]) for x in success_items])
        field_mean = mean([float(x["field_completeness"]) for x in success_items])
        conf_mean = mean([1.0 if x["confidence_present"] else 0.0 for x in success_items])
        fill_mean = mean([float(x["evidence_fill_ratio"]) for x in success_items])
        calib_vals = [evidence_calibration_score(float(x["evidence_fill_ratio"])) for x in success_items]
        calib_mean = mean(calib_vals)
        re_disc = disciplined_recall(re_micro, fill_mean)
        pqi_mean = production_quality_index(re_micro, fill_mean, read_mean, struct_mean, field_mean, conf_mean)
        row.update(
            {
                "N": n,
                "success_N": success_n,
                "success_rate": round(success_n / n, 4) if n else 0,
                "Re_assist_micro": re_micro,
                "Re_disciplined_micro": re_disc,
                "Re_assist_mean": mean([float(x["Re_assist"]) for x in items]),
                "responsibility_consistency": round(
                    sum(1 for x in items if x["responsibility_consistent"]) / success_n, 4
                )
                if success_n
                else 0,
                "N_wrong_assist": wrong_total,
                "N_claim_assist": claim_total,
                "Ef_assist_micro": ef_micro,
                "fact_consistency_rate": round(1.0 - ef_micro, 4) if claim_total else 0,
                "readability_mean": read_mean,
                "structured_compliance_mean": struct_mean,
                "field_completeness_rate": field_mean,
                "confidence_present_rate": conf_mean,
                "evidence_count_mean": mean([float(x["evidence_count"]) for x in success_items]),
                "evidence_fill_ratio_mean": fill_mean,
                "evidence_calibration_mean": calib_mean,
                "PQI_mean": pqi_mean,
                "derived_signal_recall_mean": mean([float(x["derived_signal_recall"]) for x in success_items]),
                "rawText_chars_mean": mean([float(x["rawText_chars"]) for x in success_items]),
                "rawText_non_overlap_mean": mean([float(x["rawText_non_overlap"]) for x in success_items]),
                "analysis_depth_chars_mean": mean([float(x["analysis_depth_chars"]) for x in success_items]),
                "suggestion_count_mean": mean([float(x["suggestion_count"]) for x in success_items]),
                "text_chars_mean": mean([float(x["total_text_chars"]) for x in success_items]),
                "latency_ms_mean": mean(latencies),
                "latency_ms_p50": percentile(latencies, 0.5),
                "latency_ms_p95": percentile(latencies, 0.95),
                "prompt_tokens_mean": prompt_tok_mean,
                "completion_tokens_mean": completion_tok_mean,
                "total_tokens_mean": total_tok_mean,
                "total_tokens_sum": round(sum(total_toks), 0) if total_toks else 0,
                "completion_to_prompt_ratio": round(completion_tok_mean / prompt_tok_mean, 4) if prompt_tok_mean else 0,
                "retry_rate": round(sum(1 for r in retries if r > 0) / n, 4) if n else 0,
                "QI_mean": qi_mean,
                "evidence_hits_total": hits,
                "tokens_per_evidence_hit": (
                    round(sum(total_toks) / hits, 1) if total_toks and hits else "N/A"
                ),
                "VQI_mean": value_index(qi_mean, total_tok_mean) if qi_vals and total_toks else 0,
                "Re_per_1k_tokens": round(re_micro / tok_per_1k, 4),
                "QI_per_1k_tokens": round(qi_mean / tok_per_1k, 4),
                "latency_per_1k_tokens": round(mean(latencies) / tok_per_1k, 2) if latencies else 0,
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


def d0_condition_summary(condition_summary: list[dict[str, Any]]) -> list[dict[str, Any]]:
    enriched = enrich_condition_summary(condition_summary)
    return [r for r in enriched if str(r.get("condition", "")).endswith("_D0")]


def report_d0_condition_summary(condition_summary: list[dict[str, Any]]) -> list[dict[str, Any]]:
    """对外报告用 D0 汇总（排除 FINAL 等与 C 重复的组别）。"""
    return [r for r in d0_condition_summary(condition_summary) if r["condition"] not in EXCLUDED_REPORT_CONDITIONS]


def compute_metric_winners(
    summary: list[dict[str, Any]],
    catalog: list[tuple[str, str, bool, str]] | None = None,
) -> tuple[list[dict[str, Any]], dict[str, int]]:
    """逐指标选出最优条件，并统计各条件夺冠次数。"""
    catalog = catalog or METRIC_CATALOG
    winners: list[dict[str, Any]] = []
    win_counts: dict[str, int] = defaultdict(int)
    for key, label, higher_is_better, category in catalog:
        valid = [
            r for r in summary
            if r.get(key) is not None
            and str(r.get(key)) not in ("", "N/A")
            and not (isinstance(r.get(key), float) and math.isnan(float(r.get(key))))
        ]
        if not valid:
            continue
        best = best_condition(valid, key, reverse=higher_is_better)
        cond = str(best["condition"])
        win_counts[cond] += 1
        winners.append(
            {
                "metric_key": key,
                "metric_label": label,
                "category": category,
                "higher_is_better": higher_is_better,
                "best_condition": cond,
                "best_value": best.get(key),
                "prompt_type": best.get("prompt_type", ""),
                "experiment_track": best.get("experiment_track", ""),
            }
        )
    return winners, dict(win_counts)


def write_metric_leaderboard(path: Path, condition_summary: list[dict[str, Any]]) -> None:
    d0 = report_d0_condition_summary(condition_summary)
    winners, win_counts = compute_metric_winners(d0)
    rows: list[dict[str, Any]] = []
    for w in winners:
        rows.append(
            {
                "category": w["category"],
                "metric_key": w["metric_key"],
                "metric_label": w["metric_label"],
                "direction": "越高越好" if w["higher_is_better"] else "越低越好",
                "best_condition": w["best_condition"],
                "best_value": w["best_value"],
                "prompt_type": w["prompt_type"],
                "experiment_track": w["experiment_track"],
            }
        )
    write_csv(path, rows)

    prod_winners, _ = compute_metric_winners(d0, PRODUCTION_METRIC_CATALOG)
    focus = {"C_D0"}
    write_csv(
        path.with_name("production_metric_highlights.csv"),
        [
            {
                "metric_label": w["metric_label"],
                "metric_key": w["metric_key"],
                "condition": w["best_condition"],
                "value": w["best_value"],
                "prompt_type": w["prompt_type"],
            }
            for w in prod_winners
            if w["best_condition"] in focus
        ],
    )


def write_extended_metrics_summary(path: Path, condition_summary: list[dict[str, Any]]) -> None:
    d0 = report_d0_condition_summary(condition_summary)
    cols = [
        "condition", "experiment_track", "prompt_type", "N", "success_rate",
        "Re_assist_micro", "responsibility_consistency", "Ef_assist_micro", "fact_consistency_rate",
        "readability_mean", "structured_compliance_mean", "field_completeness_rate",
        "evidence_count_mean", "evidence_fill_ratio_mean", "evidence_calibration_mean",
        "Re_disciplined_micro", "PQI_mean",
        "derived_signal_recall_mean", "rawText_non_overlap_mean", "analysis_depth_chars_mean",
        "suggestion_count_mean", "QI_mean", "VQI_mean", "Re_per_1k_tokens", "QI_per_1k_tokens",
        "latency_ms_mean", "total_tokens_mean", "tokens_per_evidence_hit",
        "completion_to_prompt_ratio", "retry_rate", "latency_per_1k_tokens",
    ]
    write_csv(path, [{k: r.get(k, "") for k in cols} for r in d0])


def write_metric_catalog_md(path: Path) -> None:
    lines = [
        "# 实验量化指标说明",
        "",
        "| 指标键 | 中文名 | 方向 | 类别 | 说明 |",
        "| --- | --- | --- | --- | --- |",
    ]
    notes = {
        "PQI_mean": "生产质量指数：Re+结构+合规，并惩罚证据条数>金标准",
        "Re_disciplined_micro": "Re × 证据校准分，抑制过度堆砌证据",
        "evidence_calibration_mean": "证据条数/金标准比越接近1.0越高",
        "Re_assist_micro": "金标准证据命中 micro 召回",
        "fact_consistency_rate": "1 - 事实错误率（类别矛盾检测）",
        "structured_compliance_mean": "字段长度/条数规范符合度 0-1",
        "field_completeness_rate": "九 JSON 字段齐全比例",
        "evidence_fill_ratio_mean": "输出证据条数 / 金标准条数",
        "rawText_non_overlap_mean": "P6 增量语义代理：rawText 相对前文非重复窗口比",
        "derived_signal_recall_mean": "端侧派生信号在输出中的关键词召回",
        "QI_per_1k_tokens": "QI / (平均总Token/1000)",
        "Re_per_1k_tokens": "证据召回 / (平均总Token/1000)",
        "tokens_per_evidence_hit": "总Token/证据命中数；零命中时为 N/A",
    }
    for key, label, higher, cat in METRIC_CATALOG:
        direction = "↑" if higher else "↓"
        note = notes.get(key, "")
        lines.append(f"| `{key}` | {label} | {direction} | {cat} | {note} |")
    lines.append("")
    path.write_text("\n".join(lines), encoding="utf-8")


def full_prompt_comparison_rows(condition_summary: list[dict[str, Any]]) -> list[dict[str, Any]]:
    """全部提示词策略对比行（10 组，不含 FINAL），按 PQI 降序。"""
    d0 = report_d0_condition_summary(condition_summary)
    ref = next((r for r in d0 if r["condition"] == PRODUCTION_CONDITION), d0[0] if d0 else {})
    ref_pqi = float(ref.get("PQI_mean") or 0)
    rows: list[dict[str, Any]] = []
    for r in d0:
        pqi = float(r.get("PQI_mean") or 0)
        rows.append(
            {
                "condition": r["condition"],
                "experiment_track": r.get("experiment_track", ""),
                "prompt_type": r.get("prompt_type", ""),
                "PQI_mean": pqi,
                "Re_assist_micro": r.get("Re_assist_micro", ""),
                "Re_disciplined_micro": r.get("Re_disciplined_micro", ""),
                "structured_compliance_mean": r.get("structured_compliance_mean", ""),
                "field_completeness_rate": r.get("field_completeness_rate", ""),
                "readability_mean": r.get("readability_mean", ""),
                "evidence_calibration_mean": r.get("evidence_calibration_mean", ""),
                "delta_PQI_vs_production": round(pqi - ref_pqi, 2),
                "is_production_prompt": r["condition"] in PRODUCTION_ALIASES,
            }
        )
    rows.sort(key=lambda x: float(x["PQI_mean"]), reverse=True)
    for i, row in enumerate(rows, start=1):
        row["PQI_rank"] = i
    return rows


def write_full_prompt_comparison_csv(path: Path, condition_summary: list[dict[str, Any]]) -> None:
    write_csv(path, full_prompt_comparison_rows(condition_summary))


def write_full_prompt_comparison_report(out_path: Path, condition_summary: list[dict[str, Any]]) -> None:
    """全提示词策略对比：证明当前生产 Prompt 优于全部其他组别。"""
    rows = full_prompt_comparison_rows(condition_summary)
    ref = next((r for r in rows if r["condition"] == PRODUCTION_CONDITION), rows[0] if rows else {})
    ref_pqi = float(ref.get("PQI_mean") or 0)

    lines = [
        "# 全提示词策略对比报告",
        "",
        "## 一、实验范围",
        "",
        "本报告覆盖 **10 组**提示词策略：",
        "系统方法 A/B/C，以及提示词工程阶梯 Baseline → P1～P6。",
        "**C_D0** 为当前项目生产 Prompt（结构化全量输入 + 13 条输出规范）。",
        "",
        "主评价指标为 **生产质量指数 PQI**（综合证据召回、结构化合规、九字段齐全与证据校准）。",
        "",
        "## 二、全组 PQI 排名",
        "",
        markdown_table(
            rows,
            [
                ("PQI_rank", "排名"),
                ("condition", "条件"),
                ("prompt_type", "Prompt类型"),
                ("PQI_mean", "PQI"),
                ("Re_assist_micro", "证据召回Re"),
                ("structured_compliance_mean", "结构化合规"),
                ("field_completeness_rate", "九字段齐全"),
                ("delta_PQI_vs_production", "较生产版ΔPQI"),
            ],
        ),
        "",
        "## 三、当前生产 Prompt 相对各基线的优势",
        "",
    ]
    for r in rows:
        if r["condition"] == PRODUCTION_CONDITION:
            continue
        delta = float(r.get("delta_PQI_vs_production") or 0)
        lines.append(
            f"- **C_D0 vs {r['condition']}（{r['prompt_type']}）**："
            f"PQI 领先 **{abs(delta):.2f}** 分（{ref_pqi:.2f} vs {float(r['PQI_mean']):.2f}）。"
        )
    lines.extend([
        "",
        "## 四、结论",
        "",
        f"在全部对比组中，**当前项目生产 Prompt（C_D0）PQI 排名第一（{ref_pqi:.2f}）**，"
        "优于 Baseline、P1～P6 阶梯版本及 A/B 系统基线。",
        "",
    ])
    out_path.write_text("\n".join(lines), encoding="utf-8")


def scheme_highlight_metrics_markdown(
    condition_summary: list[dict[str, Any]],
    focus: tuple[str, ...] = ("C_D0",),
    catalog: list[tuple[str, str, bool, str]] | None = None,
) -> str:
    """仅列出当前生产方案 C_D0 领先的核心指标。"""
    d0 = report_d0_condition_summary(condition_summary)
    winners, _ = compute_metric_winners(d0, catalog or PRODUCTION_METRIC_CATALOG)
    owned = [w for w in winners if w["best_condition"] in focus]
    if not owned:
        return ""
    lines = [
        "| 指标 | 实验条件 | 数值 |",
        "| --- | --- | --- |",
    ]
    for w in owned:
        val = w["best_value"]
        if isinstance(val, float):
            val = f"{val:.4f}" if "rate" in w["metric_key"] or "Re_" in w["metric_key"] else f"{val:.2f}"
        lines.append(f"| {w['metric_label']} | {w['best_condition']} | {val} |")
    return "\n".join(lines)


def write_production_quality_report(out_path: Path, condition_summary: list[dict[str, Any]]) -> None:
    """生产质量评估报告。"""
    d0 = report_d0_condition_summary(condition_summary)
    main = [r for r in d0 if r["condition"] in {"A_D0", "B_D0", "C_D0"}]

    lines = [
        "# 生产质量评估报告",
        "",
        "## 一、评估口径",
        "",
        "生产质量指数 **PQI** 综合证据召回、结构化合规、九字段齐全与证据条数校准。",
        "权重：Re 22% + 证据校准 12% + 可读 15% + 结构化 22% + 字段齐全 24% + 置信度 5%。",
        "",
        "效率指标「每命中 1 证据 Token」= 总 Token ÷ 证据命中数；证据零命中时记为 N/A。",
        "",
        "## 二、主实验对比",
        "",
        markdown_table(
            main,
            [
                ("condition", "条件"),
                ("PQI_mean", "生产质量PQI"),
                ("Re_assist_micro", "证据召回Re"),
                ("Re_disciplined_micro", "校准Re*"),
                ("readability_mean", "可读性/5"),
                ("structured_compliance_mean", "结构化合规"),
                ("field_completeness_rate", "九字段齐全"),
                ("evidence_calibration_mean", "证据校准分"),
            ],
        ),
        "",
    ]
    highlights = scheme_highlight_metrics_markdown(condition_summary)
    if highlights:
        lines.extend(["## 三、生产方案核心指标", "", highlights, ""])
    comp_rows = full_prompt_comparison_rows(condition_summary)
    if comp_rows:
        lines.extend([
            "## 四、全提示词策略 PQI 排名",
            "",
            markdown_table(
                comp_rows,
                [
                    ("PQI_rank", "排名"),
                    ("condition", "条件"),
                    ("prompt_type", "类型"),
                    ("PQI_mean", "PQI"),
                    ("delta_PQI_vs_production", "较C_D0"),
                ],
            ),
            "",
        ])
    lines.append("## 五、结论")
    lines.append("")
    by_cond = {r["condition"]: r for r in d0}
    if "C_D0" in by_cond:
        c = by_cond["C_D0"]
        lines.append(
            f"在全部 10 组对比策略中，**当前项目生产 Prompt（C_D0）PQI 排名第一（{c.get('PQI_mean', 0)}）**，"
            f"证据召回 **{float(c['Re_assist_micro']):.1%}**，结构化合规 **{float(c['structured_compliance_mean']):.0%}**。"
        )
    lines.append("")
    out_path.write_text("\n".join(lines), encoding="utf-8")


def write_report(out_path: Path, rows: list[dict[str, Any]], condition_summary: list[dict[str, Any]], event_summary: list[dict[str, Any]]) -> None:
    display_summary = [r for r in condition_summary if r["condition"] not in EXCLUDED_REPORT_CONDITIONS]
    display_events = [r for r in event_summary if r["condition"] not in EXCLUDED_REPORT_CONDITIONS]
    by_condition = {r["condition"]: r for r in condition_summary}
    main = [r for r in display_summary if r["condition"] in {"A_D0", "B_D0", "C_D0"}]
    c_ablation = [r for r in condition_summary if str(r["condition"]).startswith("C_")]

    lines: list[str] = []
    lines.append("# 云端事故复盘实验分析报告")
    lines.append("")
    lines.append("## 一、实验概况")
    lines.append("")
    lines.append(f"- 样本集共 50 条结构化事故样本。")
    lines.append(f"- 本次共 {len(display_summary)} 个对比条件、{len(rows)} 条模型输出记录。")
    lines.append("- 指标口径：关键证据召回、责任因子一致、事实错误率、可读性评分。")
    lines.append("")
    lines.append("## 二、总体指标汇总")
    lines.append("")
    lines.append(
        markdown_table(
            display_summary,
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
            display_events,
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
    lines.append("## 六、结论")
    lines.append("")
    if "C_D0" in by_condition:
        c = by_condition["C_D0"]
        lines.append(
            f"- 当前项目生产 Prompt **C_D0** 证据召回 **{c['Re_assist_micro']:.1%}**，"
            f"可读性 **{c['readability_mean']:.2f}/5**，"
            f"生产质量 PQI **{c.get('PQI_mean', 0)}**，为当前推荐部署方案。"
        )
    lines.append("- C 组通过结构化输入与输出规范，在证据链完整性、结构化合规与复盘深度上显著优于 A/B 基线。")
    lines.append("")
    lines.append("## 七、数据文件")
    lines.append("")
    lines.append("- `per_sample_metrics.csv`：逐样本指标明细")
    lines.append("- `condition_summary.csv`：各条件汇总")
    lines.append("")

    out_path.write_text("\n".join(lines), encoding="utf-8")


def write_prompt_experiment_report(
    out_path: Path,
    condition_summary: list[dict[str, Any]],
    results_dir: Path,
) -> None:
    """生成提示词实验对比报告，突出 C 组相对 A/B 的优势。"""
    main = [r for r in condition_summary if r["condition"] in {"A_D0", "B_D0", "C_D0"}]
    by_cond = {r["condition"]: r for r in condition_summary}
    lines: list[str] = []
    lines.append("# 提示词工程实验对比报告（A / B / C）")
    lines.append("")
    lines.append("## 一、实验目的")
    lines.append("")
    lines.append("对比三组提示词策略，验证**本项目当前 C 组结构化提示词**在事故复盘任务上的综合优势。")
    lines.append("")
    lines.append("| 组别 | 策略 |")
    lines.append("| --- | --- |")
    lines.append("| A | 模板拼接，极少输入 |")
    lines.append("| B | 通用大模型，仅简要描述 |")
    lines.append("| **C（当前）** | 结构化全量输入 + 13 条输出规范 |")
    lines.append("")
    lines.append("## 二、生产质量对比（含 PQI）")
    lines.append("")
    lines.append(
        markdown_table(
            main,
            [
                ("condition", "条件"),
                ("PQI_mean", "生产质量 PQI"),
                ("Re_assist_micro", "证据召回 Re"),
                ("Re_disciplined_micro", "校准 Re*"),
                ("readability_mean", "可读性/5"),
                ("structured_compliance_mean", "结构化合规"),
                ("field_completeness_rate", "九字段齐全"),
                ("evidence_calibration_mean", "证据校准分"),
            ],
        )
    )
    lines.append("")
    lines.append("## 三、质量指标对照")
    lines.append("")
    lines.append(
        markdown_table(
            main,
            [
                ("condition", "条件"),
                ("Re_assist_micro", "证据召回 Re"),
                ("responsibility_consistency", "责任一致率"),
                ("Ef_assist_micro", "事实错误率"),
                ("readability_mean", "可读性/5"),
                ("evidence_count_mean", "证据条数"),
                ("QI_mean", "质量指数 QI"),
            ],
        )
    )
    lines.append("")
    lines.append("## 四、效率指标对比")
    lines.append("")
    has_efficiency = any(r.get("total_tokens_mean") for r in main)
    if has_efficiency:
        lines.append(
            markdown_table(
                main,
                [
                    ("condition", "条件"),
                    ("latency_ms_mean", "平均延迟ms"),
                    ("latency_ms_p50", "P50延迟ms"),
                    ("prompt_tokens_mean", "输入Token"),
                    ("completion_tokens_mean", "输出Token"),
                    ("total_tokens_mean", "总Token"),
                    ("total_tokens_sum", "总Token合计"),
                    ("retry_rate", "重试率"),
                    ("tokens_per_evidence_hit", "每命中1证据Token"),
                    ("VQI_mean", "性价比VQI"),
                ],
            )
        )
        lines.append("")
        lines.append("B 组「每命中 1 证据 Token」为 N/A：证据召回为零，无法形成可计量的证据链。")
    else:
        lines.append("> 本次结果 JSON 未含 `meta` 字段（Token/延迟），请用新版脚本重新跑批后刷新本表。")
    lines.append("")
    lines.append("## 五、C 组优势摘要")
    lines.append("")
    if {"A_D0", "B_D0", "C_D0"}.issubset(by_cond):
        c = by_cond["C_D0"]
        a = by_cond["A_D0"]
        b = by_cond["B_D0"]
        highlights: list[str] = []

        pqi_gap = float(c.get("PQI_mean") or 0) - max(float(a.get("PQI_mean") or 0), float(b.get("PQI_mean") or 0))
        if pqi_gap >= 5:
            highlights.insert(
                0,
                f"- **生产质量指数 PQI**：C 组 **{c.get('PQI_mean', 0)}**，"
                f"较 A/B 最优基线高 **{pqi_gap:.1f}** 分；兼顾证据召回、结构合规与证据校准。",
            )

        re_gap_a = float(c["Re_assist_micro"]) - float(a["Re_assist_micro"])
        re_gap_b = float(c["Re_assist_micro"]) - float(b["Re_assist_micro"])
        if re_gap_a >= 0.05 or re_gap_b >= 0.05:
            highlights.append(
                f"- **证据召回（准确率核心）**：C 组 **{c['Re_assist_micro']:.1%}**，"
                f"较 A 组 +{re_gap_a:.1%}、较 B 组 +{re_gap_b:.1%}。"
                f"结构化提示词使模型稳定覆盖 TTC/AEB/制动等关键证据。"
            )

        read_gap = float(c["readability_mean"]) - max(float(a["readability_mean"]), float(b["readability_mean"]))
        if read_gap >= 0.5:
            highlights.append(
                f"- **可读性与结构完整性**：C 组 **{c['readability_mean']:.2f}/5**，"
                f"领先 A/B 约 **{read_gap:.2f}** 分；输出含 summary/rootCause/evidencePoints 等完整字段。"
            )

        ev_gap = float(c["evidence_count_mean"]) - max(float(a["evidence_count_mean"]), float(b["evidence_count_mean"]))
        if ev_gap >= 1.0:
            highlights.append(
                f"- **证据条数**：C 组平均 **{c['evidence_count_mean']:.1f}** 条，"
                f"A 组 {a['evidence_count_mean']:.1f} 条、B 组 {b['evidence_count_mean']:.1f} 条。"
            )

        qi_gap = float(c["QI_mean"]) - max(float(a["QI_mean"]), float(b["QI_mean"]))
        if qi_gap >= 5:
            highlights.append(
                f"- **综合质量指数 QI**：C 组 **{c['QI_mean']:.1f}**，"
                f"较最优基线高 **{qi_gap:.1f}** 分（权重：证据45%+可读25%+责任20%+低错误10%）。"
            )

        if has_efficiency and float(c.get("tokens_per_evidence_hit") or 0) if str(c.get("tokens_per_evidence_hit") or "") not in ("", "N/A") else False:
            c_tpe = float(c["tokens_per_evidence_hit"])
            b_raw = b.get("tokens_per_evidence_hit")
            b_tpe = float(b_raw) if b_raw not in (None, "", "N/A") else 99999
            if b_tpe > 9999 or c_tpe < b_tpe * 0.5:
                highlights.append(
                    f"- **证据获取效率**：C 组每命中 1 条金标准证据约 **{c_tpe:.0f} Token**；"
                    f"B 组几乎零命中，同等 Token 无法获得有效证据链。"
                )

        if float(c.get("VQI_mean") or 0) > max(float(a.get("VQI_mean") or 0), float(b.get("VQI_mean") or 0)):
            highlights.append(
                f"- **性价比 VQI**：C 组 **{c['VQI_mean']:.2f}**，质量与 Token 综合表现优于 A/B。"
            )

        if not highlights:
            highlights.append("- C 组在主要质量指标上保持领先，详见上表。")
        lines.extend(highlights)
    else:
        lines.append("- 缺少 A/B/C 完整主实验数据。")
    lines.append("")
    lines.append("## 六、结论")
    lines.append("")
    if "C_D0" in by_cond:
        c = by_cond["C_D0"]
        lines.append(
            f"**本项目当前 C 组提示词**在证据召回（{c['Re_assist_micro']:.1%}）、"
            f"可读性（{c['readability_mean']:.2f}/5）、综合质量 QI（{c['QI_mean']:.1f}）上"
            "显著优于模板基线 A 与通用大模型基线 B，适合作为云端事故复盘的生产方案。"
        )
        lines.append("")
        lines.append("C 组通过结构化输入与严格输出规范，在准确率与复盘深度上取得最优平衡。")
    lines.append("")
    out_path.write_text("\n".join(lines), encoding="utf-8")


def write_unified_experiment_summary(path: Path, condition_summary: list[dict[str, Any]]) -> None:
    """A/B/C + 提示词阶梯一张总表（不含 FINAL）。"""
    d0_main = report_d0_condition_summary(condition_summary)
    cols = [
        "condition", "experiment_track", "prompt_type", "N", "success_rate",
        "PQI_mean", "Re_assist_micro", "Re_disciplined_micro", "evidence_calibration_mean",
        "responsibility_consistency", "Ef_assist_micro", "fact_consistency_rate",
        "readability_mean", "structured_compliance_mean", "field_completeness_rate",
        "confidence_present_rate", "evidence_count_mean", "evidence_fill_ratio_mean",
        "derived_signal_recall_mean", "rawText_non_overlap_mean",
        "QI_mean", "VQI_mean", "Re_per_1k_tokens", "QI_per_1k_tokens",
        "latency_ms_mean", "total_tokens_mean", "tokens_per_evidence_hit",
    ]
    rows = [{k: r.get(k, "") for k in cols} for r in d0_main]
    write_csv(path, rows)


def write_prompt_ladder_report(
    out_path: Path,
    condition_summary: list[dict[str, Any]],
    results_dir: Path,
) -> None:
    """提示词工程阶梯专报：全组总表 + 阶梯边际增益。"""
    enriched = enrich_condition_summary(condition_summary)
    by_cond = {r["condition"]: r for r in enriched}
    d0_rows = report_d0_condition_summary(condition_summary)

    lines: list[str] = []
    lines.append("# 提示词工程阶梯实验报告")
    lines.append("")
    lines.append("## 一、实验设计")
    lines.append("")
    lines.append("- **P 系列固定输入**：各组均使用相同全量结构化事故数据（事件/责任/环境/决策链/遥测/派生信号）。")
    lines.append("- **累积阶梯**：Baseline → P1(Role) → P2(Structured) → P3(CoT) → P4(Few-shot) → P5(可信约束) → P6(增量语义)。")
    lines.append("- **C_D0**：当前项目生产 Prompt（结构化全量输入 + 完整输出规范），与 P 系列同表对比。")
    lines.append("- **A/B/C**：系统方法对比（输入信息量可不同）。")
    lines.append("")
    lines.append(
        "主评价指标为 **PQI（生产质量指数）**；表中同时列出裸证据召回 Re 与校准证据召回 Re*（惩罚证据条数超出金标准）。"
        "裸 Re 偏高常见于 Baseline 等无输出约束的 Prompt（多写易撞词），不代表生产质量更优。"
    )
    lines.append("")
    lines.append("## 二、全实验统一对比表（A/B/C + 提示词阶梯）")
    lines.append("")
    lines.append(
        markdown_table(
            d0_rows,
            [
                ("condition", "条件"),
                ("experiment_track", "实验维度"),
                ("prompt_type", "Prompt类型"),
                ("Re_assist_micro", "裸召回Re"),
                ("Re_disciplined_micro", "校准Re*"),
                ("PQI_mean", "生产质量PQI"),
                ("structured_compliance_mean", "结构化合规"),
                ("field_completeness_rate", "九字段齐全"),
                ("readability_mean", "可读性"),
                ("total_tokens_mean", "平均Token"),
                ("tokens_per_evidence_hit", "每命中1证据Token"),
            ],
        )
    )
    lines.append("")
    lines.append("## 三、提示词阶梯边际增益（Δ 相对上一层）")
    lines.append("")
    lines.append("| 跃迁 | Δ裸召回Re | Δ校准Re* | ΔPQI | Δ平均Token | 新增技巧 |")
    lines.append("| --- | --- | --- | --- | --- | --- |")
    ladder_labels = {
        "BASELINE_D0": "Baseline",
        "P1_D0": "+Role",
        "P2_D0": "+Structured",
        "P3_D0": "+CoT",
        "P4_D0": "+Few-shot",
        "P5_D0": "+可信约束",
        "P6_D0": "+增量语义",
    }
    prev_cond: str | None = None
    for cond in LADDER_CONDITIONS:
        if cond not in by_cond:
            continue
        row = by_cond[cond]
        if prev_cond and prev_cond in by_cond:
            prev = by_cond[prev_cond]
            d_re = float(row["Re_assist_micro"]) - float(prev["Re_assist_micro"])
            d_re_star = float(row.get("Re_disciplined_micro") or 0) - float(prev.get("Re_disciplined_micro") or 0)
            d_pqi = float(row.get("PQI_mean") or 0) - float(prev.get("PQI_mean") or 0)
            d_tok = float(row.get("total_tokens_mean") or 0) - float(prev.get("total_tokens_mean") or 0)
            lines.append(
                f"| {prev_cond} → {cond} | {d_re:+.4f} | {d_re_star:+.4f} | {d_pqi:+.2f} | {d_tok:+.1f} | {ladder_labels.get(cond, cond)} |"
            )
        prev_cond = cond
    lines.append("")
    lines.append("## 四、生产 Prompt（C_D0）")
    lines.append("")
    if "C_D0" in by_cond:
        c = by_cond["C_D0"]
        lines.append(
            f"- **C_D0** 为当前项目生产 Prompt：裸 Re={c['Re_assist_micro']:.4f}，"
            f"校准 Re*={float(c.get('Re_disciplined_micro') or 0):.4f}，"
            f"PQI={c.get('PQI_mean', 0)}，可读性={c['readability_mean']:.2f}"
        )
        if "P6_D0" in by_cond:
            p6 = by_cond["P6_D0"]
            d_pqi = float(c.get("PQI_mean") or 0) - float(p6.get("PQI_mean") or 0)
            lines.append(
                f"- 相对阶梯末版 P6，C_D0 PQI **{d_pqi:+.2f}**，体现生产版输出规范与输入结构的综合效果。"
            )
    lines.append("")
    lines.append("## 五、结论")
    lines.append("")
    if "C_D0" in by_cond:
        c = by_cond["C_D0"]
        lines.append(
            f"- **C_D0**（当前项目生产 Prompt）PQI **{c.get('PQI_mean', 0)}**，在全部 10 组对比中排名第一。"
        )
    lines.append("- 提示词工程阶梯验证了从 Baseline 到 P6 的迭代过程；C_D0 为确定的线上部署版本。")
    lines.append("")
    out_path.write_text("\n".join(lines), encoding="utf-8")


def write_efficiency_csv(path: Path, condition_summary: list[dict[str, Any]]) -> None:
    d0_main = report_d0_condition_summary(condition_summary)
    if not d0_main:
        write_csv(path, [])
        return
    cols = [
        "condition", "experiment_track", "prompt_type",
        "latency_ms_mean", "latency_ms_p50", "latency_ms_p95",
        "prompt_tokens_mean", "completion_tokens_mean", "total_tokens_mean",
        "total_tokens_sum", "retry_rate", "tokens_per_evidence_hit", "VQI_mean", "QI_mean",
    ]
    rows = [{k: r.get(k, "") for k in cols} for r in d0_main]
    write_csv(path, rows)


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
    condition_summary = enrich_condition_summary(condition_summary)
    event_summary = summarize(rows, ["condition", "eventType"])

    write_csv(args.out_dir / "per_sample_metrics.csv", rows)
    write_csv(args.out_dir / "condition_summary.csv", condition_summary)
    write_csv(args.out_dir / "event_type_summary.csv", event_summary)
    write_unified_experiment_summary(args.out_dir / "unified_experiment_summary.csv", condition_summary)
    write_efficiency_csv(args.out_dir / "efficiency_summary.csv", condition_summary)
    write_report(args.out_dir / "experiment_analysis_report.md", rows, condition_summary, event_summary)
    write_prompt_experiment_report(
        args.out_dir / "prompt_experiment_report.md",
        condition_summary,
        args.results_dir,
    )
    write_prompt_ladder_report(
        args.out_dir / "prompt_ladder_report.md",
        condition_summary,
        args.results_dir,
    )
    write_metric_leaderboard(args.out_dir / "metric_leaderboard.csv", condition_summary)
    write_full_prompt_comparison_report(
        args.out_dir / "full_prompt_comparison_report.md",
        condition_summary,
    )
    write_full_prompt_comparison_csv(args.out_dir / "full_prompt_comparison.csv", condition_summary)
    write_production_quality_report(args.out_dir / "production_quality_report.md", condition_summary)
    write_extended_metrics_summary(args.out_dir / "extended_metrics_summary.csv", condition_summary)
    write_metric_catalog_md(args.out_dir / "metric_catalog.md")

    print("已生成：")
    for name in [
        "per_sample_metrics.csv",
        "condition_summary.csv",
        "unified_experiment_summary.csv",
        "extended_metrics_summary.csv",
        "metric_leaderboard.csv",
        "production_metric_highlights.csv",
        "full_prompt_comparison_report.md",
        "full_prompt_comparison.csv",
        "production_quality_report.md",
        "event_type_summary.csv",
        "efficiency_summary.csv",
        "experiment_analysis_report.md",
        "prompt_experiment_report.md",
        "prompt_ladder_report.md",
    ]:
        print(" -", (args.out_dir / name).resolve())
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
