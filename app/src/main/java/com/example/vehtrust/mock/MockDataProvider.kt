package com.example.vehtrust.mock

import com.example.vehtrust.R
import com.example.vehtrust.data.SafetyModule
import kotlin.random.Random

object MockDataProvider {

    // 从 info.txt 中提取的常量映射（实际开发中可直接引用这些常量）
    private const val FORWARD_COLLISION_WARN_SNVT = 29184      // 前碰撞预警
    private const val LANE_DEPARTURE_WARNING = 43776          // 车道偏离预警
    private const val LANE_KEEPING_AID = 60928                // 车道保持辅助
    private const val AUTONOMOUS_EMERGENCY_BRAKING = 24320    // 自动紧急制动
    private const val LANE_CHANGE_WARNING_MODE = 61696        // 变道警示（盲区监测）
    private const val RCTA_WARNING_LEFT = 206592              // RCTA左报警
    private const val RCTA_WARNING_RIGHT = 206848             // RCTA右报警
    private const val DMS_DRIVER_FATIGUE_STATUS = 93952       // 驾驶员疲劳状态
    // 胎压：在 info.txt 中未检索到明确 propertyId（当前仍用模拟数据展示）
    // private const val TIRE_PRESSURE = ...
    private const val DOOR_OPEN_WARN_ACTIVE = 29696           // 车门开启提醒
    private const val LAMP_EXTERIOR_LIGHT_CONTROL = 39680     // 外灯控制
    private const val LAMP_DAYTIME_LIGHT = 101376             // 日间行车灯状态
    private const val LAMP_FRONT_POSITION = 100864            // 前位置灯状态
    private const val LAMP_REAR_POSITION = 101120             // 后位置灯状态

    // 新增：限速提醒/雨天安全/乘员安全（只读展示）
    private const val SPEED_LIMIT_WARNING_MODE = 115456       // 限速提醒模式
    private const val SPEED_LIMIT_WARNING_OFFSET_VALUE = 122112 // 限速偏差值
    private const val AUTO_CLOSE_WINDOW_RAINY = 23296         // 下雨自动关窗
    private const val AUTO_REAR_WIPING = 122880               // 倒车自动后雨刮
    private const val LOCK_AUTO_CLOSE_WINDOW = 23040          // 锁车自动关窗
    private const val CHILD_SAFETY_LOCK = 35328               // 儿童锁（Deprecated）
    private const val PAB_SWITCH = 143872                     // 副驾安全气囊使能

