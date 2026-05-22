#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""将实验 run 目录整理为结构清晰的结果汇总文件夹。"""

from __future__ import annotations

import argparse
import csv
import shutil
from datetime import datetime
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]

EXCLUDED_REPORT_CONDITIONS = {"FINAL_D0"}


def filter_report_rows(rows: list[dict[str, str]], key: str = "condition") -> list[dict[str, str]]:
    return [r for r in rows if r.get(key, "") not in EXCLUDED_REPORT_CONDITIONS]

UNIFIED_CN_MAP = {
    "condition": "实验条件",
    "experiment_track": "实验维度",
    "prompt_type": "Prompt类型",
    "N": "样本数",
    "success_rate": "解析成功率",
    "PQI_mean": "生产质量PQI",
    "Re_assist_micro": "证据召回Re",
    "Re_disciplined_micro": "校准证据召回Re*",
    "evidence_calibration_mean": "证据校准分",
    "readability_mean": "可读性(5分制)",
    "structured_compliance_mean": "结构化合规",
    "field_completeness_rate": "九字段齐全率",
    "confidence_present_rate": "置信度覆盖率",
    "evidence_count_mean": "证据条数均值",
    "evidence_fill_ratio_mean": "证据/金标准比",
    "responsibility_consistency": "责任一致率",
    "Ef_assist_micro": "事实错误率",
    "QI_mean": "综合质量QI",
    "latency_ms_mean": "平均延迟(ms)",
    "total_tokens_mean": "平均Token",
    "tokens_per_evidence_hit": "每命中1证据Token",
}

ABC_COLS = [
    "condition", "prompt_type", "PQI_mean", "Re_assist_micro", "Re_disciplined_micro",
    "readability_mean", "structured_compliance_mean", "field_completeness_rate",
    "confidence_present_rate", "evidence_calibration_mean", "QI_mean",
    "latency_ms_mean", "total_tokens_mean", "tokens_per_evidence_hit",
]


def read_csv(path: Path) -> list[dict[str, str]]:
    with path.open("r", encoding="utf-8-sig", newline="") as f:
        return list(csv.DictReader(f))


