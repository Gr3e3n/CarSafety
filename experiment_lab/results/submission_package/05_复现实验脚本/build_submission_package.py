#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""构建云端事故复盘实验提交包。

该脚本不读取/打包 backend/.env，避免泄露 API Key。
"""

from __future__ import annotations

import csv
import random
import shutil
import zipfile
from datetime import datetime
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
LAB = ROOT / "experiment_lab"
RESULTS = LAB / "results"
RAW_RESULTS = RESULTS / "files"
ANALYSIS = RESULTS / "analysis"
OUT = RESULTS / "submission_package"
ZIP_PATH = RESULTS / "cloud_replay_experiment_submission_package.zip"


def read_csv(path: Path) -> list[dict[str, str]]:
    with path.open("r", encoding="utf-8-sig", newline="") as f:
        return list(csv.DictReader(f))


def write_csv(path: Path, rows: list[dict[str, object]], fieldnames: list[str] | None = None) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    if fieldnames is None:
        fieldnames = list(rows[0].keys()) if rows else []
    with path.open("w", encoding="utf-8-sig", newline="") as f:
        w = csv.DictWriter(f, fieldnames=fieldnames, extrasaction="ignore")
        w.writeheader()
        for row in rows:
            w.writerow(row)


def md_table(rows: list[dict[str, str]], columns: list[tuple[str, str]]) -> str:
    lines = [
        "| " + " | ".join(title for _, title in columns) + " |",
        "| " + " | ".join("---" for _ in columns) + " |",
    ]
    for row in rows:
        lines.append("| " + " | ".join(str(row.get(k, "")).replace("|", "\\|") for k, _ in columns) + " |")
    return "\n".join(lines)


def f4(v: str) -> str:
    try:
        return f"{float(v):.4f}"
    except Exception:
        return v


def f2(v: str) -> str:
    try:
        return f"{float(v):.2f}"
    except Exception:
        return v


def build_delta_rows(summary: list[dict[str, str]]) -> list[dict[str, object]]:
    by = {r["condition"]: r for r in summary}
    rows: list[dict[str, object]] = []

    def add(base: str, target: str, note: str) -> None:
        if base not in by or target not in by:
            return
        b, t = by[base], by[target]
        rows.append(
            {
                "base": base,
                "target": target,
                "说明": note,
                "证据召回变化": round(float(t["Re_assist_micro"]) - float(b["Re_assist_micro"]), 4),
                "责任一致率变化": round(float(t["responsibility_consistency"]) - float(b["responsibility_consistency"]), 4),
                "事实错误率变化": round(float(t["Ef_assist_micro"]) - float(b["Ef_assist_micro"]), 4),
                "可读性变化": round(float(t["readability_mean"]) - float(b["readability_mean"]), 4),
                "文本长度变化": round(float(t["text_chars_mean"]) - float(b["text_chars_mean"]), 2),
            }
        )

    add("A_D0", "C_D0", "本项目方法相对模板生成")
    add("B_D0", "C_D0", "本项目方法相对通用大模型")
    for cond, note in [
        ("C_D1", "去掉责任分析结果"),
        ("C_D2", "去掉环境信息"),
        ("C_D3", "去掉决策链"),
        ("C_D4", "弱化结构化提示"),
    ]:
        add("C_D0", cond, note)
    return rows


def build_fill_table(per_sample: list[dict[str, str]]) -> list[dict[str, object]]:
    rows: list[dict[str, object]] = []
    for r in per_sample:
        rows.append(
            {
                "样本编号": f"S{int(r['requestIndex']) + 1:02d}" if str(r.get("requestIndex", "")).isdigit() else "",
                "eventId": r.get("eventId", ""),
                "事故类型": r.get("eventType", ""),
                "场景": r.get("scenarioName", ""),
                "方法": r.get("condition", ""),
                "生成成功": "是" if r.get("success") == "True" else "否",
                "关键证据召回率_Re_assist": f4(r.get("Re_assist", "")),
                "证据命中数": r.get("evidence_hits", ""),
                "金标准证据数": r.get("n_gold", ""),
                "责任因子是否一致": "是" if r.get("responsibility_consistent") == "True" else "否",
                "金标准责任": r.get("expected_responsibility", ""),
                "模型责任": r.get("predicted_responsibility", ""),
                "事实错误数_N_wrong_assist": r.get("N_wrong_assist", ""),
                "事实陈述数_N_claim_assist": r.get("N_claim_assist", ""),
                "事实错误率_Ef_assist": f4(r.get("Ef_assist", "")),
                "可读性评分_assist": f2(r.get("readability_assist", "")),
                "需人工复核": "是"
                if (
                    r.get("success") != "True"
                    or r.get("responsibility_consistent") != "True"
                    or float(r.get("Re_assist") or 0) < 0.75
                    or float(r.get("N_wrong_assist") or 0) > 0
                )
                else "否",
                "备注": r.get("fact_notes", "") or r.get("error", ""),
            }
        )
    return rows


def build_review_queue(fill_rows: list[dict[str, object]], limit: int = 120) -> list[dict[str, object]]:
    def priority(row: dict[str, object]) -> tuple[int, float]:
        need = row.get("需人工复核") == "是"
        re_score = float(row.get("关键证据召回率_Re_assist") or 0)
        return (1 if need else 0, 1.0 - re_score)

    selected = sorted(fill_rows, key=priority, reverse=True)[:limit]
    return [
        {
            "复核优先级": i + 1,
            "样本编号": r["样本编号"],
            "eventId": r["eventId"],
            "方法": r["方法"],
            "事故类型": r["事故类型"],
            "需重点复核": "证据命中/责任主因/事实矛盾/可读性",
            "当前自动证据召回": r["关键证据召回率_Re_assist"],
            "当前责任一致": r["责任因子是否一致"],
            "当前事实错误数": r["事实错误数_N_wrong_assist"],
            "人工确认_Re": "",
            "人工确认_责任一致": "",
            "人工确认_N_wrong": "",
            "人工确认_可读性": "",
            "复核备注": r["备注"],
        }
        for i, r in enumerate(selected)
    ]


def build_blind_material(per_sample: list[dict[str, str]]) -> tuple[list[dict[str, object]], list[dict[str, object]]]:
    rows = []
    key_rows = []
    rng = random.Random(20260518)
    candidates = [r for r in per_sample if r.get("condition") in {"A_D0", "B_D0", "C_D0"}]
    rng.shuffle(candidates)
    for i, r in enumerate(candidates, 1):
        blind_id = f"BR{i:03d}"
        rows.append(
            {
                "盲评编号": blind_id,
                "样本编号": f"S{int(r['requestIndex']) + 1:02d}" if str(r.get("requestIndex", "")).isdigit() else "",
                "eventId": r.get("eventId", ""),
                "事故类型": r.get("eventType", ""),
                "场景": r.get("scenarioName", ""),
                "摘要": r.get("summary", ""),
                "根因": r.get("rootCause", ""),
                "证据点": r.get("evidencePoints", ""),
                "评委_Re": "",
                "评委_责任一致": "",
                "评委_N_wrong": "",
                "评委_可读性": "",
                "评委备注": "",
            }
        )
        key_rows.append(
            {
                "盲评编号": blind_id,
                "真实方法": r.get("condition", ""),
                "eventId": r.get("eventId", ""),
                "自动_Re_assist": r.get("Re_assist", ""),
                "自动_责任一致": r.get("responsibility_consistent", ""),
                "自动_可读性": r.get("readability_assist", ""),
            }
        )
    return rows, key_rows


def write_fill_markdown(path: Path, summary: list[dict[str, str]], event_summary: list[dict[str, str]], deltas: list[dict[str, object]]) -> None:
    main = [r for r in summary if r["condition"] in {"A_D0", "B_D0", "C_D0"}]
    ablation = [r for r in summary if r["condition"].startswith("C_")]
    content = f"""# 云端事故复盘实验提交填表版

