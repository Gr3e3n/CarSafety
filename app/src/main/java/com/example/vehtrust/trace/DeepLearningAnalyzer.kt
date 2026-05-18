package com.example.vehtrust.trace

import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.max

/**
 * MLP 风格事故时序分析器。
 *
 * 当前实现使用“特征向量 -> 隐藏层(ReLU) -> 输出层(Softmax/Sigmoid)”的方式，
 * 更接近真实神经网络推理流程。后续可平滑替换为 TFLite / PyTorch Mobile 模型。
 */
object DeepLearningAnalyzer {

    private const val MODEL_NAME = "TinyCrashNet-MLP"
    private const val INPUT_SIZE = 16
    private const val HIDDEN_SIZE = 8
    private const val RISK_OUTPUT_SIZE = 3
    private const val AUX_OUTPUT_SIZE = 2

    private val hiddenWeights = arrayOf(
        floatArrayOf(0.42f, -0.18f, 0.25f, 0.16f, 0.08f, 0.31f, -0.12f, 0.28f, -0.09f, 0.22f, 0.14f, -0.20f, 0.17f, 0.24f, 0.10f, 0.12f),
        floatArrayOf(-0.21f, 0.46f, 0.18f, 0.09f, 0.27f, -0.11f, 0.35f, -0.26f, 0.31f, 0.14f, -0.08f, 0.22f, 0.19f, -0.16f, 0.29f, 0.11f),
        floatArrayOf(0.18f, 0.13f, -0.29f, 0.41f, 0.36f, 0.20f, 0.15f, -0.10f, 0.26f, -0.17f, 0.12f, 0.24f, -0.14f, 0.38f, 0.06f, 0.27f),
        floatArrayOf(0.33f, 0.09f, 0.12f, -0.19f, 0.21f, 0.28f, -0.16f, 0.34f, 0.25f, 0.18f, 0.07f, -0.22f, 0.31f, 0.11f, -0.12f, 0.15f),
        floatArrayOf(-0.15f, 0.24f, 0.44f, 0.07f, -0.10f, 0.17f, 0.32f, 0.16f, 0.13f, 0.29f, 0.21f, 0.08f, 0.27f, -0.09f, 0.35f, 0.19f),
        floatArrayOf(0.28f, 0.17f, -0.06f, 0.35f, 0.14f, 0.12f, 0.26f, 0.22f, -0.18f, 0.39f, 0.09f, 0.15f, 0.24f, 0.30f, 0.11f, -0.13f),
        floatArrayOf(0.10f, -0.22f, 0.19f, 0.27f, 0.33f, 0.16f, 0.12f, 0.14f, 0.37f, -0.08f, 0.28f, 0.25f, 0.18f, 0.20f, 0.05f, 0.31f),
        floatArrayOf(0.26f, 0.21f, 0.08f, -0.12f, 0.15f, 0.34f, 0.29f, 0.18f, 0.11f, 0.23f, -0.09f, 0.32f, 0.14f, 0.17f, 0.27f, 0.24f),
    )

    private val hiddenBias = floatArrayOf(0.08f, -0.04f, 0.06f, 0.03f, 0.01f, 0.05f, -0.02f, 0.04f)

    private val riskOutputWeights = arrayOf(
        floatArrayOf(0.34f, 0.11f, 0.08f, 0.27f, -0.06f, 0.21f, 0.32f, 0.15f),
        floatArrayOf(0.09f, 0.37f, 0.14f, 0.12f, 0.29f, 0.18f, 0.10f, 0.22f),
        floatArrayOf(0.12f, 0.16f, 0.31f, 0.09f, 0.17f, 0.24f, 0.13f, 0.35f),
    )

    private val riskOutputBias = floatArrayOf(0.05f, 0.03f, 0.02f)

    private val auxOutputWeights = arrayOf(
        floatArrayOf(0.21f, 0.18f, 0.13f, 0.16f, 0.22f, 0.15f, 0.19f, 0.17f),
        floatArrayOf(0.14f, 0.29f, 0.11f, 0.08f, 0.26f, 0.17f, 0.09f, 0.12f),
    )

    private val auxOutputBias = floatArrayOf(0.04f, 0.02f)

