package com.example.vehtrust.trace

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.net.UnknownHostException
import java.io.OutputStreamWriter

/**
 * 事故存证「上链」：通过 HTTP 调用 Fabric 侧的 **网关**（本仓库示例为 Go：`chaincode_and_API/carscreen-api`）。
 *
 * **当前逻辑**：向 `BASE_URL + ENDPOINT` 发送 `POST`，JSON 体为 `{ "deviceId", "data": { … } }`，
 * 与网关 `UploadRequest` 一致；成功后界面展示返回的 `hash`（及部分场景下的 `txId`）。
 *
 * **如何连接（正常接入）**
 * 1. 在本机或服务器启动 Hyperledger Fabric test-network + 部署链码，并启动网关（默认监听 **8080**）。
 * 2. 修改下面 [BASE_URL]：
 *    - Android **模拟器**访问电脑上的网关：**`http://10.0.2.2:8080`**
 *    - **真机**：改为电脑的局域网 IP，例如 **`http://192.168.x.x:8080`**（与电脑同一 WiFi，防火墙放行 8080）
 * 3. 勿与占用同端口的其它服务冲突（若 FastAPI 已占 8080，请给链网关换一个端口并把 [BASE_URL] 同步改掉）。
 *
 * **健康检查**：浏览器或 `curl` 访问 `GET {BASE_URL}/health` 应返回 `{"status":"ok"}`。
 */
object BlockchainApi {

    /** 按运行环境修改：模拟器常用 `10.0.2.2`，真机用电脑局域网 IP */
    private const val BASE_URL = "http://10.0.2.2:8080"
    private const val ENDPOINT = "/upload"
    private const val TIMEOUT_MS = 15_000

    /** 仅离线演示时设为 `true`，会跳过网络并本地生成占位哈希（勿用于真实存证） */
    private const val USE_LOCAL_MOCK = false

    data class UploadResult(
        val success: Boolean,
        val hash: String = "",
        val txId: String = "",
        val error: String = "",
    )

    suspend fun uploadAccident(bundle: AccidentDetailBundle): UploadResult =
        withContext(Dispatchers.IO) {
            if (USE_LOCAL_MOCK) return@withContext uploadAccidentMock(bundle)
            try {
                val body = buildJson(bundle)
                val (code, raw) = postWithStatus(body)
                parseHttpResponse(code, raw)
            } catch (e: Exception) {
                UploadResult(success = false, error = friendlyNetworkError(e))
            }
        }

    private fun uploadAccidentMock(bundle: AccidentDetailBundle): UploadResult {
        val mockHash =
            "0x" + bundle.event.id.hashCode().toUInt().toString(16).uppercase().padStart(8, '0') +
                "A" + System.currentTimeMillis().toUInt().toString(16).uppercase()
        return UploadResult(
            success = true,
            hash = mockHash,
            txId = "TX-" + System.currentTimeMillis().toString().takeLast(6),
        )
    }

    private fun buildJson(bundle: AccidentDetailBundle): String {
        val obj = JSONObject()
        obj.put("deviceId", "VEHTRUST_001")
        val data = JSONObject()
        data.put("eventId", bundle.event.id.toString())
        data.put("timeMillis", bundle.event.timeMillis.toString())
        data.put("location", bundle.event.locationText.toString())
        data.put("summary", bundle.event.summary.toString())
        data.put("driverFactor", bundle.responsibility.driverFactor.toString())
        data.put("systemFactor", bundle.responsibility.systemFactor.toString())
        data.put("envFactor", bundle.responsibility.environmentFactor.toString())
        data.put("conclusion", bundle.responsibility.conclusion.toString())
        obj.put("data", data)
        return obj.toString()
    }

    private fun postWithStatus(jsonBody: String): Pair<Int, String> {
        val url = URL("$BASE_URL$ENDPOINT")
        val conn = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = TIMEOUT_MS
            readTimeout = TIMEOUT_MS
            doOutput = true
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            setRequestProperty("Accept", "application/json")
        }
        OutputStreamWriter(conn.outputStream, Charsets.UTF_8).use {
            it.write(jsonBody)
            it.flush()
        }
        val code = conn.responseCode
        val text = readResponseBody(conn, code)
        return code to text
    }

    private fun readResponseBody(conn: HttpURLConnection, code: Int): String {
        val primary = if (code in HttpURLConnection.HTTP_OK..299) conn.inputStream else conn.errorStream
        return try {
            primary?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }
                ?: conn.inputStream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }
                ?: ""
        } catch (_: Exception) {
            ""
        }
    }

    private fun parseHttpResponse(code: Int, raw: String): UploadResult {
        if (code !in HttpURLConnection.HTTP_OK..299) {
            val msg = extractServerMessage(raw).ifBlank { raw.ifBlank { "HTTP $code" } }
            return UploadResult(success = false, error = msg)
        }
        return parseSuccessBody(raw)
    }

    private fun extractServerMessage(raw: String): String =
        try {
            val obj = JSONObject(raw)
            obj.optString("error").ifEmpty { obj.optString("message") }
        } catch (_: Exception) {
            ""
        }

    private fun parseSuccessBody(raw: String): UploadResult {
        return try {
            val obj = JSONObject(raw)
            val successFlag = obj.optBoolean("success", true)
            if (!successFlag) {
                val err = extractServerMessage(raw).ifEmpty { "服务器返回失败" }
                return UploadResult(success = false, error = err)
            }
            val hash = obj.optString("hash", "")
            val txId = obj.optString("txId", "").ifEmpty { obj.optString("tx_id", "") }
            if (hash.isNotEmpty()) {
                UploadResult(success = true, hash = hash, txId = txId)
            } else {
                UploadResult(success = false, error = "响应缺少 hash：$raw".take(500))
            }
        } catch (e: Exception) {
            UploadResult(success = false, error = "响应解析失败：${e.message ?: e.javaClass.simpleName}")
        }
    }

    private fun friendlyNetworkError(e: Exception): String =
        when (e) {
            is UnknownHostException ->
                "无法连接主机，请确认 BASE_URL（模拟器可用 10.0.2.2，真机用电脑 IP）且网关已启动"
            is SocketTimeoutException ->
                "请求超时（${TIMEOUT_MS}ms），请检查网络或增大网关超时"
            is IOException ->
                "网络错误：${e.message ?: e.javaClass.simpleName}"
            else -> e.message ?: "未知错误"
        }
}
