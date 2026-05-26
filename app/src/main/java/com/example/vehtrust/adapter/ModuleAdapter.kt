package com.example.vehtrust.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vehtrust.R
import com.example.vehtrust.data.ModuleMetric
import com.example.vehtrust.data.SafetyModule
import com.example.vehtrust.databinding.ItemSafetyModuleFeaturedBinding
import com.example.vehtrust.databinding.ItemSafetyModuleRichBinding

class ModuleAdapter(
    private var modules: List<SafetyModule>,
    private val onItemClick: (SafetyModule) -> Unit,
    private val onItemLongClick: (SafetyModule) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_FEATURED = 0
        private const val TYPE_RICH = 1
    }

    inner class FeaturedViewHolder(private val binding: ItemSafetyModuleFeaturedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(module: SafetyModule) {
            val ctx = binding.root.context
            val themeColor = ctx.getColor(module.colorRes)
            applyIconTheme(binding.ivIcon, binding.vIconBg, themeColor, module.iconRes, 32)

            binding.tvTitle.text = module.title
            binding.tvSubtitle.text = module.subtitle.ifBlank { "EDR 取证 · 责任界定 · 可信存证" }
            bindMetrics(
                listOf(
                    binding.tvMetric0Label to binding.tvMetric0Value,
                    binding.tvMetric1Label to binding.tvMetric1Value,
                    binding.tvMetric2Label to binding.tvMetric2Value,
                ),
                module.metrics,
            )
            bindHighlightChips(binding.layoutHighlights, module.highlights, themeColor)
            binding.tvStatus.text = module.status
            binding.btnEnter.text = "进入溯源中心 →"

            binding.root.setOnClickListener { onItemClick(module) }
            binding.btnEnter.setOnClickListener { onItemClick(module) }
            binding.root.setOnLongClickListener {
                onItemLongClick(module)
                true
            }
        }
    }

    inner class RichViewHolder(private val binding: ItemSafetyModuleRichBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(module: SafetyModule) {
            val ctx = binding.root.context
            val themeColor = ctx.getColor(module.colorRes)
            applyIconTheme(binding.ivIcon, binding.vIconBg, themeColor, module.iconRes, 22)

            binding.tvTitle.text = module.title
            binding.tvSubtitle.text = module.subtitle
            binding.tvSubtitle.visibility =
                if (module.subtitle.isBlank()) View.GONE else View.VISIBLE

            when (module.riskLevel) {
                2 -> {
                    binding.tvRiskBadge.visibility = View.VISIBLE
                    binding.tvRiskBadge.text = "高风险"
                    binding.tvRiskBadge.setBackgroundResource(R.drawable.bg_risk_badge_red)
                    binding.vStatusDot.setBackgroundResource(R.drawable.bg_status_dot_red)
                }
                1 -> {
                    binding.tvRiskBadge.visibility = View.VISIBLE
                    binding.tvRiskBadge.text = "关注"
                    binding.tvRiskBadge.setBackgroundResource(R.drawable.bg_risk_badge_yellow)
                    binding.vStatusDot.setBackgroundResource(R.drawable.bg_status_dot_yellow)
                }
                else -> {
                    binding.tvRiskBadge.visibility = View.GONE
                    binding.vStatusDot.setBackgroundResource(R.drawable.bg_status_dot_green)
                }
            }

            val metricPairs = listOf(
                binding.tvRichMetric0Label to binding.tvRichMetric0Value,
                binding.tvRichMetric1Label to binding.tvRichMetric1Value,
                binding.tvRichMetric2Label to binding.tvRichMetric2Value,
            )
            val metrics = module.metrics.take(3)
            metricPairs.forEachIndexed { index, (labelView, valueView) ->
                val parent = when (index) {
                    0 -> binding.richMetric0
                    1 -> binding.richMetric1
                    else -> binding.richMetric2
                }
                val metric = metrics.getOrNull(index)
                if (metric == null) {
                    parent.visibility = View.GONE
                } else {
                    parent.visibility = View.VISIBLE
                    labelView.text = metric.label
                    valueView.text = metric.value
                }
            }
            if (metrics.isEmpty()) {
                binding.richMetric0.visibility = View.GONE
                binding.richMetric1.visibility = View.GONE
                binding.richMetric2.visibility = View.GONE
            }

            binding.tvStatus.text = when {
                module.riskLevel > 0 && module.riskReason.isNotBlank() -> module.riskReason
                else -> module.status
            }

            binding.root.setOnClickListener { onItemClick(module) }
            binding.root.setOnLongClickListener {
                onItemLongClick(module)
                true
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (modules[position].isFeatured || modules[position].id == "trace") {
            TYPE_FEATURED
        } else {
            TYPE_RICH
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_FEATURED -> FeaturedViewHolder(
                ItemSafetyModuleFeaturedBinding.inflate(inflater, parent, false),
            )
            else -> RichViewHolder(
                ItemSafetyModuleRichBinding.inflate(inflater, parent, false),
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val module = modules[position]
        when (holder) {
            is FeaturedViewHolder -> holder.bind(module)
            is RichViewHolder -> holder.bind(module)
        }
    }

    override fun getItemCount() = modules.size

    fun updateModules(newModules: List<SafetyModule>) {
        modules = newModules
        notifyDataSetChanged()
    }

    fun isFeaturedPosition(position: Int): Boolean =
        getItemViewType(position) == TYPE_FEATURED

    private fun applyIconTheme(
        icon: android.widget.ImageView,
        iconBg: View,
        themeColor: Int,
        iconRes: Int,
        iconDp: Int,
    ) {
        icon.setImageResource(iconRes)
        icon.setColorFilter(themeColor)
        val r = Color.red(themeColor)
        val g = Color.green(themeColor)
        val b = Color.blue(themeColor)
        iconBg.background = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(Color.argb(38, r, g, b))
        }
    }

    private fun bindMetrics(
        views: List<Pair<TextView, TextView>>,
        metrics: List<ModuleMetric>,
    ) {
        views.forEachIndexed { index, (labelView, valueView) ->
            val metric = metrics.getOrNull(index)
            if (metric == null) {
                labelView.text = ""
                valueView.text = "—"
            } else {
                labelView.text = metric.label
                valueView.text = metric.value
            }
        }
    }

    private fun bindHighlightChips(
        container: LinearLayout,
        highlights: List<String>,
        themeColor: Int,
    ) {
        container.removeAllViews()
        val ctx = container.context
        val padH = (8 * ctx.resources.displayMetrics.density).toInt()
        val padV = (4 * ctx.resources.displayMetrics.density).toInt()
        val chipGap = (6 * ctx.resources.displayMetrics.density).toInt()
        highlights.forEach { label ->
            val chip = TextView(ctx).apply {
                this.text = label
                textSize = 10f
                setTextColor(ctx.getColor(R.color.brand_on_container))
                setBackgroundResource(R.drawable.bg_trace_chip)
                setPadding(padH, padV, padH, padV)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                ).apply {
                    marginEnd = chipGap
                }
            }
            container.addView(chip)
        }
    }
}
