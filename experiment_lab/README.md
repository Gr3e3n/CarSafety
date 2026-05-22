# 云端复盘实验工作台（主实验 A/B/C + 消融 D0–D4）

本目录脚本与模板对齐《云端事故复盘能力实验说明文档》**§3～§9、§13**，用于在跑完 App 批量或自建请求后：**归档、拉表、辅助统计**。

**一步步怎么做（环境 → 跑批 → 拷文件 → CSV → 填指标 → 消融 → 归档）**：请直接打开《[实验操作与填表全流程指南.md](./实验操作与填表全流程指南.md)》。

## 1. 样本与工程位置

| 内容 | 路径 |
| ---- | ---- |
| 50 条结构化样本（含金标准 `responsibility.reasons`） | `VehTrust0518/backend/experiment_samples_realistic_50.json` |
| App 内置同文件 | `VehTrust0518/app/src/main/assets/experiment_samples_realistic_50.json` |
| 重新生成样本 | `cd backend && python generate_experiment_samples.py` |

## 2. PC 一键提示词实验（推荐）

无需 Android App，在 PC 上自动跑 **系统方法 A/B/C** + **提示词阶梯 Baseline/P1～P6**，并生成统一对比报告：

```powershell
cd d:\Users\coco\Desktop\sax_0519_1
# 默认 10 组 × 50 条 ≈ 500 次 API
python experiment_lab/run_prompt_experiment.py

# 仅系统方法对比（150 次 API）
python experiment_lab/run_prompt_experiment.py --groups-method

# 仅提示词工程阶梯（400 次 API）
python experiment_lab/run_prompt_experiment.py --groups-prompt

# 仅分析已有 JSON（零 API 成本）
python experiment_lab/run_prompt_experiment.py --analyze-only --results-dir experiment_lab/results/run_xxx
```

| 实验维度 | 组别 | 说明 |
| --- | --- | --- |
| 系统方法对比 | A, B, C | 输入信息量可不同（对齐说明文档 §3.2） |
| 提示词工程阶梯 | BASELINE, P1～P6 | **固定全量输入**，仅 Prompt 累积叠加 |

P 系列累积顺序：Baseline → +Role → +Structured → +CoT → +Few-shot → +可信约束 → +增量语义。**C_D0 为当前项目生产 Prompt**。

输出目录 `experiment_lab/results/run_<timestamp>/analysis/`：
- **`unified_experiment_summary.csv`** — A/B/C + P 系列一张总表
- **`prompt_ladder_report.md`** — 阶梯边际增益（ΔRe、ΔQI、ΔToken）
- `prompt_experiment_report.md` / `condition_summary.csv` / `efficiency_summary.csv`

## 3. App 侧操作（模式切换 + 存结果）

1. 启动 `backend`（`uvicorn`，默认 **8080**），配置 `.env` 中 `OPENAI_API_KEY` 等。  
2. 打开 **事故溯源详情页**，在「AI 事故原因分析」中选 **A / B / C**，消融选 **D0～D4**（主实验建议三组均为 **D0**）。  
3. 返回 **事故溯源列表页**，点 **「批量跑 50 条并保存结果」**。  
4. 结果文件形如：`cloud_experiment_results_C_D0_yyyyMMdd_HHmmss.json`（含 **组别 + 消融**，便于文件夹归档）。  
5. 将文件拷到本机后，在本目录或 `backend` 下运行下方脚本。

## 4. 脚本说明

### 4.1 批量结果 → CSV（拉平模型输出）

```bash
cd ../backend
python export_batch_results_to_csv.py 你的/cloud_experiment_results_C_D0_xxx.json 输出.csv
```

### 4.2 与金标准合并 + **辅助**证据命中率（非最终 \(R_e\)）

说明文档中的 \(R_e\) 应以**人工或经复核的判据**为准。`score_with_gold_assist.py` 仅做**字符串级粗匹配**（每条 `reason` 若有一段连续字符出现在模型输出中则计 1 次命中），用于**初筛 / 对照**，不可替代盲评与人工表。

```bash
python score_with_gold_assist.py ../backend/experiment_samples_realistic_50.json ../backend/A_D0.json assist_A_D0.csv
```

### 4.3 单次跑批健康度摘要

```bash
python summarize_batch.py ../backend/cloud_experiment_results_C_D0_xxx.json
```

输出：条数、成功数、`evidencePoints` 平均长度等，用于检查是否大量解析失败。

## 5. 填表与预填表再生成

- **主填表（推荐）**：《[云端事故复盘实验数据记录表（预填版）.md](./云端事故复盘实验数据记录表（预填版）.md)》— 对齐说明文档全文结构，已预填样本登记表、金标准明细等；**§九～§十一** 待跑批后填写。  
- **精简工作台**：《[复盘与消融实验记录（工作台模板）.md](./复盘与消融实验记录（工作台模板）.md)》  
- **若你重新生成了 `experiment_samples_realistic_50.json`**，在本目录执行：  
  `python _gen_data_record_template.py`  
  可刷新《云端事故复盘实验数据记录表（预填版）.md》中的 **§六、§七**。

## 6. 合规

模型输出不得直接作为司法定责结论；辅助脚本命中率高也不代表语义正确，正式报告须写清局限（见说明文档 **§11**）。