    fun analyze(
        event: AccidentEvent,
        telemetry: List<TelemetryPoint>,
        env: EnvironmentSnapshot?,
        trace: DecisionTrace?,
        metrics: ResponsibilityAnalyzer.DetailedMetrics,
    ): DeepLearningResult {
        if (telemetry.isEmpty()) {
            return DeepLearningResult(
                modelName = MODEL_NAME,
                accidentTypeConfidence = 0,
                driverRiskScore = 0,
                systemRiskScore = 0,
                environmentRiskScore = 0,
                overallRiskScore = 0,
                predictedLabel = "无数据",
                evidence = listOf("未采集到事故前时序遥测，无法进行 MLP 推理"),
            )
        }

        val stats = computeStats(telemetry)
        val features = buildFeatureVector(event, env, trace, metrics, stats)
        val hidden = denseRelu(features, hiddenWeights, hiddenBias)
        val riskLogits = dense(hidden, riskOutputWeights, riskOutputBias)
        val riskProbs = softmax(riskLogits)
        val auxOutputs = dense(hidden, auxOutputWeights, auxOutputBias)

        val driverRisk = (riskProbs[0] * 100).toInt().coerceIn(0, 100)
        val systemRisk = (riskProbs[1] * 100).toInt().coerceIn(0, 100)
        val envRisk = (riskProbs[2] * 100).toInt().coerceIn(0, 100)

        val confidenceBase = sigmoid(auxOutputs[0])
        val severityBase = sigmoid(auxOutputs[1])
        val typeConfidence = when (event.type) {
            AccidentType.COLLISION,
            AccidentType.DRIVER_SLOW_REACTION,
            AccidentType.AEB_DELAY_OR_MISS,
            AccidentType.TTC_LOW_RISK,
            AccidentType.ENVIRONMENT_DISTURB,
            AccidentType.MULTI_FACTOR,
            -> ((0.52f + confidenceBase * 0.42f) * 100).toInt()
            AccidentType.AUTOPILOT_FAULT, AccidentType.DRIVER_TAKEOVER_FAIL -> ((0.50f + confidenceBase * 0.45f) * 100).toInt()
        }.coerceIn(35, 99)

        val overallRisk = (
            driverRisk * 0.35f +
                systemRisk * 0.35f +
                envRisk * 0.20f +
                severityBase * 10f +
                sigmoid((stats.brakePeak - 65f) / 15f) * 6f
            ).toInt().coerceIn(0, 100)

        val predictedLabel = when {
            overallRisk >= 80 -> "高危事故模式"
            overallRisk >= 60 -> "中高危事故模式"
            overallRisk >= 35 -> "中度风险模式"
            else -> "低风险模式"
        }

        val evidence = buildList {
            add("模型：$MODEL_NAME（输入 ${INPUT_SIZE} 维特征 → 隐藏层 ${HIDDEN_SIZE} 维 → 输出层）")
            add("输入特征包含速度、减速度、制动、转向、反应时间、TTC、AEB 延迟及环境编码")
            add("风险输出：驾驶员 $driverRisk%，系统 $systemRisk%，环境 $envRisk%")
            add("综合风险 $overallRisk%，事故类型置信度 $typeConfidence%，模式标签：$predictedLabel")
            add("关键统计：均速 ${format(stats.speedMean)} km/h，最小加速度 ${format(stats.minAccel)} m/s²，峰值制动 ${stats.brakePeak}%")
            if (stats.steerPeak >= 8f) add("峰值转角 ${format(stats.steerPeak)}°，模型检测到明显规避转向特征")
            if (trace != null) add("存在自动驾驶决策链记录，系统侧神经元激活增强")
            if (env != null) add("环境编码：${env.weather} / ${env.road} / ${env.laneMarking}")
        }

        return DeepLearningResult(
            modelName = MODEL_NAME,
            accidentTypeConfidence = typeConfidence,
            driverRiskScore = driverRisk,
            systemRiskScore = systemRisk,
            environmentRiskScore = envRisk,
            overallRiskScore = overallRisk,
            predictedLabel = predictedLabel,
            evidence = evidence,
        )
    }

