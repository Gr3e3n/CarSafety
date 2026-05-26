package com.example.carscreen.trace

import android.content.Context
import com.example.carscreen.db.AccidentDao
import com.example.carscreen.db.AccidentDatabase
import com.example.carscreen.db.AccidentEventEntity
import com.example.carscreen.db.ResponsibilityEntity
import com.example.carscreen.db.TelemetryEntity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.random.Random

/**
 * 事故溯源监控器（模拟数据版 + Room 持久化）
 *
 * - 20Hz 高频采样写入环形缓冲
 * - 触发条件满足时冻结"事故前 10 秒"并继续采集"事故后 10 秒"
 * - 写入 Room 数据库（持久化，App 重启后数据仍在）
 * - 写入内存 AccidentRepository（UI 实时刷新）
 *
 * 真实接入：仅需替换 sampleTelemetry() 中的数据来源为 CarPropertyManager 订阅值。
 */
object AccidentMonitor {

    const val SAMPLE_INTERVAL_MS: Long = 50L    // 20Hz
    const val PRE_WINDOW_MS: Int = 10_000       // 事故前 10 秒
    const val POST_WINDOW_MS: Int = 10_000      // 事故后 10 秒
    private const val BUFFER_MS: Int = 25_000
    private const val COOLDOWN_MS: Long = 600_000L  // 触发冷却 10 分钟

    private val gson = Gson()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var job: Job? = null
    private var dao: AccidentDao? = null

    private val ring = RingBuffer<TelemetryPoint>(
        capacity = max(1, (BUFFER_MS / SAMPLE_INTERVAL_MS.toInt()) + 50)
    )

    private var inCapture = false
    private var lastTriggerAt: Long = 0L

    // ── 公共 API ──────────────────────────────────────────────────────

    fun start(context: Context? = null, dao: AccidentDao? = null) {
        if (job != null) return
        this.dao = dao ?: context?.let {
            AccidentDatabase.getInstance(it).accidentDao()
        }
        job = scope.launch { runSamplingLoop() }
    }

    fun stop() {
        job?.cancel()
        job = null
    }

    // ── 采样主循环 ────────────────────────────────────────────────────

    private suspend fun runSamplingLoop() {
        var tMs = -PRE_WINDOW_MS
        while (true) {
            val p = sampleTelemetry(tMs)
            ring.add(p)
            val now = System.currentTimeMillis()
            val cooldownOk = now - lastTriggerAt > COOLDOWN_MS
            if (!inCapture && cooldownOk && shouldTrigger(p)) {
                inCapture = true
                lastTriggerAt = now
                triggerCapture()
                inCapture = false
                tMs = -PRE_WINDOW_MS
            } else {
                tMs = (tMs + SAMPLE_INTERVAL_MS.toInt()).coerceAtMost(0)
            }
            delay(SAMPLE_INTERVAL_MS)
        }
    }

    // ── 触发处理 ──────────────────────────────────────────────────────

    private suspend fun triggerCapture() {
        val pre = ring.snapshotLastByTime(PRE_WINDOW_MS)
        val post = collectPostWindow()
        val detail = buildDetail(pre, post)
        AccidentRepository.upsertCapturedEvent(detail)
        persistToRoom(detail)
    }

    private suspend fun collectPostWindow(): List<TelemetryPoint> {
        val list = mutableListOf<TelemetryPoint>()
        var t = 0
        repeat((POST_WINDOW_MS / SAMPLE_INTERVAL_MS).toInt()) {
            val p = sampleTelemetry(t)
            list += p
            ring.add(p)
            t += SAMPLE_INTERVAL_MS.toInt()
            delay(SAMPLE_INTERVAL_MS)
        }
        return list
    }

    // ── Room 持久化 ───────────────────────────────────────────────────

    private suspend fun persistToRoom(detail: AccidentDetailBundle) {
        val d = dao ?: return
        val e = detail.event
        d.insertEvent(
            AccidentEventEntity(
                id = e.id,
                type = e.type.name,
                timeMillis = e.timeMillis,
                locationText = e.locationText,
                triggerReasonsJson = gson.toJson(e.triggerReasons),
                severity = e.severity.name,
                autoDrivingState = e.autoDrivingState.name,
                summary = e.summary,
            )
        )
        d.insertTelemetry(
            detail.telemetry10sBefore.map { p ->
                TelemetryEntity(
                    eventId = e.id,
                    tMs = p.tMs,
                    speedKph = p.speedKph,
                    axMS2 = p.axMS2,
                    brake = p.brake,
                    steerDeg = p.steerDeg,
                )
            }
        )
        val resp = detail.responsibility
        val metrics = ResponsibilityAnalyzer.analyze(e, detail.telemetry10sBefore)
        d.insertResponsibility(
            ResponsibilityEntity(
                eventId = e.id,
                driverFactor = resp.driverFactor,
                systemFactor = resp.systemFactor,
                environmentFactor = resp.environmentFactor,
                conclusion = resp.conclusion,
                reasonsJson = gson.toJson(resp.reasons),
                reactionTimeMs = metrics.reactionTimeMs,
                brakeRiseTimeMs = metrics.brakeRiseTimeMs,
                maxDecelerationMS2 = metrics.maxDecelerationMS2,
                aebDelayMs = metrics.aebDelayMs,
                ttcAtBrakeStart = metrics.ttcAtBrakeStart,
            )
        )
    }