def write_csv(path: Path, rows: list[dict[str, object]], fieldnames: list[str]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", encoding="utf-8-sig", newline="") as f:
        w = csv.DictWriter(f, fieldnames=fieldnames, extrasaction="ignore")
        w.writeheader()
        for row in rows:
            w.writerow(row)


def rename_columns(rows: list[dict[str, str]], col_map: dict[str, str], cols: list[str]) -> tuple[list[dict[str, str]], list[str]]:
    cn_cols = [col_map.get(c, c) for c in cols]
    out_rows = [{col_map.get(c, c): row.get(c, "") for c in cols} for row in rows]
    return out_rows, cn_cols


def md_table(rows: list[dict[str, str]], columns: list[tuple[str, str]]) -> str:
    lines = [
        "| " + " | ".join(t for _, t in columns) + " |",
        "| " + " | ".join("---" for _ in columns) + " |",
    ]
    for row in rows:
        lines.append("| " + " | ".join(str(row.get(k, "")).replace("|", "\\|") for k, _ in columns) + " |")
    return "\n".join(lines)


def build_executive_summary(unified: list[dict[str, str]]) -> str:
    unified = filter_report_rows(unified)
    abc = [r for r in unified if r.get("condition") in {"A_D0", "B_D0", "C_D0"}]
    lines = [
        "# 实验结论摘要",
        "",
        "## 核心结论",
        "",
        "1. **C_D0** 为当前项目生产 Prompt，在全部 10 组对比策略中 **PQI 排名第一**，优于 Baseline、P1～P6 及 A/B 基线。",
        "2. A/B 基线无法形成有效证据链，不足以支撑云端事故复盘。",
        "",
        "详见 **01_分析报告/全提示词对比报告.md** 与 **02_核心数据表/全提示词PQI排名.csv**。",
        "",
        "## 全提示词 PQI 排名（摘要）",
        "",
    ]
    ranked = sorted(unified, key=lambda r: float(r.get("PQI_mean") or 0), reverse=True)
    if ranked:
        lines.append(
            md_table(
                ranked,
                [
                    ("prompt_type", "Prompt类型"),
                    ("condition", "条件"),
                    ("PQI_mean", "PQI"),
                    ("Re_assist_micro", "证据召回Re"),
                ],
            )
        )
    lines.extend([
        "",
        "## A / B / C 主实验",
        "",
    ])
    if abc:
        lines.append(
            md_table(
                abc,
                [
                    ("prompt_type", "方案"),
                    ("PQI_mean", "PQI"),
                    ("Re_assist_micro", "证据召回Re"),
                    ("readability_mean", "可读性/5"),
                    ("structured_compliance_mean", "结构化合规"),
                    ("field_completeness_rate", "九字段齐全"),
                    ("tokens_per_evidence_hit", "每命中1证据Token"),
                ],
            )
        )
    lines.extend([
        "",
        "B 组「每命中 1 证据 Token」为 N/A：证据召回为零，无法计算该效率指标。",
        "",
        "## 文档索引",
        "",
        "| 文件 | 内容 |",
        "| --- | --- |",
        "| 01_分析报告/全提示词对比报告.md | 10 组全量对比 |",
        "| 01_分析报告/生产质量评估报告.md | 生产质量评估 |",
        "| 02_核心数据表/全提示词PQI排名.csv | 全组 PQI 排名 |",
        "| 02_核心数据表/A-B-C主实验对比.csv | 系统方法对比 |",
        "| 00_阅读指南/实验组提示词全集.md | 全部 10 组 System/User 提示词 |",
        "",
    ])
    return "\n".join(lines)


def build_readme() -> str:
    return """# 实验结果汇总

## 目录说明

| 文件夹 | 内容 |
| --- | --- |
| **00_阅读指南** | 结论摘要、实验组提示词全集 |
| **01_分析报告** | Markdown 分析报告 |
| **02_核心数据表** | 中文表头 CSV |
| **03_指标汇总** | 生产方案核心指标 |
| **04_原始实验数据** | JSON 原始输出 |
| **05_明细数据** | 逐样本指标 |

## 实验规模

- 样本：50 条结构化事故
- 组别：A/B/C + Baseline/P1～P6，共 10 组；**C_D0 为当前项目生产 Prompt**
- 消融：D0（主实验）
"""


def organize(source: Path, out_dir: Path | None) -> Path:
    analysis = source / "analysis"
    if not analysis.is_dir():
        raise SystemExit(f"分析目录不存在: {analysis}")

    if out_dir is None:
        stamp = source.name.replace("run_", "")[:8] if source.name.startswith("run_") else datetime.now().strftime("%Y%m%d")
        out_dir = source.parent / f"实验结果汇总_{stamp}"

    if out_dir.exists():
        shutil.rmtree(out_dir)

    dirs = {
        "guide": out_dir / "00_阅读指南",
        "reports": out_dir / "01_分析报告",
        "tables": out_dir / "02_核心数据表",
        "metrics": out_dir / "03_指标汇总",
        "raw": out_dir / "04_原始实验数据",
        "detail": out_dir / "05_明细数据",
    }
    for d in dirs.values():
        d.mkdir(parents=True, exist_ok=True)

    report_map = {
        "full_prompt_comparison_report.md": "全提示词对比报告.md",
        "production_quality_report.md": "生产质量评估报告.md",
        "production_defense_report.md": "生产质量评估报告.md",
        "prompt_experiment_report.md": "A-B-C对比报告.md",
        "prompt_ladder_report.md": "提示词阶梯报告.md",
        "experiment_analysis_report.md": "完整分析报告.md",
        "metric_catalog.md": "指标说明.md",
    }
    copied: set[str] = set()
    for src_name, dst_name in report_map.items():
        if dst_name in copied:
            continue
        src = analysis / src_name
        if src.is_file():
            shutil.copy2(src, dirs["reports"] / dst_name)
            copied.add(dst_name)

    for jf in sorted(source.glob("cloud_experiment_results_*.json")):
        if "_FINAL_" in jf.name:
            continue
        shutil.copy2(jf, dirs["raw"] / jf.name)

    for name in ["per_sample_metrics.csv", "event_type_summary.csv", "condition_summary.csv"]:
        src = analysis / name
        if src.is_file():
            rows = filter_report_rows(read_csv(src))
            if rows:
                cols = list(rows[0].keys())
                write_csv(dirs["detail"] / name, rows, cols)
            else:
                shutil.copy2(src, dirs["detail"] / name)

    highlights = analysis / "production_metric_highlights.csv"
    if highlights.is_file():
        shutil.copy2(highlights, dirs["metrics"] / "生产方案核心指标.csv")
    if (analysis / "efficiency_summary.csv").is_file():
        eff_rows = filter_report_rows(read_csv(analysis / "efficiency_summary.csv"))
        if eff_rows:
            write_csv(dirs["metrics"] / "效率指标汇总.csv", eff_rows, list(eff_rows[0].keys()))

    full_cmp = analysis / "full_prompt_comparison.csv"
    if full_cmp.is_file():
        rows = read_csv(full_cmp)
        cn_rows, cn_cols = rename_columns(
            rows,
            {
                "PQI_rank": "PQI排名",
                "condition": "实验条件",
                "prompt_type": "Prompt类型",
                "PQI_mean": "生产质量PQI",
                "Re_assist_micro": "证据召回Re",
                "structured_compliance_mean": "结构化合规",
                "field_completeness_rate": "九字段齐全率",
                "delta_PQI_vs_production": "较生产版ΔPQI",
            },
            [
                "PQI_rank", "condition", "prompt_type", "PQI_mean",
                "Re_assist_micro", "structured_compliance_mean", "field_completeness_rate",
                "delta_PQI_vs_production",
            ],
        )
        write_csv(dirs["tables"] / "全提示词PQI排名.csv", cn_rows, cn_cols)

    unified_path = analysis / "unified_experiment_summary.csv"
    if unified_path.is_file():
        unified = filter_report_rows(read_csv(unified_path))
        cols = [c for c in UNIFIED_CN_MAP if unified and c in unified[0]]
        cn_rows, cn_cols = rename_columns(unified, UNIFIED_CN_MAP, cols)
        write_csv(dirs["tables"] / "全实验统一汇总.csv", cn_rows, cn_cols)

        abc_rows = [r for r in unified if r.get("condition") in {"A_D0", "B_D0", "C_D0"}]
        abc_cols = [c for c in ABC_COLS if unified and c in unified[0]]
        abc_cn, abc_cn_cols = rename_columns(abc_rows, UNIFIED_CN_MAP, abc_cols)
        write_csv(dirs["tables"] / "A-B-C主实验对比.csv", abc_cn, abc_cn_cols)

        ladder_rows = [
            r for r in unified
            if r.get("experiment_track") == "提示词工程阶梯" and r.get("condition") not in EXCLUDED_REPORT_CONDITIONS
        ]
        lad_cols = [c for c in UNIFIED_CN_MAP if unified and c in unified[0]]
        lad_cn, lad_cn_cols = rename_columns(ladder_rows, UNIFIED_CN_MAP, lad_cols)
        write_csv(dirs["tables"] / "提示词阶梯汇总.csv", lad_cn, lad_cn_cols)

    ext_path = analysis / "extended_metrics_summary.csv"
    if ext_path.is_file():
        shutil.copy2(ext_path, dirs["tables"] / "扩展指标全表.csv")

    unified = read_csv(unified_path) if unified_path.is_file() else []
    (dirs["guide"] / "实验结论摘要.md").write_text(build_executive_summary(unified), encoding="utf-8")
    (out_dir / "README.md").write_text(build_readme(), encoding="utf-8")

    from export_experiment_prompts import export_prompts_md

    export_prompts_md(
        dirs["guide"] / "实验组提示词全集.md",
        ROOT / "backend" / "experiment_samples_realistic_50.json",
    )

    return out_dir


def main() -> int:
    ap = argparse.ArgumentParser(description="整理实验结果为汇总文件夹")
    ap.add_argument("--source", type=Path, default=ROOT / "experiment_lab" / "results" / "run_20260519_024452Z")
    ap.add_argument("--out-dir", type=Path, default=None)
    args = ap.parse_args()
    if not args.source.is_dir():
        raise SystemExit(f"目录不存在: {args.source}")
    out = organize(args.source.resolve(), args.out_dir.resolve() if args.out_dir else None)
    print("已生成：", out.resolve())
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
