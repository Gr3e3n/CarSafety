VehTrust 区块链批量测试数据
生成时间: 20260523_005700
条数: 300
deviceId: VEHTRUST_001

文件说明:
  1. accidents_full_300.json — 完整事故（遥测/环境/责任/复盘全文）
  2. blockchain_upload_300.json — 上链 POST 数组（与 App BlockchainApi 兼容并扩展字段）
  3. blockchain_upload_300.jsonl — 每行一条 POST JSON，适合脚本批量 curl
  4. accidents_summary_300.csv — 表格预览（Excel 可打开）

单条上链示例（网关默认 http://192.168.119.128:8080/upload）:
  curl -X POST http://192.168.119.128:8080/upload ^
    -H "Content-Type: application/json" ^
    -d @"blockchain_upload_300.jsonl"  REM 需取 jsonl 中单行

批量上链: 在本目录执行
  powershell -File ..\batch_upload.ps1 -Jsonl .\blockchain_upload_300.jsonl

类型分布: {'COLLISION': 42, 'AUTOPILOT_FAULT': 36, 'DRIVER_SLOW_REACTION': 36, 'AEB_DELAY_OR_MISS': 36, 'TTC_LOW_RISK': 36, 'DRIVER_TAKEOVER_FAIL': 36, 'ENVIRONMENT_DISTURB': 36, 'MULTI_FACTOR': 42}