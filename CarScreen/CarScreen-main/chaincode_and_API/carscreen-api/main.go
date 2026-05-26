package main

import (
    "encoding/json"
    "fmt"
    "log"
    "net/http"
    "os/exec"
    "regexp"
    "strings"
)

// ── 配置（按你的实际路径修改）────────────────────────────────────
const (
    testNetworkDir = "/home/songju/go/src/github.com/hyperledger/fabric-samples/test-network"
    ordererCA      = testNetworkDir + "/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem"
    org1TLS        = testNetworkDir + "/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt"
    org2TLS        = testNetworkDir + "/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt"
    channelName    = "mychannel"
    chaincodeName  = "asset"
    listenPort     = ":8080"
)

// ── 请求/响应结构 ─────────────────────────────────────────────────

type UploadRequest struct {
    DeviceID string                 `json:"deviceId"`
    Data     map[string]interface{} `json:"data"`
}

type UploadResponse struct {
    Success bool   `json:"success"`
    Hash    string `json:"hash,omitempty"`
    TxID    string `json:"txId,omitempty"`
    Error   string `json:"error,omitempty"`
}

type QueryResponse struct {
    Success bool        `json:"success"`
    Record  interface{} `json:"record,omitempty"`
    Error   string      `json:"error,omitempty"`
}

// ── 主入口 ────────────────────────────────────────────────────────

func main() {
    http.HandleFunc("/upload", corsMiddleware(handleUpload))
    http.HandleFunc("/query", corsMiddleware(handleQuery))
    http.HandleFunc("/health", corsMiddleware(handleHealth))

    log.Printf("CarScreen API 服务启动，监听 %s", listenPort)
    if err := http.ListenAndServe(listenPort, nil); err != nil {
        log.Fatal(err)
    }
}

// ── 上链接口：POST /upload ─────────────────────────────────────────
// Body: {"deviceId":"CARSCREEN_001","data":{"speed":"80","brake":"60",...}}
// 返回: {"success":true,"hash":"0cd942...","txId":"e9070b..."}

func handleUpload(w http.ResponseWriter, r *http.Request) {
    if r.Method != http.MethodPost {
        jsonError(w, "仅支持 POST", http.StatusMethodNotAllowed)
        return
    }

    var req UploadRequest
    if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
        jsonError(w, "请求解析失败: "+err.Error(), http.StatusBadRequest)
        return
    }
    if req.DeviceID == "" {
        req.DeviceID = "CARSCREEN_001"
    }

    // 将 data map 序列化为 JSON 字符串
    dataBytes, err := json.Marshal(req.Data)
    if err != nil {
        jsonError(w, "数据序列化失败: "+err.Error(), http.StatusInternalServerError)
        return
    }
    dataJSON := string(dataBytes)

    // 构造 chaincode 调用参数
    ccArgs := fmt.Sprintf(
        `{"function":"UploadVehicleData","Args":["%s","%s"]}`,
        req.DeviceID,
        strings.ReplaceAll(dataJSON, `"`, `\"`),
    )

    // 执行 peer chaincode invoke
    cmd := exec.Command("peer", "chaincode", "invoke",
        "-o", "localhost:7050",
        "--ordererTLSHostnameOverride", "orderer.example.com",
        "--tls",
        "--cafile", ordererCA,
        "-C", channelName,
        "-n", chaincodeName,
        "--peerAddresses", "localhost:7051",
        "--tlsRootCertFiles", org1TLS,
        "--peerAddresses", "localhost:9051",
        "--tlsRootCertFiles", org2TLS,
        "-c", ccArgs,
    )
    cmd.Dir = testNetworkDir

    out, err := cmd.CombinedOutput()
    output := string(out)
    log.Printf("[upload] output: %s", output)

    if err != nil {
        jsonError(w, "链码调用失败: "+output, http.StatusInternalServerError)
        return
    }

    // 从输出中提取 payload（即 hash）
    hash := extractPayload(output)
    if hash == "" {
        jsonError(w, "未能提取 hash，原始输出: "+output, http.StatusInternalServerError)
        return
    }

    // 查询 txId
    txID := queryTxID(hash)

    jsonOK(w, UploadResponse{
        Success: true,
        Hash:    hash,
        TxID:    txID,
    })
}

// ── 查询接口：GET /query?hash=xxx ─────────────────────────────────

func handleQuery(w http.ResponseWriter, r *http.Request) {
    hash := r.URL.Query().Get("hash")
    if hash == "" {
        jsonError(w, "缺少 hash 参数", http.StatusBadRequest)
        return
    }

    ccArgs := fmt.Sprintf(`{"function":"QueryVehicleData","Args":["%s"]}`, hash)
    cmd := exec.Command("peer", "chaincode", "query",
        "-C", channelName,
        "-n", chaincodeName,
        "-c", ccArgs,
    )
    cmd.Dir = testNetworkDir

    out, err := cmd.CombinedOutput()
    output := string(out)
    log.Printf("[query] output: %s", output)

    if err != nil {
        jsonError(w, "查询失败: "+output, http.StatusInternalServerError)
        return
    }

    // 解析链上返回的 JSON
    var record interface{}
    if err := json.Unmarshal([]byte(strings.TrimSpace(output)), &record); err != nil {
        jsonError(w, "结果解析失败: "+output, http.StatusInternalServerError)
        return
    }

    jsonOK(w, QueryResponse{Success: true, Record: record})
}

// ── 健康检查：GET /health ─────────────────────────────────────────

func handleHealth(w http.ResponseWriter, r *http.Request) {
    jsonOK(w, map[string]string{"status": "ok"})
}

// ── 工具函数 ──────────────────────────────────────────────────────

// 从 peer invoke 输出中提取 payload 字段（即 hash）
func extractPayload(output string) string {
    re := regexp.MustCompile(`payload:"([^"]+)"`)
    m := re.FindStringSubmatch(output)
    if len(m) >= 2 {
        return strings.TrimSpace(m[1])
    }
    return ""
}

// 上链后查询 txId
func queryTxID(hash string) string {
    ccArgs := fmt.Sprintf(`{"function":"QueryVehicleData","Args":["%s"]}`, hash)
    cmd := exec.Command("peer", "chaincode", "query",
        "-C", channelName,
        "-n", chaincodeName,
        "-c", ccArgs,
    )
    cmd.Dir = testNetworkDir
    out, err := cmd.CombinedOutput()
    if err != nil {
        return ""
    }
    var m map[string]interface{}
    if err := json.Unmarshal([]byte(strings.TrimSpace(string(out))), &m); err != nil {
        return ""
    }
    if v, ok := m["txId"].(string); ok {
        return v
    }
    return ""
}

func jsonOK(w http.ResponseWriter, v interface{}) {
    w.Header().Set("Content-Type", "application/json")
    json.NewEncoder(w).Encode(v)
}

func jsonError(w http.ResponseWriter, msg string, code int) {
    w.Header().Set("Content-Type", "application/json")
    w.WriteHeader(code)
    json.NewEncoder(w).Encode(map[string]string{"error": msg})
}

func corsMiddleware(next http.HandlerFunc) http.HandlerFunc {
    return func(w http.ResponseWriter, r *http.Request) {
        w.Header().Set("Access-Control-Allow-Origin", "*")
        w.Header().Set("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
        w.Header().Set("Access-Control-Allow-Headers", "Content-Type")
        if r.Method == http.MethodOptions {
            w.WriteHeader(http.StatusOK)
            return
        }
        next(w, r)
    }
}
