package com.csa.chesuan.data

import com.csa.chesuan.R

/**
 * 模块详情页「只读参数」清单，与 [CarExtPropertyIds] / EcarX-CarExt-SDK/info.txt 对齐。
 */
object ModuleCatalog {

    data class Param(
        val name: String,
        val propertyId: Int,
        val valueType: String,
        val meaning: String,
    )

    data class ModuleDetail(
        val title: String,
        val iconRes: Int,
        val sdkGroup: String = "",
        val description: String,
        val params: List<Param>,
        val tips: List<String>,
        /** 详情页能力标签 */
        val capabilities: List<String> = emptyList(),
    )

    fun detailFor(moduleId: String): ModuleDetail {
        val resolved = when (moduleId) {
            "child_safety" -> "occupant"
            "collision", "lane", "lane_keep" -> "adas"
            else -> moduleId
        }
        return when (resolved) {
            "adas" -> ModuleDetail(
                title = "驾驶辅助",
                iconRes = R.drawable.ic_auto_brake,
                sdkGroup = "IADAS",
                description = "聚合 AEB、前向碰撞预警、车道偏离/保持与紧急车道保持等可提取开关与状态，用于首页风险总览（只读，不控车）。",
                params = listOf(
                    p("AUTONOMOUS_EMERGENCY_BRAKING", CarExtPropertyIds.AUTONOMOUS_EMERGENCY_BRAKING, "boolean", "自动紧急制动 AEB 开关"),
                    p("FORWARD_COLLISION_WARN_SNVT", CarExtPropertyIds.FORWARD_COLLISION_WARN_SNVT, "int", "前向碰撞预警灵敏等级（关/低/中/高）"),
                    p("LANE_DEPARTURE_WARNING", CarExtPropertyIds.LANE_DEPARTURE_WARNING, "boolean", "车道偏离预警 LDW"),
                    p("LANE_KEEPING_AID", CarExtPropertyIds.LANE_KEEPING_AID, "boolean", "车道保持辅助 LKA"),
                    p("EMERGENCY_LANE_KEEP_AID", CarExtPropertyIds.EMERGENCY_LANE_KEEP_AID, "boolean", "紧急车道保持 ELK"),
                ),
                tips = listOf(
                    "高速或拥堵路段建议保持 AEB/FCW 开启。",
                    "原「碰撞预警 / 车道偏离 / 车道保持」已合并为本卡片，避免重复入口。",
                ),
                capabilities = listOf("AEB", "FCW", "LDW", "LKA", "ELK"),
            )

            "blindspot" -> ModuleDetail(
                title = "侧向与变道",
                iconRes = R.drawable.ic_blind_spot,
                sdkGroup = "IADAS",
                description = "变道警示模式与倒车横向来车报警（RCTA）左右侧实时状态，对应侧后方风险场景。",
                params = listOf(
                    p("LANE_CHANGE_WARNING_MODE", CarExtPropertyIds.LANE_CHANGE_WARNING_MODE, "int", "变道警示：关/视觉/声音/视觉+声音"),
                    p("REAR_CROSS_TRAFFIC_ALERT", CarExtPropertyIds.REAR_CROSS_TRAFFIC_ALERT, "boolean", "倒车横向来车预警 RCTA 总开关"),
                    p("RCTA_WARNING_LEFT", CarExtPropertyIds.RCTA_WARNING_LEFT, "int", "左侧 RCTA 报警状态（v446k 读）"),
                    p("RCTA_WARNING_RIGHT", CarExtPropertyIds.RCTA_WARNING_RIGHT, "int", "右侧 RCTA 报警状态（v446k 读）"),
                ),
                tips = listOf(
                    "高速变道建议「视觉+声音」。",
                    "RCTA 报警中请在详情页结合后视镜确认后再操作。",
                ),
                capabilities = listOf("变道警示", "RCTA 左右", "侧后方报警"),
            )

            "rear_safety" -> ModuleDetail(
                title = "后侧安全",
                iconRes = R.drawable.ic_collision,
                sdkGroup = "IADAS",
                description = "后向碰撞预警 RCW 与倒车横向来车 RCTA，覆盖倒车、泊车及低速跟驰后侧风险。",
                params = listOf(
                    p("REAR_COLLISION_WARNING", CarExtPropertyIds.REAR_COLLISION_WARNING, "boolean", "后碰撞预警 RCW"),
                    p("REAR_CROSS_TRAFFIC_ALERT", CarExtPropertyIds.REAR_CROSS_TRAFFIC_ALERT, "boolean", "倒车横向来车预警 RCTA"),
                ),
                tips = listOf(
                    "倒车出库建议同时开启 RCW 与 RCTA。",
                    "后侧两项均关闭时首页卡片会标「关注」。",
                ),
                capabilities = listOf("RCW", "RCTA", "倒车横向来车"),
            )

            "fatigue" -> ModuleDetail(
                title = "驾驶员监测",
                iconRes = R.drawable.ic_fatigue_monitor,
                sdkGroup = "IBiometric",
                description = "DMS 疲劳与面部表情识别结果，用于分心/疲劳驾驶风险提示。",
                params = listOf(
                    p("DMS_DRIVER_FATIGUE_STATUS", CarExtPropertyIds.DMS_DRIVER_FATIGUE_STATUS, "int", "疲劳状态：未知/正常/分心/疲劳"),
                    p("DMS_DRIVER_FACIAL_EXPRESSION", CarExtPropertyIds.DMS_DRIVER_FACIAL_EXPRESSION, "int", "面部表情：未知/平静/异常等"),
                ),
                tips = listOf(
                    "疲劳或分心提示出现时应尽快休息或换人驾驶。",
                    "可与限速提醒、驾驶辅助卡片联动查看整体风险。",
                ),
                capabilities = listOf("疲劳识别", "表情监测", "DMS"),
            )

            "door" -> ModuleDetail(
                title = "开门预警",
                iconRes = R.drawable.ic_door,
                sdkGroup = "IADAS",
                description = "车门开启预警 DOW（Door Open Warning），提醒开门时侧后方来车/行人。",
                params = listOf(
                    p("DOOR_OPEN_WARN_ACTIVE", CarExtPropertyIds.DOOR_OPEN_WARN_ACTIVE, "boolean", "DOW 开门预警开关"),
                ),
                tips = listOf(
                    "路边停车开门前务必观察侧后方。",
                    "儿童乘坐场景建议配合乘员安全卡片检查儿童锁。",
                ),
                capabilities = listOf("DOW", "开门预警"),
            )

            "speed_limit" -> ModuleDetail(
                title = "限速提醒",
                iconRes = R.drawable.ic_speed_limit,
                sdkGroup = "IADAS",
                description = "限速警告模式与允许超速偏差，用于判断驾驶是否倾向超速。",
                params = listOf(
                    p("SPEED_LIMIT_WARNING_MODE", CarExtPropertyIds.SPEED_LIMIT_WARNING_MODE, "int", "限速警告模式（车型枚举）"),
                    p("SPEED_LIMIT_WARNING_OFFSET_VALUE", CarExtPropertyIds.SPEED_LIMIT_WARNING_OFFSET_VALUE, "int", "限速偏差 km/h（正负整数）"),
                ),
                tips = listOf(
                    "偏差过大时首页会标黄/标红。",
                    "建议与 DMS、AEB 等模块一并查看。",
                ),
                capabilities = listOf("限速模式", "超速偏差"),
            )

            "rain_safety" -> ModuleDetail(
                title = "雨天车身",
                iconRes = R.drawable.ic_rain,
                sdkGroup = "IBcm",
                description = "雨天自动关窗、倒车联动后雨刮、锁车自动关窗等车身舒适与安全相关状态。",
                params = listOf(
                    p("AUTO_CLOSE_WINDOW_RAINY", CarExtPropertyIds.AUTO_CLOSE_WINDOW_RAINY, "boolean", "下雨自动关窗"),
                    p("AUTO_REAR_WIPING", CarExtPropertyIds.AUTO_REAR_WIPING, "boolean", "倒车且前雨刮开启时自动后雨刮"),
                    p("LOCK_AUTO_CLOSE_WINDOW", CarExtPropertyIds.LOCK_AUTO_CLOSE_WINDOW, "boolean", "锁车自动关窗"),
                ),
                tips = listOf(
                    "雨天建议开启自动关窗，降低进水与视野风险。",
                    "锁车关窗关闭时驻车下雨需手动确认车窗。",
                ),
                capabilities = listOf("雨天关窗", "后雨刮联动", "锁车关窗"),
            )

            "occupant" -> ModuleDetail(
                title = "乘员安全",
                iconRes = R.drawable.ic_child_lock,
                sdkGroup = "IBcm + ISeat",
                description = "儿童安全锁、副驾气囊使能与座椅占位（占位为常见座舱信号，接入时以实车属性为准）。",
                params = listOf(
                    p("CHILD_SAFETY_LOCK", CarExtPropertyIds.CHILD_SAFETY_LOCK, "boolean", "儿童安全锁（info 标记 Deprecated，实车可用原生 ID）"),
                    p("PAB_SWITCH", CarExtPropertyIds.PAB_SWITCH, "boolean", "副驾安全气囊使能"),
                    p("SEAT_OCCUPANCY_STATUS", CarExtPropertyIds.SEAT_OCCUPANCY_STATUS, "int", "座椅占位状态（按座椅 area 分区读取）"),
                ),
                tips = listOf(
                    "儿童乘坐请启用儿童锁并正确使用安全座椅。",
                    "副驾有人且气囊禁用时系统会提示关注。",
                ),
                capabilities = listOf("儿童锁", "副驾气囊", "座椅占位"),
            )

            "light" -> ModuleDetail(
                title = "车外灯光",
                iconRes = R.drawable.ic_light,
                sdkGroup = "ILamp",
                description = "外灯模式、日间行车灯与前后位置灯状态，用于低能见度风险提示。",
                params = listOf(
                    p("LAMP_EXTERIOR_LIGHT_CONTROL", CarExtPropertyIds.LAMP_EXTERIOR_LIGHT_CONTROL, "int", "外灯：OFF/AUTO/近光/远光/自动远光等"),
                    p("LAMP_DAYTIME_LIGHT", CarExtPropertyIds.LAMP_DAYTIME_LIGHT, "boolean", "日间行车灯 DRL"),
                    p("LAMP_FRONT_POSITION", CarExtPropertyIds.LAMP_FRONT_POSITION, "boolean", "前位置灯"),
                    p("LAMP_REAR_POSITION", CarExtPropertyIds.LAMP_REAR_POSITION, "boolean", "后位置灯"),
                ),
                tips = listOf(
                    "隧道/夜间建议使用 AUTO 或近光。",
                    "外灯长期 OFF 且环境昏暗时首页会提示关注。",
                ),
                capabilities = listOf("外灯模式", "DRL", "位置灯"),
            )

            else -> ModuleDetail(
                title = "模块详情",
                iconRes = R.drawable.ic_collision,
                description = "暂无该模块的参数定义，请在 ModuleCatalog 中补充。",
                params = emptyList(),
                tips = listOf("模块 id：$moduleId"),
            )
        }
    }

    private fun p(name: String, id: Int, type: String, meaning: String) =
        Param(name, id, type, meaning)
}
