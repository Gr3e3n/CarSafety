package com.example.vehtrust

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehtrust.data.SafetyModule
import com.example.vehtrust.mock.MockDataProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SafetyViewModel : ViewModel() {

    private val _modules = MutableLiveData<List<SafetyModule>>()
    val modules: LiveData<List<SafetyModule>> = _modules

    init {
        startMockDataUpdate()
    }

    private fun startMockDataUpdate() {
        viewModelScope.launch {
            while (true) {
                _modules.postValue(MockDataProvider.generateModules())
                delay(2000) // 每2秒更新一次
            }
        }
    }

    // ========== 真实数据接入接口 ==========

    /**
     * 从车辆系统接收真实数据更新
     * @param moduleId 模块ID（对应info.txt中的常量名）
     * @param value 新的值（可以是Boolean、Int、Float等）
     */
    fun updateModuleStatus(moduleId: String, value: Any) {
        val currentList = _modules.value ?: return
        val updatedList = currentList.map { module ->
            if (module.id == moduleId) {
                module.copy(status = formatStatus(module, value))
            } else module
        }
        _modules.postValue(updatedList)
    }

    /**
     * 批量更新多个模块
     */
    fun updateAllModules(newModules: List<SafetyModule>) {
        _modules.postValue(newModules)
    }

    /**
     * 根据值类型格式化状态文本
     */
    private fun formatStatus(module: SafetyModule, value: Any): String {
        return when (module.id) {
            "adas" -> {
                // 处理复合状态（需要更多数据）
                module.status // 暂保留原有格式
            }
            "blindspot" -> {
                when (value) {
                    is Int -> when (value) {
                        1 -> "视觉预警"
                        2 -> "声音预警"
                        3 -> "视觉+声音"
                        else -> "关闭"
                    }
                    else -> module.status
                }
            }
            "fatigue" -> {
                when (value) {
                    is Int -> when (value) {
                        1 -> "未知"
                        2 -> "正常"
                        3 -> "分心"
                        4 -> "疲劳"
                        else -> "未知"
                    }
                    else -> module.status
                }
            }
            "collision" -> {
                when (value) {
                    is Int -> when (value) {
                        1 -> "低灵敏度"
                        2 -> "中灵敏度"
                        3 -> "高灵敏度"
                        255 -> "关闭"
                        else -> "未知"
                    }
                    else -> module.status
                }
            }
            else -> value.toString()
        }
    }

    /**
     * 发送控制命令（用户点击卡片时调用）
     */
    fun sendControlCommand(moduleId: String, command: Any) {
        // 这里调用 CarExt API 发送控制指令
        // 例如：carExt.getCarManager(ISafety::class.java).setProperty(propertyId, command)
        println("发送控制指令: $moduleId -> $command")
    }
}