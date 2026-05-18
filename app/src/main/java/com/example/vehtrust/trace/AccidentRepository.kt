package com.example.vehtrust.trace

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vehtrust.db.AccidentDatabase
import com.example.vehtrust.db.AccidentDao
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

/**
 * 事故溯源仓库
 * - 内存 LiveData 供 UI 实时订阅
 * - Room DAO 供持久化读写（App 重启后数据不丢）
 * - 两源合并：Room 数据 + 运行时新触发事件均会更新 UI
 */
object AccidentRepository {

    private val gson = Gson()
    private val scope = CoroutineScope(Dispatchers.IO)

    // 内存事件列表（LiveData，供 UI 订阅）
    private val _events = MutableLiveData<List<AccidentEvent>>(seedEvents())
    val events: LiveData<List<AccidentEvent>> = _events

    // 内存详情缓存
    private val detailCache = LinkedHashMap<String, AccidentDetailBundle>().apply {
        seedEvents().forEach { e -> put(e.id, seedDetail(e)) }
    }

    // Room DAO（由 initWithContext 注入）
    private var dao: AccidentDao? = null

    /**
     * 注入 Room DAO 并从数据库加载历史事件合并到列表。
     * 在 Application 或 Service onCreate 中调用一次。
     */
    fun initWithContext(context: Context) {
        dao = AccidentDatabase.getInstance(context).accidentDao()
        scope.launch {
            val dbEvents = dao!!.listEvents()
            if (dbEvents.isNotEmpty()) {
                val mapped = dbEvents.map { it.toAccidentEvent() }
                val current = _events.value ?: emptyList()
                val merged = (mapped + current)
                    .distinctBy { it.id }
                    .sortedByDescending { it.timeMillis }
                _events.postValue(merged)
            }
        }
    }

    fun listEvents(): List<AccidentEvent> = _events.value ?: emptyList()

    fun loadDetail(eventId: String): AccidentDetailBundle {
        return detailCache[eventId] ?: loadPersistedDetail(eventId)?.also { detail ->
            detailCache[eventId] = detail
        } ?: run {
            val event = listEvents().firstOrNull { it.id == eventId }
                ?: seedEvents().first()
            val d = seedDetail(event)
            detailCache[eventId] = d
            d
        }
    }

    /** 由 AccidentMonitor 触发调用：新事件写入内存+缓存 */
    fun upsertCapturedEvent(detail: AccidentDetailBundle) {
        detailCache[detail.event.id] = detail
        val current = _events.value ?: emptyList()
        val filtered = current.filterNot { it.id == detail.event.id }
        _events.postValue(listOf(detail.event) + filtered)
    }

    /** 生成可信存证（哈希+模拟签名） */
    fun generateEvidence(bundle: AccidentDetailBundle): EvidenceRecord {
        val payload = buildString {
            append("eventId=").append(bundle.event.id).append('\n')
            append("type=").append(bundle.event.type).append('\n')
            append("time=").append(bundle.event.timeMillis).append('\n')
            append("location=").append(bundle.event.locationText).append('\n')
            append("triggers=").append(bundle.event.triggerReasons.joinToString()).append('\n')
            append("telemetryBefore=").append(bundle.telemetry10sBefore.joinToString()).append('\n')
            append("telemetryAfter=").append(bundle.telemetry10sAfter.joinToString()).append('\n')
            append("env=").append(bundle.environmentSnapshot?.toString() ?: "null").append('\n')
            append("trace=").append(bundle.decisionTrace?.toString() ?: "null").append('\n')
            append("responsibility=").append(bundle.responsibility.toString()).append('\n')
        }
        val sha = Sha256.sha256Hex(payload)
        val ts = System.currentTimeMillis()
        val evidenceId = "EV-${bundle.event.id}"
        val record = EvidenceRecord(
            evidenceId = evidenceId,
            sha256 = sha,
            timestampMillis = ts,
            blockchainTxId = "0x" + Random.nextBytes(12).joinToString("") { "%02x".format(it) },
            signature = "SIG-" + sha.take(16).uppercase(),
        )
        // 异步写入 Room
        scope.launch {
            dao?.insertEvidence(
                com.example.vehtrust.db.EvidenceEntity(
                    evidenceId = record.evidenceId,
                    eventId = bundle.event.id,
                    sha256 = record.sha256,
                    timestampMillis = record.timestampMillis,
                    blockchainTxId = record.blockchainTxId,
                    signature = record.signature,
                )
            )
        }
        return record
    }

    // ── 环境 / 决策链路（委托 [AccidentContextGenerator]）────────────────

    // ── Entity 转换 ─────────────────────────────────────────────────

