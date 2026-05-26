package com.example.vehtrust.mock

import com.example.vehtrust.R
import com.example.vehtrust.data.CarExtPropertyIds
import com.example.vehtrust.data.ModuleMetric
import com.example.vehtrust.data.SafetyModule
import kotlin.random.Random

/**
 * 首页安全模块 Mock 数据：字段与 [com.example.vehtrust.data.CarExtPropertyIds]、
 * [com.example.vehtrust.data.ModuleCatalog] 对齐，仅展示/风险判断，不做车机控制。
 */
object MockDataProvider {

    fun generateModules(): List<SafetyModule> {
        val modules = listOf(
            traceModule(),
            adasModule(),
            blindspotModule(),
            rearSafetyModule(),
            fatigueModule(),
            speedLimitModule(),
            rainSafetyModule(),
            doorWarnModule(),
            lightModule(),
            occupantModule(),
        )
        return modules.map { it.withRiskComputed().withRichCardMeta() }
    }

    private fun traceModule() = SafetyModule(
        id = "trace",
        title = "事故溯源",
        iconRes = R.drawable.ic_collision,
        status = "20Hz 监控运行中 · 触发后自动冻结前后 10 秒",
        colorRes = R.color.trace_primary,
        propertyId = 0,
        valueType = "action",
        subtitle = "EDR 取证 · 责任界定 · AI 研判 · 区块链存证",
        metrics = listOf(
            ModuleMetric("已存证", "—"),
            ModuleMetric("采样率", "20Hz"),
            ModuleMetric("最近", "—"),
        ),
        highlights = listOf("前后10s回放", "责任占比", "AI报告", "严重度", "上链存证"),
        isFeatured = true,
        sdkGroup = "EDR",
        extractableParamCount = 0,
    )

    /** 合并原「碰撞预警 / 车道偏离 / 车道保持」为统一 ADAS 卡片 */
    private fun adasModule(): SafetyModule {
        val aeb = Random.nextBoolean()
        val fcwLevel = Random.nextInt(0, 4) // 0关 1低 2中 3高
        val ldw = Random.nextBoolean()
        val lka = Random.nextBoolean()
        val elk = Random.nextBoolean()
        val fcwLabel = when (fcwLevel) {
            0 -> "关闭"
            1 -> "低"
            2 -> "中"
            else -> "高"
        }
        return SafetyModule(
            id = "adas",
            title = "驾驶辅助",
            iconRes = R.drawable.ic_auto_brake,
            status = buildString {
                append(if (aeb) "AEB 开" else "AEB 关")
                append(" · FCW ").append(fcwLabel)
                append(" · LDW ").append(if (ldw) "开" else "关")
                append(" · LKA ").append(if (lka) "开" else "关")
                if (elk) append(" · ELK 开")
            },
            colorRes = R.color.module_blue,
            propertyId = CarExtPropertyIds.AUTONOMOUS_EMERGENCY_BRAKING,
            valueType = "mixed",
            sdkGroup = "IADAS",
            extractableParamCount = 5,
        )
    }

    private fun blindspotModule(): SafetyModule {
        val mode = Random.nextInt(0, 4)
        val modeLabel = when (mode) {
            0 -> "关闭"
            1 -> "视觉"
            2 -> "声音"
            3 -> "视觉+声音"
            else -> "—"
        }
        val leftAlert = Random.nextBoolean()
        val rightAlert = Random.nextBoolean()
        return SafetyModule(
            id = "blindspot",
            title = "侧向与变道",
            iconRes = R.drawable.ic_blind_spot,
            status = buildString {
                append("变道警示 ").append(modeLabel)
                append(" · RCTA ")
                append(
                    when {
                        leftAlert && rightAlert -> "双侧报警"
                        leftAlert -> "左侧报警"
                        rightAlert -> "右侧报警"
                        else -> "无报警"
                    },
                )
            },
            colorRes = R.color.module_green,
            propertyId = CarExtPropertyIds.LANE_CHANGE_WARNING_MODE,
            valueType = "int",
            sdkGroup = "IADAS",
            extractableParamCount = 4,
        )
    }