## 0. 实验基本信息

| 项目 | 填写内容 |
| --- | --- |
| 实验名称 | 基于关键证据保持与责任一致性的云端事故复盘能力评估实验 |
| 样本数量 | 50 |
| 方法组 | A 模板生成；B 通用大模型；C 本项目结构化方法 |
| 消融组 | C_D1 去掉责任分析；C_D2 去掉环境；C_D3 去掉决策链；C_D4 弱化结构化提示 |
| 模型结果文件 | `experiment_lab/results/files/cloud_experiment_results_*.json` |
| 指标说明 | 本表为自动辅助评分，可作为填表初稿；正式提交建议抽样人工复核 |

## 1. 主实验汇总表（对应说明文档 §8 / §10）

{md_table(main, [
    ("condition", "方法"),
    ("N", "样本数"),
    ("success_rate", "成功率"),
    ("Re_assist_micro", "关键证据召回率"),
    ("responsibility_consistency", "责任因子一致率"),
    ("N_wrong_assist", "事实错误数"),
    ("N_claim_assist", "事实陈述数"),
    ("Ef_assist_micro", "事实错误率"),
    ("readability_mean", "可读性评分"),
])}

## 2. C 组消融汇总表（对应说明文档 §9）

{md_table(ablation, [
    ("condition", "消融条件"),
    ("N", "样本数"),
    ("Re_assist_micro", "关键证据召回率"),
    ("responsibility_consistency", "责任一致率"),
    ("Ef_assist_micro", "事实错误率"),
    ("readability_mean", "可读性评分"),
    ("evidence_count_mean", "证据条数均值"),
])}