    // ── 事件构建 ──────────────────────────────────────────────────────

    private fun buildDetail(
        pre: List<TelemetryPoint>,
        post: List<TelemetryPoint>,
    ): AccidentDetailBundle {
        val now = System.currentTimeMillis()
        val severity = when {
            pre.any { it.axMS2 <= -8.5f } -> Severity.HIGH
            pre.any { it.axMS2 <= -6.5f } -> Severity.MEDIUM
            else -> Severity.LOW
        }
        val type = if (Random.nextBoolean()) AccidentType.COLLISION else AccidentType.AUTOPILOT_FAULT
        val triggers = when (type) {
            AccidentType.COLLISION -> listOf("碰撞加速度超阈值", "紧急制动+急减速")
            AccidentType.AUTOPILOT_FAULT -> listOf("自动驾驶异常退出", "控制模块响应异常")
        }
        val event = AccidentEvent(
            id = "E-$now",
            type = type,
            timeMillis = now,
            locationText = listOf("南京·雨花台区", "上海·浦东新区", "北京·亦庄").random(),
            triggerReasons = triggers,
            severity = severity,
            autoDrivingState = when (type) {
                AccidentType.COLLISION -> AutoDrivingState.L2_ASSIST
                AccidentType.AUTOPILOT_FAULT -> AutoDrivingState.AUTONOMOUS
            },
            summary = when (type) {
                AccidentType.COLLISION -> "检测到急减速与强制动，触发自动取证"
                AccidentType.AUTOPILOT_FAULT -> "检测到自动驾驶异常与控制延迟，触发故障溯源"
            },
        )
        val telemetry10sBefore = normalizePreWindow(pre)
        val env = if (type == AccidentType.AUTOPILOT_FAULT) AccidentRepository.generateEnvironment() else null
        val trace = if (type == AccidentType.AUTOPILOT_FAULT) AccidentRepository.generateDecisionTrace() else null
        val metrics = ResponsibilityAnalyzer.analyze(event, telemetry10sBefore)
        val resp = ResponsibilityAnalyzer.inferResponsibility(event, metrics, env, trace)
        return AccidentDetailBundle(
            event = event,
            telemetry10sBefore = telemetry10sBefore,
            environmentSnapshot = env,
            decisionTrace = trace,
            responsibility = resp,
        )
    }

    private fun normalizePreWindow(pre: List<TelemetryPoint>): List<TelemetryPoint> {
        if (pre.isEmpty()) return pre
        val lastT = pre.last().tMs
        return pre.map { it.copy(tMs = it.tMs - lastT - PRE_WINDOW_MS) }
    }

    // ── 触发判断 ──────────────────────────────────────────────────────

    private fun shouldTrigger(p: TelemetryPoint): Boolean {
        val collisionByAccel = p.axMS2 <= -7.8f
        val emergencyBrake = p.brake >= 70 && p.axMS2 <= -5.5f
        val randomDemo = Random.nextInt(0, 144_000) == 1  // 极低概率随机，仅用于演示（约2小时触发一次）
        return collisionByAccel || emergencyBrake || randomDemo
    }

    // ── 模拟数据源 ────────────────────────────────────────────────────
    // TODO 真实接入：替换为 CarPropertyManager 最新订阅值

    private fun sampleTelemetry(tMs: Int): TelemetryPoint {
        val baseSpeed = 55f + (Random.nextFloat() - 0.5f) * 3f
        val brakingPhase = tMs >= -1200
        val brake = when {
            brakingPhase -> (65 + Random.nextInt(0, 30)).coerceAtMost(100)
            tMs >= -2500 -> (20 + Random.nextInt(0, 25))
            else -> Random.nextInt(0, 10)
        }
        val ax = when {
            tMs >= -800  -> (-8.2f + Random.nextFloat() * 1.2f)
            tMs >= -2500 -> (-3.2f + Random.nextFloat() * 1.0f)
            else         -> (-0.3f + Random.nextFloat() * 0.8f)
        }
        val steer = if (brakingPhase) {
            (Random.nextFloat() - 0.5f) * 10f
        } else {
            (Random.nextFloat() - 0.5f) * 4f
        }
        val speed = (baseSpeed + ax * 0.8f).coerceAtLeast(0f)
        return TelemetryPoint(
            tMs = tMs,
            speedKph = speed,
            axMS2 = ax,
            brake = brake,
            steerDeg = steer,
        )
    }

    // ── 环形缓冲 ──────────────────────────────────────────────────────

    private class RingBuffer<T>(private val capacity: Int) {
        private val data = ArrayList<T>(capacity)

        @Synchronized
        fun add(v: T) {
            if (data.size < capacity) data.add(v)
            else { data.removeAt(0); data.add(v) }
        }

        @Synchronized
        fun snapshotLastByTime(windowMs: Int): List<TelemetryPoint> {
            val count = max(1, (windowMs / SAMPLE_INTERVAL_MS).toInt())
            val start = max(0, data.size - count)
            @Suppress("UNCHECKED_CAST")
            return data.subList(start, data.size).map { it as TelemetryPoint }
        }
    }
}
