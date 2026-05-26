package com.example.vehtrust

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Typeface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.SeekBar
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.vehtrust.databinding.ActivityAccidentTraceDetailBinding
import com.example.vehtrust.trace.AccidentDetailBundle
import com.example.vehtrust.trace.AccidentEvent
import com.example.vehtrust.trace.AccidentSiteCoordinates
import com.example.vehtrust.trace.AccidentTraceViewModel
import com.example.vehtrust.trace.AccidentType
import com.example.vehtrust.trace.CollisionSeverityPredictionResult
import com.example.vehtrust.trace.DeepLearningResult
import com.example.vehtrust.trace.ExperimentRuntime
import com.example.vehtrust.trace.ResponsibilityAnalyzer
import com.example.vehtrust.trace.TelemetryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

class AccidentTraceDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccidentTraceDetailBinding
    private lateinit var viewModel: AccidentTraceViewModel
    private var bundle: AccidentDetailBundle? = null
    private var replayAnimator: ValueAnimator? = null
    private var replayTelemetry: List<TelemetryPoint> = emptyList()
    private var replayStartMs: Int = 0
    private var replayEndMs: Int = 0
    private var replayCurrentMs: Int = 0
    private var replayPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val perfTimer = PerfTimer.start()
        ExperimentRuntime.loadPersisted(this)
        binding = ActivityAccidentTraceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarDetail.setNavigationOnClickListener { finish() }

        // 顶部返回 + 物理/手势返回优先关闭地图页/WebView，再退出 Activity
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (::binding.isInitialized && binding.wvAccidentSiteMap.canGoBack()) {
                        binding.wvAccidentSiteMap.goBack()
                    } else {
                        finish()
                    }
                }
            },
        )

        setupScrollAndMapTouchDelegation()

        viewModel = ViewModelProvider(this)[AccidentTraceViewModel::class.java]

        val eventId = intent.getStringExtra(EXTRA_EVENT_ID) ?: return
        bundle = viewModel.loadDetail(eventId)
        setupReplayControls()
        render(bundle!!)
        setupSeverityPrediction()
        setupAiAnalysis()

        binding.btnUploadChain.setOnClickListener {
            val b = bundle ?: return@setOnClickListener

            binding.btnUploadChain.isEnabled = false
            binding.btnUploadChain.text = "上链中..."
            binding.layoutChainSuccess.visibility = View.GONE
            binding.tvChainError.visibility = View.GONE

            lifecycleScope.launch {
                val t = PerfTimer.start()
                val result = viewModel.uploadToBlockchain(b)
                t.log("Blockchain", if (result.success) "上链成功" else "上链失败")

                if (result.success) {
                    binding.tvChainHash.text = result.hash
                    binding.tvChainTime.text = formatTime(System.currentTimeMillis())
                    binding.layoutChainSuccess.visibility = View.VISIBLE
                    binding.btnUploadChain.text = "已上链 ✓"
                } else {
                    binding.tvChainError.setTextColor(getColor(android.R.color.holo_red_dark))
                    binding.tvChainError.text = "❌ 上链失败：${result.error}"
                    binding.tvChainError.visibility = View.VISIBLE
                    binding.btnUploadChain.isEnabled = true
                    binding.btnUploadChain.text = "重新上链"
                }
            }
        }

        window.decorView.viewTreeObserver.addOnPreDrawListener(
            object : android.view.ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    perfTimer.log("AccidentTraceDetail", "首帧加载")
                    @Suppress("DEPRECATION")
                    window.decorView.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            },
        )
    }

    override fun onStop() {
        super.onStop()
        pauseReplay(resetButton = true)
    }

    override fun onResume() {
        super.onResume()
        binding.wvAccidentSiteMap.onResume()
    }

    override fun onPause() {
        binding.wvAccidentSiteMap.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        replayAnimator?.cancel()
        replayAnimator = null
        runCatching { binding.viewAccidentReplay.setLayerType(View.LAYER_TYPE_NONE, null) }
        if (!isChangingConfigurations && isFinishing) {
            runCatching {
                binding.wvAccidentSiteMap.stopLoading()
                binding.wvAccidentSiteMap.loadUrl("about:blank")
                binding.wvAccidentSiteMap.destroy()
            }
        }
        super.onDestroy()
    }

    private fun setupSeverityPrediction() {
        binding.btnPredictSeverity.setOnClickListener {
            val b = bundle ?: return@setOnClickListener
            binding.btnPredictSeverity.isEnabled = false
            binding.btnPredictSeverity.text = getString(R.string.severity_prediction_loading)
            binding.tvSeverityStatus.visibility = View.VISIBLE
            binding.tvSeverityStatus.text = getString(R.string.severity_prediction_loading)
            binding.tvSeverityHeadline.visibility = View.GONE
            binding.tvSeverityNarrative.visibility = View.GONE
            binding.tvSeverityResult.visibility = View.GONE
            binding.tvSeverityFeatureTable.visibility = View.GONE
            binding.tvSeverityEvidence.visibility = View.GONE

            lifecycleScope.launch {
                val t = PerfTimer.start()
                val result = viewModel.analyzeCollisionSeverity(b)
                t.log("CollisionSeverity", "碰撞严重度预测")
                renderCollisionSeverity(result)
                binding.btnPredictSeverity.isEnabled = true
                binding.btnPredictSeverity.text = getString(R.string.severity_prediction_retry)
            }
        }
    }

    private fun setupAiAnalysis() {
        binding.btnAnalyzeAi.setOnClickListener {
            val b = bundle ?: return@setOnClickListener
            binding.btnAnalyzeAi.isEnabled = false
            binding.btnAnalyzeAi.text = getString(R.string.ai_analysis_loading)
            binding.tvAiAnalysisStatus.visibility = View.VISIBLE
            binding.tvAiAnalysisStatus.text = getString(R.string.ai_analysis_loading)
            binding.tvAiAnalysisResult.visibility = View.GONE

            lifecycleScope.launch {
                val t = PerfTimer.start()
                val metrics = viewModel.analyzeMetrics(b)
                val severityText = binding.tvSeverityNarrative.takeIf { it.visibility == View.VISIBLE }
                    ?.text?.toString()?.trim()?.takeIf { it.isNotEmpty() }
                val result = viewModel.analyzeByAi(b, metrics, severityText)
                t.log("AiAnalysis", if (result.remoteError != null) "AI离线降级" else "AI在线分析")
                binding.tvAiAnalysisResult.text = result.toStyledDisplayText()
                binding.tvAiAnalysisResult.visibility = View.VISIBLE
                binding.tvAiAnalysisStatus.visibility = View.VISIBLE
                binding.tvAiAnalysisStatus.text = result.remoteError?.let {
                    getString(R.string.ai_analysis_fallback) + " 原因：" + it
                } ?: result.modelHint
                binding.btnAnalyzeAi.isEnabled = true
                binding.btnAnalyzeAi.text = getString(R.string.ai_analysis_retry)
            }
        }
    }

    private fun renderCollisionSeverity(result: CollisionSeverityPredictionResult) {
        val b = bundle ?: return
        val metrics = viewModel.analyzeMetrics(b)
        val topConfidence = ((result.probabilities[result.predictedSeverity] ?: 0.0) * 100).toInt()

        binding.tvSeverityHeadline.text = buildString {
            append(severityLabel(result.predictedSeverity))
            append(" · 置信度 ").append(topConfidence).append("%\n")
            append("严重度指数：").append(result.severityScore).append("/100")
        }
        binding.tvSeverityHeadline.visibility = View.VISIBLE

        binding.tvSeverityNarrative.text = result.narrative.ifBlank {
            "本次结果由端侧本地模型生成，综合事故前速度、减速度、制动、TTC 与环境特征进行离线三分类研判。"
        }
        binding.tvSeverityNarrative.visibility = View.VISIBLE

        binding.tvSeverityResult.text = buildSeverityProbabilityTable(result)
        binding.tvSeverityResult.visibility = View.VISIBLE

        binding.tvSeverityFeatureTable.text = buildSeverityFeatureTable(b, result, metrics)
        binding.tvSeverityFeatureTable.visibility = View.VISIBLE

        binding.tvSeverityEvidence.text = buildSeverityEvidence(result)
        binding.tvSeverityEvidence.visibility = View.VISIBLE

        binding.tvSeverityStatus.visibility = View.VISIBLE
        binding.tvSeverityStatus.text = result.modelHint
    }

    private fun render(b: AccidentDetailBundle) {
        binding.tvDetailHeroTitle.text = b.event.summary.ifBlank { "事故事件 ${b.event.id}" }
        binding.tvDetailHeroMeta.text = buildString {
            append(formatTime(b.event.timeMillis))
            append("  ·  ")
            append(b.event.locationText.ifBlank { "地点待补充" })
            append("\n触发：")
            append(b.event.triggerReasons.joinToString(" · ").ifBlank { "—" })
        }

        binding.tvHeader.text = buildString {
            append("事件：").append(b.event.id).append('\n')
            append("时间：").append(formatTime(b.event.timeMillis)).append('\n')
            append("地点：").append(b.event.locationText).append('\n')
            append("触发：").append(b.event.triggerReasons.joinToString(" · ")).append('\n')
            append("摘要：").append(b.event.summary)
        }

        renderTelemetry(b)
        renderReplay(b)
        setupAccidentSiteMap(b.event)

        val metrics = viewModel.analyzeMetrics(b)
        renderMetrics(metrics)
        renderDeepLearning(viewModel.analyzeWithDeepLearning(b))

        val resp = b.responsibility
        binding.tvAiInference.text = buildString {
            append(resp.conclusion).append('\n')
        }
        val driverWeight = resp.driverFactor.toFloat()
        val systemWeight = resp.systemFactor.toFloat()
        val envWeight = resp.environmentFactor.toFloat()

        setBarWeight(binding.tvBarDriverLabel, driverWeight)
        setBarWeight(binding.tvBarSystemLabel, systemWeight)
        setBarWeight(binding.tvBarEnvLabel, envWeight)
        setBarWeight(binding.barDriver, driverWeight)
        setBarWeight(binding.barSystem, systemWeight)
        setBarWeight(binding.barEnv, envWeight)
        setBarWeight(binding.tvBarDriverPct, driverWeight)
        setBarWeight(binding.tvBarSystemPct, systemWeight)
        setBarWeight(binding.tvBarEnvPct, envWeight)

        binding.tvBarDriverPct.text = "${resp.driverFactor}%"
        binding.tvBarSystemPct.text = "${resp.systemFactor}%"
        binding.tvBarEnvPct.text = "${resp.environmentFactor}%"

        binding.tvDriverAnalysis.text = resp.reasons.joinToString("\n")

        binding.tvSystemTrace.text = buildString {
            val env = b.environmentSnapshot
            val tr = b.decisionTrace
            if (env == null && tr == null) {
                append("（碰撞事件：无需记录自动驾驶链路）")
                return@buildString
            }
            if (env != null) {
                append("环境信息记录\n")
                append("· 天气：").append(env.weather).append('\n')
                append("· 路况：").append(env.road).append('\n')
                append("· 障碍物：").append(env.obstacle).append('\n')
                append("· 车道线：").append(env.laneMarking).append("\n\n")
            }
            if (tr != null) {
                append("决策链路追踪（感知→决策→执行）\n")
                append("· 传感器：").append(tr.sensorInput).append('\n')
                append("· 感知：").append(tr.perception).append('\n')
                append("· 规划：").append(tr.planning).append('\n')
                append("· 控制：").append(tr.control).append('\n')
            }
        }
    }

    private fun setupReplayControls() {
        binding.btnReplayPlay.setOnClickListener {
            if (replayPlaying) {
                pauseReplay(resetButton = true)
            } else {
                startReplay()
            }
        }

        binding.seekReplay.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (!fromUser || replayTelemetry.isEmpty()) return
                    pauseReplay(resetButton = true)
                    val tMs = progressToReplayTime(progress)
                    updateReplayAt(tMs, updateSeek = false)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

                override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
            },
        )
    }

    private fun renderReplay(b: AccidentDetailBundle) {
        replayTelemetry = (b.telemetry10sBefore + b.telemetry10sAfter)
            .distinctBy { it.tMs }
            .sortedBy { it.tMs }
        if (replayTelemetry.isEmpty()) {
            binding.tvReplayStatus.text = "暂无可放映的遥测窗口。"
            binding.tvReplayMetrics.text = ""
            binding.btnReplayPlay.isEnabled = false
            binding.seekReplay.isEnabled = false
            binding.viewAccidentReplay.setTelemetry(emptyList())
            return
        }

        replayStartMs = replayTelemetry.first().tMs
        replayEndMs = replayTelemetry.last().tMs
        binding.btnReplayPlay.isEnabled = true
        binding.seekReplay.isEnabled = true
        binding.seekReplay.max = REPLAY_SEEK_MAX
        binding.viewAccidentReplay.setAccidentContext(b.event, b.environmentSnapshot)
        binding.viewAccidentReplay.setTelemetry(replayTelemetry)
        updateReplayAt(replayStartMs)
        binding.tvReplayStatus.text = buildString {
            append("回放窗口：")
            append(formatReplayTime(replayStartMs))
            append(" 至 ")
            append(formatReplayTime(replayEndMs))
            append(" · ")
            append(if (b.telemetry10sAfter.isNotEmpty()) "已包含事故后窗口" else "当前仅含事故前窗口")
            append(" · 场景、HUD 与曲线共用同一播放头。")
        }
    }

    private fun startReplay() {
        if (replayTelemetry.isEmpty()) return
        if (replayCurrentMs >= replayEndMs) {
            updateReplayAt(replayStartMs)
        }
        replayAnimator?.cancel()
        replayPlaying = true
        binding.btnReplayPlay.text = "暂停"
        setReplayAcceleration(true)
        val spanTelemetry = (replayEndMs - replayCurrentMs).coerceAtLeast(300)
        replayAnimator = ValueAnimator.ofInt(replayCurrentMs, replayEndMs).apply {
            duration =
                (spanTelemetry * REPLAY_DURATION_SCALE).toLong().coerceIn(800L, 72_000L)
            interpolator = LinearInterpolator()
            addUpdateListener { animator ->
                updateReplayAt(animator.animatedValue as Int)
            }
            addListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        replayPlaying = false
                        setReplayAcceleration(false)
                        binding.btnReplayPlay.text = if (replayCurrentMs >= replayEndMs) "重播" else "播放"
                    }
                },
            )
            start()
        }
    }

    private fun pauseReplay(resetButton: Boolean) {
        replayAnimator?.cancel()
        replayAnimator = null
        replayPlaying = false
        setReplayAcceleration(false)
        if (resetButton) {
            binding.btnReplayPlay.text =
                if (replayCurrentMs >= replayEndMs && replayTelemetry.isNotEmpty()) "重播" else "播放"
        }
    }

    private fun setReplayAcceleration(playing: Boolean) {
        binding.viewAccidentReplay.setLayerType(
            if (playing) View.LAYER_TYPE_HARDWARE else View.LAYER_TYPE_NONE,
            null,
        )
    }

    private fun updateReplayAt(tMs: Int, updateSeek: Boolean = true) {
        if (replayTelemetry.isEmpty()) return
        replayCurrentMs = tMs.coerceIn(replayStartMs, replayEndMs)
        val frame = interpolateReplayFrame(replayCurrentMs)
        binding.viewAccidentReplay.setPlayhead(replayCurrentMs)
        binding.tvReplayTime.text = "T${formatReplayTime(replayCurrentMs)}"
        binding.tvReplayMetrics.text = buildString {
            append("车速 ").append(formatNumber(frame.speedKph.toDouble())).append("km/h")
            append("  |  制动 ").append(frame.brake).append("%  油门 ").append(frame.throttlePct).append("%\n")
            append("aₓ ").append(formatNumber(frame.axMS2.toDouble(), 2)).append("  aᵧ ")
                .append(formatNumber(frame.ayMS2.toDouble(), 2)).append("  横摆 ")
                .append(formatNumber(frame.yawRateDegS.toDouble(), 1)).append("°/s\n")
            append("FCW L").append(frame.fcwActiveLevel).append("  ·  AEB ")
                .append(if (frame.aebActive) "ON" else "off")
        }
        if (updateSeek) {
            binding.seekReplay.progress = replayTimeToProgress(replayCurrentMs)
        }
    }

    private fun interpolateReplayFrame(tMs: Int): TelemetryPoint {
        val nextIndex = replayTelemetry.indexOfFirst { it.tMs >= tMs }.takeIf { it >= 0 } ?: replayTelemetry.lastIndex
        val prev = replayTelemetry[(nextIndex - 1).coerceAtLeast(0)]
        val next = replayTelemetry[nextIndex]
        val span = (next.tMs - prev.tMs).takeIf { it != 0 } ?: 1
        val ratioRaw = ((tMs - prev.tMs).toFloat() / span).coerceIn(0f, 1f)
        val ratio = smoothstepReplayRatio(ratioRaw)
        return TelemetryPoint(
            tMs = tMs,
            speedKph = lerp(prev.speedKph, next.speedKph, ratio),
            axMS2 = lerp(prev.axMS2, next.axMS2, ratio),
            brake = lerp(prev.brake.toFloat(), next.brake.toFloat(), ratio).toInt().coerceIn(0, 100),
            steerDeg = lerp(prev.steerDeg, next.steerDeg, ratio),
            ayMS2 = lerp(prev.ayMS2, next.ayMS2, ratio),
            yawRateDegS = lerp(prev.yawRateDegS, next.yawRateDegS, ratio),
            throttlePct = lerp(prev.throttlePct.toFloat(), next.throttlePct.toFloat(), ratio).toInt().coerceIn(0, 100),
            aebActive = lerp(if (prev.aebActive) 1f else 0f, if (next.aebActive) 1f else 0f, ratioRaw) >= 0.5f,
            blinkerCode = if (ratioRaw >= 0.5f) next.blinkerCode else prev.blinkerCode,
            fcwActiveLevel = lerp(prev.fcwActiveLevel.toFloat(), next.fcwActiveLevel.toFloat(), ratio)
                .toInt().coerceIn(0, 3),
        )
    }

    private fun replayTimeToProgress(tMs: Int): Int {
        if (replayEndMs == replayStartMs) return 0
        val ratio = (tMs - replayStartMs).toFloat() / (replayEndMs - replayStartMs)
        return (ratio.coerceIn(0f, 1f) * REPLAY_SEEK_MAX).toInt()
    }

    private fun progressToReplayTime(progress: Int): Int {
        val ratio = (progress.toFloat() / REPLAY_SEEK_MAX).coerceIn(0f, 1f)
        return replayStartMs + ((replayEndMs - replayStartMs) * ratio).toInt()
    }

    private fun lerp(start: Float, end: Float, ratio: Float): Float = start + (end - start) * ratio

    /** 与回放视图一致的段内平滑，拖动进度条时 HUD 数值不跳齿 */
    private fun smoothstepReplayRatio(raw: Float): Float {
        val t = raw.coerceIn(0f, 1f)
        return t * t * (3f - 2f * t)
    }

    private fun renderDeepLearning(result: DeepLearningResult) {
        binding.tvDlSummary.text = buildString {
            append(getString(R.string.dl_model_prefix)).append("：").append(result.modelName).append('\n')
            append(getString(R.string.dl_predicted_label_prefix)).append("：").append(result.predictedLabel).append('\n')
            append(getString(R.string.dl_overall_risk_prefix)).append("：").append(result.overallRiskScore).append("%\n")
            append(getString(R.string.dl_confidence_prefix)).append("：").append(result.accidentTypeConfidence).append("%")
        }
        binding.tvDlDriverRisk.text = "${result.driverRiskScore}%"
        binding.tvDlSystemRisk.text = "${result.systemRiskScore}%"
        binding.tvDlEnvRisk.text = "${result.environmentRiskScore}%"
        binding.tvDlEvidence.text = result.evidence.joinToString("\n") { "• $it" }
    }

    private fun renderMetrics(m: ResponsibilityAnalyzer.DetailedMetrics) {
        binding.tvReactionTime.text = if (m.reactionTimeMs >= 0) "${m.reactionTimeMs}ms" else "N/A"
        binding.tvReactionTimeLabel.text = when {
            m.reactionTimeMs >= 2500 -> "过长，风险极高"
            m.reactionTimeMs >= 1500 -> "偏长，需关注"
            m.reactionTimeMs >= 0 -> "正常"
            else -> ""
        }
        binding.tvReactionTimeLabel.setTextColor(
            getColor(
                when {
                    m.reactionTimeMs >= 2500 -> R.color.warning_red
                    m.reactionTimeMs >= 1500 -> R.color.module_orange
                    else -> R.color.module_teal
                },
            ),
        )

        binding.tvBrakeRiseTime.text = if (m.brakeRiseTimeMs >= 0) "${m.brakeRiseTimeMs}ms" else "未达"
        binding.tvBrakeRiseLabel.text = when {
            m.brakeRiseTimeMs < 0 -> "未达全力制动"
            m.brakeRiseTimeMs <= 400 -> "制动果断"
            else -> "上升偏慢"
        }

        binding.tvMaxDecel.text = String.format(Locale.getDefault(), "%.1f", m.maxDecelerationMS2)
        binding.tvMaxDecelLabel.text = when {
            m.maxDecelerationMS2 <= -8f -> "碰撞级"
            m.maxDecelerationMS2 <= -5f -> "强制动"
            else -> "中等"
        }

        binding.tvAebDelay.text = if (m.aebDelayMs != -1) "${m.aebDelayMs}ms" else "未触发"
        binding.tvAebLabel.text = if (m.aebDelayMs != -1) "相对事故时刻" else "系统未介入"
        binding.tvAebDelay.setTextColor(
            getColor(if (m.aebDelayMs != -1) R.color.module_teal else R.color.warning_red),
        )

        binding.tvTtc.text = if (m.ttcAtBrakeStart >= 0f) {
            String.format(Locale.getDefault(), "%.1fs", m.ttcAtBrakeStart)
        } else {
            "N/A"
        }
        binding.tvTtcLabel.text = when {
            m.ttcAtBrakeStart in 0f..1.5f -> "跟车时距不足"
            m.ttcAtBrakeStart > 1.5f -> "时距尚可"
            else -> "不可用"
        }

        binding.tvBrakeEffective.text = if (m.brakeEffective) "有效" else "不足"
        binding.tvBrakeEffective.setTextColor(
            getColor(if (m.brakeEffective) R.color.module_teal else R.color.warning_red),
        )
        binding.tvBrakeEffectiveLabel.text = if (m.brakeEffective) "及时达到强制动" else "未及时强制动"
    }

    private fun setBarWeight(view: View, weight: Float) {
        val params = view.layoutParams as android.widget.LinearLayout.LayoutParams
        params.weight = weight.coerceAtLeast(1f)
        view.layoutParams = params
    }

    private fun renderTelemetry(b: AccidentDetailBundle) {
        val telemetry = b.telemetry10sBefore.sortedBy { it.tMs }
        val scrollTelemetryTable = findViewById<View>(R.id.scrollTelemetryTable)
        val tableTelemetry = findViewById<TableLayout>(R.id.tableTelemetry)

        if (telemetry.isEmpty()) {
            binding.tvTelemetry.text = "暂无事故前遥测数据"
            scrollTelemetryTable.visibility = View.GONE
            tableTelemetry.removeAllViews()
            return
        }

        val avgSpeed = telemetry.map { it.speedKph }.average()
        val maxSpeed = telemetry.maxOf { it.speedKph.toDouble() }
        val minAccel = telemetry.minOf { it.axMS2.toDouble() }
        val brakePeak = telemetry.maxOf { it.brake }
        val steerPeak = telemetry.maxOf { abs(it.steerDeg.toDouble()) }
        val shown = telemetry.filter { it.tMs % 1000 == 0 || it.tMs == 0 }

        binding.tvTelemetry.text = buildString {
            append("【采样摘要】\n")
            append("窗口：事故前 10 秒 · 20Hz · ").append(telemetry.size).append(" 个采样点\n")
            append("均速：").append(formatNumber(avgSpeed)).append("km/h")
            append("  |  峰值速度：").append(formatNumber(maxSpeed)).append("km/h\n")
            append("峰值制动：").append(brakePeak).append("%")
            append("  |  最小加速度：").append(formatNumber(minAccel, 2)).append("m/s²")
            append("  |  最大转角：").append(formatNumber(steerPeak)).append("°\n\n")
            append("【关键时序表】")
        }

        scrollTelemetryTable.visibility = View.VISIBLE
        tableTelemetry.removeAllViews()
        tableTelemetry.addView(
            createTelemetryRow(
                "时间t(ms)",
                "速度(km/h)",
                "加速度(m/s²)",
                "刹车",
                "转角(°)",
                isHeader = true,
            ),
        )
        shown.forEachIndexed { index, item ->
            tableTelemetry.addView(
                createTelemetryRow(
                    item.tMs.toString(),
                    formatNumber(item.speedKph.toDouble()),
                    formatNumber(item.axMS2.toDouble(), 2),
                    "${item.brake}%",
                    formatNumber(item.steerDeg.toDouble()),
                    isStriped = index % 2 == 1,
                ),
            )
        }
    }

    private fun createTelemetryRow(
        time: String,
        speed: String,
        acceleration: String,
        brake: String,
        steer: String,
        isHeader: Boolean = false,
        isStriped: Boolean = false,
    ): TableRow {
        val row = TableRow(this).apply {
            layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
            )
            setBackgroundColor(
                getColor(
                    when {
                        isHeader -> android.R.color.transparent
                        isStriped -> R.color.background
                        else -> android.R.color.white
                    },
                ),
            )
        }

        listOf(time, speed, acceleration, brake, steer).forEachIndexed { index, value ->
            row.addView(createTelemetryCell(value, index, isHeader))
        }
        return row
    }

    private fun createTelemetryCell(text: String, columnIndex: Int, isHeader: Boolean): TextView = TextView(this).apply {
        layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT,
        )
        minWidth = telemetryColumnMinWidth(columnIndex)
        setPadding(dp(10), dp(8), dp(10), dp(8))
        gravity = Gravity.CENTER
        maxLines = 1
        setTextColor(getColor(if (isHeader) android.R.color.darker_gray else android.R.color.black))
        textSize = 11f
        typeface = Typeface.create(Typeface.MONOSPACE, if (isHeader) Typeface.BOLD else Typeface.NORMAL)
        this.text = text
    }

    private fun telemetryColumnMinWidth(columnIndex: Int): Int = when (columnIndex) {
        0 -> dp(96)
        1 -> dp(82)
        2 -> dp(108)
        3 -> dp(68)
        else -> dp(82)
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

    private fun buildSeverityProbabilityTable(result: CollisionSeverityPredictionResult): String = buildString {
        // 严重度概率表：3列
        val probWidths = intArrayOf(9, 6, 22)
        append("【严重度概率表】\n")
        append(tableRow(probWidths, "等级", "概率", "含义")).append('\n')
        append(tableRow(probWidths, "-------", "----", "----------------------")).append('\n')
        listOf("Fatal", "Serious", "Slight").forEach { label ->
            val probability = ((result.probabilities[label] ?: 0.0) * 100).toInt()
            append(
                tableRow(
                    probWidths,
                    severityLabel(label),
                    "$probability%",
                    when (label) {
                        "Fatal" -> "高伤害/高人伤风险"
                        "Serious" -> "需重点核查人伤与结构损伤"
                        else -> "以轻伤或低结构损伤为主"
                    },
                ),
            ).append('\n')
        }
    }.trim()

    private fun buildSeverityFeatureTable(
        bundle: AccidentDetailBundle,
        result: CollisionSeverityPredictionResult,
        metrics: ResponsibilityAnalyzer.DetailedMetrics,
    ): String = buildString {
        // 本地模型特征表：3列（特征名较长，说明也长）
        val featWidths = intArrayOf(13, 16, 18)
        append("【本地模型特征表】\n")
        append(tableRow(featWidths, "特征", "数值", "说明")).append('\n')
        append(tableRow(featWidths, "-------------", "----------------", "------------------")).append('\n')
        append(tableRow(featWidths, "事件类型", bundle.event.type.name, "仅 COLLISION 为主场景")).append('\n')
        append(tableRow(featWidths, "事件等级", bundle.event.severity.name, "原始触发等级")).append('\n')
        append(tableRow(featWidths, "事故前3s均速", featureValue(result.derivedFeatures.avgSpeedLast3sKph?.let { "${formatNumber(it.toDouble())}km/h" }), "持续动能水平")).append('\n')
        append(tableRow(featWidths, "峰值减速度", featureValue(result.derivedFeatures.maxDecelerationMS2?.let { "${formatNumber(it.toDouble(), 2)}m/s²" }), "冲击强度核心指标")).append('\n')
        append(tableRow(featWidths, "峰值制动", featureValue(result.derivedFeatures.brakePeak?.let { "$it%" }), "驾驶/系统制动强度")).append('\n')
        append(tableRow(featWidths, "制动时TTC", if (metrics.ttcAtBrakeStart >= 0f) "${formatNumber(metrics.ttcAtBrakeStart.toDouble())}s" else "N/A", "余度越小风险越高")).append('\n')
        append(tableRow(featWidths, "AEB介入", if (metrics.aebDelayMs != -1) "${metrics.aebDelayMs}ms" else "未触发", "系统干预时机")).append('\n')
        append(tableRow(featWidths, "制动有效性", if (metrics.brakeEffective) "有效" else "不足", "2秒内是否形成强制动")).append('\n')
        append(tableRow(featWidths, "涉事车辆数", featureValue(result.derivedFeatures.vehicleCount?.toString()), "端侧推断特征")).append('\n')
        append(tableRow(featWidths, "推断限速", featureValue(result.derivedFeatures.speedLimit?.let { "${it}km/h" }), "由速度区间映射")).append('\n')
        append(tableRow(featWidths, "天气", featureValue(result.derivedFeatures.weatherConditions?.let { weatherLabel(it) }), "环境风险编码")).append('\n')
        append(tableRow(featWidths, "昼夜", result.derivedFeatures.isNight?.let { if (it) "夜间" else "白天" } ?: "未知", "能见度与反应裕量")).append('\n')
    }.trim()

    private fun buildSeverityEvidence(result: CollisionSeverityPredictionResult): String = buildString {
        append("【关键因子与使用建议】\n")
        result.keyFactors.forEach { append("• ").append(it).append('\n') }
        append("• 本页严重度结果由端侧本地模型离线生成，可用于事故初筛、页面展示和取证优先级排序。")
    }.trim()

    /**
     * 生成等宽表格行，每列按 [widths] 指定的显示宽度对齐。
     * 中文字符按约2个英文字符宽度计算，保证列对齐。
     *
     * 原理：padEnd 按 Java 字符串长度（char count）补空格，
     * 但中文字符的视觉宽度≈2个英文字符，所以需要把"显示宽度差"
     * 转换为"字符数差"来补偿。由于空格本身显示宽度=1、字符长度=1，
     * 直接在原字符串长度上追加 (目标显示宽 - 当前显示宽) 个空格即可。
     */
    private fun tableRow(widths: IntArray, vararg columns: String): String {
        return columns.mapIndexed { index, value ->
            if (index == columns.lastIndex) value
            else {
                val target = widths.getOrElse(index) { 12 }
                val dw = displayWidth(value)
                // 需要额外补的空格数 = 目标显示宽 - 当前显示宽（不足则补0）
                val extraSpaces = (target - dw).coerceAtLeast(0)
                value.padEnd(value.length + extraSpaces, ' ')
            }
        }.joinToString("  ")
    }

    /** 计算字符串的显示宽度（中文字符算2，其他算1） */
    private fun displayWidth(s: String): Int {
        var w = 0
        for (i in s.indices) {
            val code = s[i].code
            // CJK 统一汉字 + 中文标点范围
            if ((code >= 0x4E00 && code <= 0x9FFF) || (code >= 0x3000 && code <= 0x303F)) {
                w += 2
            } else {
                w += 1
            }
        }
        return w
    }

    private fun featureValue(value: String?): String = value ?: "未知"

    private fun severityLabel(value: String): String = when (value) {
        "Fatal" -> "致命事故"
        "Serious" -> "重伤事故"
        "Slight" -> "轻微事故"
        else -> value
    }

    private fun weatherLabel(value: Int): String = when (value) {
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

    private fun formatNumber(value: Double, digits: Int = 1): String =
        String.format(Locale.getDefault(), "% .${digits}f".replace(" ", ""), value)

    private fun formatReplayTime(tMs: Int): String {
        val seconds = tMs / 1000f
        return if (seconds >= 0f) {
            "+${String.format(Locale.getDefault(), "%.1f", seconds)}s"
        } else {
            "${String.format(Locale.getDefault(), "%.1f", seconds)}s"
        }
    }

    private fun formatTime(ms: Long): String =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(ms))

    private fun setupScrollAndMapTouchDelegation() {
        binding.wvAccidentSiteMap.setOnTouchListener { _, ev ->
            when (ev.actionMasked) {
                MotionEvent.ACTION_DOWN ->
                    binding.scrollDetailRoot.requestDisallowInterceptTouchEvent(true)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
                    binding.scrollDetailRoot.requestDisallowInterceptTouchEvent(false)
            }
            false
        }
    }

    /** Leaflet + OSM 瓦片（需网络）；实车可换高德/百度 SDK 或离线 MBTiles */
    private fun setupAccidentSiteMap(event: AccidentEvent) {
        val coord = AccidentSiteCoordinates.resolve(event)
        binding.tvMapCoordinates.text = String.format(
            Locale.getDefault(),
            "WGS84  %.5f°N, %.5f°E  ·  文本推断近似点",
            coord.latitude,
            coord.longitude,
        )
        binding.wvAccidentSiteMap.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            cacheMode = WebSettings.LOAD_DEFAULT
            loadsImagesAutomatically = true
            blockNetworkImage = false
            blockNetworkLoads = false
            useWideViewPort = true
            loadWithOverviewMode = true
            builtInZoomControls = false
            displayZoomControls = false
            setSupportZoom(true)
            mediaPlaybackRequiresUserGesture = false
        }
        // 硬件图层 + Leaflet 在部分机型上会出现整块发黑；地图区用默认合成更稳
        binding.wvAccidentSiteMap.setLayerType(View.LAYER_TYPE_NONE, null)
        binding.wvAccidentSiteMap.setBackgroundColor(Color.WHITE)
        binding.wvAccidentSiteMap.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view ?: return
                // WebView 初次布局高度偶发为 0，延迟触发 Leaflet 重算尺寸，避免灰/黑底块
                view.postDelayed({
                    view.evaluateJavascript(
                        "try{window.__vtLeafletMap&&window.__vtLeafletMap.invalidateSize(true)}catch(e){}",
                        null,
                    )
                }, 60)
                view.postDelayed({
                    view.evaluateJavascript(
                        "try{window.__vtLeafletMap&&window.__vtLeafletMap.invalidateSize(true)}catch(e){}",
                        null,
                    )
                }, 320)
            }
        }
        binding.wvAccidentSiteMap.isHorizontalScrollBarEnabled = false
        binding.wvAccidentSiteMap.isVerticalScrollBarEnabled = false
        binding.wvAccidentSiteMap.overScrollMode = View.OVER_SCROLL_NEVER
        // 读取本地 leaflet 文件并内联到 HTML，不依赖任何外部文件
        val leafletCss = assets.open("leaflet.css").bufferedReader(Charsets.UTF_8).readText()
        val leafletJs = assets.open("leaflet.js").bufferedReader(Charsets.UTF_8).readText()
        val html = buildLeafletMapHtml(
            coord.latitude, coord.longitude,
            event.locationText.ifBlank { "事故位置" },
            leafletCss, leafletJs,
        )
        binding.wvAccidentSiteMap.loadDataWithBaseURL(
            null, html, "text/html", "UTF-8", null,
        )
        binding.btnOpenExternalMap.setOnClickListener {
            openExternalMap(coord.latitude, coord.longitude, event.locationText.ifBlank { "事故位置" })
        }
    }

    private fun buildLeafletMapHtml(
        lat: Double, lon: Double, label: String,
        leafletCss: String, leafletJs: String,
    ): String {
        val safePopup = label
            .replace("\\", "\\\\")
            .replace("'", "\\'")
            .replace("\"", "\\\"")
            .replace("\n", " ")
            .replace("\r", " ")
            .take(120)
        val cartoVoyagerTiles =
            "https://tile.openstreetmap.org/{z}/{x}/{y}.png"
        return sequenceOf(
            "<!DOCTYPE html>",
            "<html><head><meta charset=\"utf-8\"/>",
            "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no\"/>",
            "<style>", leafletCss, "</style>",
            "<style>",
            "html,body{height:100%;margin:0;background:#e8eef5;color-scheme:light;}",
            "#mapWrap{height:100%;width:100%;border-radius:12px;overflow:hidden;",
            "  box-shadow:inset 0 0 0 1px rgba(148,163,184,.35);}",
            "#m{height:100%;width:100%;}",
            ".leaflet-container{background:#e8eef5;font-family:system-ui,-apple-system,sans-serif;}",
            ".leaflet-tile-pane{filter:none;}",
            ".leaflet-control-scale-line{border:1px solid rgba(15,23,42,.25)!important;",
            "  background:rgba(255,255,255,.92)!important;color:#334155!important;}",
            ".vt-pin-outer{background:transparent!important;border:none!important;}",
            "</style>",
            "</head><body>",
            "<div id=\"mapWrap\"><div id=\"m\"></div></div>",
            "<div id=\"_dbg\" style=\"position:fixed;bottom:0;left:0;right:0;max-height:80px;overflow:auto;background:rgba(0,0,0,.8);color:#0f0;font:10px monospace;padding:4px;z-index:9999;display:none\"></div>",
            "<script>", leafletJs, "</script>",
            "<script>",
            "function _log(m){var d=document.getElementById('_dbg');d.style.display='block';d.innerHTML+=m+'<br>';console.log(m);}",
            "_log('leaflet.js loaded, L='+(typeof L));",
            "(function(){",
            "var lat=" + lat + ",lon=" + lon + ";",
            "_log('coords: '+lat+','+lon);",
            "var map=L.map('m',{",
            "  zoomControl:true,zoomSnap:1,preferCanvas:false,",
            "  zoomAnimation:true,fadeAnimation:true,",
            "  attributionControl:true,",
            "  scrollWheelZoom:true",
            "}).setView([lat-0.0024,lon-0.0016],13.5);",
            "window.__vtLeafletMap=map;",
            "_log('map created');",
            "var tiles=L.tileLayer(",
            "  '" + cartoVoyagerTiles + "',",
            "  {maxZoom:19,updateWhenIdle:true,keepBuffer:3,",
            "   attribution:'&copy; OpenStreetMap'}",
            ");",
            "tiles.on('tileerror',function(e){_log('TILE ERR z='+e.tile._tileCoords.z+' x='+e.tile._tileCoords.x+' y='+e.tile._tileCoords.y);});",
            "tiles.on('load',function(){_log('first tile loaded OK');});",
            "tiles.addTo(map);",
            "_log('tile layer added');",
            "L.circle([lat,lon],{",
            "  radius:95,color:'#0ea5e9',fillColor:'#38bdf8',fillOpacity:0.12,weight:2,opacity:0.85",
            "}).addTo(map);",
            "var pinHtml='<div style=\"width:24px;height:24px;background:linear-gradient(145deg,#3b82f6,#1d4ed8);border:3px solid #fff;border-radius:50%;box-shadow:0 3px 12px rgba(0,0,0,.4)\"></div>';",
            "var icon=L.divIcon({className:'vt-pin-outer',html:pinHtml,iconSize:[24,24],iconAnchor:[12,12]});",
            "var mk=L.marker([lat,lon],{icon:icon,riseOnHover:true}).addTo(map);",
            "mk.bindPopup('<div style=\"min-width:180px;max-width:260px;padding:2px 0\">'+",
            "  '<div style=\"font-weight:700;color:#0f172a;font-size:14px;line-height:1.35'>" + safePopup + "</div>'+",
            "  '<div style=\"margin-top:6px;font-size:11px;color:#64748b\">演示用近似坐标 · 实车请接入 GNSS</div></div>',",
            "  {maxWidth:280,className:'vt-popup'});",
            "map.whenReady(function(){",
            "  map.invalidateSize(true);",
            "  try{map.doubleClickZoom.disable();}catch(e){}",
            "  L.control.scale({metric:true,imperial:false,maxWidth:100}).addTo(map);",
            "  requestAnimationFrame(function(){",
            "    map.flyTo([lat,lon],17,{duration:0.95,easeLinearity:0.24,animate:true});",
            "    window.setTimeout(function(){try{mk.openPopup();}catch(e2){}},1000);",
            "  });",
            "});",
            "})();",
            "</script></body></html>",
        ).joinToString("\n")
    }

    private fun openExternalMap(lat: Double, lon: Double, label: String) {
        val uri = Uri.parse("geo:0,0?q=${lat},${lon}(${Uri.encode(label)})")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(Intent.createChooser(intent, "选择地图应用"))
    }

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
        private const val REPLAY_SEEK_MAX = 1000
        private const val REPLAY_DURATION_SCALE = 1.82f
    }
}
