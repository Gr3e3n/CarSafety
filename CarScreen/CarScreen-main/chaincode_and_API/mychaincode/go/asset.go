package main

import (
	"crypto/sha256"
	"encoding/json"
	"fmt"
	"time"

	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

// SmartContract 定义合约结构
type SmartContract struct {
	contractapi.Contract
}

// VehicleData 车辆数据结构（存入账本的完整对象）
type VehicleData struct {
	TxID        string            `json:"txId"`        // 链上交易ID
	Hash        string            `json:"hash"`        // 数据哈希
	Timestamp   string            `json:"timestamp"`   // 上链时间
	DeviceID    string            `json:"deviceId"`    // 车机设备ID
	DataPayload map[string]string `json:"dataPayload"` // 车辆数据键值对
}

// UploadVehicleData 上传车辆数据并返回 Hash
// 参数:
//   deviceId - 设备ID，例如 "CARSCREEN_001"
//   dataJson - 车辆数据JSON字符串，例如 {"speed":"80","rpm":"3000"}
func (s *SmartContract) UploadVehicleData(
	ctx contractapi.TransactionContextInterface,
	deviceId string,
	dataJson string,
) (string, error) {

	// --- 1. 解析传入的车辆数据 ---
	var dataPayload map[string]string
	if err := json.Unmarshal([]byte(dataJson), &dataPayload); err != nil {
		return "", fmt.Errorf("解析数据失败: %v", err)
	}

	// --- 2. 获取链上交易ID（确定性）---
	txID := ctx.GetStub().GetTxID()

	// --- 3. 获取交易时间戳（确定性，所有 Peer 一致）---
	txTimestamp, err := ctx.GetStub().GetTxTimestamp()
	if err != nil {
		return "", fmt.Errorf("获取交易时间戳失败: %v", err)
	}
	timestamp := time.Unix(txTimestamp.Seconds, int64(txTimestamp.Nanos)).UTC().Format(time.RFC3339)

	// --- 4. 计算 SHA-256 Hash（使用 txID 保证确定性）---
	// ✅ 使用 txID 替代 time.Now()，确保所有 Peer 计算结果一致
	hashInput := deviceId + dataJson + txID
	hashBytes := sha256.Sum256([]byte(hashInput))
	hashStr := fmt.Sprintf("%x", hashBytes)

	// --- 5. 构建完整存储对象 ---
	vehicleData := VehicleData{
		TxID:        txID,
		Hash:        hashStr,
		Timestamp:   timestamp,
		DeviceID:    deviceId,
		DataPayload: dataPayload,
	}

	// --- 6. 序列化为 JSON 并写入账本 ---
	dataBytes, err := json.Marshal(vehicleData)
	if err != nil {
		return "", fmt.Errorf("序列化失败: %v", err)
	}

	// 以 Hash 值作为 Key 存储到账本
	if err := ctx.GetStub().PutState(hashStr, dataBytes); err != nil {
		return "", fmt.Errorf("写入账本失败: %v", err)
	}

	// --- 7. 返回 Hash 值给调用方 ---
	return hashStr, nil
}

// QueryVehicleData 根据 Hash 查询车辆数据
// 参数:
//   hash - 上链时返回的 Hash 值
func (s *SmartContract) QueryVehicleData(
	ctx contractapi.TransactionContextInterface,
	hash string,
) (*VehicleData, error) {

	// 从账本读取数据
	dataBytes, err := ctx.GetStub().GetState(hash)
	if err != nil {
		return nil, fmt.Errorf("查询账本失败: %v", err)
	}
	if dataBytes == nil {
		return nil, fmt.Errorf("未找到 Hash 为 [%s] 的数据", hash)
	}

	// 反序列化
	var vehicleData VehicleData
	if err := json.Unmarshal(dataBytes, &vehicleData); err != nil {
		return nil, fmt.Errorf("反序列化失败: %v", err)
	}

	return &vehicleData, nil
}

func main() {
	chaincode, err := contractapi.NewChaincode(&SmartContract{})
	if err != nil {
		panic(fmt.Sprintf("创建链码失败: %v", err))
	}
	if err := chaincode.Start(); err != nil {
		panic(fmt.Sprintf("启动链码失败: %v", err))
	}
}
