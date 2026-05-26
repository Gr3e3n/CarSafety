# 车机系统区块链数据上链项目 (CarScreen Blockchain API)

本项目是一个基于 Hyperledger Fabric 的车机数据上链与查询系统。项目包含两个主要部分：
1. **智能合约 (Chaincode)**：负责将车机数据（如车速、转速等）进行 SHA-256 哈希处理并安全存入区块链账本。
2. **Go API 服务**：作为中间件，提供 HTTP 接口供车机 Android 端调用，实现数据的上链与查询。

## 🛠 环境要求

在部署本项目之前，请确保您的系统已安装以下依赖：
- [Docker](https://docs.docker.com/get-docker/) & Docker Compose
- [Go](https://golang.org/dl/) (推荐 1.19 或以上版本)
- [Hyperledger Fabric v2.x 环境及 fabric-samples](https://hyperledger-fabric.readthedocs.io/en/latest/install.html)

## 🚀 部署步骤

### 1. 准备 Fabric 环境与链码
首先，确保你已经克隆了官方的 `fabric-samples` 仓库。然后将本仓库中的链码复制到 `fabric-samples` 目录中：

```bash
# 假设你已经下载了 fabric-samples
# 将本项目的 mychaincode 文件夹复制到 fabric-samples 根目录下
cp -r ./mychaincode /path/to/fabric-samples/
```

### 2. 启动区块链网络并创建通道
进入 `fabric-samples/test-network` 目录，启动测试网络并创建名为 `mychannel` 的通道：

```bash
cd /path/to/fabric-samples/test-network
./network.sh up createChannel -c mychannel
```
*(如果通道已存在，可以直接运行 `./network.sh up`)*

### 3. 部署智能合约 (Chaincode)
在 `test-network` 目录下运行以下命令，将链码部署到通道中（链码名称指定为 `asset`）：

```bash
./network.sh deployCC \
  -c mychannel \
  -ccn asset \
  -ccp ../mychaincode/go \
  -ccl go
```

### 4. 配置环境变量
为了让 API 服务能够与区块链网络通信，需要导出 Org1 的环境变量。请在终端中执行以下命令（**注意替换 `/path/to/fabric-samples` 为你的实际路径**）：

```bash
export PATH=$PATH:/path/to/fabric-samples/bin
export FABRIC_CFG_PATH=/path/to/fabric-samples/config
export CORE_PEER_TLS_ENABLED=true
export CORE_PEER_LOCALMSPID="Org1MSP"
export CORE_PEER_TLS_ROOTCERT_FILE=/path/to/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
export CORE_PEER_MSPCONFIGPATH=/path/to/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
export CORE_PEER_ADDRESS=localhost:7051
```

### 5. 启动 API 服务
在**同一个配置了环境变量的终端**中，进入 API 服务目录并启动服务：

```bash
cd /path/to/你的仓库/carscreen-api
go run main.go
```
如果看到 `CarScreen API 服务启动，监听 :8080`，说明服务启动成功！

---

## 接口测试

服务启动后，可以通过 HTTP 请求测试数据上链功能。

### 1. 车辆数据上链 (POST)
将车机数据发送到 API，API 会将其上链并返回交易 Hash。

**请求命令：**
```bash
curl -X POST http://localhost:8080/upload \
  -H "Content-Type: application/json" \
  -d '{"deviceId":"CARSCREEN_001","data":{"speed":"80","rpm":"3000"}}'
```

**成功响应示例：**
```json
{
  "success": true,
  "hash": "56ee9d29a7459614bedf8efb995ec446cad25ceee07aa8c5931ca8d49ad9bf7c",
  "txId": "ad77a2809346203ea68beb744ae7204cf5d608212e73120a5f8f7e6415c21c9c"
}
```

*(注：返回的 `hash` 值可用于后续的数据溯源和查询验证)*

## 🛑 停止网络与清理
如果需要关闭区块链网络并清理容器数据，请在 `test-network` 目录下执行：
```bash
./network.sh down
```