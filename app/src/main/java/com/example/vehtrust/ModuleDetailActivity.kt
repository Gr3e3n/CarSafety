package com.example.vehtrust

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vehtrust.data.ModuleCatalog
import com.example.vehtrust.databinding.ActivityModuleDetailBinding

class ModuleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModuleDetailBinding
    private lateinit var viewModel: SafetyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModuleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moduleId = intent.getStringExtra(EXTRA_MODULE_ID).orEmpty()
        val detail = ModuleCatalog.detailFor(moduleId)
        supportActionBar?.title = detail.title

        binding.ivIcon.setImageResource(detail.iconRes)
        binding.tvTitle.text = detail.title
        binding.tvDescription.text = detail.description

        viewModel = ViewModelProvider(this)[SafetyViewModel::class.java]
        viewModel.modules.observe(this) { modules ->
            val module = modules.firstOrNull { it.id == moduleId }
            val riskText = when (module?.riskLevel) {
                2 -> "高风险：${module.riskReason.ifBlank { "需要立即关注" }}"
                1 -> "关注：${module.riskReason.ifBlank { "建议检查" }}"
                else -> "状态：${module?.status ?: "暂无数据"}"
            }
            binding.tvStatus.text = riskText
            binding.tvParams.text = buildParamsText(detail, module?.status, module)
        }

        binding.tvTips.text = detail.tips.joinToString("\n") { "• $it" }
    }

    private fun buildParamsText(
        detail: ModuleCatalog.ModuleDetail,
        status: String?,
        module: com.example.vehtrust.data.SafetyModule?,
    ): String {
        if (detail.params.isEmpty()) return "（无参数）"
        return buildString {
            append("当前概览：").append(status ?: "暂无").append("\n\n")
            if (module != null && module.riskLevel > 0) {
                append("风险判断：")
                    .append(if (module.riskLevel == 2) "高风险" else "关注")
                    .append('\n')
                append("原因：").append(module.riskReason).append("\n\n")
            }
            detail.params.forEach { p ->
                append(p.name).append('\n')
                append("  propertyId: ").append(p.propertyId).append('\n')
                append("  valueType : ").append(p.valueType).append('\n')
                append("  meaning   : ").append(p.meaning).append("\n\n")
            }
        }.trimEnd()
    }

    companion object {
        const val EXTRA_MODULE_ID = "extra_module_id"
    }
}