    private fun loadPersistedDetail(eventId: String): AccidentDetailBundle? {
        val d = dao ?: return null
        return runBlocking(Dispatchers.IO) {
            val eventEntity = d.getEvent(eventId) ?: return@runBlocking null
            val event = eventEntity.toAccidentEvent()
            val telemetry = d.getTelemetry(eventId).map {
                TelemetryPoint(
                    tMs = it.tMs,
                    speedKph = it.speedKph,
                    axMS2 = it.axMS2,
                    brake = it.brake,
                    steerDeg = it.steerDeg,
                    ayMS2 = it.ayMS2,
                    yawRateDegS = it.yawRateDegS,
                    throttlePct = it.throttlePct,
                    aebActive = it.aebActive != 0,
                    blinkerCode = it.blinkerCode,
                    fcwActiveLevel = it.fcwActiveLevel,
                )
            }
            val before = telemetry.filter { it.tMs <= 0 }
            val after = telemetry.filter { it.tMs > 0 }
            val env = AccidentContextGenerator.environmentFor(event)
            val trace = if (event.type == AccidentType.AUTOPILOT_FAULT || event.type == AccidentType.DRIVER_TAKEOVER_FAIL) {
                AccidentContextGenerator.decisionTraceFor(event, env)
            } else {
                null
            }
            val responsibility = d.getResponsibility(eventId)?.toResponsibilityResult()
                ?: ResponsibilityAnalyzer.inferResponsibility(
                    event,
                    ResponsibilityAnalyzer.analyze(event, before, aebTriggerTMs = firstAebTMs(before)),
                    env,
                    trace,
                )
            AccidentDetailBundle(
                event = event,
                telemetry10sBefore = before,
                telemetry10sAfter = after,
                environmentSnapshot = env,
                decisionTrace = trace,
                responsibility = responsibility,
            )
        }
    }

    private fun com.example.vehtrust.db.AccidentEventEntity.toAccidentEvent(): AccidentEvent {
        val triggerType = object : TypeToken<List<String>>() {}.type
        return AccidentEvent(
            id = id,
            type = AccidentType.valueOf(type),
            timeMillis = timeMillis,
            locationText = locationText,
            triggerReasons = gson.fromJson(triggerReasonsJson, triggerType),
            severity = Severity.valueOf(severity),
            autoDrivingState = AutoDrivingState.valueOf(autoDrivingState),
            summary = summary,
        )
    }

    private fun com.example.vehtrust.db.ResponsibilityEntity.toResponsibilityResult(): ResponsibilityResult {
        val reasonType = object : TypeToken<List<String>>() {}.type
        return ResponsibilityResult(
            driverFactor = driverFactor,
            systemFactor = systemFactor,
            environmentFactor = environmentFactor,
            conclusion = conclusion,
            reasons = gson.fromJson(reasonsJson, reasonType),
        )
    }

    // ── 种子数据（首次运行 / 无历史时展示）─────────────────────────

    private fun seedEvents(): List<AccidentEvent> {
        val now = System.currentTimeMillis()
        return listOf(
            AccidentEvent(
                id = "E-20260318-0001",
                type = AccidentType.COLLISION,
                timeMillis = now - 25 * 60 * 1000L,
                locationText = "南京·雨花台区 软件大道",
                triggerReasons = listOf("碰撞加速度超阈值", "紧急制动+急减速"),
                severity = Severity.HIGH,
                autoDrivingState = AutoDrivingState.L2_ASSIST,
                summary = "前车急停，跟车距离不足导致追尾",
            ),
            AccidentEvent(
                id = "E-20260318-0002",
                type = AccidentType.AUTOPILOT_FAULT,
                timeMillis = now - 2 * 60 * 60 * 1000L,
                locationText = "上海·浦东新区 张江路段",
                triggerReasons = listOf("自动驾驶异常退出", "传感器数据异常"),
                severity = Severity.MEDIUM,
                autoDrivingState = AutoDrivingState.AUTONOMOUS,
                summary = "系统退出后接管提示延迟，车辆偏离车道",
            ),
            AccidentEvent(
                id = "E-20260318-0003",
                type = AccidentType.COLLISION,
                timeMillis = now - 4 * 60 * 60 * 1000L,
                locationText = "杭州·滨江区 江南大道",
                triggerReasons = listOf("前方异物", "紧急制动避让"),
                severity = Severity.LOW,
                autoDrivingState = AutoDrivingState.MANUAL,
                summary = "前方障碍物导致急制动，车辆完成避让未形成直接碰撞",
            )
        )
    }

    private fun seedDetail(event: AccidentEvent): AccidentDetailBundle {
        val telemetry = AccidentTelemetrySimulator.simulatePreWindow(event)
        val telemetryAfter = AccidentTelemetrySimulator.simulatePostWindow(event, telemetry.lastOrNull())
        val env = AccidentContextGenerator.environmentFor(event)
        val trace = if (event.type == AccidentType.AUTOPILOT_FAULT || event.type == AccidentType.DRIVER_TAKEOVER_FAIL) {
            AccidentContextGenerator.decisionTraceFor(event, env)
        } else {
            null
        }
        val metrics = ResponsibilityAnalyzer.analyze(event, telemetry, aebTriggerTMs = firstAebTMs(telemetry))
        val resp = ResponsibilityAnalyzer.inferResponsibility(event, metrics, env, trace)
        return AccidentDetailBundle(
            event = event,
            telemetry10sBefore = telemetry,
            telemetry10sAfter = telemetryAfter,
            environmentSnapshot = env,
            decisionTrace = trace,
            responsibility = resp,
        )
    }

    private fun firstAebTMs(telemetry: List<TelemetryPoint>): Int? =
        telemetry.firstOrNull { it.aebActive }?.tMs
}
