package com.example.vehtrust.trace

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Locale
import kotlin.math.exp
import kotlin.math.roundToInt

data class CollisionSeverityDerivedFeatures(
    val vehicleCount: Int? = null,
    val speedLimit: Int? = null,
    val weatherConditions: Int? = null,
    val isNight: Boolean? = null,
    val avgSpeedLast3sKph: Float? = null,
    val maxDecelerationMS2: Float? = null,
    val brakePeak: Int? = null,
    val ttcAtBrakeStart: Float? = null,
    val brakeEffective: Boolean? = null,
)

data class CollisionSeverityPredictionResult(
    val predictedSeverity: String,
    val probabilities: Map<String, Double>,
    val modelHint: String,
    val derivedFeatures: CollisionSeverityDerivedFeatures = CollisionSeverityDerivedFeatures(),
    val severityScore: Int = 0,
    val narrative: String = "",
    val keyFactors: List<String> = emptyList(),
    val remoteError: String? = null,
) {
    fun toDisplayText(): String = buildString {
        val topProbability = probabilities.maxByOrNull { it.value }
        append("预测结果：")
            .append(predictedSeverity.toDisplaySeverityLabel())
        if (topProbability != null) {
            append("（置信度 ")
                .append((topProbability.value * 100).roundToInt())
                .append("%）")
        }
        append("\n严重度指数：").append(severityScore).append("/100")

        if (narrative.isNotBlank()) {
            append("\n\n研判摘要：\n")
            append(narrative.trim())
        }

        if (probabilities.isNotEmpty()) {
            append("\n\n概率分布：\n")
            preferredProbabilityOrder().forEach { label ->
                probabilities[label]?.let { prob ->
                    append("• ")
                        .append(label.toDisplaySeverityLabel())
                        .append("：")
                        .append((prob * 100).roundToInt())
                        .append("%\n")
                }
            }
        }

        if (keyFactors.isNotEmpty()) {
            append("\n关键因素：\n")
            keyFactors.forEach { append("• ").append(it).append('\n') }
        }

        append("\n模型说明：").append(modelHint)
    }.trim()
}

private data class CollisionSeverityRuntimeModel(
    @SerializedName("model_name") val modelName: String,
    @SerializedName("source_joblib") val sourceJoblib: String,
    @SerializedName("output_classes") val outputClasses: List<String>,
    @SerializedName("base_logits") val baseLogits: List<Double>,
    @SerializedName("numeric_features") val numericFeatures: Map<String, RuntimeNumericFeature>,
    @SerializedName("categorical_features") val categoricalFeatures: Map<String, RuntimeCategoricalFeature>,
)

private data class RuntimeNumericFeature(
    @SerializedName("base_value") val baseValue: Double,
    val scale: Double,
    val coefficients: List<Double>,
)

private data class RuntimeCategoricalFeature(
    @SerializedName("default_value") val defaultValue: String,
    val deltas: Map<String, List<Double>>,
)

private data class CollisionSeverityRuntimeInputs(
    val numericValues: Map<String, Double>,
    val categoricalValues: Map<String, String>,
)

object CollisionSeverityApi {

    private const val MODEL_ASSET_NAME = "collision_severity_model.json"
    private const val LOCAL_MODEL_VERSION = "joblib-export-v1"
    private val gson = Gson()

    @Volatile
    private var cachedRuntimeModel: CollisionSeverityRuntimeModel? = null

    suspend fun predictCollisionSeverity(
        context: Context,
        bundle: AccidentDetailBundle,
    ): CollisionSeverityPredictionResult = withContext(Dispatchers.Default) {
        localSeverityPrediction(context.applicationContext, bundle)
    }

