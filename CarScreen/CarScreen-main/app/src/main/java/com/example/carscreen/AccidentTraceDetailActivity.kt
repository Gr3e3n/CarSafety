package com.example.carscreen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.carscreen.databinding.ActivityAccidentTraceDetailBinding
import com.example.carscreen.trace.AccidentDetailBundle
import com.example.carscreen.trace.AccidentTraceViewModel
import com.example.carscreen.trace.ResponsibilityAnalyzer
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AccidentTraceDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccidentTraceDetailBinding
    private lateinit var viewModel: AccidentTraceViewModel
    private var bundle: AccidentDetailBundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccidentTraceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "溯源详情"

        viewModel = ViewModelProvider(this)[AccidentTraceViewModel::class.java]

        val eventId = intent.getStringExtra(EXTRA_EVENT_ID) ?: return
        bundle = viewModel.loadDetail(eventId)
        render(bundle!!)

        // ── 上链按钮 ──────────────────────────────────────────────────
        binding.btnUploadChain.setOnClickListener {
            val b = bundle ?: return@setOnClickListener

            // 按钮置灰，防止重复点击；隐藏上次结果
            binding.btnUploadChain.isEnabled = false
            binding.btnUploadChain.text = "上链中..."
            binding.layoutChainSuccess.visibility = View.GONE
            binding.tvChainError.visibility = View.GONE  // ← 隐藏上次错误

            lifecycleScope.launch {
                val result = viewModel.uploadToBlockchain(b)

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
    }

    private fun render(b: AccidentDetailBundle) {
        // ── 事件基本信息 ──────────────────────────────────────────────
        binding.tvHeader.text = buildString {
            append("事件：").append(b.event.id).append('\n')
            append("时间：").append(formatTime(b.event.timeMillis)).append('\n')
            append("地点：").append(b.event.locationText).append('\n')
            append("触发：").append(b.event.triggerReasons.joinToString(" · ")).append('\n')
            append("摘要：").append(b.event.summary)
        }

        // ── 遥测表格（事故前 10 秒，展示每 0.5s 一点）────────────────
        binding.tvTelemetry.text = buildString {
            append("采样：20Hz · 环形缓冲冻结快照\n")
            append("t(ms)\t速度(km/h)\tax(m/s²)\t刹车(%)\t转角(°)\n")
            val shown = b.telemetry10sBefore.filter { it.tMs % 500 == 0 }
            shown.forEach {
                append(it.tMs).append('\t')
                append(String.format(Locale.getDefault(), "%.1f", it.speedKph)).append('\t')
                append(String.format(Locale.getDefault(), "%.2f", it.axMS2)).append('\t')
                append(it.brake).append('\t')
                append(String.format(Locale.getDefault(), "%.1f", it.steerDeg)).append('\n')
            }
        }

        // ── 可解释责任指标 ────────────────────────────────────────────
        val metrics = viewModel.analyzeMetrics(b)
        renderMetrics(metrics)

        // ── AI 因果推断 + 责任占比进度条 ─────────────────────────────
        val resp = b.responsibility
        binding.tvAiInference.text = buildString {
            append(resp.conclusion).append('\n')
        }
        setBarWeight(binding.barDriver, resp.driverFactor.toFloat())
        setBarWeight(binding.barSystem, resp.systemFactor.toFloat())
        setBarWeight(binding.barEnv, resp.environmentFactor.toFloat())
        binding.tvBarDriverPct.text = "${resp.driverFactor}%"
        binding.tvBarSystemPct.text = "${resp.systemFactor}%"
        binding.tvBarEnvPct.text = "${resp.environmentFactor}%"

        // ── 驾驶员行为分析文本 ────────────────────────────────────────
        binding.tvDriverAnalysis.text = resp.reasons.joinToString("\n")

        // ── 自动驾驶故障链路与环境 ────────────────────────────────────
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

    private fun renderMetrics(m: ResponsibilityAnalyzer.DetailedMetrics) {
        binding.tvReactionTime.text = if (m.reactionTimeMs >= 0) "${m.reactionTimeMs}ms" else "N/A"
        binding.tvReactionTimeLabel.text = when {
            m.reactionTimeMs >= 2500 -> "过长，风险极高"
            m.reactionTimeMs >= 1500 -> "偏长，需关注"
            m.reactionTimeMs >= 0    -> "正常"
            else                     -> ""
        }
        binding.tvReactionTimeLabel.setTextColor(
            getColor(when {
                m.reactionTimeMs >= 2500 -> R.color.warning_red
                m.reactionTimeMs >= 1500 -> R.color.module_orange
                else                     -> R.color.module_teal
            })
        )

        binding.tvBrakeRiseTime.text = if (m.brakeRiseTimeMs >= 0) "${m.brakeRiseTimeMs}ms" else "未达"
        binding.tvBrakeRiseLabel.text = when {
            m.brakeRiseTimeMs < 0    -> "未达全力制动"
            m.brakeRiseTimeMs <= 400 -> "制动果断"
            else                     -> "上升偏慢"
        }

        binding.tvMaxDecel.text = String.format(Locale.getDefault(), "%.1f", m.maxDecelerationMS2)
        binding.tvMaxDecelLabel.text = when {
            m.maxDecelerationMS2 <= -8f -> "碰撞级"
            m.maxDecelerationMS2 <= -5f -> "强制动"
            else                        -> "中等"
        }

        binding.tvAebDelay.text = if (m.aebDelayMs != -1) "${m.aebDelayMs}ms" else "未触发"
        binding.tvAebLabel.text = if (m.aebDelayMs != -1) "相对事故时刻" else "系统未介入"
        binding.tvAebDelay.setTextColor(
            getColor(if (m.aebDelayMs != -1) R.color.module_teal else R.color.warning_red)
        )

        binding.tvTtc.text = if (m.ttcAtBrakeStart >= 0f) {
            String.format(Locale.getDefault(), "%.1fs", m.ttcAtBrakeStart)
        } else "N/A"
        binding.tvTtcLabel.text = when {
            m.ttcAtBrakeStart in 0f..1.5f -> "跟车时距不足"
            m.ttcAtBrakeStart > 1.5f      -> "时距尚可"
            else                          -> "不可用"
        }

        binding.tvBrakeEffective.text = if (m.brakeEffective) "有效" else "不足"
        binding.tvBrakeEffective.setTextColor(
            getColor(if (m.brakeEffective) R.color.module_teal else R.color.warning_red)
        )
        binding.tvBrakeEffectiveLabel.text = if (m.brakeEffective) "及时达到强制动" else "未及时强制动"
    }

    private fun setBarWeight(view: View, weight: Float) {
        val params = view.layoutParams as android.widget.LinearLayout.LayoutParams
        params.weight = weight.coerceAtLeast(1f)
        view.layoutParams = params
    }

    private fun formatTime(ms: Long): String =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(ms))

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }
}