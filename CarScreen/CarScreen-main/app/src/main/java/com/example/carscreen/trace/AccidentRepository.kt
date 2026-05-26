package com.example.carscreen.trace

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.carscreen.db.AccidentDatabase
import com.example.carscreen.db.AccidentDao
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
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
        return detailCache[eventId] ?: run {
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
            append("telemetry=").append(bundle.telemetry10sBefore.joinToString()).append('\n')
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
                com.example.carscreen.db.EvidenceEntity(
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

    // ── 环境 / 决策链路 / 责任界定（供 Monitor 调用）────────────────

    fun generateEnvironment(): EnvironmentSnapshot {
        val weather = listOf("小雨", "多云", "晴", "雾").random()
        val road = listOf("城市主干道", "高架匝道", "快速路", "隧道入口").random()
        val obstacle = listOf("前方慢车", "施工锥桶", "行人横穿", "无明显障碍").random()
        val lane = listOf("车道线清晰", "车道线模糊", "车道线被水渍反光干扰").random()
        return EnvironmentSnapshot(weather = weather, road = road, obstacle = obstacle, laneMarking = lane)
    }

    fun generateDecisionTrace(): DecisionTrace {
        return DecisionTrace(
            sensorInput = "雷达: 前方 28m 目标; 摄像头: 车道线置信度 0.62; IMU: 加速度波动",
            perception = "识别到前车减速趋势，但障碍物分类置信度下降（雨雾/反光）",
            planning = "规划保持车道+减速，目标时距 1.6s；在 -1.8s 出现再规划抖动",
            control = "制动指令在 -1.2s 才达到 35%，横向控制出现 180ms 延迟",
        )
    }

    // ── Entity 转换 ─────────────────────────────────────────────────

    private fun com.example.carscreen.db.AccidentEventEntity.toAccidentEvent(): AccidentEvent {
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
            )
        )
    }

    private fun seedDetail(event: AccidentEvent): AccidentDetailBundle {
        val telemetry = generateTelemetry10sBefore(event)
        val env = if (event.type == AccidentType.AUTOPILOT_FAULT) generateEnvironment() else null
        val trace = if (event.type == AccidentType.AUTOPILOT_FAULT) generateDecisionTrace() else null
        val metrics = ResponsibilityAnalyzer.analyze(event, telemetry)
        val resp = ResponsibilityAnalyzer.inferResponsibility(event, metrics, env, trace)
        return AccidentDetailBundle(
            event = event,
            telemetry10sBefore = telemetry,
            environmentSnapshot = env,
            decisionTrace = trace,
            responsibility = resp,
        )
    }

    private fun generateTelemetry10sBefore(event: AccidentEvent): List<TelemetryPoint> {
        val points = mutableListOf<TelemetryPoint>()
        val baseSpeed = when (event.severity) {
            Severity.LOW -> 25f
            Severity.MEDIUM -> 45f
            Severity.HIGH -> 65f
        }
        for (t in -10_000..0 step 500) {
            val tNorm = (t / 10_000f).absoluteValue
            val brake = when {
                t > -1500 -> (60 + Random.nextInt(0, 30)).coerceAtMost(100)
                t > -3500 -> (20 + Random.nextInt(0, 25))
                else -> Random.nextInt(0, 10)
            }
            val ax = when {
                t > -800  -> (-8.5f + Random.nextFloat() * 1.5f)
                t > -2500 -> (-3.0f + Random.nextFloat() * 1.0f)
                else      -> (-0.4f + Random.nextFloat() * 0.8f)
            }
            val speed = (baseSpeed + (Random.nextFloat() - 0.5f) * 2f + ax * (1f - tNorm)).coerceAtLeast(0f)
            val steer = when {
                event.type == AccidentType.AUTOPILOT_FAULT && t > -2000 -> (5f + Random.nextFloat() * 8f)
                t > -2000 -> (Random.nextFloat() - 0.5f) * 6f
                else -> (Random.nextFloat() - 0.5f) * 3f
            }
            points += TelemetryPoint(tMs = t, speedKph = speed, axMS2 = ax, brake = brake, steerDeg = steer)
        }
        return points
    }
}