    private fun localSeverityPrediction(
        context: Context,
        bundle: AccidentDetailBundle,
    ): CollisionSeverityPredictionResult {
        val metrics = ResponsibilityAnalyzer.analyze(bundle.event, bundle.telemetry10sBefore)
        val maxSpeed = bundle.telemetry10sBefore.maxOfOrNull { it.speedKph } ?: 0f
        val brakePeak = bundle.telemetry10sBefore.maxOfOrNull { it.brake } ?: 0
        val speedLimit = inferSpeedLimit(maxSpeed)
        val vehicleCount = if (bundle.event.type == AccidentType.COLLISION) 2 else 1
        val weatherCode = inferWeatherConditions(bundle.environmentSnapshot?.weather)
        val isNight = Calendar.getInstance().apply { timeInMillis = bundle.event.timeMillis }
            .get(Calendar.HOUR_OF_DAY)
            .let { it in 0..5 || it in 22..23 }

        val derivedFeatures = CollisionSeverityDerivedFeatures(
            vehicleCount = vehicleCount,
            speedLimit = speedLimit,
            weatherConditions = weatherCode,
            isNight = isNight,
            avgSpeedLast3sKph = metrics.avgSpeedLast3sKph,
            maxDecelerationMS2 = metrics.maxDecelerationMS2,
            brakePeak = brakePeak,
            ttcAtBrakeStart = metrics.ttcAtBrakeStart,
            brakeEffective = metrics.brakeEffective,
        )

        if (bundle.event.type != AccidentType.COLLISION) {
            return CollisionSeverityPredictionResult(
                predictedSeverity = "Slight",
                probabilities = linkedMapOf("Fatal" to 0.04, "Serious" to 0.18, "Slight" to 0.78),
                modelHint = "collision_severity_pipeline.joblib-runtime · 非碰撞事件按低风险展示",
                derivedFeatures = derivedFeatures,
                severityScore = 23,
                narrative = "当前事件类型并非碰撞类事故。严重度模块仍保持端侧离线处理，但不会调用远端接口；页面结果仅用于统一展示，不作为碰撞伤害等级主判据。",
                keyFactors = listOf("事件类型不是 COLLISION，训练模型不参与主判", "严重度模块仍保持纯本地处理，不访问后端服务"),
                remoteError = null,
            )
        }

        val runtimeModel = loadRuntimeModel(context)
        val runtimeInputs = buildRuntimeInputs(bundle, vehicleCount, speedLimit, weatherCode, isNight)
        val logits = evaluateRuntimeModel(runtimeModel, runtimeInputs)
        val probabilitiesArray = softmax(logits.map { it.toFloat() }.toFloatArray())

        val probabilities = linkedMapOf(
            "Fatal" to probabilitiesArray[0].toDouble(),
            "Serious" to probabilitiesArray[1].toDouble(),
            "Slight" to probabilitiesArray[2].toDouble(),
        )
        val predicted = probabilities.maxByOrNull { it.value }?.key ?: "Serious"
        val severityScore = (
            probabilitiesArray[0] * 100f +
                probabilitiesArray[1] * 65f +
                probabilitiesArray[2] * 25f
            ).roundToInt().coerceIn(0, 100)

        val factors = buildKeyFactors(
            bundle = bundle,
            metrics = metrics,
            maxSpeed = maxSpeed,
            brakePeak = brakePeak,
            weatherCode = weatherCode,
            isNight = isNight,
            speedLimit = speedLimit,
            probabilities = probabilities,
        )

        val narrative = buildNarrative(
            predicted = predicted,
            severityScore = severityScore,
            metrics = metrics,
            brakePeak = brakePeak,
            speedLimit = speedLimit,
            weatherCode = weatherCode,
            isNight = isNight,
            probabilities = probabilities,
        )

        return CollisionSeverityPredictionResult(
            predictedSeverity = predicted,
            probabilities = probabilities,
            modelHint = "${runtimeModel.modelName} · $LOCAL_MODEL_VERSION · 训练模型导出参数 · 端侧离线推断",
            derivedFeatures = derivedFeatures,
            severityScore = severityScore,
            narrative = narrative,
            keyFactors = factors,
            remoteError = null,
        )
    }

    private fun loadRuntimeModel(context: Context): CollisionSeverityRuntimeModel {
        cachedRuntimeModel?.let { return it }
        return synchronized(this) {
            cachedRuntimeModel?.let { return@synchronized it }
            val json = context.assets.open(MODEL_ASSET_NAME).bufferedReader(Charsets.UTF_8).use { it.readText() }
            gson.fromJson(json, CollisionSeverityRuntimeModel::class.java).also { cachedRuntimeModel = it }
        }
    }