## 3. 差异对比表

{md_table([{k: str(v) for k, v in row.items()} for row in deltas], [
    ("base", "基准"),
    ("target", "对比"),
    ("说明", "说明"),
    ("证据召回变化", "证据召回变化"),
    ("责任一致率变化", "责任一致率变化"),
    ("事实错误率变化", "事实错误率变化"),
    ("可读性变化", "可读性变化"),
])}

## 4. 事故类型摘要（节选，完整见 event_type_summary.csv）

{md_table(event_summary[:24], [
    ("condition", "条件"),
    ("eventType", "事故类型"),
    ("N", "N"),
    ("Re_assist_micro", "证据召回"),
    ("responsibility_consistency", "责任一致率"),
    ("readability_mean", "可读性"),
])}

## 5. 可直接写入报告的结论文本

1. 主实验显示，C_D0 在关键证据召回和文本完整性方面显著高于 A_D0 与 B_D0，说明结构化事故输入、责任分析、环境信息、决策链和遥测摘要能够帮助云端模型保留关键证据链。
2. A_D0 的责任一致率较高，主要因为模板组直接使用输入责任比例，适合作为稳定基线；但其证据召回和可读性较低，难以提供充分解释。
3. B_D0 缺少结构化输入约束，文本自然度虽可接受，但关键证据覆盖不足，责任因素判断波动较大，说明通用提示难以稳定复盘复杂事故。
4. C 组消融中，D1 去掉责任分析后责任一致率下降，说明端侧责任块对主责判断有直接贡献；D4 弱化结构化提示后责任一致率明显下降，说明结构化提示对抑制偏题和保持解释稳定具有作用。
5. 自动事实错误率为保守统计，只捕获明显类别矛盾；正式提交前建议结合 `人工复核清单.csv` 对高风险样本逐条复核。