    private fun rearSafetyModule(): SafetyModule {
        val rcw = Random.nextBoolean()
        val rcta = Random.nextBoolean()
        return SafetyModule(
            id = "rear_safety",
            title = "后侧安全",
            iconRes = R.drawable.ic_collision,
            status = buildString {
                append("RCW ").append(if (rcw) "开" else "关")
                append(" · RCTA ").append(if (rcta) "开" else "关")
            },
            colorRes = R.color.module_teal,
            propertyId = CarExtPropertyIds.REAR_COLLISION_WARNING,
            valueType = "boolean",
            sdkGroup = "IADAS",
            extractableParamCount = 2,
        )
    }

    private fun fatigueModule(): SafetyModule {
        val fatigue = Random.nextInt(1, 5)
        val expression = Random.nextInt(1, 4)
        val statusText = when (fatigue) {
            2 -> "状态正常"
            3 -> "分心驾驶"
            4 -> "疲劳驾驶"
            else -> "未知状态"
        }
        val exprText = when (expression) {
            1 -> "表情未知"
            2 -> "表情平静"
            else -> "表情异常"
        }
        return SafetyModule(
            id = "fatigue",
            title = "驾驶员监测",
            iconRes = R.drawable.ic_fatigue_monitor,
            status = "$statusText · $exprText",
            colorRes = R.color.module_orange,
            propertyId = CarExtPropertyIds.DMS_DRIVER_FATIGUE_STATUS,
            valueType = "int",
            sdkGroup = "IBiometric",
            extractableParamCount = 2,
        )
    }

    private fun speedLimitModule(): SafetyModule {
        val mode = Random.nextInt(0, 4)
        val offset = listOf(-10, -5, 0, 5, 10, 15).random()
        return SafetyModule(
            id = "speed_limit",
            title = "限速提醒",
            iconRes = R.drawable.ic_speed_limit,
            status = "模式 $mode · 偏差 ${if (offset >= 0) "+$offset" else "$offset"} km/h",
            colorRes = R.color.module_teal,
            propertyId = CarExtPropertyIds.SPEED_LIMIT_WARNING_MODE,
            valueType = "int",
            sdkGroup = "IADAS",
            extractableParamCount = 2,
        )
    }

    private fun rainSafetyModule(): SafetyModule {
        val rainy = Random.nextBoolean()
        val rearWipe = Random.nextBoolean()
        val lockClose = Random.nextBoolean()
        return SafetyModule(
            id = "rain_safety",
            title = "雨天车身",
            iconRes = R.drawable.ic_rain,
            status = buildString {
                append(if (rainy) "雨天关窗开" else "雨天关窗关")
                append(" · ")
                append(if (rearWipe) "后雨刮联动开" else "后雨刮联动关")
                append(" · ")
                append(if (lockClose) "锁车关窗开" else "锁车关窗关")
            },
            colorRes = R.color.module_blue_light,
            propertyId = CarExtPropertyIds.AUTO_CLOSE_WINDOW_RAINY,
            valueType = "boolean",
            sdkGroup = "IBcm",
            extractableParamCount = 3,
        )
    }

    private fun doorWarnModule(): SafetyModule {
        val dow = Random.nextBoolean()
        return SafetyModule(
            id = "door",
            title = "开门预警",
            iconRes = R.drawable.ic_door,
            status = if (dow) "DOW 开门预警 已开启" else "DOW 开门预警 已关闭",
            colorRes = R.color.module_yellow,
            propertyId = CarExtPropertyIds.DOOR_OPEN_WARN_ACTIVE,
            valueType = "boolean",
            sdkGroup = "IADAS",
            extractableParamCount = 1,
        )
    }

    private fun lightModule(): SafetyModule {
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
        return SafetyModule(
            id = "light",
            title = "车外灯光",
            iconRes = R.drawable.ic_light,
            status = buildString {
                append("外灯 ").append(control)
                append(" · DRL ").append(if (drl) "开" else "关")
                append(" · 前位灯 ").append(if (frontPos) "开" else "关")
                append(" · 后位灯 ").append(if (rearPos) "开" else "关")
            },
            colorRes = R.color.module_purple,
            propertyId = CarExtPropertyIds.LAMP_EXTERIOR_LIGHT_CONTROL,
            valueType = "int",
            sdkGroup = "ILamp",
            extractableParamCount = 4,
        )
    }

