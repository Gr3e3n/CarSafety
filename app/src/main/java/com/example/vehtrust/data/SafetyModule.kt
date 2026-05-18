package com.example.vehtrust.data

/**
 * 安全功能模块数据类
 * @param id 功能ID（对应info.txt中的常量名）
 * @param title 显示名称
 * @param iconRes 图标资源ID
 * @param status 当前状态描述
 * @param colorRes 主题色
 * @param propertyId 对应的车辆属性ID（来自info.txt）
 * @param areaType 区域类型（如全局、座椅、车门等）
 */
data class SafetyModule(
    val id: String,
    val title: String,
    val iconRes: Int,
    var status: String,
    val riskLevel: Int = 0,      // 0=正常(绿) 1=关注(黄) 2=高风险(红)
    val riskReason: String = "", // 风险原因（用于卡片标签与详情展示）
    val colorRes: Int,
    val propertyId: Int,        // 车辆属性ID，用于读取/写入
    val areaType: Int = 0,       // 区域类型（默认为全局）
    val valueType: String = "boolean" // 值类型：boolean, int, float, string
)