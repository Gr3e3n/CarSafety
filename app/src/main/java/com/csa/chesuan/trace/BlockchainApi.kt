package com.csa.chesuan.trace

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
 * 事故存证「上链」HTTP 客户端（`chaincode_and_API/carscreen-api`）。
 *
 * **默认 `BASE_URL`**：`http://127.0.0.1:8080` —— 配合 **ADB 反向端口映射**（见仓库根目录 `修改.md`）：
 * 在 **Android Automotive / 部分车机模拟器** 上 `10.0.2.2` 不可用（`Network is unreachable`），改用本机环回地址 +
 * `adb reverse tcp:8080 tcp:8080`，将设备内 `127.0.0.1:8080` 转到宿主机 `8080`（再可由宿主机转发至 Linux 虚拟机网关）。
 *
 * 其它环境可改 [BASE_URL]：**经典 AVD** 可用 `http://10.0.2.2:8080`；**真机** 用电脑局域网 IP，例如 `http://192.168.x.x:8080`。
 */
object BlockchainApi {

    /** 与队友 CarScreen **`e41aebe`**、`carscreen-api` 默认、`README` curl 示例一致：`CARSCREEN_001` */
    private const val DEVICE_ID = "CARSCREEN_001"

    /** Automotive + `adb reverse tcp:8080 tcp:8080` 时使用；参见 `修改.md`。 */
    private const val BASE_URL = "http://127.0.0.1:8080"
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
        obj.put("deviceId", DEVICE_ID)
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
                "无法连接主机：请确认 BASE_URL、区块链网关已启动；" +
                    " Automotive/车机模拟器请用 127.0.0.1 + `adb reverse tcp:8080 tcp:8080`（见修改.md）；经典模拟器可用 10.0.2.2；真机用电脑 IP"
            is SocketTimeoutException ->
                "请求超时（${TIMEOUT_MS}ms），请检查网络或增大网关超时"
            is IOException ->
                "网络错误：${e.message ?: e.javaClass.simpleName}"
            else -> e.message ?: "未知错误"
        }
}
