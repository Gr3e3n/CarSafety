package com.example.vehtrust

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.vehtrust.data.CarExtPropertyIds
import com.example.vehtrust.data.ModuleCatalog
import com.example.vehtrust.data.ModuleMetric
import com.example.vehtrust.data.ModuleParamValueResolver
import com.example.vehtrust.data.SafetyModule
import com.example.vehtrust.databinding.ActivityModuleDetailBinding
import com.example.vehtrust.databinding.ItemModuleParamRowBinding
import com.example.vehtrust.databinding.ItemModuleTipRowBinding
import com.google.android.material.chip.Chip

class ModuleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModuleDetailBinding
    private lateinit var viewModel: SafetyViewModel
    private var moduleId: String = ""
    private var themeColorRes: Int = R.color.brand_primary
    private lateinit var catalogDetail: ModuleCatalog.ModuleDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModuleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moduleId = intent.getStringExtra(EXTRA_MODULE_ID).orEmpty()
        themeColorRes = intent.getIntExtra(EXTRA_COLOR_RES, R.color.brand_primary)
        catalogDetail = ModuleCatalog.detailFor(moduleId)

        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.title = catalogDetail.title

        bindStaticHero()
        bindTips(catalogDetail.tips)

        viewModel = ViewModelProvider(this)[SafetyViewModel::class.java]
        viewModel.modules.observe(this) { modules ->
            val module = modules.firstOrNull { it.id == moduleId }
            bindLiveData(module)
        }
    }

    private fun bindStaticHero() {
        val themeColor = getColor(themeColorRes)
        applyIconTheme(binding.ivIcon, binding.vIconBg, themeColor, catalogDetail.iconRes)

        binding.tvTitle.text = catalogDetail.title
        if (catalogDetail.sdkGroup.isNotBlank()) {
            binding.tvSdkGroup.isVisible = true
            binding.tvSdkGroup.text = "CarExt · ${catalogDetail.sdkGroup}"
        } else {
            binding.tvSdkGroup.isVisible = false
        }
        binding.tvDescription.text = catalogDetail.description
        bindCapabilityChips(catalogDetail.capabilities, themeColor)
        binding.tvParamCount.text = "共 ${catalogDetail.params.size} 项"
    }

    private fun bindLiveData(module: SafetyModule?) {
        bindRiskUi(module)
        bindMetrics(module?.metrics.orEmpty())
        bindStatusCard(module)
        bindParamRows(module)
    }

    private fun bindRiskUi(module: SafetyModule?) {
        val level = module?.riskLevel ?: 0
        when (level) {
            2 -> {
                binding.tvRiskBadge.isVisible = true
                binding.tvRiskBadge.text = "高风险"
                binding.tvRiskBadge.setBackgroundResource(R.drawable.bg_risk_badge_red)
                binding.vStatusDot.setBackgroundResource(R.drawable.bg_status_dot_red)
                binding.tvStatusTitle.text = "需要立即关注"
            }
            1 -> {
                binding.tvRiskBadge.isVisible = true
                binding.tvRiskBadge.text = "关注"
                binding.tvRiskBadge.setBackgroundResource(R.drawable.bg_risk_badge_yellow)
                binding.vStatusDot.setBackgroundResource(R.drawable.bg_status_dot_yellow)
                binding.tvStatusTitle.text = "建议检查"
            }
            else -> {
                binding.tvRiskBadge.isVisible = false
                binding.vStatusDot.setBackgroundResource(R.drawable.bg_status_dot_green)
                binding.tvStatusTitle.text = "状态正常"
            }
        }
        val reason = module?.riskReason.orEmpty()
        if (level > 0 && reason.isNotBlank()) {
            binding.tvRiskReason.isVisible = true
            binding.tvRiskReason.text = reason
        } else {
            binding.tvRiskReason.isVisible = false
        }
    }

    private fun bindMetrics(metrics: List<ModuleMetric>) {
        val cells = listOf(
            binding.metricCell0 to (binding.tvMetric0Label to binding.tvMetric0Value),
            binding.metricCell1 to (binding.tvMetric1Label to binding.tvMetric1Value),
            binding.metricCell2 to (binding.tvMetric2Label to binding.tvMetric2Value),
        )
        cells.forEachIndexed { index, (cell, labels) ->
            val metric = metrics.getOrNull(index)
            if (metric == null) {
                cell.isVisible = false
            } else {
                cell.isVisible = true
                labels.first.text = metric.label
                labels.second.text = metric.value
            }
        }
    }

    private fun bindStatusCard(module: SafetyModule?) {
        binding.tvStatus.text = module?.status ?: "等待数据刷新…"
    }

    private fun bindParamRows(module: SafetyModule?) {
        binding.layoutParams.removeAllViews()
        val resolved = ModuleParamValueResolver.resolve(module, catalogDetail)
        val inflater = LayoutInflater.from(this)
        resolved.forEach { row ->
            val item = ItemModuleParamRowBinding.inflate(inflater, binding.layoutParams, true)
            item.tvParamName.text = shortParamName(row.param.name)
            item.tvParamMeaning.text = row.param.meaning
            item.tvParamId.text = "${row.param.propertyId} (${CarExtPropertyIds.hex(row.param.propertyId)})"
            item.tvParamType.text = row.param.valueType
            item.tvParamValue.text = row.displayValue
            applyValueTone(item.tvParamValue, row.tone)
        }
    }

    private fun bindTips(tips: List<String>) {
        binding.layoutTips.removeAllViews()
        val inflater = LayoutInflater.from(this)
        tips.forEach { tip ->
            val row = ItemModuleTipRowBinding.inflate(inflater, binding.layoutTips, true)
            row.tvTip.text = tip
        }
    }

    private fun bindCapabilityChips(labels: List<String>, themeColor: Int) {
        binding.chipCapabilities.removeAllViews()
        labels.forEach { label ->
            val chip = Chip(this).apply {
                text = label
                isClickable = false
                isCheckable = false
                chipBackgroundColor = android.content.res.ColorStateList.valueOf(
                    Color.argb(38, Color.red(themeColor), Color.green(themeColor), Color.blue(themeColor)),
                )
                setTextColor(themeColor)
                textSize = 10f
                chipMinHeight = resources.displayMetrics.density * 24
            }
            binding.chipCapabilities.addView(chip)
        }
    }

    private fun shortParamName(fullName: String): String = when {
        fullName.length <= 28 -> fullName
        else -> fullName.take(26) + "…"
    }

    private fun applyValueTone(view: TextView, tone: ModuleParamValueResolver.ValueTone) {
        when (tone) {
            ModuleParamValueResolver.ValueTone.OK -> {
                view.setBackgroundResource(R.drawable.bg_param_value_ok)
                view.setTextColor(getColor(R.color.success_on_container))
            }
            ModuleParamValueResolver.ValueTone.WARN -> {
                view.setBackgroundResource(R.drawable.bg_param_value_warn)
                view.setTextColor(Color.parseColor("#B45309"))
            }
            ModuleParamValueResolver.ValueTone.OFF -> {
                view.setBackgroundResource(R.drawable.bg_param_value_off)
                view.setTextColor(getColor(R.color.text_tertiary))
            }
            ModuleParamValueResolver.ValueTone.NEUTRAL -> {
                view.setBackgroundResource(R.drawable.bg_module_chip)
                view.setTextColor(getColor(R.color.brand_on_container))
            }
        }
    }

    private fun applyIconTheme(icon: ImageView, iconBg: View, themeColor: Int, iconRes: Int) {
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

    companion object {
        const val EXTRA_MODULE_ID = "extra_module_id"
        const val EXTRA_COLOR_RES = "extra_color_res"
    }
}
