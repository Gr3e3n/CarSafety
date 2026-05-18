package com.example.vehtrust.trace

import kotlin.math.abs
import kotlin.math.min
import kotlin.random.Random

/**
 * 从底盘核心量派生与 CarExt `IADAS` / 车身动力学语义对齐的扩展遥测。
 * 车机实接时用 CarProperty / CarExt 读数直接写入 [TelemetryPoint]，可移除此合成器。
 */
object TelemetrySignalSynthesizer {

    fun enrich(
        event: AccidentEvent,
        tMs: Int,
        dtMs: Int,
        speedKph: Float,
        axMS2: Float,
        brake: Int,
        steerDeg: Float,
        prevSteerDeg: Float,
        isFirstSample: Boolean,
    ): TelemetryPoint {
        val dtSec = (dtMs / 1000f).coerceAtLeast(0.05f)
        val yawRateDegS = if (isFirstSample) {
            0f
        } else {
            ((steerDeg - prevSteerDeg) / dtSec) * 0.92f + (Random.nextFloat() - 0.5f) * 1.8f
        }
        val speedNorm = (speedKph / 58f).coerceIn(0.12f, 1.85f)
        val ayMS2 = steerDeg * 0.105f * speedNorm + (Random.nextFloat() - 0.5f) * 0.55f
        val throttlePct = when {
            brake > 72 -> Random.nextInt(0, 10)
            brake > 38 -> (48 - brake / 2 + Random.nextInt(-5, 7)).coerceIn(6, 52)
            else -> (78 - brake / 3 + Random.nextInt(-8, 8)).coerceIn(10, 95)
        }
        val aebActive = when (event.type) {
            AccidentType.AUTOPILOT_FAULT -> brake >= 52 && axMS2 <= -3.4f && tMs > -2600
            else -> brake >= 58 && axMS2 <= -4.3f
        }
        val fcwCap = when (event.severity) {
            Severity.LOW -> 2
            Severity.MEDIUM -> 3
            Severity.HIGH -> 3
        }
        val fcwRaw = when {
            speedKph < 12f -> 0
            tMs > -700 && speedKph > 38f -> 3
            tMs > -1800 && speedKph > 26f -> 2
            tMs > -4200 && speedKph > 18f -> 1
            else -> 0
        }
        val fcwActiveLevel = min(fcwRaw, fcwCap)
        val blinkerCode = when {
            abs(axMS2) >= 7.2f && event.severity == Severity.HIGH -> 3
            steerDeg >= 7f -> 2
            steerDeg <= -7f -> 1
            event.type == AccidentType.AUTOPILOT_FAULT && tMs > -2800 && abs(steerDeg) > 4f ->
                if (steerDeg > 0f) 2 else 1
            else -> 0
        }
        return TelemetryPoint(
            tMs = tMs,
            speedKph = speedKph,
            axMS2 = axMS2,
            brake = brake,
            steerDeg = steerDeg,
            ayMS2 = ayMS2,
            yawRateDegS = yawRateDegS,
            throttlePct = throttlePct,
            aebActive = aebActive,
            blinkerCode = blinkerCode,
            fcwActiveLevel = fcwActiveLevel,
        )
    }
}
