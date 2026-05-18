# -*- coding: utf-8 -*-
"""根据 assets 中 50 条样本生成《云端事故复盘实验数据记录表（预填版）.md》。"""
from __future__ import annotations

import json
import re
from collections import Counter
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
SAMPLES = ROOT / "app" / "src" / "main" / "assets" / "experiment_samples_realistic_50.json"
OUT = Path(__file__).resolve().parent / "云端事故复盘实验数据记录表（预填版）.md"

TYPE_CN = {
    "COLLISION": "碰撞/接触类",
    "AUTOPILOT_FAULT": "自动驾驶功能异常",
    "DRIVER_SLOW_REACTION": "驾驶员反应不足",
    "AEB_DELAY_OR_MISS": "AEB 触发延迟或未触发",
    "TTC_LOW_RISK": "TTC 过低碰撞风险",
    "DRIVER_TAKEOVER_FAIL": "驾驶员接管不足",
    "ENVIRONMENT_DISTURB": "环境因素干扰",
    "MULTI_FACTOR": "多因素共同作用",
}


def main_factor_from_conclusion(conc: str) -> str:
    if "多因素" in conc or "共同" in conc:
        return "多因素"
    if "系统" in conc and ("主要" in conc or "为主" in conc):
        return "系统"
    if "环境" in conc and "主要" in conc:
        return "环境"
    if "驾驶员" in conc and ("主要" in conc or "为主" in conc):
        return "驾驶员"
    return "多因素"


def shorten_evid(reasons: list[str], n: int = 3) -> str:
    parts = []
    for r in reasons[:n]:
        t = re.sub(r"\s+", " ", r.strip())
        if len(t) > 100:
            t = t[:99] + "…"
        parts.append(t)
    return "；".join(parts)


def standard_text(x: dict) -> str:
    summ = x.get("summary", "")
    conc = (x.get("responsibility") or {}).get("conclusion", "")
    txt = f"{summ}。{conc}"
    return txt[:152] + ("…" if len(txt) > 152 else "")