    private fun occupantModule(): SafetyModule {
        val childLock = Random.nextBoolean()
        val airbag = Random.nextBoolean()
        val occupied = Random.nextBoolean()
        return SafetyModule(
            id = "occupant",
            title = "乘员安全",
            iconRes = R.drawable.ic_child_lock,
            status = buildString {
                append(if (childLock) "儿童锁开" else "儿童锁关")
                append(" · 副驾气囊").append(if (airbag) "启用" else "禁用")
                append(" · 副驾占位").append(if (occupied) "有人" else "空")
            },
            colorRes = R.color.module_orange,
            propertyId = CarExtPropertyIds.CHILD_SAFETY_LOCK,
            valueType = "boolean",
            sdkGroup = "IBcm+ISeat",
            extractableParamCount = 3,
        )
    }

    private fun SafetyModule.withRichCardMeta(): SafetyModule {
        if (id == "trace") return this
        val groupHint = if (sdkGroup.isNotBlank()) "$sdkGroup · ${extractableParamCount}项可提取" else ""
        return when (id) {
            "adas" -> {
                val aeb = if (status.contains("AEB 开")) "开启" else "关闭"
                val fcw = status.substringAfter("FCW ").substringBefore(" ·").trim()
                val ldw = if (status.contains("LDW 开")) "开启" else "关闭"
                copy(
                    subtitle = groupHint.ifBlank { "AEB · FCW · LDW · LKA · ELK" },
                    metrics = listOf(
                        ModuleMetric("AEB", aeb),
                        ModuleMetric("FCW", fcw),
                        ModuleMetric("LDW", ldw),
                    ),
                )
            }

            "blindspot" -> {
                val mode = status.substringAfter("变道警示 ").substringBefore(" ·").trim()
                val rcta = status.substringAfter("RCTA ").trim()
                copy(
                    subtitle = groupHint.ifBlank { "变道警示 · RCTA 左右侧" },
                    metrics = listOf(
                        ModuleMetric("变道", mode),
                        ModuleMetric("RCTA", rcta),
                        ModuleMetric("属性", CarExtPropertyIds.hex(propertyId)),
                    ),
                )
            }

            "rear_safety" -> {
                val rcw = if (status.contains("RCW 开")) "开启" else "关闭"
                val rcta = if (status.contains("RCTA 开")) "开启" else "关闭"
                copy(
                    subtitle = groupHint.ifBlank { "后碰撞预警 · 倒车横向来车" },
                    metrics = listOf(
                        ModuleMetric("RCW", rcw),
                        ModuleMetric("RCTA", rcta),
                        ModuleMetric("ID", CarExtPropertyIds.hex(CarExtPropertyIds.REAR_COLLISION_WARNING)),
                    ),
                )
            }

            "fatigue" -> {
                val level = when {
                    status.contains("疲劳") -> "疲劳"
                    status.contains("分心") -> "分心"
                    status.contains("未知") -> "未知"
                    else -> "正常"
                }
                copy(
                    subtitle = groupHint.ifBlank { "DMS 疲劳 · 面部表情" },
                    metrics = listOf(
                        ModuleMetric("疲劳", level),
                        ModuleMetric("表情", status.substringAfter("· ").trim()),
                        ModuleMetric("ID", CarExtPropertyIds.hex(propertyId)),
                    ),
                )
            }

            "speed_limit" -> {
                val mode = Regex("模式\\s*(\\d+)").find(status)?.groupValues?.getOrNull(1) ?: "—"
                val offset = Regex("偏差\\s*([+-]?\\d+)").find(status)?.groupValues?.getOrNull(1) ?: "0"
                copy(
                    subtitle = groupHint.ifBlank { "限速模式 · 超速偏差" },
                    metrics = listOf(
                        ModuleMetric("模式", mode),
                        ModuleMetric("偏差", "${offset}km/h"),
                        ModuleMetric("偏移ID", CarExtPropertyIds.hex(CarExtPropertyIds.SPEED_LIMIT_WARNING_OFFSET_VALUE)),
                    ),
                )
            }

            "rain_safety" -> copy(
                subtitle = groupHint.ifBlank { "雨天关窗 · 后雨刮 · 锁车关窗" },
                metrics = listOf(
                    ModuleMetric("雨天关窗", if (status.contains("雨天关窗开")) "开" else "关"),
                    ModuleMetric("后雨刮", if (status.contains("后雨刮联动开")) "开" else "关"),
                    ModuleMetric("锁车关窗", if (status.contains("锁车关窗开")) "开" else "关"),
                ),
            )

            "door" -> copy(
                subtitle = groupHint.ifBlank { "DOW 车门开启预警" },
                metrics = listOf(
                    ModuleMetric("DOW", if (status.contains("已开启")) "开启" else "关闭"),
                    ModuleMetric("属性ID", CarExtPropertyIds.hex(propertyId)),
                    ModuleMetric("分区", "GLOBAL"),
                ),
            )

            "light" -> {
                val ext = status.substringAfter("外灯 ").substringBefore(" ·").trim()
                copy(
                    subtitle = groupHint.ifBlank { "外灯模式 · DRL · 位置灯" },
                    metrics = listOf(
                        ModuleMetric("外灯", ext),
                        ModuleMetric("DRL", if (status.contains("DRL 开")) "开" else "关"),
                        ModuleMetric("后位灯", if (status.contains("后位灯 开")) "开" else "关"),
                    ),
                )
            }

            "occupant" -> copy(
                subtitle = groupHint.ifBlank { "儿童锁 · 副驾气囊 · 座椅占位" },
                metrics = listOf(
                    ModuleMetric("儿童锁", if (status.contains("儿童锁开")) "开" else "关"),
                    ModuleMetric("副驾气囊", if (status.contains("启用")) "启用" else "禁用"),
                    ModuleMetric("副驾占位", if (status.contains("有人")) "有人" else "空"),
                ),
            )

            else -> copy(subtitle = groupHint)
        }
    }

