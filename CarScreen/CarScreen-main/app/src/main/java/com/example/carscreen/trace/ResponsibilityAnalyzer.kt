package com.example.carscreen.trace

import kotlin.math.abs

/**
 * 可解释责任界定分析器
 * 所有指标都有明确计算公式与数据依据，便于向当事人/保险/司法解释。
 */
object ResponsibilityAnalyzer {

    private const val BRAKE_LIGHT_PCT = 20
    private const val BRAKE_FULL_PCT = 80
    private const val BRAKE_HARD_PCT = 60
    private const val AEB_ACCEL_THRESHOLD = -4.0f
    private const val REACTION_WARNING_MS = 1500
    private const val REACTION_DANGER_MS = 2500

    data class DetailedMetrics(
        /** 驾驶员反应时间：危险减速出现 → 制动踏板>=20% 的时间差（ms） */
        val reactionTimeMs: Int,
        /** 制动上升时间：制动踏板从 20% 升至 80% 的时间（ms），-1=未达到 */
        val brakeRiseTimeMs: Int,
        /** 峰值减速度（m/s²，负值） */
        val maxDecelerationMS2: Float,
        /** AEB 介入延迟估算（ms，相对事故时刻），-1=未检测到系统介入 */
        val aebDelayMs: Int,
        /** 制动开始时 TTC 估算（s），-1=不可用 */
        val ttcAtBrakeStart: Float,
        /** 事故前 3 秒平均速度（km/h） */
        val avgSpeedLast3sKph: Float,
        /** 事故前 2 秒最大方向盘角（°） */
        val maxSteerLast2sDeg: Float,
        /** 制动有效性：危险出现 2000ms 内是否达到强制动 */
        val brakeEffective: Boolean,
        /** 人类可读指标摘要（用于 UI 展示） */
        val summaryLines: List<String>,
    )

    /**
     * 分析遥测数据，返回详细可解释指标。
     * [telemetry] 按 tMs 升序，tMs=0 为事故时刻，负值为事故前。
     * [aebTriggerTMs] 真实接入时从 AEB 状态总线传入；null=用减速度估算。
     */
    fun analyze(
        event: AccidentEvent,
        telemetry: List<TelemetryPoint>,
        aebTriggerTMs: Int? = null,
    ): DetailedMetrics {
        if (telemetry.isEmpty()) return emptyMetrics()
        val sorted = telemetry.sortedBy { it.tMs }

        // 1. 危险出现时刻：首次 ax <= -3.0 m/s²
        val dangerTMs = sorted.firstOrNull { it.axMS2 <= -3.0f }?.tMs ?: sorted.first().tMs

        // 2. 驾驶员反应时间
        val brakeStartPoint = sorted.firstOrNull { it.tMs >= dangerTMs && it.brake >= BRAKE_LIGHT_PCT }
        val reactionTimeMs = if (brakeStartPoint != null) {
            (brakeStartPoint.tMs - dangerTMs).coerceAtLeast(0)
        } else {
            (sorted.last().tMs - dangerTMs).coerceAtLeast(0)
        }

        // 3. 制动上升时间（20% -> 80%）
        val brakeLight = sorted.firstOrNull { it.brake >= BRAKE_LIGHT_PCT }
        val brakeFull = if (brakeLight != null) {
            sorted.firstOrNull { it.tMs >= brakeLight.tMs && it.brake >= BRAKE_FULL_PCT }
        } else null
        val brakeRiseTimeMs = if (brakeLight != null && brakeFull != null) {
            (brakeFull.tMs - brakeLight.tMs).coerceAtLeast(0)
        } else -1

        // 4. 峰值减速度
        val maxDecelerationMS2 = sorted.minOfOrNull { it.axMS2 } ?: 0f

        // 5. AEB 介入延迟（优先真实信号，否则用减速度估算）
        val aebDelayMs = when {
            aebTriggerTMs != null -> aebTriggerTMs
            else -> sorted.firstOrNull { it.axMS2 <= AEB_ACCEL_THRESHOLD }?.tMs ?: -1
        }

        // 6. TTC 估算（制动开始时刻，粗略：v / |a|）
        val ttcAtBrakeStart = if (brakeStartPoint != null && brakeStartPoint.speedKph >= 5f) {
            val vMs = brakeStartPoint.speedKph / 3.6f
            val ax = abs(brakeStartPoint.axMS2).coerceAtLeast(0.1f)
            vMs / ax
        } else -1f

        // 7. 事故前 3 秒平均速度
        val last3s = sorted.filter { it.tMs >= -3000 }
        val avgSpeedLast3sKph = if (last3s.isNotEmpty()) {
            last3s.map { it.speedKph }.average().toFloat()
        } else 0f

        // 8. 事故前 2 秒最大方向盘角
        val last2s = sorted.filter { it.tMs >= -2000 }
        val maxSteerLast2sDeg = last2s.maxOfOrNull { abs(it.steerDeg) } ?: 0f

        // 9. 制动有效性
        val brakeEffective = if (brakeStartPoint != null) {
            val brakeHardPoint = sorted.firstOrNull {
                it.tMs >= brakeStartPoint.tMs && it.brake >= BRAKE_HARD_PCT
            }
            brakeHardPoint != null && (brakeHardPoint.tMs - dangerTMs) <= 2000
        } else false

        // 10. 组装摘要
        val summaryLines = buildSummaryLines(
            event, reactionTimeMs, brakeRiseTimeMs, maxDecelerationMS2,
            aebDelayMs, ttcAtBrakeStart, avgSpeedLast3sKph, maxSteerLast2sDeg, brakeEffective
        )

        return DetailedMetrics(
            reactionTimeMs = reactionTimeMs,
            brakeRiseTimeMs = brakeRiseTimeMs,
            maxDecelerationMS2 = maxDecelerationMS2,
            aebDelayMs = aebDelayMs,
            ttcAtBrakeStart = ttcAtBrakeStart,
            avgSpeedLast3sKph = avgSpeedLast3sKph,
            maxSteerLast2sDeg = maxSteerLast2sDeg,
            brakeEffective = brakeEffective,
            summaryLines = summaryLines,
        )
    }