def main() -> None:
    arr = json.loads(SAMPLES.read_text(encoding="utf-8"))
    n = len(arr)
    assert n == 50, f"expected 50 samples, got {n}"

    lines: list[str] = []
    ap = lambda *a: lines.append("".join(a))

    ap("# 溯安行 · 云端事故复盘实验数据记录表（预填版）\n\n")
    ap("> **依据**：《云端事故复盘能力实验说明文档.md》全文（实验名称 §1、目的 §2、对比 §3、数据 §4、标注 §5、步骤 §6、指标 §7、消融 §9、工程衔接 §13）。\n\n")
    ap("> **预填说明**：下列「固定项」「样本元数据」「金标准（来自 JSON 生成字段）」可在**不跑模型**时写好；**\(R_e\)、责任一致、事实错误、可读性及汇总**须跑批后对照输入与金标准人工（或经复核）填写。`Re_assist` 仅可用 `score_with_gold_assist.py` **辅助**，不可替代正式 \(R_e\)（见说明文档 §13.7）。\n\n")
    ap("---\n\n## 〇、实验元信息（部分预填）\n\n")
    ap("| 项目 | 内容 |\n| ---- | ---- |\n")
    ap("| 实验名称 | 基于关键证据保持与责任一致性的云端事故复盘能力评估实验 |\n")
    ap("| 文档版本 / 填表日期 | v1.0 / 2026-05-19（按实修改） |\n")
    ap("| 评估集规模 \\(N\\) | **50** |\n")
    ap("| 解码温度 | **0.2**（后端 `/api/accident/analyze`，与说明文档 §13.1 一致） |\n")
    ap("| 结构化样本路径（仓库内） | `VehTrust0518/app/src/main/assets/experiment_samples_realistic_50.json`（与 `VehTrust0518/backend/` 下同名文件同源） |\n")
    ap("| 云端模型（厂商 + 型号） | **待填**（与 `backend/.env` 中 `OPENAI_MODEL` 一致） |\n")
    ap("| 后端 / App 版本或 Git 提交 | **待填** |\n")
    ap("| 评审人数 / 方式 | **待填**（说明文档 §5.1 建议 3 人；单人须注明「非盲评」局限，§13.6） |\n")
    ap("| 填写人 / 复核人 | **待填** |\n\n---\n\n")

    ap("## 一、实验目的与评价重点（摘自说明文档 §2，可不改）\n\n")
    ap("- 云端 AI 是否覆盖事故中的**关键证据点**；\n")
    ap("- 生成的主要**责任因素**是否与金标准一致；\n")
    ap("- 是否出现与**输入不符**的事实陈述；\n")
    ap("- **结构化输入**是否提升复盘质量；\n")
    ap("- **本项目方法（C）**是否优于 A（模板）、B（通用提示）。\n\n---\n\n")

    ap("## 二、对比方案 A / B / C（说明文档 §3.2）\n\n")
    ap("| 组别 | 方法 | 说明 |\n| ---- | ---- | ---- |\n")
    ap("| A 组 | 模板生成 | 固定规则拼接摘要、责任说明与建议 |\n")
    ap("| B 组 | 通用大模型 | 仅简要描述 + 基础状态，弱化结构化分析结果 |\n")
    ap("| C 组 | 本项目方法 | 完整结构化输入 + 项目内提示模板 |\n\n---\n\n")

    ap("## 三、消融设计 D0～D4（说明文档 §9）\n\n")
    ap("| 方案 | 移除内容 | 重点观察指标 | 验证目的 |\n")
    ap("| ---- | -------- | ------------ | -------- |\n")
    ap("| D0 | 无（完整 C 输入） | 作基线 | 与 D1～D4 对比 |\n")
    ap("| D1 | 责任分析结果 | \\(A_r\\)、可读性 | 结构化责任是否提升判断质量 |\n")
    ap("| D2 | 环境信息 | 根因质量、\\(E_f\\) | 环境上下文是否抑制遗漏与幻觉 |\n")
    ap("| D3 | 决策链 | \\(R_e\\) | 决策过程是否增强证据覆盖 |\n")
    ap("| D4 | 弱化结构化提示 | \\(E_f\\)、\\(R_e\\) | 结构化提示是否抑制幻觉 |\n\n---\n\n")

    ap("## 四、评价指标与计分约定（说明文档 §7）\n\n")
    ap("- **\\(R_e = N_{\\mathrm{hit}} / N_{\\mathrm{gold}}\\)**：\\(N_{\\mathrm{gold}}\\) 见下节金标准表「证据条数」。\n")
    ap("- **责任因子一致率 \\(A_r\\)**：主实验汇总用「一致样本数 / \\(N\\)」。\n")
    ap("- **\\(E_f = N_{\\mathrm{wrong}} / N_{\\mathrm{claim}}\\)**（\\(N_{\\mathrm{claim}}=0\\) 时填「—」并备注）。\n")
    ap("- **可读性**：建议 1～5 分，或四维（清晰 / 结构 / 友好 / 追责）均分，团队内统一口径后记在「备注」。\n\n")
    ap("**计分约定（预填）**：事实陈述拆分规则由团队事先书面约定；单人实验建议保留打分依据（截图或批注）。\n\n---\n\n")

    ap("## 五、事故类型分布自检（本评估集预填）\n\n")
    cnt = Counter(x["eventType"] for x in arr)
    ap("| `eventType` | 中文含义 | 本集条数 |\n| ------------- | -------- | -------- |\n")
    for k in [
        "COLLISION",
        "AUTOPILOT_FAULT",
        "DRIVER_SLOW_REACTION",
        "AEB_DELAY_OR_MISS",
        "TTC_LOW_RISK",
        "DRIVER_TAKEOVER_FAIL",
        "ENVIRONMENT_DISTURB",
        "MULTI_FACTOR",
    ]:
        ap(f"| `{k}` | {TYPE_CN[k]} | {cnt[k]} |\n")
    ap("\n---\n\n## 六、样本登记表（S01 = JSON 第 1 条 … S50 = 第 50 条）\n\n")
    ap("| 样本 | `eventId` | `eventType` | 中文类型 | `scenarioName` | 结构化 JSON 路径 |\n")
    ap("| ---- | --------- | ----------- | -------- | ---------------- | ---------------- |\n")
    path_full = "`VehTrust0518/app/src/main/assets/experiment_samples_realistic_50.json`（S01=第1条…S50=第50条）"
    for i, x in enumerate(arr):
        sid = f"S{i+1:02d}"
        eid = x["eventId"]
        et = x["eventType"]
        sn = (x.get("scenarioName") or "").replace("|", "｜")
        p = path_full if i == 0 else "同上"
        ap(f"| {sid} | `{eid}` | `{et}` | {TYPE_CN.get(et, et)} | {sn} | {p} |\n")
    ap("\n---\n\n")

    ap("## 七、金标准明细（预填来源：样本 JSON 中 `responsibility` + `summary`）\n\n")
    ap("> **说明**：证据点与条数取自 `responsibility.reasons`；「主要责任因素」由 `conclusion` 归纳到「驾驶员 / 系统 / 环境 / 多因素」四选一（含「共同」归为「多因素」）。**正式答辩或论文提交前请人工复核**是否改写措辞。\n\n")
    ap("| 样本 | 关键证据点（前 3 条摘要） | \\(N_{\\mathrm{gold}}\\) | 主要责任因素 | 标准复盘文本（摘录） |\n")
    ap("| ---- | -------------------------- | ------------------------ | -------------- | -------------------- |\n")
    for i, x in enumerate(arr):
        sid = f"S{i+1:02d}"
        r = x.get("responsibility") or {}
        reasons = list(r.get("reasons") or [])
        ng = len(reasons)
        mf = main_factor_from_conclusion(r.get("conclusion", ""))
        ev = shorten_evid(reasons).replace("|", "｜")
        st = standard_text(x).replace("|", "｜")
        ap(f"| {sid} | {ev} | {ng} | {mf} | {st} |\n")
    ap("\n---\n\n")

    ap("## 八、标注与一致性（不做实验可预填单人占位）\n\n")
    ap("| 标注员 | 角色 |\n| ------ | ---- |\n")
    ap("| R1 | 项目内（样本与工程操作） |\n")
    ap("| R2 | —（未参与时填「—」） |\n")
    ap("| R3 | — |\n\n")
    ap("**分歧处理**：单人流程无交叉评审；若增加第二标注员，在此补充纪要。\n\n---\n\n")

    ap("## 九、主实验结果明细（每样本 × 每方法；指标列 **跑批后填**）\n\n")
    ap("| 样本 | 方法 | \\(R_e\\) | 责任一致 | \\(N_{\\mathrm{wrong}}\\) | \\(N_{\\mathrm{claim}}\\) | \\(E_f\\) | 可读性 | 备注 |\n")
    ap("| ---- | ---- | ------- | -------- | ---------------------- | ---------------------- | ------- | ------ | ---- |\n")
    for i in range(50):
        sid = f"S{i+1:02d}"
        for m in ("A", "B", "C"):
            ap(f"| {sid} | {m} | 待实验填写 | 待实验填写 | 待实验填写 | 待实验填写 | 待实验填写 | 待实验填写 |  |\n")
    ap("\n---\n\n## 十、主实验汇总（跑批后由 §九 汇总）\n\n")
    ap("| 方法 | 平均 \\(R_e\\) | \\(A_r\\) | 平均 \\(E_f\\) | 平均可读性 |\n| ---- | ------------ | ------- | ------------ | ---------- |\n")
    ap("| A | 待实验汇总 | 待实验汇总 | 待实验汇总 | 待实验汇总 |\n")
    ap("| B | 待实验汇总 | 待实验汇总 | 待实验汇总 | 待实验汇总 |\n")
    ap("| C | 待实验汇总 | 待实验汇总 | 待实验汇总 | 待实验汇总 |\n\n---\n\n")

    ap("## 十一、消融实验汇总（相对 C+D0；跑批后填）\n\n")
    ap("| 条件 | 平均 \\(R_e\\) | \\(A_r\\) | 平均可读性 | 平均 \\(E_f\\) | 结果 JSON 归档路径 |\n")
    ap("| ---- | ------------ | ------- | ---------- | ------------ | -------------------- |\n")
    for d in ["D0（完整）", "D1", "D2", "D3", "D4"]:
        ap(f"| C + {d} | 待实验汇总 | 待实验汇总 | 待实验汇总 | 待实验汇总 | 待填：`cloud_experiment_results_C_*.json` |\n")
    ap("\n---\n\n## 十二、结果分析提纲（说明文档 §10；有数据后填写）\n\n")
    ap("- 三组 \\(R_e\\) 对比：  \n- 责任因子一致率：  \n- 事实错误与 \\(E_f\\)：  \n- 可读性：  \n- 最易误判的事故类型：  \n\n---\n\n")

    ap("## 十三、如何做实验并填写「待实验」列（操作清单）\n\n")
    ap("### 13.1 环境\n\n")
    ap("1. 在 `VehTrust0518/backend/` 配置 `.env`（`OPENAI_API_KEY`，可选 `OPENAI_MODEL`、`OPENAI_BASE_URL`），启动 `uvicorn`（默认 **8080**）。\n")
    ap("2. Android 模拟器访问本机后端：**`10.0.2.2:8080`**；真机改为电脑局域网 IP。\n\n")
    ap("### 13.2 主实验（说明文档 §6 步骤 3 + §13.2）\n\n")
    ap("1. 打开 **事故溯源详情页** →「AI 事故原因分析」选 **A**，消融选 **D0**。\n")
    ap("2. 返回 **事故溯源列表页** → 点击 **「批量跑 50 条并保存结果」**。\n")
    ap("3. 在 `Android/data/<包名>/files/`（或 Device File Explorer）找到 **`cloud_experiment_results_A_D0_*.json`**，拷贝到电脑并改名归档。\n")
    ap("4. 重复步骤 1～3，分别选 **B**、**C**（均 **D0**），得到另两份 JSON。\n")
    ap("5. 在 `backend/` 执行：`python export_batch_results_to_csv.py <某.json> <某.csv>`，用 Excel 打开，对照 **§七 金标准** 填写 CSV 中「Re_人工填」等列，再抄回 **§九** 表格（或直接维护 CSV）。\n")
    ap("6. （可选）`experiment_lab/score_with_gold_assist.py` 生成 **Re_assist** 作初筛，**最终以人工判定 \\(R_e\\) 为准**。\n\n")
    ap("### 13.3 消融实验（说明文档 §9 + §13.3）\n\n")
    ap("1. 详情页固定选 **C 组**，消融依次选 **D0**（若主实验已跑可复用）、**D1、D2、D3、D4**。\n")
    ap("2. 每选一种消融，回列表执行一次 **批量跑 50 条**，各保存一份 `cloud_experiment_results_C_Dx_*.json`。\n")
    ap("3. 与 **C+D0** 对比填写 **§十一**。\n\n")
    ap("### 13.4 盲评（说明文档 §6 步骤 4～5、§11）\n\n")
    ap("- 将 A/B/C 输出**隐去组别标签**后再评分；单人可采用「隔日打乱顺序自评」并在 **〇** 中注明局限。\n\n")
    ap("### 13.5 合规（说明文档 §11）\n\n")
    ap("- 云端 AI 复盘结果**仅作辅助解释与工程分析**，不构成司法意义上的最终责任裁定。\n\n---\n\n")
    ap("*本表由 `experiment_lab/_gen_data_record_template.py` 根据当前 `experiment_samples_realistic_50.json` 自动生成；重新生成样本后请重新运行脚本更新 §六、§七。*\n")

    raw = "".join(lines)
    while "\n\n\n\n" in raw:
        raw = raw.replace("\n\n\n\n", "\n\n\n")
    OUT.write_text(raw, encoding="utf-8")
    print("Wrote", OUT, "lines", len(lines))


if __name__ == "__main__":
    main()