    private fun buildRuntimeInputs(
        bundle: AccidentDetailBundle,
        vehicleCount: Int,
        speedLimit: Int,
        weatherCode: Int,
        isNight: Boolean,
    ): CollisionSeverityRuntimeInputs {
        val calendar = Calendar.getInstance().apply { timeInMillis = bundle.event.timeMillis }
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val isWeekend = if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) 1 else 0
        val roadSurfaceCode = inferRoadSurfaceCondition(weatherCode)
        val lightCode = if (isNight) "4" else "1"
        val urbanOrRuralCode = if (speedLimit >= 50) "2" else "1"

        return CollisionSeverityRuntimeInputs(
            numericValues = mapOf(
                "number_of_vehicles" to vehicleCount.toDouble(),
                "speed_limit" to speedLimit.toDouble(),
                "collision_month" to month.toDouble(),
                "collision_day" to day.toDouble(),
                "collision_hour" to hour.toDouble(),
                "collision_minute" to minute.toDouble(),
                "is_weekend" to isWeekend.toDouble(),
                "is_night" to if (isNight) 1.0 else 0.0,
                "vehicle_record_count" to vehicleCount.toDouble(),
            ),
            categoricalValues = mapOf(
                "day_of_week" to dayOfWeek.toString(),
                "weather_conditions" to weatherCode.toString(),
                "light_conditions" to lightCode,
                "road_surface_conditions" to roadSurfaceCode.toString(),
                "urban_or_rural_area" to urbanOrRuralCode,
            ),
        )
    }

    private fun evaluateRuntimeModel(
        runtimeModel: CollisionSeverityRuntimeModel,
        inputs: CollisionSeverityRuntimeInputs,
    ): MutableList<Double> {
        val logits = runtimeModel.baseLogits.toMutableList()

        runtimeModel.numericFeatures.forEach { (name, feature) ->
            val inputValue = inputs.numericValues[name] ?: feature.baseValue
            val scale = feature.scale.takeIf { it != 0.0 } ?: 1.0
            val normalizedDelta = (inputValue - feature.baseValue) / scale
            feature.coefficients.forEachIndexed { index, coefficient ->
                logits[index] += coefficient * normalizedDelta
            }
        }

        runtimeModel.categoricalFeatures.forEach { (name, feature) ->
            val inputValue = inputs.categoricalValues[name] ?: feature.defaultValue
            val delta = feature.deltas[inputValue] ?: feature.deltas[feature.defaultValue] ?: zeroDeltas(logits.size)
            delta.forEachIndexed { index, contribution ->
                logits[index] += contribution
            }
        }

        return logits
    }

    private fun buildNarrative(
        predicted: String,
        severityScore: Int,
        metrics: ResponsibilityAnalyzer.DetailedMetrics,
        brakePeak: Int,
        speedLimit: Int,
        weatherCode: Int,
        isNight: Boolean,
        probabilities: Map<String, Double>,
    ): String {
        val fatalPct = ((probabilities["Fatal"] ?: 0.0) * 100).roundToInt()
        val seriousPct = ((probabilities["Serious"] ?: 0.0) * 100).roundToInt()
        val slightPct = ((probabilities["Slight"] ?: 0.0) * 100).roundToInt()

        return buildString {
            append("本次严重度结果由训练完成的 collision severity 表格模型导出参数在端侧离线计算完成，不依赖后端或网络。")
            append(" 模型实际参与推断的本地输入包括涉事车辆数、推断限速、事故月份/日期/时刻、周末与昼夜标记、天气、光照、路面状态以及城乡属性映射。")
            append(" 当前样本映射得到限速 ${speedLimit}km/h、天气 ${weatherCode.toWeatherLabel()}、")
            append(if (isNight) "夜间光照" else "白天光照")
            append("；模型输出 Fatal ${fatalPct}% / Serious ${seriousPct}% / Slight ${slightPct}% ，最终判定为${predicted.toDisplaySeverityLabel()}，严重度指数 ${severityScore}/100。")
            append(" 页面同时展示事故前 3 秒均速 ${format1(metrics.avgSpeedLast3sKph)}km/h、峰值减速度 ${format2(metrics.maxDecelerationMS2)}m/s²、峰值制动 ${brakePeak}% 等遥测指标，作为对模型结论的本地解释补充。")
        }
    }

    private fun buildKeyFactors(
        bundle: AccidentDetailBundle,
        metrics: ResponsibilityAnalyzer.DetailedMetrics,
        maxSpeed: Float,
        brakePeak: Int,
        weatherCode: Int,
        isNight: Boolean,
        speedLimit: Int,
        probabilities: Map<String, Double>,
    ): List<String> = buildList {
        add("本次结论来自训练好的 collision severity 模型导出参数，推断过程完全在本地执行。")
        add("模型输入映射包含：涉事车辆数=${if (bundle.event.type == AccidentType.COLLISION) 2 else 1}、限速=${speedLimit}km/h、天气=${weatherCode.toWeatherLabel()}、${if (isNight) "夜间" else "白天"}。")
        if (maxSpeed >= 90f) add("事故前峰值速度 ${format1(maxSpeed)}km/h，页面遥测显示动能水平偏高。")
        if (metrics.maxDecelerationMS2 <= -8f) add("峰值减速度 ${format2(metrics.maxDecelerationMS2)}m/s²，达到碰撞级减速区间。")
        if (metrics.ttcAtBrakeStart in 0f..1.5f) add("制动时 TTC 仅 ${format1(metrics.ttcAtBrakeStart)}s，碰撞余度不足。")
        if (brakePeak >= 80) add("峰值制动 ${brakePeak}%，说明事故前已出现高强度制动动作。")
        if (weatherCode in setOf(2, 3, 5, 7)) add("天气被映射为 ${weatherCode.toWeatherLabel()}，模型会提高风险场景权重。")
        if (isNight) add("夜间光照条件会抬升高严重度类别的本地预测概率。")
        add("当前概率分布：Fatal ${(probabilities["Fatal"] ?: 0.0).times(100).roundToInt()}% / Serious ${(probabilities["Serious"] ?: 0.0).times(100).roundToInt()}% / Slight ${(probabilities["Slight"] ?: 0.0).times(100).roundToInt()}%。")
    }.take(6)
}

