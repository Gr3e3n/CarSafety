# 与 CarScreen 仓库「区块链 API」提交对齐说明

远程仓库：<https://github.com/Gr3e3n/CarScreen>

同名提交（说明均为 **「更新了UI界面并添加了区块链API」**）在 Git 历史里出现两次：

| 提交 | 内容要点 |
|------|----------|
| **`e41aebe`** | **Android**：`INTERNET` + `usesCleartextTraffic`、详情页上链 UI 与逻辑、`BlockchainApi.kt`（HTTP POST `/upload`）、`AccidentTraceViewModel.uploadToBlockchain` |
| **`0eef728`** | **链侧**：`chaincode_and_API/`（`carscreen-api`、`mychaincode`、README 等）；与当前 `sax_0522_1/chaincode_and_API` 已同构，一般无需再拷贝 |

本工程 **VehTrust**（`com.csa.chesuan`）已与 **`e41aebe` 的协议与设备号** 对齐：`deviceId = CARSCREEN_001`，请求体字段与网关 `UploadVehicleData` 一致；并在 `BlockchainApi` 中保留更强能力（HTTP 状态码、`txId` 解析、错误信息、模拟器 `10.0.2.2`）。

## 你以后如何自己拉队友最新改动

在项目旁克隆官方历史（任选目录）：

```powershell
cd D:\Users\Desktop
git clone https://github.com/Gr3e3n/CarScreen.git CarScreen-upstream
cd CarScreen-upstream
git log --oneline --grep="区块链" 
git show e41aebe --stat
```

把 **app 模块**里与溯源/区块链相关的改动，对照合并进 `sax_0522_1/app/...`，注意包名从 `carscreen` 改成 `vehtrust`。

也可给嵌入式副本加远程跟踪（路径以你仓库内 `CarScreen/CarScreen-main` 为准）：

```powershell
cd D:\Users\Desktop\sax_0522_1\CarScreen\CarScreen-main
git remote add origin https://github.com/Gr3e3n/CarScreen.git
git fetch origin
git log origin/main --oneline -30
```

## 模拟器与 BASE_URL

- 队友 **`e41aebe`** 里写死：`http://192.168.119.128:8080`（局域网网关）。
- **Android Studio 模拟器访问本机**应使用：`http://10.0.2.2:8080`（当前 `BlockchainApi` 默认值）。
