VehTrust 全量区块链测试包（含大模型复盘）
条数: 500
deviceId: VEHTRUST_001

数据来源:
  - 默认: 仓库 experiment_lab 下 cloud_experiment_results_*.json（真实历史 API 输出）
  - 原始事故/责任/环境: experiment_samples_realistic_50.json 按 eventId 对齐

是否要调后端:
  - 本包默认 【不需要】，模型回复已来自历史实验 JSON
  - 仅当需要全新回复时: 启动 backend + --call-backend --also-generate N

文件:
  dataset_full_500.json  — 完整记录（originalAccident/responsibility/aiAnalysis/aiMeta）
  blockchain_upload_500.json — 上链 POST 数组（含 rootCause/comprehensiveAnalysis/rawText 等）
  blockchain_upload_500.jsonl — 每行一条 POST
  dataset_summary_500.csv — 摘要表

批量上链:
  powershell -File ..\batch_upload.ps1 -Jsonl blockchain_upload_500.jsonl