    /**
     * 综合详细指标 + 事件信息，输出责任倾向结果。
     */
    fun inferResponsibility(
        event: AccidentEvent,
        metrics: DetailedMetrics,
        env: EnvironmentSnapshot?,
        trace: DecisionTrace?,
    ): ResponsibilityResult {
        // 驾驶员分
        var driverScore = 30
        driverScore += when {
            metrics.reactionTimeMs >= REACTION_DANGER_MS -> 40
            metrics.reactionTimeMs >= REACTION_WARNING_MS -> 25
            else -> 10
        }
        if (!metrics.brakeEffective) driverScore += 15
        if (metrics.ttcAtBrakeStart in 0f..1.5f) driverScore += 10
        if (event.type == AccidentType.AUTOPILOT_FAULT) driverScore -= 15

        // 系统分
        var systemScore = 15
        if (event.type == AccidentType.AUTOPILOT_FAULT) systemScore += 30
        if (metrics.aebDelayMs in -5000..-500) systemScore += 15
        if (metrics.aebDelayMs == -1 && event.type == AccidentType.COLLISION) systemScore += 10
        if (trace != null) systemScore += 10

        // 环境分
        var envScore = 10
        if (env != null) {
            if (env.weather in listOf("小雨", "大雨", "雾", "冰雪")) envScore += 15
            if (env.laneMarking.contains("模糊") || env.laneMarking.contains("反光")) envScore += 10
            if (env.obstacle.contains("行人") || env.obstacle.contains("施工")) envScore += 5
        }

        val total = (driverScore + systemScore + envScore).coerceAtLeast(1)
        val d = driverScore * 100 / total
        val s = systemScore * 100 / total
        val e = envScore * 100 / total

        val reasons = mutableListOf<String>()
        reasons += "【反应时间】${metrics.reactionTimeMs}ms — " + when {
            metrics.reactionTimeMs >= REACTION_DANGER_MS -> "过长（>${REACTION_DANGER_MS}ms），显著增加碰撞风险"
            metrics.reactionTimeMs >= REACTION_WARNING_MS -> "偏长（>${REACTION_WARNING_MS}ms），建议关注注意力状态"
            else -> "正常范围"
        }
        if (metrics.brakeRiseTimeMs >= 0) {
            reasons += "【制动上升时间】${metrics.brakeRiseTimeMs}ms（踏板 20%→80%）— " +
                if (metrics.brakeRiseTimeMs <= 400) "制动果断" else "制动迟缓，上升过慢"
        }
        reasons += "【峰值减速度】${
            String.format("%.2f", metrics.maxDecelerationMS2)
        } m/s² — " + when {
            metrics.maxDecelerationMS2 <= -8f -> "极强制动（碰撞级）"
            metrics.maxDecelerationMS2 <= -5f -> "强制动"
            else -> "中等制动"
        }
        if (metrics.aebDelayMs != -1) {
            reasons += "【AEB/系统介入时刻】${metrics.aebDelayMs}ms（相对事故时刻）"
        } else {
            reasons += "【AEB/系统介入】未检测到系统主动介入"
        }
        if (metrics.ttcAtBrakeStart >= 0f) {
            reasons += "【制动时 TTC 估算】${
                String.format("%.1f", metrics.ttcAtBrakeStart)
            }s — " + if (metrics.ttcAtBrakeStart < 1.5f) "跟车时距不足，风险极高" else "时距尚可"
        }
        reasons += "【事故前 3s 均速】${
            String.format("%.1f", metrics.avgSpeedLast3sKph)
        } km/h"
        if (metrics.maxSteerLast2sDeg > 8f) {
            reasons += "【方向盘最大角】${
                String.format("%.1f", metrics.maxSteerLast2sDeg)
            }° — 存在明显转向纠偏操作"
        }
        if (env != null) {
            reasons += "【环境】天气:${env.weather} · 路况:${env.road} · 车道线:${env.laneMarking}"
        }
        if (trace != null) {
            reasons += "【系统决策链路】控制执行:${trace.control}"
        }

        val conclusion = when (event.type) {
            AccidentType.COLLISION -> when {
                d >= 55 -> "责任倾向：驾驶员主要责任（反应不足/跟车过近）"
                d >= 40 -> "责任倾向：驾驶员与系统共同责任"
                else -> "责任倾向：多因素共同作用（需结合更多证据）"
            }
            AccidentType.AUTOPILOT_FAULT -> when {
                s >= 45 -> "责任倾向：系统与接管协同问题为主"
                d >= 45 -> "责任倾向：驾驶员接管不足为主"
                else -> "责任倾向：系统与驾驶员共同责任"
            }
        }

        return ResponsibilityResult(
            driverFactor = d,
            systemFactor = s,
            environmentFactor = e,
            conclusion = conclusion,
            reasons = reasons,
        )
    }

    private fun buildSummaryLines(
        event: AccidentEvent,
        reactionTimeMs: Int,
        brakeRiseTimeMs: Int,
        maxDecelerationMS2: Float,
        aebDelayMs: Int,
        ttcAtBrakeStart: Float,
        avgSpeedLast3sKph: Float,
        maxSteerLast2sDeg: Float,
        brakeEffective: Boolean,
    ): List<String> = listOf(
        "反应时间：${reactionTimeMs}ms",
        "制动上升时间：" + if (brakeRiseTimeMs >= 0) "${brakeRiseTimeMs}ms" else "未达全力制动",
        "峰值减速度：${String.format("%.2f", maxDecelerationMS2)} m/s²",
        "AEB介入：" + if (aebDelayMs != -1) "${aebDelayMs}ms" else "未触发",
        "制动时TTC：" + if (ttcAtBrakeStart >= 0f) "${String.format("%.1f", ttcAtBrakeStart)}s" else "不可用",
        "事故前3s均速：${String.format("%.1f", avgSpeedLast3sKph)} km/h",
        "方向盘最大角(前2s)：${String.format("%.1f", maxSteerLast2sDeg)}°",
        "制动有效性：" + if (brakeEffective) "有效" else "不足",
    )

    private fun emptyMetrics() = DetailedMetrics(
        reactionTimeMs = -1,
        brakeRiseTimeMs = -1,
        maxDecelerationMS2 = 0f,
        aebDelayMs = -1,
        ttcAtBrakeStart = -1f,
        avgSpeedLast3sKph = 0f,
        maxSteerLast2sDeg = 0f,
        brakeEffective = false,
        summaryLines = listOf("无遥测数据"),
    )
}
