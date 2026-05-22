#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""导出全部实验组 System / User 提示词为 Markdown 文档。"""

from __future__ import annotations

import argparse
import json
import sys
from datetime import datetime, timezone
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
BACKEND_DIR = ROOT / "backend"
DEFAULT_SAMPLES = BACKEND_DIR / "experiment_samples_realistic_50.json"

SYSTEM_A = "你是一个固定模板填充助手，只按规则生成结构化JSON，不要添加额外推理。"
SYSTEM_B = "你是通用大模型助手，仅基于简要描述生成事故复盘，不要使用结构化输入。"
SYSTEM_C = (
    "你是资深车载事故分析专家。请严格基于输入数据进行专业、谨慎、可解释的因果分析，"
    "不要虚构事实，输出结构化 JSON。若发现后文与前文语义重复，"
    "必须优先改写为新增证据、新增视角或新增不确定性说明。"
)

EXPERIMENT_GROUPS: list[tuple[str, str, str]] = [
    ("A", "A_D0", "系统方法对比 · 模板生成"),
    ("B", "B_D0", "系统方法对比 · 通用大模型"),
    ("C", "C_D0", "系统方法对比 · 结构化全量（当前项目生产 Prompt）"),
    ("BASELINE", "BASELINE_D0", "提示词阶梯 · 普通 Prompt"),
    ("P1", "P1_D0", "提示词阶梯 · +Role"),
    ("P2", "P2_D0", "提示词阶梯 · +Structured"),
    ("P3", "P3_D0", "提示词阶梯 · +CoT"),
    ("P4", "P4_D0", "提示词阶梯 · +Few-shot"),
    ("P5", "P5_D0", "提示词阶梯 · +可信约束"),
    ("P6", "P6_D0", "提示词阶梯 · +增量语义"),
]

LADDER_INCREMENTS = {
    "BASELINE": "无额外技巧（裸 Prompt + 全量输入）",
    "P1": "累积 + 专家 Role（System Prompt）",
    "P2": "累积 + Structured 输出规范（字段 1～9）",
    "P3": "累积 + Chain-of-Thought 分步推理要求",
    "P4": "累积 + Few-shot 合成示范",
    "P5": "累积 + 可信性约束（禁止编造、须说明不确定性）",
    "P6": "累积 + rawText 增量语义（创新点）",
}


def _setup_backend_imports():
    if str(BACKEND_DIR) not in sys.path:
        sys.path.insert(0, str(BACKEND_DIR))


def load_reference_sample(path: Path) -> dict[str, Any]:
    data = json.loads(path.read_text(encoding="utf-8"))
    if not isinstance(data, list) or not data:
        raise ValueError(f"样本文件无效: {path}")
    return data[0]


def build_prompt_pair(group: str, sample: dict[str, Any]) -> tuple[str, str]:
    _setup_backend_imports()
    from main import AccidentAnalyzeRequest, build_prompt_a, build_prompt_b, build_prompt_c
    from prompt_ladder import SYSTEM_GENERIC, SYSTEM_ROLE, build_prompt_variant, is_ladder_variant

    payload = AccidentAnalyzeRequest.model_validate(
        {**sample, "experimentGroup": group, "ablationMode": "D0"}
    )
    g = group.upper()
    if g == "A":
        return SYSTEM_A, build_prompt_a(payload)
    if g == "B":
        return SYSTEM_B, build_prompt_b(payload)
    if is_ladder_variant(g):
        user, system = build_prompt_variant(g, payload)
        return system, user
    # C 及默认
    return SYSTEM_C, build_prompt_c(payload)


def md_code_block(text: str, lang: str = "text") -> str:
    body = text.replace("```", "``\\`")
    return f"```{lang}\n{body}\n```"


