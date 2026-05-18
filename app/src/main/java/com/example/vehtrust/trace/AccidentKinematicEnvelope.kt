package com.example.vehtrust.trace

import kotlin.math.sin
import kotlin.random.Random

/**
 * 事故前/后 10s 的 **多阶段包络**（smoothstep 衔接）+ 可复现微噪声。
 * 与实车采样解耦；实车接入时用 CarProperty 流替换即可。
 */
object AccidentKinematicEnvelope {

    private fun smoothstep(u: Float): Float {
        val t = u.coerceIn(0f, 1f)
        return t * t * (3f - 2f * t)
    }

    /** t ∈ [t0,t1] 时从 0 过渡到 1 */
    private fun ramp(tMs: Int, t0: Int, t1: Int): Float {
        if (t1 <= t0) return if (tMs >= t1) 1f else 0f
        return smoothstep((tMs - t0).toFloat() / (t1 - t0))
    }

    private fun noise(rng: Random, scale: Float): Float = (rng.nextFloat() - 0.5f) * 2f * scale

    data class PostSample(
        val speedKphHint: Float,
        val brakePct: Float,
        val axMS2: Float,
        val steerDeg: Float,
    )

    /** 事故前：制动 0–100、纵向加速度 m/s²、方向盘 ° */
    fun preKinematics(
        tMs: Int,
        arch: AccidentSimulationArchetype,
        rng: Random,
        tNorm: Float,
    ): Triple<Float, Float, Float> {
        val brake = when (arch) {
            AccidentSimulationArchetype.REAR_END_SEVERE -> {
                val b = 4f +
                    22f * ramp(tMs, -10_000, -6500) +
                    28f * ramp(tMs, -5200, -3200) +
                    38f * ramp(tMs, -2400, -900) +
                    8f * ramp(tMs, -900, -200)
                (b + noise(rng, 4f)).coerceIn(0f, 100f)
            }
            AccidentSimulationArchetype.REAR_END_MODERATE -> {
                val b = 5f +
                    18f * ramp(tMs, -9800, -6200) +
                    32f * ramp(tMs, -5000, -2200) +
                    28f * ramp(tMs, -2200, -700)
                (b + noise(rng, 3.5f)).coerceIn(0f, 100f)
            }
            AccidentSimulationArchetype.DEFENSIVE_LIGHT -> {
                val b = 3f +
                    14f * ramp(tMs, -9000, -5200) +
                    38f * ramp(tMs, -3600, -1400) +
                    12f * ramp(tMs, -1400, -400)
                (b + noise(rng, 3f)).coerceIn(0f, 95f)
            }
            AccidentSimulationArchetype.AUTOPILOT_DEGRADE -> {
                val b = 2f +
                    10f * ramp(tMs, -9800, -5200) +
                    28f * ramp(tMs, -3200, -1400) +
                    22f * ramp(tMs, -1400, -400)
                (b + noise(rng, 3f)).coerceIn(0f, 92f)
            }
        }

        val ax = when (arch) {
            AccidentSimulationArchetype.REAR_END_SEVERE -> {
                val a = -0.35f +
                    -1.1f * ramp(tMs, -9800, -5200) +
                    -2.4f * ramp(tMs, -4800, -2400) +
                    -4.2f * ramp(tMs, -2200, -900) +
                    -2.8f * ramp(tMs, -900, -250)
                a + noise(rng, 0.35f)
            }
            AccidentSimulationArchetype.REAR_END_MODERATE -> {
                val a = -0.3f +
                    -0.9f * ramp(tMs, -9600, -5400) +
                    -2.0f * ramp(tMs, -4600, -2200) +
                    -3.2f * ramp(tMs, -2000, -800) +
                    -1.2f * ramp(tMs, -800, -300)
                a + noise(rng, 0.32f)
            }
            AccidentSimulationArchetype.DEFENSIVE_LIGHT -> {
                val a = -0.25f +
                    -0.7f * ramp(tMs, -9000, -5000) +
                    -1.6f * ramp(tMs, -4200, -2200) +
                    -2.2f * ramp(tMs, -2000, -900) +
                    -0.9f * ramp(tMs, -900, -350)
                a + noise(rng, 0.28f)
            }
            AccidentSimulationArchetype.AUTOPILOT_DEGRADE -> {
                val a = -0.15f +
                    -0.35f * ramp(tMs, -10_000, -6000) +
                    -0.55f * ramp(tMs, -5200, -3200) +
                    -1.15f * ramp(tMs, -3000, -1600) +
                    -0.85f * ramp(tMs, -1600, -500)
                a + noise(rng, 0.22f)
            }
        }.coerceIn(-12f, 2f)

        val steer = when (arch) {
            AccidentSimulationArchetype.AUTOPILOT_DEGRADE -> {
                val s = (1f - tNorm).coerceIn(0f, 1f)
                val base = 3.5f + 14f * ramp(tMs, -9800, -4200) + 9f * s * s
                base + noise(rng, 1.8f)
            }
            AccidentSimulationArchetype.DEFENSIVE_LIGHT -> {
                val weave = 6f * sin(tMs / 950f) * ramp(tMs, -8000, -2000)
                weave + (-4f + 10f * ramp(tMs, -4200, -1200)) + noise(rng, 2.2f)
            }
            AccidentSimulationArchetype.REAR_END_MODERATE -> {
                noise(rng, 1.1f) +
                    noise(rng, 1.2f) * ramp(tMs, -9000, -1500) +
                    (rng.nextFloat() - 0.5f) * 5f * ramp(tMs, -5000, -800)
            }
            AccidentSimulationArchetype.REAR_END_SEVERE -> {
                noise(rng, 1.2f) + (rng.nextFloat() - 0.5f) * 4f * ramp(tMs, -6000, -900)
            }
        }.coerceIn(-22f, 22f)

        return Triple(brake, ax, steer)
    }

