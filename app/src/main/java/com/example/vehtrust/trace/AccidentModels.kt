package com.example.vehtrust.trace

data class AccidentEvent(
    val id: String,
    val type: AccidentType,
    val timeMillis: Long,
    val locationText: String,
    val triggerReasons: List<String>,
    val severity: Severity,
    val autoDrivingState: AutoDrivingState,
    val summary: String,
)

enum class AccidentType {
    COLLISION, AUTOPILOT_FAULT,
    // 实验新增事故类型（云端复盘实验用）
    DRIVER_SLOW_REACTION,      // 驾驶员反应不足
    AEB_DELAY_OR_MISS,         // AEB触发延迟或未触发
    TTC_LOW_RISK,              // TTC过低导致碰撞风险
    DRIVER_TAKEOVER_FAIL,      // 驾驶员接管不足
    ENVIRONMENT_DISTURB,       // 环境因素干扰
    MULTI_FACTOR               // 多因素共同作用
}

enum class Severity { LOW, MEDIUM, HIGH }

enum class AutoDrivingState { MANUAL, L2_ASSIST, AUTONOMOUS }

data class TelemetryPoint(
    val tMs: Int, // 相对事故时刻（毫秒），负值表示事故前
    val speedKph: Float,
    val axMS2: Float,
    val brake: Int, // 0-100
    val steerDeg: Float,
    /** 横向加速度 m/s² */
    val ayMS2: Float = 0f,
    /** 横摆角速度 °/s */
    val yawRateDegS: Float = 0f,
    /** 油门 0-100 */
    val throttlePct: Int = 0,
    /** AEB 介入语义（对齐 IADAS AUTONOMOUS_EMERGENCY_BRAKING） */
    val aebActive: Boolean = false,
    /** 0 无 / 1 左 / 2 右 / 3 双闪 */
    val blinkerCode: Int = 0,
    /** FCW 预警等级 0-3（对齐 FORWARD_COLLISION_WARN 灵敏度语义） */
    val fcwActiveLevel: Int = 0,
)

data class EnvironmentSnapshot(
    val weather: String,
    val road: String,
    val obstacle: String,
    val laneMarking: String,
)

data class DecisionTrace(
    val sensorInput: String,
    val perception: String,
    val planning: String,
    val control: String,
)

data class ResponsibilityResult(
    val driverFactor: Int, // 0-100
    val systemFactor: Int, // 0-100
    val environmentFactor: Int, // 0-100
    val conclusion: String,
    val reasons: List<String>,
)

data class DeepLearningResult(
    val modelName: String,
    val accidentTypeConfidence: Int,
    val driverRiskScore: Int,
    val systemRiskScore: Int,
    val environmentRiskScore: Int,
    val overallRiskScore: Int,
    val predictedLabel: String,
    val evidence: List<String>,
)

data class EvidenceRecord(
    val evidenceId: String,
    val sha256: String,
    val timestampMillis: Long,
    val blockchainTxId: String,
    val signature: String,
)

data class AccidentDetailBundle(
    val event: AccidentEvent,
    val telemetry10sBefore: List<TelemetryPoint>,
    val telemetry10sAfter: List<TelemetryPoint> = emptyList(),
    val environmentSnapshot: EnvironmentSnapshot?,
    val decisionTrace: DecisionTrace?,
    val responsibility: ResponsibilityResult,
)
