# 后端 OpenAI 使用说明

本文档用于说明如何在本项目中启动 `backend/main.py` 并接入 OpenAI 进行事故分析。

## 1. 环境要求

- Python 3.10+
- 可访问 OpenAI 接口的网络环境
- 已获取可用的 OpenAI API Key

## 2. 安装依赖

在项目根目录下执行（Windows PowerShell）：

```powershell
cd d:\...\VehTrust-main-pro\backend
pip install -r requirements.txt
```

## 3. 配置环境变量

在 `backend` 目录创建 `.env` 文件：

```env
OPENAI_API_KEY=你的OpenAI密钥
OPENAI_MODEL=gpt-4o-mini
```

说明：
- `OPENAI_API_KEY`：必填，不配置会返回 `OPENAI_API_KEY 未配置`。
- `OPENAI_MODEL`：可选，默认 `gpt-4o-mini`。

## 4. 启动后端服务

```powershell
cd d:\...\VehTrust-main-pro\backend

python -m uvicorn backend.main:app --host 0.0.0.0 --port 8080 --reload
```

可查看地址：`http://127.0.0.1:8080/health`

## 5. 接口快速验证

### 5.1 健康检查

```powershell
curl http://127.0.0.1:8080/health
```

期望返回示例：

```json
{"ok":true,"model":"gpt-4o-mini"}
```

### 5.2 AI 分析请求

```powershell
curl -X POST "http://127.0.0.1:8080/api/accident/analyze" ^
  -H "Content-Type: application/json" ^
  -d "{\"eventId\":\"evt-001\",\"eventType\":\"COLLISION\",\"timeMillis\":1710000000000,\"location\":\"测试路段\",\"summary\":\"测试事故\",\"triggerReasons\":[\"急减速\"],\"severity\":\"HIGH\",\"autoDrivingState\":\"ENABLED\",\"telemetry\":[],\"responsibility\":{\"driverFactor\":40,\"systemFactor\":50,\"environmentFactor\":10,\"conclusion\":\"系统主责\",\"reasons\":[\"制动延迟\"]},\"environment\":null,\"decisionTrace\":null}"
```

返回字段包含：`summary`、`rootCause`、`comprehensiveAnalysis`、`scenarioReconstruction`、`confidenceStatement`、`evidencePoints`、`suggestions`、`modelHint`、`rawText`。

其中 `modelHint` 当前固定格式为：`模型说明：{OPENAI_MODEL}`（例如 `模型说明：gpt-4o-mini`）。

## 6. 与 Android 端联调说明

Android 端 `OpenAiAnalysisApi.kt` 默认请求：

- `BASE_URL = http://10.0.2.2:8080`

说明：
- Android 模拟器访问宿主机本地服务应使用 `10.0.2.2`。
- 如果改了后端端口或地址，需要同步修改 `OpenAiAnalysisApi.kt`。

## 7. 常见问题

- **报错：OPENAI_API_KEY 未配置**
  - 检查 `.env` 是否放在 `backend` 目录，变量名是否正确。

- **报错：Connection refused / 无法连接后端**
  - 确认 `uvicorn` 已启动在 `8080` 端口。
  - 确认 Android 侧地址为 `10.0.2.2:8080`（模拟器场景）。

- **报错：请求 OpenAI 超时 / 502**
  - 检查网络、代理或网关配置。
  - 可先用 `gpt-4o-mini` 进行验证，必要时重试。

## 8. 生产建议

- 将 API Key 通过安全配置系统注入，不要硬编码入仓库。
- 关闭 `--reload`，并增加日志、限流、重试与异常监控。
- 对请求体做更严格的字段校验和审计。