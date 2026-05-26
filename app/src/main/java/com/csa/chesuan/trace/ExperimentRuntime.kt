package com.csa.chesuan.trace

import android.content.Context

/**
 * 云端事故复盘实验运行时配置（对齐《云端事故复盘能力实验说明文档》§3.2、§9）。
 * 单条分析与 assets 批量请求共用同一组开关；在**事故溯源列表页**修改并持久化，详情页仅展示。
 */
object ExperimentRuntime {

    /** A=模板 B=通用 C=本项目结构化 */
    @Volatile
    var cloudExperimentGroup: String = "C"

    /** D0=完整；D1 去掉责任；D2 去掉环境；D3 去掉决策链；D4 弱化结构化提示（仅 C 组在后端生效） */
    @Volatile
    var cloudAblationMode: String = "D0"

    private const val PREFS_NAME = "vehtrust_experiment_runtime"
    private const val KEY_GROUP = "cloud_experiment_group"
    private const val KEY_ABLATION = "cloud_ablation_mode"

    fun loadPersisted(context: Context) {
        val p = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        cloudExperimentGroup = p.getString(KEY_GROUP, "C")?.trim()?.uppercase().takeIf { it in setOf("A", "B", "C") } ?: "C"
        cloudAblationMode = p.getString(KEY_ABLATION, "D0")?.trim()?.uppercase().takeIf { it in setOf("D0", "D1", "D2", "D3", "D4") } ?: "D0"
    }

    fun persist(context: Context) {
        val p = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        p.edit()
            .putString(KEY_GROUP, normalizedGroup())
            .putString(KEY_ABLATION, normalizedAblation())
            .apply()
    }

    fun normalizedGroup(): String = when (cloudExperimentGroup.uppercase()) {
        "A", "B", "C" -> cloudExperimentGroup.uppercase()
        else -> "C"
    }

    fun normalizedAblation(): String = when (cloudAblationMode.uppercase()) {
        "D0", "D1", "D2", "D3", "D4" -> cloudAblationMode.uppercase()
        else -> "D0"
    }

    fun summaryLabel(): String = "${normalizedGroup()} + ${normalizedAblation()}"
}
