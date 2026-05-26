package com.example.carscreen.trace

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

enum class AccidentType { COLLISION, AUTOPILOT_FAULT }

enum class Severity { LOW, MEDIUM, HIGH }

enum class AutoDrivingState { MANUAL, L2_ASSIST, AUTONOMOUS }

data class TelemetryPoint(
    val tMs: Int, // 相对事故时刻（毫秒），负值表示事故前
    val speedKph: Float,
    val axMS2: Float,
    val brake: Int, // 0-100
    val steerDeg: Float,
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
    val environmentSnapshot: EnvironmentSnapshot?,
    val decisionTrace: DecisionTrace?,
    val responsibility: ResponsibilityResult,
)