    fun generateModules(): List<SafetyModule> {
        val modules = listOf(
            // 事故溯源（事故责任界定/系统故障溯源入口）
            SafetyModule(
                id = "trace",
                title = "事故溯源",
                iconRes = R.drawable.ic_collision,
                status = "碰撞/故障一键取证",
                riskLevel = 0,
                riskReason = "用于取证与追溯",
                colorRes = R.color.warning_red,
                propertyId = 0,
                valueType = "action"
            ),

            // 驾驶辅助（包含车道保持和自动刹车）
            SafetyModule(
                id = "adas",
                title = "驾驶辅助",
                iconRes = R.drawable.ic_auto_brake,
                status = buildAdasStatus(),
                riskLevel = 0,
                riskReason = "",
                colorRes = R.color.module_blue,
                propertyId = AUTONOMOUS_EMERGENCY_BRAKING,
                valueType = "boolean"
            ),

            // 盲区监测（关联 LANE_CHANGE_WARNING_MODE 和 RCTA_*）
            SafetyModule(
                id = "blindspot",
                title = "盲区监测",
                iconRes = R.drawable.ic_blind_spot,
                status = buildBlindSpotStatus(),
                riskLevel = 0,
                riskReason = "",
                colorRes = R.color.module_green,
                propertyId = LANE_CHANGE_WARNING_MODE,
                valueType = "int"  // 因为 LANE_CHANGE_WARNING_MODE 有多个模式值
            ),

            // 疲劳监测（关联 DMS_DRIVER_FATIGUE_STATUS）
            SafetyModule(
                id = "fatigue",
                title = "疲劳监测",
                iconRes = R.drawable.ic_fatigue_monitor,
                status = buildFatigueStatus(),
                riskLevel = 0,
                riskReason = "",
                colorRes = R.color.module_orange,
                propertyId = DMS_DRIVER_FATIGUE_STATUS,
                valueType = "int"  // 疲劳状态有多个枚举值
            ),

            // 碰撞预警（关联 FORWARD_COLLISION_WARN_SNVT）
            SafetyModule(
                id = "collision",
                title = "碰撞预警",
                iconRes = R.drawable.ic_collision,
                status = if (Random.nextBoolean()) "前向预警 开" else "前向预警 关",
                riskLevel = 0,
                riskReason = "",
                colorRes = R.color.module_purple,
                propertyId = FORWARD_COLLISION_WARN_SNVT,
                valueType = "int"  // 有 OFF/LOW/NORMAL/HIGH 等级
            ),

            // 车道偏离（关联 LANE_DEPARTURE_WARNING）
            SafetyModule(
                id = "lane",
                title = "车道偏离",
                iconRes = R.drawable.ic_lane_departure,
                status = if (Random.nextBoolean()) "偏离预警 开" else "偏离预警 关",
                riskLevel = 0,
                riskReason = "",
                colorRes = R.color.module_teal,
                propertyId = LANE_DEPARTURE_WARNING,
                valueType = "boolean"
            ),

            // 车道保持（关联 LANE_KEEPING_AID）
            SafetyModule(
                id = "lane_keep",
                title = "车道保持",
                iconRes = R.drawable.ic_lane_keep,
                status = if (Random.nextBoolean()) "辅助开启" else "辅助关闭",
                riskLevel = 0,
                riskReason = "",
                colorRes = R.color.module_pink,
                propertyId = LANE_KEEPING_AID,
                valueType = "boolean"
            ),

            // 胎压监测（关联 Tire Pressure 相关）
            SafetyModule(
                id = "tpms",
                title = "胎压监测",
                iconRes = R.drawable.ic_tpms,
                status = buildTirePressureStatus(),
                riskLevel = 0,
                riskReason = "",
                colorRes = R.color.module_blue_light,
                propertyId = 0,  // 需要实际的胎压属性ID
                valueType = "float[]"
            ),

            // 车门状态（关联 DOOR_OPEN_WARN_ACTIVE 和相关车门状态）
            SafetyModule(
                id = "door",
                title = "车门状态",
                iconRes = R.drawable.ic_door,
                status = buildDoorStatus(),
                riskLevel = 0,
                riskReason = "",
                colorRes = R.color.module_yellow,
                propertyId = DOOR_OPEN_WARN_ACTIVE,
                valueType = "int[]"  // 多个车门状态
            ),

            // 灯光控制（关联 LAMP_EXTERIOR_LIGHT_CONTROL）
            SafetyModule(
                id = "light",
                title = "灯光控制",
                iconRes = R.drawable.ic_light,
                status = buildLightStatus(),
                riskLevel = 0,
                riskReason = "",
                colorRes = R.color.module_purple,
                propertyId = LAMP_EXTERIOR_LIGHT_CONTROL,
                valueType = "int"
            ),

            // 限速提醒（展示模式与偏差值）
            SafetyModule(
                id = "speed_limit",
                title = "限速提醒",
                iconRes = R.drawable.ic_speed_limit,
                status = buildSpeedLimitStatus(),
                riskLevel = 0,
                riskReason = "",
                colorRes = R.color.module_teal,
                propertyId = SPEED_LIMIT_WARNING_MODE,
                valueType = "int"
            ),

            // 雨天安全（关窗/雨刮等）
            SafetyModule(
                id = "rain_safety",
                title = "雨天安全",
                iconRes = R.drawable.ic_rain,
                status = buildRainSafetyStatus(),
                riskLevel = 0,
                riskReason = "",
                colorRes = R.color.module_blue_light,
                propertyId = AUTO_CLOSE_WINDOW_RAINY,
                valueType = "boolean"
            ),

            // 乘员安全（儿童锁/副驾气囊）
            SafetyModule(
                id = "child_safety",
                title = "乘员安全",
                iconRes = R.drawable.ic_child_lock,
                status = buildOccupantSafetyStatus(),
                riskLevel = 0,
                riskReason = "",
                colorRes = R.color.module_orange,
                propertyId = CHILD_SAFETY_LOCK,
                valueType = "boolean"
            )
        )

        // 基于“只读状态”计算风险标签（不做控制）
        return modules.map { it.withRiskComputed() }
    }

    // 辅助方法：构建复合状态字符串
    private fun buildAdasStatus(): String {
        val laneKeep = if (Random.nextBoolean()) "车道保持开" else "车道保持关"
        val autoBrake = if (Random.nextBoolean()) "自动刹车开" else "自动刹车关"
        return "$laneKeep · $autoBrake"
    }

    private fun buildBlindSpotStatus(): String {
        val left = if (Random.nextBoolean()) "左盲区有车" else ""
        val right = if (Random.nextBoolean()) "右盲区有车" else ""
        return when {
            left.isNotEmpty() && right.isNotEmpty() -> "两侧有车"
            left.isNotEmpty() -> left
            right.isNotEmpty() -> right
            else -> "无盲区车辆"
        }
    }

