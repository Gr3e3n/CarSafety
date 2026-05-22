# 实验量化指标说明

| 指标键 | 中文名 | 方向 | 类别 | 说明 |
| --- | --- | --- | --- | --- |
| `PQI_mean` | 生产质量指数 PQI | ↑ | 综合 | 生产质量指数：Re+结构+合规，并惩罚证据条数>金标准 |
| `Re_disciplined_micro` | 校准证据召回 Re* | ↑ | 质量 | Re × 证据校准分，抑制过度堆砌证据 |
| `evidence_calibration_mean` | 证据条数校准分 | ↑ | 质量 | 证据条数/金标准比越接近1.0越高 |
| `Re_assist_micro` | 证据召回率 Re | ↑ | 质量 | 金标准证据命中 micro 召回 |
| `responsibility_consistency` | 责任一致率 | ↑ | 质量 |  |
| `Ef_assist_micro` | 事实错误率 | ↓ | 质量 |  |
| `fact_consistency_rate` | 事实一致率(1-Ef) | ↑ | 质量 | 1 - 事实错误率（类别矛盾检测） |
| `readability_mean` | 可读性/5 | ↑ | 质量 |  |
| `structured_compliance_mean` | 结构化规范符合度 | ↑ | 质量 | 字段长度/条数规范符合度 0-1 |
| `field_completeness_rate` | 九字段齐全率 | ↑ | 质量 | 九 JSON 字段齐全比例 |
| `confidence_present_rate` | 置信度说明覆盖率 | ↑ | 质量 |  |
| `evidence_count_mean` | 证据条数均值 | ↑ | 质量 |  |
| `evidence_fill_ratio_mean` | 证据条数/金标准比(参考) | ↑ | 质量 | 输出证据条数 / 金标准条数 |
| `derived_signal_recall_mean` | 派生信号关键词召回 | ↑ | 质量 | 端侧派生信号在输出中的关键词召回 |
| `rawText_non_overlap_mean` | rawText增量非重复率 | ↑ | 质量 | P6 增量语义代理：rawText 相对前文非重复窗口比 |
| `analysis_depth_chars_mean` | 分析+复盘字符深度 | ↑ | 质量 |  |
| `suggestion_count_mean` | 建议条数均值 | ↑ | 质量 |  |
| `QI_mean` | 综合质量指数 QI | ↑ | 综合 |  |
| `VQI_mean` | 性价比 VQI | ↑ | 综合 |  |
| `Re_per_1k_tokens` | 每千Token证据召回 | ↑ | 综合 | 证据召回 / (平均总Token/1000) |
| `QI_per_1k_tokens` | 每千Token质量分 | ↑ | 综合 | QI / (平均总Token/1000) |
| `success_rate` | 解析成功率 | ↑ | 稳定 |  |
| `retry_rate` | JSON重试率 | ↓ | 稳定 |  |
| `latency_ms_mean` | 平均延迟ms | ↓ | 效率 |  |
| `total_tokens_mean` | 平均总Token | ↓ | 效率 |  |
| `tokens_per_evidence_hit` | 每命中1证据Token | ↓ | 效率 | 总Token/证据命中数；零命中时为 N/A |
| `completion_to_prompt_ratio` | 输出/输入Token比 | ↑ | 效率 |  |