def build_markdown(sample: dict[str, Any], sample_path: Path) -> str:
    event_id = sample.get("eventId", "")
    scenario = sample.get("scenarioName") or sample.get("summary", "")[:40]
    now = datetime.now(timezone.utc).strftime("%Y-%m-%d %H:%M UTC")
    try:
        sample_display = sample_path.resolve().relative_to(ROOT.resolve()).as_posix()
    except ValueError:
        sample_display = sample_path.as_posix()

    lines: list[str] = [
        "# 实验组提示词全集",
        "",
        "## 文档说明",
        "",
        f"- **生成时间**：{now}",
        f"- **参考样本**：`{event_id}`（{scenario}）",
        f"- **样本文件**：`{sample_display}`",
        "- **消融模式**：全部为主实验 **D0**（完整输入 / 完整规范）",
        "- **组别数量**：10 组（A/B/C + Baseline/P1～P6）；**C_D0 为当前项目生产 Prompt**",
        "- **P 系列**：各组使用相同全量结构化输入（`shared_input_blocks`），仅 Prompt 指令累积叠加",
        "- **说明**：下文 User Prompt 为参考样本实例化后的完整文本；换样本时仅「输入块」部分变化，指令部分不变",
        "",
        "## 组别总览",
        "",
        "| 组别 | 实验条件 | 说明 | 累积增量 |",
        "| --- | --- | --- | --- |",
    ]

    for group, cond, label in EXPERIMENT_GROUPS:
        inc = LADDER_INCREMENTS.get(group, "—（系统方法组，输入信息量随 A/B/C 不同）")
        if group == "C":
            inc = "结构化全量输入 + 完整 13 条输出规范（= 生产版）"
        elif group == "A":
            inc = "极少输入 + 固定模板填充"
        elif group == "B":
            inc = "仅简要描述，无结构化责任/环境/决策链"
        lines.append(f"| {group} | {cond} | {label} | {inc} |")

    lines.extend(["", "---", ""])

    for idx, (group, cond, label) in enumerate(EXPERIMENT_GROUPS, start=1):
        system, user = build_prompt_pair(group, sample)
        lines.extend([
            f"## {idx}. {group}（{cond}）",
            "",
            f"**{label}**",
            "",
            "### System Prompt",
            "",
            md_code_block(system),
            "",
            "### User Prompt",
            "",
            md_code_block(user),
            "",
            "---",
            "",
        ])

    lines.extend([
        "## 附录：P 系列与 C 组共享输入块结构",
        "",
        "以下块由 `prompt_ladder.shared_input_blocks()` 生成，插入各 P 组 User Prompt 末尾（C 组 D0 同样使用）：",
        "",
        "1. **【事件】** — eventId / type / time / location / summary / triggers / severity / autoDrivingState",
        "2. **【责任分析】** — conclusion / driver% / system% / environment% / reasons",
        "3. **【环境】** — environment JSON",
        "4. **【决策链】** — decisionTrace JSON",
        "5. **【事故前关键遥测】** — 最多 20 条 telemetry 行",
        "6. **【端侧派生信号】** — derivedSignals JSON（TTC/AEB/制动/接管等）",
        "",
        "## 附录：C 组与 P6 输出规范差异摘要",
        "",
        "C 组 D0（生产版）在 P6 基础上额外强调：",
        "",
        "- evidencePoints 须逐条覆盖 reasons 关键证据（含标签与数值）",
        "- rootCause 主责任方向须与 responsibility 百分比最高项一致",
        "- comprehensiveAnalysis / evidencePoints 须引用端侧派生信号中的 TTC、AEB、制动、接管等指标",
        "",
    ])
    return "\n".join(lines)


def export_prompts_md(
    out_path: Path,
    sample_path: Path = DEFAULT_SAMPLES,
) -> Path:
    sample = load_reference_sample(sample_path)
    out_path.parent.mkdir(parents=True, exist_ok=True)
    out_path.write_text(build_markdown(sample, sample_path), encoding="utf-8")
    return out_path


def main() -> int:
    ap = argparse.ArgumentParser(description="导出实验组提示词 Markdown")
    ap.add_argument(
        "--out",
        type=Path,
        default=ROOT / "experiment_lab" / "results" / "实验结果汇总_20260519" / "00_阅读指南" / "实验组提示词全集.md",
    )
    ap.add_argument("--samples", type=Path, default=DEFAULT_SAMPLES)
    args = ap.parse_args()
    out = export_prompts_md(args.out.resolve(), args.samples.resolve())
    print("已生成：", out)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