    private fun buildFatigueStatus(): String {
        return when (Random.nextInt(1, 5)) {
            1 -> "状态正常"
            2 -> "分心驾驶"
            3 -> "疲劳驾驶"
            4 -> "未知状态"
            else -> "正常"
        }
    }

    private fun buildTirePressureStatus(): String {
        val pressures = List(4) { 2.2f + Random.nextFloat() * 0.6f }
        return String.format("左前 %.1f · 右前 %.1f\n左后 %.1f · 右后 %.1f",
            pressures[0], pressures[1], pressures[2], pressures[3])
    }

    private fun buildDoorStatus(): String {
        val doors = listOf("主驾", "副驾", "左后", "右后")
        val openDoors = doors.filter { Random.nextBoolean() }
        return if (openDoors.isEmpty()) "全部关闭" else "${openDoors.joinToString()} 开启"
    }

    private fun buildLightStatus(): String {
        val control = when (Random.nextInt(0, 5)) {
            0 -> "OFF"
            1 -> "AUTO"
            2 -> "近光"
            3 -> "远光"
            else -> "自动远光"
        }
        val drl = Random.nextBoolean()
        val frontPos = Random.nextBoolean()
        val rearPos = Random.nextBoolean()
        return "外灯:$control · DRL:${if (drl) "开" else "关"} · 前位:${if (frontPos) "开" else "关"} · 后位:${if (rearPos) "开" else "关"}"
    }

    private fun buildSpeedLimitStatus(): String {
        // 这里不假设具体枚举语义，仅给出“模式值 + 偏差值”用于展示/判断
        val mode = Random.nextInt(0, 4)
        val offset = listOf(-10, -5, 0, 5, 10, 15).random()
        return "模式 $mode · 偏差 ${if (offset >= 0) "+$offset" else "$offset"}"
    }

    private fun buildRainSafetyStatus(): String {
        val autoClose = Random.nextBoolean()
        val rearWipe = Random.nextBoolean()
        val lockClose = Random.nextBoolean()
        val tags = mutableListOf<String>()
        tags += if (autoClose) "雨天关窗开" else "雨天关窗关"
        tags += if (rearWipe) "后雨刮开" else "后雨刮关"
        tags += if (lockClose) "锁车关窗开" else "锁车关窗关"
        return tags.joinToString(" · ")
    }

    private fun buildOccupantSafetyStatus(): String {
        val childLock = Random.nextBoolean()
        val airbagEnabled = Random.nextBoolean()
        val a = if (childLock) "儿童锁开" else "儿童锁关"
        val b = if (airbagEnabled) "副驾气囊启用" else "副驾气囊禁用"
        return "$a · $b"
    }

    private fun SafetyModule.withRiskComputed(): SafetyModule {
        if (id == "trace") return this

        fun normal() = copy(riskLevel = 0, riskReason = "状态正常")
        fun attention(reason: String) = copy(riskLevel = 1, riskReason = reason)
        fun high(reason: String) = copy(riskLevel = 2, riskReason = reason)

        return when (id) {
            "fatigue" -> when {
                status.contains("疲劳") -> high("检测到疲劳驾驶")
                status.contains("分心") -> attention("检测到分心驾驶")
                status.contains("未知") -> attention("状态未知，建议关注")
                else -> normal()
            }

            "door" -> when {
                status.contains("开启") && !status.contains("全部关闭") -> attention("存在车门未关")
                else -> normal()
            }

            "collision" -> when {
                status.contains("关") -> attention("碰撞预警可能处于关闭状态")
                else -> normal()
            }

            "lane" -> when {
                status.contains("关") -> attention("车道偏离预警关闭")
                else -> normal()
            }

            "lane_keep" -> when {
                status.contains("关闭") -> attention("车道保持辅助关闭")
                else -> normal()
            }

            "speed_limit" -> run {
                // "模式 X · 偏差 +10"
                val offset = Regex("偏差\\s*([+-]?\\d+)").find(status)?.groupValues?.getOrNull(1)?.toIntOrNull() ?: 0
                when {
                    kotlin.math.abs(offset) >= 15 -> high("限速偏差过大：$offset")
                    kotlin.math.abs(offset) >= 10 -> attention("限速偏差偏大：$offset")
                    else -> normal()
                }
            }

            "rain_safety" -> run {
                val problems = mutableListOf<String>()
                if (status.contains("雨天关窗关")) problems += "雨天自动关窗关闭"
                if (status.contains("锁车关窗关")) problems += "锁车自动关窗关闭"
                if (problems.isEmpty()) normal() else attention(problems.joinToString("、"))
            }

            "child_safety" -> run {
                if (status.contains("儿童锁关")) attention("儿童锁未开启") else normal()
            }

            else -> normal()
        }
    }
}