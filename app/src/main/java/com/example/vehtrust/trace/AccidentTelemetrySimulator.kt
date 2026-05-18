package com.example.vehtrust.trace

import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * 合成事故 EDR 时间窗（事故前/后 10s，500ms 步进），与 [TelemetrySignalSynthesizer] 衔接。
 * 运动学包络见 [AccidentKinematicEnvelope]；剧本枚举见 [AccidentSimulationArchetype]。
 */
object AccidentTelemetrySimulator {

    const val WINDOW_PRE_MS: Int = 10_000
    const val WINDOW_POST_MS: Int = 10_000
    const val STEP_MS: Int = 500

    private fun archetypeFor(event: AccidentEvent): AccidentSimulationArchetype = when (event.id) {
        "E-20260318-0001" -> AccidentSimulationArchetype.REAR_END_SEVERE
        "E-20260318-0002" -> AccidentSimulationArchetype.AUTOPILOT_DEGRADE
        "E-20260318-0003" -> AccidentSimulationArchetype.DEFENSIVE_LIGHT
        else -> when (event.type) {
            AccidentType.AUTOPILOT_FAULT, AccidentType.DRIVER_TAKEOVER_FAIL -> AccidentSimulationArchetype.AUTOPILOT_DEGRADE
            AccidentType.COLLISION,
            AccidentType.DRIVER_SLOW_REACTION,
            AccidentType.AEB_DELAY_OR_MISS,
            AccidentType.TTC_LOW_RISK,
            AccidentType.ENVIRONMENT_DISTURB,
            AccidentType.MULTI_FACTOR,
            -> when (event.severity) {
                Severity.HIGH -> AccidentSimulationArchetype.REAR_END_SEVERE
                Severity.MEDIUM -> AccidentSimulationArchetype.REAR_END_MODERATE
                Severity.LOW -> AccidentSimulationArchetype.DEFENSIVE_LIGHT
            }
        }
    }

    /** 与事件绑定的可复现随机源（同 id 重进详情页波形一致） */
    private fun rngFor(event: AccidentEvent, salt: Long): Random =
        Random(event.id.hashCode().toLong() xor salt xor (event.timeMillis and 0x7fffL))

    private fun baseSpeedKph(arch: AccidentSimulationArchetype, event: AccidentEvent): Float {
        val base = when (arch) {
            AccidentSimulationArchetype.DEFENSIVE_LIGHT -> 25f
            AccidentSimulationArchetype.AUTOPILOT_DEGRADE -> 45f
            AccidentSimulationArchetype.REAR_END_MODERATE -> 48f
            AccidentSimulationArchetype.REAR_END_SEVERE -> 65f
        }
        val severityDelta = when (event.severity) {
            Severity.LOW -> -2f
            Severity.MEDIUM -> 0f
            Severity.HIGH -> 2f
        }
        return (base + severityDelta).coerceIn(18f, 88f)
    }

    private fun defaultImpactSpeed(event: AccidentEvent): Float = when (event.severity) {
        Severity.LOW -> 18f
        Severity.MEDIUM -> 28f
        Severity.HIGH -> 36f
    }

    fun simulatePreWindow(event: AccidentEvent): List<TelemetryPoint> {
        val arch = archetypeFor(event)
        val rng = rngFor(event, 0x51EE_0001L)
        val points = mutableListOf<TelemetryPoint>()
        var prevSteer = 0f
        var speedKph = baseSpeedKph(arch, event)
        val dtSec = STEP_MS / 1000f
        for (t in -WINDOW_PRE_MS..0 step STEP_MS) {
            val tNorm = abs(t / WINDOW_PRE_MS.toFloat())
            val (brakeF, ax, steer) = AccidentKinematicEnvelope.preKinematics(t, arch, rng, tNorm)
            speedKph = (speedKph + ax * dtSec * 3.6f + noiseSpeed(rng)).coerceIn(0f, 135f)
            points += TelemetrySignalSynthesizer.enrich(
                event = event,
                tMs = t,
                dtMs = STEP_MS,
                speedKph = speedKph,
                axMS2 = ax,
                brake = brakeF.roundToInt().coerceIn(0, 100),
                steerDeg = steer,
                prevSteerDeg = prevSteer,
                isFirstSample = t == -WINDOW_PRE_MS,
            )
            prevSteer = steer
        }
        return points
    }

    fun simulatePostWindow(event: AccidentEvent, impactPoint: TelemetryPoint?): List<TelemetryPoint> {
        val arch = archetypeFor(event)
        val rng = rngFor(event, 0x51EE_0002L)
        val impactSpeed = impactPoint?.speedKph ?: defaultImpactSpeed(event)
        val points = mutableListOf<TelemetryPoint>()
        var prevSteer = impactPoint?.steerDeg ?: 0f
        var speedKph = impactSpeed.coerceAtLeast(0f)
        val dtSec = STEP_MS / 1000f
        for (t in STEP_MS..WINDOW_POST_MS step STEP_MS) {
            val decay = (1f - t / WINDOW_POST_MS.toFloat()).coerceIn(0f, 1f)
            val sample = AccidentKinematicEnvelope.postKinematics(t, decay, arch, impactSpeed, rng)
            speedKph = (speedKph + sample.axMS2 * dtSec * 3.6f + noiseSpeed(rng)).coerceAtLeast(0f)
            speedKph = speedKph * 0.82f + sample.speedKphHint * 0.18f
            points += TelemetrySignalSynthesizer.enrich(
                event = event,
                tMs = t,
                dtMs = STEP_MS,
                speedKph = speedKph.coerceIn(0f, 130f),
                axMS2 = sample.axMS2,
                brake = sample.brakePct.roundToInt().coerceIn(0, 100),
                steerDeg = sample.steerDeg,
                prevSteerDeg = prevSteer,
                isFirstSample = false,
            )
            prevSteer = sample.steerDeg
        }
        return points
    }

    private fun noiseSpeed(rng: Random): Float = (rng.nextFloat() - 0.5f) * 0.12f
}
