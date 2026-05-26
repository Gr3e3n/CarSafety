package com.csa.chesuan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.csa.chesuan.data.ModuleMetric
import com.csa.chesuan.data.SafetyModule
import com.csa.chesuan.mock.MockDataProvider
import com.csa.chesuan.trace.AccidentRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SafetyViewModel : ViewModel() {

    private val _modules = MutableLiveData<List<SafetyModule>>()
    val modules: LiveData<List<SafetyModule>> = _modules

    init {
        startMockDataUpdate()
    }

    private fun startMockDataUpdate() {
        viewModelScope.launch {
            while (true) {
                val base = MockDataProvider.generateModules()
                _modules.postValue(enrichTraceModule(base))
                delay(2000)
            }
        }
    }

    private fun enrichTraceModule(modules: List<SafetyModule>): List<SafetyModule> {
        val events = AccidentRepository.listEvents()
        val count = events.size
        val last = events.maxByOrNull { it.timeMillis }
        val lastTime = last?.let {
            SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(Date(it.timeMillis))
        } ?: "暂无"
        val lastSummary = last?.summary?.take(18)?.let { if (it.length == 18) "$it…" else it }

        return modules.map { module ->
            if (module.id != "trace") return@map module
            module.copy(
                metrics = listOf(
                    ModuleMetric("已存证", "${count}条"),
                    ModuleMetric("采样率", "20Hz"),
                    ModuleMetric("最近", lastTime),
                ),
                status = when {
                    lastSummary != null -> "最近事件：$lastSummary · 点击进入详情/上链"
                    count > 0 -> "共 $count 条记录 · 支持回放、AI 与链上存证"
                    else -> "20Hz 监控运行中 · 触发后自动冻结前后 10 秒"
                },
            )
        }
    }

    fun updateModuleStatus(moduleId: String, value: Any) {
        val currentList = _modules.value ?: return
        val updatedList = currentList.map { module ->
            if (module.id == moduleId) {
                module.copy(status = formatStatus(module, value))
            } else module
        }
        _modules.postValue(enrichTraceModule(updatedList))
    }

    fun updateAllModules(newModules: List<SafetyModule>) {
        _modules.postValue(enrichTraceModule(newModules))
    }

    private fun formatStatus(module: SafetyModule, value: Any): String {
        return when (module.id) {
            "adas", "rear_safety", "rain_safety", "door", "occupant", "light" -> module.status
            "blindspot" -> when (value) {
                is Int -> when (value) {
                    1 -> "变道警示 视觉 · RCTA 无报警"
                    2 -> "变道警示 声音 · RCTA 无报警"
                    3 -> "变道警示 视觉+声音 · RCTA 无报警"
                    else -> "变道警示 关闭 · RCTA 无报警"
                }
                else -> module.status
            }
            "fatigue" -> when (value) {
                is Int -> when (value) {
                    2 -> "状态正常 · 表情平静"
                    3 -> "分心驾驶 · 表情异常"
                    4 -> "疲劳驾驶 · 表情异常"
                    else -> "未知状态 · 表情未知"
                }
                else -> module.status
            }
            "speed_limit" -> module.status
            else -> value.toString()
        }
    }

    fun sendControlCommand(moduleId: String, command: Any) {
        println("发送控制指令: $moduleId -> $command")
    }
}
