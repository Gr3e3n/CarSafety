# 区块链批量上链测试数据

本目录**独立于 App 业务代码**，用于给 Hyperledger Fabric 网关同学批量造数、上链压测。

---

## 新数据 500 条（模拟 + 调大模型，不合并历史）

请直接看：**`backend/模拟事故500条-操作步骤.md`**  
先 **终端 A 起 uvicorn**，再模拟 500 条，**默认 500 次独立 `POST /analyze` + 进度条**（见 `backend/模拟事故500条-操作步骤.md`）。

---

## 【旧说明】模拟事故 → 大模型复盘 → 导出（500 条示例）

### 第一步：配置大模型（只需一次）

在 `backend` 目录创建 `.env`（参考 `backend/OPENAI使用说明.md`）：

```env
OPENAI_API_KEY=你的密钥
OPENAI_MODEL=deepseek-v4-flash
OPENAI_BASE_URL=https://你的兼容接口/v1
OPENAI_BATCH_CONCURRENCY=4
```

### 第二步：一条命令跑完（推荐 CLI，不用先起 uvicorn）

```powershell
cd D:\Users\Desktop\sax_0522_1
pip install -r backend\requirements.txt
python blockchain_test_export/run_simulate_and_analyze.py --count 500
```

脚本会依次：

1. **模拟** 500 条事故（8 类：碰撞、智驾故障、反应不足、AEB、TTC、接管失败、环境、多因素），含遥测/环境/责任比例  
2. **调用** 与 App 相同的 `C + D0` 大模型 Prompt 做复盘  
3. **写出** `simulated_accidents_500.json`、`cloud_experiment_results_C_D0_*.json`、`blockchain_upload_500.jsonl`

只生成模拟、不调模型（调试结构用）：

```powershell
python blockchain_test_export/run_simulate_and_analyze.py --count 500 --skip-ai
```

### 可选：HTTP 模式（先启动后端）

```powershell
# 终端 1
cd D:\Users\Desktop\sax_0522_1\backend
python -m uvicorn main:app --host 0.0.0.0 --port 8080

# 终端 2
cd D:\Users\Desktop\sax_0522_1
python blockchain_test_export/run_simulate_and_analyze.py --count 500 --mode http
```

500 条会消耗较多 Token，耗时可数十分钟到数小时，取决于模型速度与并发。

---

## 要不要调后端 / 大模型？

| 需求 | 要不要调后端 | 怎么做 |
|------|----------------|--------|
| **大量数据 + 真实模型复盘**（summary / comprehensiveAnalysis / rawText 等） | **不需要** | 跑下面「全量合并」脚本，仓库里已有 **35 个实验 JSON ≈ 1750 条** 历史 API 输出 |
| **全新事故 + 全新模型回复** | **需要** | 启动 `backend`（配置 `OPENAI_API_KEY`），再加 `--call-backend --also-generate N` |

之前 `generate_blockchain_bulk_data.py` 生成的 300 条只有**规则模板复盘**，没有大模型字段。要做链上测试且字段齐全，请用 **`build_full_blockchain_dataset.py`**。

---

## 0. 全量数据（含大模型回复）— 推荐

```powershell
cd D:\Users\Desktop\sax_0522_1
python blockchain_test_export/build_full_blockchain_dataset.py
```

输出 `blockchain_test_export/output/full_<时间戳>/`：

| 文件 | 内容 |
|------|------|
| `dataset_full_<N>.json` | 原始事故 + 环境 + 决策链 + **责任比例** + **aiAnalysis 全字段** + token 元数据 |
| `blockchain_upload_<N>.jsonl` | 上链 POST（含 `comprehensiveAnalysis`、`rawText`、`rootCause` 等） |
| `dataset_summary_<N>.csv` | Excel 摘要 |

每条 `aiAnalysis` 含：`summary`, `rootCause`, `comprehensiveAnalysis`, `scenarioReconstruction`, `confidenceStatement`, `evidencePoints`, `suggestions`, `modelHint`, `rawText`。

若要**再生成 200 条并调后端拉新模型回复**（耗 API）：

```powershell
# 终端1
cd backend
uvicorn main:app --host 0.0.0.0 --port 8080

# 终端2
python blockchain_test_export/build_full_blockchain_dataset.py --also-generate 200 --call-backend
```

---

## 1. 仅规则复盘数据（300 条，无大模型）

```powershell
cd D:\Users\Desktop\sax_0522_1
python blockchain_test_export/generate_blockchain_bulk_data.py --count 300
```

可选参数：

| 参数 | 说明 |
|------|------|
| `--count 500` | 生成条数 |
| `--device-id VEHTRUST_001` | 与 App `BlockchainApi` 一致 |
| `--seed 20260522` | 固定随机种子，可复现 |

输出在 `blockchain_test_export/output/<时间戳>/`：

| 文件 | 内容 |
|------|------|
| `accidents_full_<N>.json` | **原始事故**：遥测、环境、决策链、责任比例、复盘全文 |
| `blockchain_upload_<N>.json` | 上链 POST 体数组 |
| `blockchain_upload_<N>.jsonl` | 每行一条 POST，供脚本批量提交 |
| `accidents_summary_<N>.csv` | Excel 可打开的摘要表 |

每条记录包含：

- **责任占比**：`driverFactor` / `systemFactor` / `environmentFactor`（合计 100%）
- **环境**：天气、道路、障碍、车道线
- **复盘全文**：`comprehensiveAnalysis`（多段结构化中文报告）
- **上链体**：对齐 `app/.../BlockchainApi.kt`，并扩展 `eventType`、`telemetryJson`、`comprehensiveAnalysis` 等字段

## 2. 批量上链（网关需已启动）

```powershell
cd blockchain_test_export\output\<你的时间戳目录>
powershell -ExecutionPolicy Bypass -File ..\batch_upload.ps1 -Jsonl blockchain_upload_300.jsonl
```

先试 10 条：

```powershell
powershell -ExecutionPolicy Bypass -File ..\batch_upload.ps1 -Jsonl blockchain_upload_300.jsonl -Limit 10
```

网关地址与 App 一致：`http://192.168.119.128:8080/upload`（可用 `-BaseUrl` 修改）。

## 3. 单条手工测试

```powershell
curl -X POST http://192.168.119.128:8080/upload `
  -H "Content-Type: application/json" `
  -d "{\"deviceId\":\"VEHTRUST_001\",\"data\":{\"eventId\":\"CHAIN-TEST-001\",\"timeMillis\":\"1740000000000\",\"location\":\"南京·雨花台区\",\"summary\":\"测试\",\"driverFactor\":\"55\",\"systemFactor\":\"30\",\"envFactor\":\"15\",\"conclusion\":\"测试结论\"}}"
```

## 4. 与现有 50 条实验样本的关系

- 逻辑复用 `backend/generate_experiment_samples.py`（与 App 责任分析规则一致）
- 事件 ID 使用 `CHAIN-YYYYMMDD-####`，**不会覆盖** `experiment_samples_realistic_50.json`
- 不修改 `app/src/main/assets/` 与任何 Kotlin 源码
