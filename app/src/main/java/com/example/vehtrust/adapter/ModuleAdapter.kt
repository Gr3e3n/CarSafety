package com.example.vehtrust.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vehtrust.R
import com.example.vehtrust.data.SafetyModule
import com.example.vehtrust.databinding.ItemSafetyModuleBinding

class ModuleAdapter(
    private var modules: List<SafetyModule>,
    private val onItemClick: (SafetyModule) -> Unit,
    private val onItemLongClick: (SafetyModule) -> Unit,
) : RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder>() {

    inner class ModuleViewHolder(private val binding: ItemSafetyModuleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(module: SafetyModule) {
            val ctx = binding.root.context
            val themeColor = ctx.getColor(module.colorRes)

            // 风险标签（只读判断）
            when (module.riskLevel) {
                2 -> {
                    binding.tvRiskBadge.visibility = android.view.View.VISIBLE
                    binding.tvRiskBadge.text = "高风险"
                    binding.tvRiskBadge.setBackgroundResource(R.drawable.bg_risk_badge_red)
                }
                1 -> {
                    binding.tvRiskBadge.visibility = android.view.View.VISIBLE
                    binding.tvRiskBadge.text = "关注"
                    binding.tvRiskBadge.setBackgroundResource(R.drawable.bg_risk_badge_yellow)
                }
                else -> {
                    // 正常默认不展示，避免信息噪音；需要的话可改为展示“正常”
                    binding.tvRiskBadge.visibility = android.view.View.GONE
                }
            }

            // 图标
            binding.ivIcon.setImageResource(module.iconRes)
            binding.ivIcon.setColorFilter(themeColor)

            // 图标圆圈背景：模块主题色 15% 透明度
            val bgDrawable = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                val r = Color.red(themeColor)
                val g = Color.green(themeColor)
                val b = Color.blue(themeColor)
                setColor(Color.argb(38, r, g, b))  // 15% alpha
            }
            binding.vIconBg.background = bgDrawable

            // 文字
            binding.tvTitle.text = module.title
            binding.tvStatus.text = when {
                module.riskLevel > 0 && module.riskReason.isNotBlank() -> module.riskReason
                else -> module.status
            }

            // 左侧细线装饰：顶部细线用主题色
            binding.cardContainer.setCardBackgroundColor(
                ctx.getColor(android.R.color.white)
            )

            // 点击
            binding.root.setOnClickListener { onItemClick(module) }
            binding.root.setOnLongClickListener {
                onItemLongClick(module)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        val binding = ItemSafetyModuleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ModuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        holder.bind(modules[position])
    }

    override fun getItemCount() = modules.size

    fun updateModules(newModules: List<SafetyModule>) {
        modules = newModules
        notifyDataSetChanged()
    }
}