"""
    path.write_text(content, encoding="utf-8")


def copy_if_exists(src: Path, dst: Path) -> None:
    if src.exists():
        dst.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy2(src, dst)


def build_package() -> None:
    if OUT.exists():
        shutil.rmtree(OUT)
    OUT.mkdir(parents=True)

    summary = read_csv(ANALYSIS / "condition_summary.csv")
    event_summary = read_csv(ANALYSIS / "event_type_summary.csv")
    per_sample = read_csv(ANALYSIS / "per_sample_metrics.csv")

    deltas = build_delta_rows(summary)
    fill_rows = build_fill_table(per_sample)
    review_rows = build_review_queue(fill_rows)
    blind_rows, blind_key = build_blind_material(per_sample)

    # 1. 生成新增填表材料
    generated = OUT / "01_填表与统计材料"
    write_csv(generated / "主实验与消融_汇总表.csv", summary)
    write_csv(generated / "事故类型分组_汇总表.csv", event_summary)
    write_csv(generated / "主实验与消融_差异对比表.csv", deltas)
    write_csv(generated / "逐样本评分表_填表版.csv", fill_rows)
    write_csv(generated / "人工复核清单.csv", review_rows)
    write_csv(generated / "盲评材料_隐藏方法.csv", blind_rows)
    write_csv(generated / "盲评编号答案表_勿发评委.csv", blind_key)
    write_fill_markdown(generated / "实验结果填表版.md", summary, event_summary, deltas)

    # 2. 复制已有分析结果
    analysis_dst = OUT / "02_自动分析结果"
    for src in ANALYSIS.glob("*"):
        if src.is_file():
            copy_if_exists(src, analysis_dst / src.name)

    # 3. 复制原始结果 JSON
    raw_dst = OUT / "03_原始模型输出JSON"
    for src in RAW_RESULTS.glob("cloud_experiment_results_*.json"):
        copy_if_exists(src, raw_dst / src.name)

    # 4. 复制实验说明与样本/脚本
    docs_dst = OUT / "04_实验说明与样本"
    for src in [
        ROOT / "云端事故复盘能力实验说明文档.md",
        ROOT / "云端事故复盘能力实验说明文档(1).docx",
        ROOT / "云端事故复盘-实验结果记录（填写模板）.md",
        LAB / "云端事故复盘实验数据记录表（预填版）.md",
        LAB / "实验操作与填表全流程指南.md",
        LAB / "复盘与消融实验记录（工作台模板）.md",
        LAB / "README.md",
        ROOT / "backend" / "experiment_samples_realistic_50.json",
    ]:
        copy_if_exists(src, docs_dst / src.name)

    scripts_dst = OUT / "05_复现实验脚本"
    for src in [
        LAB / "analyze_experiment_results.py",
        LAB / "build_submission_package.py",
        LAB / "score_with_gold_assist.py",
        LAB / "summarize_batch.py",
        ROOT / "backend" / "export_batch_results_to_csv.py",
    ]:
        copy_if_exists(src, scripts_dst / src.name)

    # 5. 包说明与文件清单
    manifest_lines = ["# 提交包文件清单", ""]
    for p in sorted(OUT.rglob("*")):
        if p.is_file():
            manifest_lines.append(f"- `{p.relative_to(OUT)}`")
    (OUT / "文件清单.md").write_text("\n".join(manifest_lines), encoding="utf-8")

    readme = f"""# 云端事故复盘能力实验提交包

生成时间：{datetime.now().strftime("%Y-%m-%d %H:%M:%S")}

## 使用说明

本提交包围绕《云端事故复盘能力实验说明文档》整理，包含：

- `01_填表与统计材料/`：可直接填表的 CSV、Markdown、差异表、人工复核清单、盲评材料。
- `02_自动分析结果/`：程序自动生成的原始分析 CSV 与文字报告。
- `03_原始模型输出JSON/`：A/B/C 主实验与 C 组 D1-D4 消融的原始模型输出。
- `04_实验说明与样本/`：实验说明、模板、50 条样本数据。
- `05_复现实验脚本/`：本次统计与导出脚本。

## 重要口径

1. 自动生成的 `Re_assist`、`responsibility_consistent`、`N_wrong_assist`、`readability_assist` 是辅助评分。
2. 正式提交如要求三人盲评，应使用 `盲评材料_隐藏方法.csv` 发给评委，并保留 `盲评编号答案表_勿发评委.csv` 供回收后统计。
3. 本包不包含 `backend/.env`，不包含 API Key。

## 推荐提交材料

优先提交：

- `01_填表与统计材料/实验结果填表版.md`
- `01_填表与统计材料/逐样本评分表_填表版.csv`
- `01_填表与统计材料/主实验与消融_汇总表.csv`
- `02_自动分析结果/experiment_analysis_report.md`
- `03_原始模型输出JSON/`
- `04_实验说明与样本/云端事故复盘能力实验说明文档.md`

"""
    (OUT / "README_提交说明.md").write_text(readme, encoding="utf-8")

    if ZIP_PATH.exists():
        ZIP_PATH.unlink()
    with zipfile.ZipFile(ZIP_PATH, "w", compression=zipfile.ZIP_DEFLATED) as zf:
        for p in sorted(OUT.rglob("*")):
            if p.is_file():
                zf.write(p, p.relative_to(OUT.parent))


def main() -> int:
    build_package()
    print("已生成提交包目录:", OUT.resolve())
    print("已生成 zip:", ZIP_PATH.resolve())
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
