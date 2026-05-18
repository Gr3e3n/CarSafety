package com.example.vehtrust.trace

/**
 * 合成事故运动学剧本（仅用于演示 EDR / 回放，非车规模型）。
 * 与 [AccidentTelemetrySimulator]、[AccidentKinematicEnvelope] 配合使用。
 */
enum class AccidentSimulationArchetype {
    /** 高速跟车，末段强减速 */
    REAR_END_SEVERE,
    /** 中等碰撞 / 城市跟车 */
    REAR_END_MODERATE,
    /** 低严重度：避让或浅制动 */
    DEFENSIVE_LIGHT,
    /** 智驾异常：横向累积后纵向响应滞后 */
    AUTOPILOT_DEGRADE,
}
