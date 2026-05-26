package com.example.vehtrust.trace

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object BlockchainApi {

    // ── 服务器地址，按实际修改 ────────────────────────────────────
    private const val BASE_URL = "http://192.168.119.128:8080"
    private const val ENDPOINT = "/upload"
    private const val TIMEOUT_MS = 10_000

    // ── 返回结果数据类 ────────────────────────────────────────────
    data class UploadResult(
        val success: Boolean,
        val hash: String = "",
        val txId: String = "",
        val error: String = ""
    )

    // ── 主入口：上传事故存证 ──────────────────────────────────────
    suspend fun uploadAccident(bundle: AccidentDetailBundle): UploadResult =
        withContext(Dispatchers.IO) {
            try {
                val json = buildJson(bundle)
                val response = post(json)
                parseResponse(response)
            } catch (e: Exception) {
                UploadResult(success = false, error = e.message ?: "未知错误")
            }
        }

    // ── 构造请求 JSON（所有字段统一用字符串）─────────────────────
    private fun buildJson(bundle: AccidentDetailBundle): String {
        val obj = JSONObject()
        obj.put("deviceId", "VEHTRUST_001")
        val data = JSONObject()
        data.put("eventId",      bundle.event.id.toString())
        data.put("timeMillis",   bundle.event.timeMillis.toString())
        data.put("location",     bundle.event.locationText.toString())
        data.put("summary",      bundle.event.summary.toString())
        data.put("driverFactor", bundle.responsibility.driverFactor.toString())
        data.put("systemFactor", bundle.responsibility.systemFactor.toString())
        data.put("envFactor",    bundle.responsibility.environmentFactor.toString())
        data.put("conclusion",   bundle.responsibility.conclusion.toString())
        obj.put("data", data)
        return obj.toString()
    }

    // ── HTTP POST ─────────────────────────────────────────────────
    private fun post(jsonBody: String): String {
        val url = URL("$BASE_URL$ENDPOINT")
        val conn = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = TIMEOUT_MS
            readTimeout    = TIMEOUT_MS
            doOutput       = true
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            setRequestProperty("Accept",       "application/json")
        }
        OutputStreamWriter(conn.outputStream, Charsets.UTF_8).use {
            it.write(jsonBody)
            it.flush()
        }
        val code = conn.responseCode
        val stream = if (code in 200..299) conn.inputStream else conn.errorStream
        return stream.bufferedReader(Charsets.UTF_8).use { it.readText() }
    }

    // ── 解析服务器返回 ────────────────────────────────────────────
    private fun parseResponse(raw: String): UploadResult {
        return try {
            val obj = JSONObject(raw)
            val success = obj.optBoolean("success", false)
            if (success) {
                UploadResult(
                    success = true,
                    hash    = obj.optString("hash", ""),
                    txId    = ""
                )
            } else {
                UploadResult(
                    success = false,
                    error   = obj.optString("message", "服务器返回失败")
                )
            }
        } catch (e: Exception) {
            UploadResult(success = false, error = "响应解析失败：${e.message}")
        }
    }
}