package com.example.vehtrust.trace

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import org.json.JSONArray
import org.json.JSONObject

data class AiAccidentAnalysisResult(
    val summary: String,
    val rootCause: String,
    val comprehensiveAnalysis: String = "",
    val scenarioReconstruction: String = "",
    val confidenceStatement: String = "",
    val evidencePoints: List<String>,
    val suggestions: List<String>,
    val modelHint: String,
    val rawText: String,
    val remoteError: String? = null,
) {
    fun toDisplayText(): String = buildString {
        append("AI 事故分析报告\n\n")

        append("【总体摘要】\n")
        append(summary.ifBlank { "暂无摘要" }).append("\n\n")

        append("【根因判断】\n")
        append(rootCause.ifBlank { "暂无根因结论" }).append("\n\n")

        if (comprehensiveAnalysis.isNotBlank()) {
            append("【综合分析】\n")
            append(compactParagraphSpacing(comprehensiveAnalysis)).append("\n\n")
        }

        if (scenarioReconstruction.isNotBlank()) {
            append("【过程复盘】\n")
            append(compactParagraphSpacing(scenarioReconstruction)).append("\n\n")
        }

        if (confidenceStatement.isNotBlank()) {
            append("【置信度说明】\n")
            append(compactParagraphSpacing(confidenceStatement)).append("\n\n")
        }

        if (evidencePoints.isNotEmpty()) {
            append("【关键证据】\n")
            evidencePoints.forEach { append("- ").append(it).append('\n') }
            append('\n')
        }

        if (suggestions.isNotEmpty()) {
            append("【建议与改进】\n")
            suggestions.forEach { append("- ").append(it).append('\n') }
            append('\n')
        }

        if (rawText.isNotBlank()) {
            append("【扩展叙述】\n")
            append(formatNarrative(rawText))
        }
    }.trim()

    fun toStyledDisplayText(): CharSequence {
        val plain = toDisplayText()
        val styled = SpannableStringBuilder(plain)
        val sectionRegex = Regex("^【.+】$")
        var cursor = 0

        plain.split('\n').forEach { line ->
            val start = cursor
            val end = start + line.length
            val normalizedLine = line.replace(" ", "")
            if (normalizedLine == "AI事故分析报告") {
                styled.setSpan(StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                styled.setSpan(RelativeSizeSpan(1.12f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else if (sectionRegex.matches(line)) {
                styled.setSpan(StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                styled.setSpan(RelativeSizeSpan(1.06f), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            cursor = end + 1
        }
        return styled
    }

    private fun formatNarrative(text: String): String {
        val normalized = compactParagraphSpacing(text)
        if (normalized.isBlank()) return ""

        val manualParagraphs = normalized
            .split("\n")
            .map { it.trim() }
            .filter { it.isNotBlank() }
        if (manualParagraphs.size >= 2) {
            return manualParagraphs.joinToString("\n") { "- $it" }
        }

        val sentences = Regex("(?<=[。！？!?])").split(normalized)
            .map { it.trim() }
            .filter { it.isNotBlank() }
        if (sentences.size <= 3) {
            return listOf(normalized).joinToString("\n") { "- $it" }
        }

        return sentences
            .chunked(3)
            .joinToString("\n") { chunk -> "- ${chunk.joinToString("")}" }
    }

    private fun compactParagraphSpacing(text: String): String {
        return text
            .replace("\r\n", "\n")
            .replace(Regex("\n{2,}"), "\n")
            .trim()
    }
}

internal fun AccidentDetailBundle.toAiRequestJson(
    metrics: ResponsibilityAnalyzer.DetailedMetrics? = null,
    severityPredictionSummary: String? = null,
): String {
    val root = JSONObject()
    root.put("eventId", event.id)
    root.put("eventType", event.type.name)
    root.put("timeMillis", event.timeMillis)
    root.put("location", event.locationText)
    root.put("summary", event.summary)
    root.put("triggerReasons", JSONArray(event.triggerReasons))
    root.put("severity", event.severity.name)
    root.put("autoDrivingState", event.autoDrivingState.name)

    val telemetryArray = JSONArray()
    telemetry10sBefore.forEach { point ->
        telemetryArray.put(
            JSONObject().apply {
                put("tMs", point.tMs)
                put("speedKph", point.speedKph)
                put("axMS2", point.axMS2)
                put("brake", point.brake)
                put("steerDeg", point.steerDeg)
            }
        )
    }
    root.put("telemetry", telemetryArray)

    root.put(
        "responsibility",
        JSONObject().apply {
            put("driverFactor", responsibility.driverFactor)
            put("systemFactor", responsibility.systemFactor)
            put("environmentFactor", responsibility.environmentFactor)
            put("conclusion", responsibility.conclusion)
            put("reasons", JSONArray(responsibility.reasons))
        }
    )

    environmentSnapshot?.let { env ->
        root.put(
            "environment",
            JSONObject().apply {
                put("weather", env.weather)
                put("road", env.road)
                put("obstacle", env.obstacle)
                put("laneMarking", env.laneMarking)
            }
        )
    }

    decisionTrace?.let { trace ->
        root.put(
            "decisionTrace",
            JSONObject().apply {
                put("sensorInput", trace.sensorInput)
                put("perception", trace.perception)
                put("planning", trace.planning)
                put("control", trace.control)
            }
        )
    }

    root.put("experimentGroup", ExperimentRuntime.normalizedGroup())
    root.put("ablationMode", ExperimentRuntime.normalizedAblation())

    val derived = JSONObject()
    metrics?.let { m ->
        derived.put("reactionTimeMs", m.reactionTimeMs)
        derived.put("brakeRiseTimeMs", m.brakeRiseTimeMs)
        derived.put("aebDelayMs", m.aebDelayMs)
        if (m.ttcAtBrakeStart >= 0f) {
            derived.put("ttcAtBrakeSeconds", m.ttcAtBrakeStart.toDouble())
        }
        derived.put("brakeEffective", m.brakeEffective)
        derived.put("maxSteerLast2sDeg", m.maxSteerLast2sDeg.toDouble())
        val takeover = when (event.autoDrivingState) {
            AutoDrivingState.AUTONOMOUS -> "自动驾驶模式：需结合系统提示与接管时序评估人机责任"
            AutoDrivingState.L2_ASSIST -> "L2 辅助驾驶：驾驶员对纵向/横向监督责任仍为主"
            AutoDrivingState.MANUAL -> "手动驾驶：以驾驶员操作与反应为主"
        }
        derived.put("driverTakeoverSummary", takeover)
    }
    if (!severityPredictionSummary.isNullOrBlank()) {
        derived.put("riskPredictionSummary", severityPredictionSummary)
    }
    if (derived.length() > 0) {
        root.put("derivedSignals", derived)
    }

    return root.toString()
}

internal fun fallbackAiAnalysis(
    bundle: AccidentDetailBundle,
    remoteError: String? = null,
): AiAccidentAnalysisResult {
    val reasons = bundle.responsibility.reasons.take(3)
    val suggestions = mutableListOf<String>()
    if (bundle.environmentSnapshot != null) suggestions += "补充环境传感器原始帧，确认天气/车道线识别质量"
    if (bundle.decisionTrace != null) suggestions += "导出自动驾驶感知与控制日志，核查系统链路延迟"
    suggestions += "结合车外视频或路侧监控，确认目标车辆/障碍物相对位置变化"

    val rootCause = when {
        bundle.responsibility.driverFactor >= bundle.responsibility.systemFactor &&
            bundle.responsibility.driverFactor >= bundle.responsibility.environmentFactor ->
            "驾驶员反应与制动处置不足是主要诱因"
        bundle.responsibility.systemFactor >= bundle.responsibility.environmentFactor ->
            "系统介入延迟或自动驾驶链路异常是主要诱因"
        else -> "复杂环境与路况干扰是主要诱因"
    }

    return AiAccidentAnalysisResult(
        summary = bundle.event.summary,
        rootCause = rootCause,
        evidencePoints = reasons,
        suggestions = suggestions,
        modelHint = "当前为端侧回退分析；若配置 OpenAI 分析服务，将返回更完整的事故因果报告。",
        rawText = "未连接远端 AI 服务，已根据本地责任分析结果生成摘要。",
        remoteError = remoteError,
    )
}