private fun zeroDeltas(size: Int): List<Double> = List(size) { 0.0 }

private fun preferredProbabilityOrder(): List<String> = listOf("Fatal", "Serious", "Slight")

private fun softmax(values: FloatArray): FloatArray {
    val maxValue = values.maxOrNull() ?: 0f
    val exps = values.map { exp(it - maxValue) }
    val sum = exps.sum().coerceAtLeast(0.0001f)
    return exps.map { it / sum }.toFloatArray()
}

private fun String.toDisplaySeverityLabel(): String = when (this) {
    "Fatal" -> "致命事故"
    "Serious" -> "重伤事故"
    "Slight" -> "轻微事故"
    else -> this
}

private fun Int.toWeatherLabel(): String = when (this) {
    1 -> "晴/多云"
    2 -> "雨天"
    3 -> "雪天"
    4 -> "大风晴天"
    5 -> "雨天伴大风"
    7 -> "雾天"
    8 -> "其他天气"
    9 -> "未知天气"
    else -> "未知"
}

private fun inferSpeedLimit(maxSpeedKph: Float): Int = when {
    maxSpeedKph <= 25f -> 20
    maxSpeedKph <= 40f -> 30
    maxSpeedKph <= 55f -> 40
    maxSpeedKph <= 70f -> 50
    maxSpeedKph <= 90f -> 60
    else -> 70
}

private fun inferWeatherConditions(weather: String?): Int = when {
    weather.isNullOrBlank() -> 1
    weather.contains("雾") -> 7
    weather.contains("雪") -> 3
    weather.contains("雨") && weather.contains("风") -> 5
    weather.contains("雨") -> 2
    weather.contains("风") -> 4
    else -> 1
}

private fun inferRoadSurfaceCondition(weatherCode: Int): Int = when (weatherCode) {
    2, 5, 7 -> 2
    3 -> 3
    else -> 1
}

private fun format1(value: Float): String = String.format(Locale.getDefault(), "%.1f", value)

private fun format2(value: Float): String = String.format(Locale.getDefault(), "%.2f", value)