    fun postKinematics(
        tMs: Int,
        decay: Float,
        arch: AccidentSimulationArchetype,
        impactSpeed: Float,
        rng: Random,
    ): PostSample {
        val damp = decay.coerceIn(0f, 1f)
        val brake = when (arch) {
            AccidentSimulationArchetype.REAR_END_SEVERE -> {
                val peak = 72f + 22f * ramp(tMs, 500, 2200) * damp
                val tail = 28f + 18f * (1f - damp)
                (peak.coerceAtMost(100f) * damp + tail * (1f - damp * 0.35f) + noise(rng, 3f)).coerceIn(0f, 100f)
            }
            AccidentSimulationArchetype.REAR_END_MODERATE ->
                (58f * damp * ramp(tMs, 400, 2000) + 22f + noise(rng, 2.5f)).coerceIn(0f, 100f)
            AccidentSimulationArchetype.DEFENSIVE_LIGHT ->
                (42f * damp * ramp(tMs, 500, 2400) + 12f + noise(rng, 2f)).coerceIn(0f, 88f)
            AccidentSimulationArchetype.AUTOPILOT_DEGRADE ->
                (32f * damp * ramp(tMs, 400, 2800) + 10f + noise(rng, 2f)).coerceIn(0f, 75f)
        }

        val ax = when (arch) {
            AccidentSimulationArchetype.REAR_END_SEVERE ->
                (-3.8f * damp * ramp(tMs, 200, 1400) - 0.35f + noise(rng, 0.35f)) * (0.55f + 0.45f * damp)
            AccidentSimulationArchetype.REAR_END_MODERATE ->
                (-2.6f * damp * ramp(tMs, 250, 1600) - 0.25f + noise(rng, 0.3f)) * (0.5f + 0.5f * damp)
            AccidentSimulationArchetype.DEFENSIVE_LIGHT ->
                (-1.5f * damp * ramp(tMs, 300, 1500) - 0.12f + noise(rng, 0.22f))
            AccidentSimulationArchetype.AUTOPILOT_DEGRADE ->
                (-0.45f + noise(rng, 0.18f)) * (0.4f + 0.6f * damp)
        }.coerceIn(-8f, 1.5f)

        val speedHint = when (arch) {
            AccidentSimulationArchetype.AUTOPILOT_DEGRADE ->
                (impactSpeed * (0.76f + damp * 0.18f) + noise(rng, 0.6f)).coerceAtLeast(0f)
            AccidentSimulationArchetype.DEFENSIVE_LIGHT ->
                (impactSpeed * (0.32f + damp * 0.48f) + noise(rng, 0.55f)).coerceAtLeast(0f)
            AccidentSimulationArchetype.REAR_END_MODERATE ->
                (impactSpeed * (0.38f + damp * 0.5f) + noise(rng, 0.5f)).coerceAtLeast(0f)
            AccidentSimulationArchetype.REAR_END_SEVERE ->
                (impactSpeed * damp * 0.92f + noise(rng, 0.45f)).coerceAtLeast(0f)
        }

        val steer = when (arch) {
            AccidentSimulationArchetype.AUTOPILOT_DEGRADE ->
                (8f + 7f * (1f - damp) + noise(rng, 2f)).coerceIn(-20f, 20f)
            AccidentSimulationArchetype.DEFENSIVE_LIGHT ->
                ((-6f + 12f * damp) * ramp(tMs, 400, 2200) + noise(rng, 1.8f)).coerceIn(-18f, 18f)
            else -> noise(rng, 1.4f) * (0.35f + 0.65f * damp)
        }

        return PostSample(speedKphHint = speedHint, brakePct = brake, axMS2 = ax, steerDeg = steer)
    }
}