    private fun buildFeatureVector(
        event: AccidentEvent,
        env: EnvironmentSnapshot?,
        trace: DecisionTrace?,
        metrics: ResponsibilityAnalyzer.DetailedMetrics,
        stats: TelemetryStats,
    ): FloatArray = floatArrayOf(
        normalize(stats.speedMean, 0f, 120f),
        normalize(-stats.minAccel, 0f, 12f),
        normalize(stats.brakePeak.toFloat(), 0f, 100f),
        normalize(stats.steerPeak, 0f, 30f),
        normalize(stats.accelVariance, 0f, 12f),
        normalizeSafe(metrics.reactionTimeMs, 0f, 4000f),
        normalizeSafe(metrics.brakeRiseTimeMs, 0f, 2000f),
        normalizeSafe(metrics.ttcAtBrakeStart, 0f, 5f),
        normalizeSafe(metrics.aebDelayMs, -5000f, 1000f),
        if (metrics.brakeEffective) 1f else 0f,
        if (event.type == AccidentType.AUTOPILOT_FAULT || event.type == AccidentType.DRIVER_TAKEOVER_FAIL) 1f else 0f,
        if (env?.weather in listOf("小雨", "大雨", "雾", "冰雪")) 1f else 0f,
        if (env?.laneMarking?.contains("模糊") == true || env?.laneMarking?.contains("反光") == true) 1f else 0f,
        if (env?.obstacle?.contains("行人") == true || env?.obstacle?.contains("施工") == true) 1f else 0f,
        if (trace != null) 1f else 0f,
        normalize(stats.brakeMean, 0f, 100f),
    )

    private fun denseRelu(input: FloatArray, weights: Array<FloatArray>, bias: FloatArray): FloatArray {
        val output = FloatArray(weights.size)
        for (i in weights.indices) {
            var sum = bias[i]
            for (j in input.indices) {
                sum += weights[i][j] * input[j]
            }
            output[i] = relu(sum)
        }
        return output
    }

    private fun dense(input: FloatArray, weights: Array<FloatArray>, bias: FloatArray): FloatArray {
        val output = FloatArray(weights.size)
        for (i in weights.indices) {
            var sum = bias[i]
            for (j in input.indices) {
                sum += weights[i][j] * input[j]
            }
            output[i] = sum
        }
        return output
    }

    private fun computeStats(telemetry: List<TelemetryPoint>): TelemetryStats {
        val speedMean = telemetry.map { it.speedKph }.average().toFloat()
        val minAccel = telemetry.minOf { it.axMS2 }
        val brakePeak = telemetry.maxOf { it.brake }
        val brakeMean = telemetry.map { it.brake }.average().toFloat()
        val steerPeak = telemetry.maxOf { abs(it.steerDeg) }
        val accelMean = telemetry.map { it.axMS2 }.average().toFloat()
        val accelVariance = telemetry
            .map { it.axMS2 - accelMean }
            .map { it * it }
            .average()
            .toFloat()
        return TelemetryStats(
            speedMean = speedMean,
            minAccel = minAccel,
            brakePeak = brakePeak,
            brakeMean = brakeMean,
            steerPeak = steerPeak,
            accelVariance = accelVariance,
        )
    }

    private fun softmax(values: FloatArray): FloatArray {
        val maxValue = values.maxOrNull() ?: 0f
        val exps = values.map { exp(it - maxValue) }
        val sum = max(exps.sum(), 0.0001f)
        return exps.map { it / sum }.toFloatArray()
    }

    private fun sigmoid(value: Float): Float = (1f / (1f + exp(-value)))

    private fun relu(value: Float): Float = if (value > 0f) value else 0f

    private fun normalize(value: Float, min: Float, max: Float): Float {
        if (max <= min) return 0f
        return ((value - min) / (max - min)).coerceIn(0f, 1f)
    }

    private fun normalizeSafe(value: Int, min: Float, max: Float): Float =
        if (value < 0) 0f else normalize(value.toFloat(), min, max)

    private fun normalizeSafe(value: Float, min: Float, max: Float): Float =
        if (value < 0f) 0f else normalize(value, min, max)

    private fun format(value: Float): String = String.format("%.1f", value)

    private data class TelemetryStats(
        val speedMean: Float,
        val minAccel: Float,
        val brakePeak: Int,
        val brakeMean: Float,
        val steerPeak: Float,
        val accelVariance: Float,
    )
}