    private fun SafetyModule.withRiskComputed(): SafetyModule {
        if (id == "trace") return this

        fun normal() = copy(riskLevel = 0, riskReason = "")
        fun attention(reason: String) = copy(riskLevel = 1, riskReason = reason)
        fun high(reason: String) = copy(riskLevel = 2, riskReason = reason)

        return when (id) {
            "adas" -> when {
                status.contains("AEB 关") -> attention("自动紧急制动(AEB)未开启")
                status.contains("FCW 关闭") -> attention("前向碰撞预警关闭")
                status.contains("LDW 关") -> attention("车道偏离预警关闭")
                status.contains("LKA 关") -> attention("车道保持辅助关闭")
                else -> normal()
            }

            "blindspot" -> when {
                status.contains("变道警示 关闭") -> attention("变道警示已关闭")
                status.contains("双侧报警") || status.contains("左侧报警") || status.contains("右侧报警")
                -> attention("侧后方来车报警中")
                else -> normal()
            }

            "rear_safety" -> when {
                status.contains("RCW 关") && status.contains("RCTA 关") ->
                    attention("后侧碰撞与横向来车预警均未开启")
                status.contains("RCW 关") -> attention("后碰撞预警(RCW)关闭")
                else -> normal()
            }

            "fatigue" -> when {
                status.contains("疲劳") -> high("DMS 检测到疲劳驾驶")
                status.contains("分心") -> attention("DMS 检测到分心驾驶")
                status.contains("未知") -> attention("驾驶员状态未知")
                else -> normal()
            }

            "speed_limit" -> {
                val offset = Regex("偏差\\s*([+-]?\\d+)").find(status)?.groupValues?.getOrNull(1)?.toIntOrNull() ?: 0
                when {
                    kotlin.math.abs(offset) >= 15 -> high("限速偏差过大：${offset}km/h")
                    kotlin.math.abs(offset) >= 10 -> attention("限速偏差偏大：${offset}km/h")
                    else -> normal()
                }
            }

            "rain_safety" -> {
                val issues = mutableListOf<String>()
                if (status.contains("雨天关窗关")) issues += "雨天自动关窗关闭"
                if (status.contains("锁车关窗关")) issues += "锁车自动关窗关闭"
                if (issues.isEmpty()) normal() else attention(issues.joinToString("、"))
            }

            "door" -> when {
                status.contains("已关闭") -> attention("开门预警(DOW)未开启")
                else -> normal()
            }

            "light" -> when {
                status.startsWith("外灯 OFF") -> attention("外灯处于关闭状态")
                else -> normal()
            }

            "occupant" -> when {
                status.contains("儿童锁关") -> attention("儿童锁未开启")
                status.contains("副驾气囊禁用") && status.contains("有人") ->
                    attention("副驾有人但气囊禁用，请确认配置")
                else -> normal()
            }

            else -> normal()
        }
    }
}
