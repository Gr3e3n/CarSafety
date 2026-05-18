package com.example.vehtrust.trace

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * 对齐《云端事故复盘能力实验说明文档》§6 步骤 3：对固定样本批量调用云端，并保存原始 JSON 便于盲评与归档。
 *
 * 样本文件：[assets/experiment_samples_realistic_50.json]（与 backend/generate_experiment_samples.py 一致）。
 * 结果写入应用外部专属目录：`Android/data/.../files/cloud_experiment_results_{A|B|C}_{D0-D4}_*.json`
 */
object ExperimentBatchRunner {

    private const val ASSET_NAME = "experiment_samples_realistic_50.json"

    /** 文件名时间戳：统一用 UTC，避免模拟器系统时区/校时与主机不一致。 */
    private val RESULT_STAMP_UTC: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss'Z'").withZone(ZoneOffset.UTC)

    suspend fun runAndSave(context: Context): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val input = context.assets.open(ASSET_NAME).bufferedReader(StandardCharsets.UTF_8).use { it.readText() }
            val arr = JSONArray(input)
            val group = ExperimentRuntime.normalizedGroup()
            val ablation = ExperimentRuntime.normalizedAblation()
            val requestArray = JSONArray()
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                obj.put("experimentGroup", group)
                obj.put("ablationMode", ablation)
                requestArray.put(obj)
            }
            val outArray = try {
                val raw = OpenAiAnalysisApi.postAnalyzeBatchJsonRaw(requestArray.toString())
                JSONObject(raw).optJSONArray("results")
                    ?: error("batch_response_missing_results")
            } catch (_: Exception) {
                runSequential(requestArray, group, ablation)
            }
            val stamp = RESULT_STAMP_UTC.format(Instant.now())
            val dir = context.getExternalFilesDir(null) ?: context.filesDir
            val safeGroup = group.replace(Regex("[^A-Za-z0-9]"), "")
            val safeAblation = ablation.replace(Regex("[^A-Za-z0-9]"), "")
            val outFile = File(dir, "cloud_experiment_results_${safeGroup}_${safeAblation}_$stamp.json")
            outFile.writeText(outArray.toString(2), StandardCharsets.UTF_8)
            outFile.absolutePath
        }
    }

    private suspend fun runSequential(requestArray: JSONArray, group: String, ablation: String): JSONArray {
        val outArray = JSONArray()
        for (i in 0 until requestArray.length()) {
            val obj = requestArray.getJSONObject(i)
            val raw = OpenAiAnalysisApi.postAnalyzeJsonRaw(obj.toString())
            val wrapper = JSONObject()
            wrapper.put("requestIndex", i)
            wrapper.put("eventId", obj.optString("eventId"))
            wrapper.put("experimentGroup", group)
            wrapper.put("ablationMode", ablation)
            val parsed = runCatching { JSONObject(raw) }.getOrElse { err ->
                JSONObject().apply {
                    put("parseError", err.message ?: "unknown")
                    put("rawText", raw)
                }
            }
            wrapper.put("rawResponse", parsed)
            outArray.put(wrapper)
        }
        return outArray
    }
}
