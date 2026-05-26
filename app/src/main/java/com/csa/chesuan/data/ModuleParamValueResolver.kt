package com.csa.chesuan.data

/**
 * 根据首页卡片 [SafetyModule.status] 为详情页各 property 生成可读的模拟当前值（只读展示）。
 */
object ModuleParamValueResolver {

    enum class ValueTone { OK, WARN, OFF, NEUTRAL }

    data class ResolvedParam(
        val param: ModuleCatalog.Param,
        val displayValue: String,
        val tone: ValueTone,
    )

    fun resolve(module: SafetyModule?, detail: ModuleCatalog.ModuleDetail): List<ResolvedParam> {
        val id = module?.id ?: return detail.params.map { ResolvedParam(it, "—", ValueTone.NEUTRAL) }
        val status = module.status
        return detail.params.map { param ->
            val (value, tone) = resolveOne(id, param, status)
            ResolvedParam(param, value, tone)
        }
    }

    private fun resolveOne(
        moduleId: String,
        param: ModuleCatalog.Param,
        status: String,
    ): Pair<String, ValueTone> {
        return when (moduleId) {
            "adas" -> resolveAdas(param.propertyId, status)
            "blindspot" -> resolveBlindspot(param.propertyId, status)
            "rear_safety" -> resolveRear(param.propertyId, status)
            "fatigue" -> resolveFatigue(param.propertyId, status)
            "speed_limit" -> resolveSpeedLimit(param.propertyId, status)
            "rain_safety" -> resolveRain(param.propertyId, status)
            "door" -> resolveDoor(param.propertyId, status)
            "occupant" -> resolveOccupant(param.propertyId, status)
            "light" -> resolveLight(param.propertyId, status)
            else -> "—" to ValueTone.NEUTRAL
        }
    }

    private fun resolveAdas(propertyId: Int, status: String): Pair<String, ValueTone> = when (propertyId) {
        CarExtPropertyIds.AUTONOMOUS_EMERGENCY_BRAKING ->
            boolTone(status.contains("AEB 开"), "开启", "关闭")
        CarExtPropertyIds.FORWARD_COLLISION_WARN_SNVT -> {
            val level = status.substringAfter("FCW ").substringBefore(" ·").trim()
            when (level) {
                "关闭" -> level to ValueTone.OFF
                "高" -> level to ValueTone.OK
                "低", "中" -> level to ValueTone.NEUTRAL
                else -> level to ValueTone.NEUTRAL
            }
        }
        CarExtPropertyIds.LANE_DEPARTURE_WARNING ->
            boolTone(status.contains("LDW 开"), "开启", "关闭")
        CarExtPropertyIds.LANE_KEEPING_AID ->
            boolTone(status.contains("LKA 开"), "开启", "关闭")
        CarExtPropertyIds.EMERGENCY_LANE_KEEP_AID ->
            boolTone(status.contains("ELK 开"), "开启", "关闭")
        else -> "—" to ValueTone.NEUTRAL
    }

    private fun resolveBlindspot(propertyId: Int, status: String): Pair<String, ValueTone> {
        val mode = status.substringAfter("变道警示 ").substringBefore(" ·").trim()
        val rctaPart = status.substringAfter("RCTA ").trim()
        return when (propertyId) {
            CarExtPropertyIds.LANE_CHANGE_WARNING_MODE -> when (mode) {
                "关闭" -> mode to ValueTone.OFF
                "视觉+声音" -> mode to ValueTone.OK
                else -> mode to ValueTone.NEUTRAL
            }
            CarExtPropertyIds.REAR_CROSS_TRAFFIC_ALERT ->
                boolTone(!rctaPart.contains("无报警"), "功能开启", "无报警")
            CarExtPropertyIds.RCTA_WARNING_LEFT -> when {
                rctaPart.contains("双侧") || rctaPart.contains("左侧") -> "报警中" to ValueTone.WARN
                else -> "无报警" to ValueTone.OK
            }
            CarExtPropertyIds.RCTA_WARNING_RIGHT -> when {
                rctaPart.contains("双侧") || rctaPart.contains("右侧") -> "报警中" to ValueTone.WARN
                else -> "无报警" to ValueTone.OK
            }
            else -> "—" to ValueTone.NEUTRAL
        }
    }

    private fun resolveRear(propertyId: Int, status: String): Pair<String, ValueTone> = when (propertyId) {
        CarExtPropertyIds.REAR_COLLISION_WARNING ->
            boolTone(status.contains("RCW 开"), "开启", "关闭")
        CarExtPropertyIds.REAR_CROSS_TRAFFIC_ALERT ->
            boolTone(status.contains("RCTA 开"), "开启", "关闭")
        else -> "—" to ValueTone.NEUTRAL
    }

    private fun resolveFatigue(propertyId: Int, status: String): Pair<String, ValueTone> {
        val fatiguePart = status.substringBefore("·").trim()
        val exprPart = status.substringAfter("· ").trim()
        return when (propertyId) {
            CarExtPropertyIds.DMS_DRIVER_FATIGUE_STATUS -> when {
                fatiguePart.contains("疲劳") -> "疲劳驾驶" to ValueTone.WARN
                fatiguePart.contains("分心") -> "分心驾驶" to ValueTone.WARN
                fatiguePart.contains("未知") -> "未知" to ValueTone.NEUTRAL
                else -> "正常" to ValueTone.OK
            }
            CarExtPropertyIds.DMS_DRIVER_FACIAL_EXPRESSION -> when {
                exprPart.contains("异常") -> exprPart to ValueTone.WARN
                exprPart.contains("平静") -> exprPart to ValueTone.OK
                else -> exprPart to ValueTone.NEUTRAL
            }
            else -> "—" to ValueTone.NEUTRAL
        }
    }

    private fun resolveSpeedLimit(propertyId: Int, status: String): Pair<String, ValueTone> {
        val mode = Regex("模式\\s*(\\d+)").find(status)?.groupValues?.getOrNull(1) ?: "—"
        val offset = Regex("偏差\\s*([+-]?\\d+)").find(status)?.groupValues?.getOrNull(1)?.toIntOrNull() ?: 0
        return when (propertyId) {
            CarExtPropertyIds.SPEED_LIMIT_WARNING_MODE -> "模式 $mode" to ValueTone.NEUTRAL
            CarExtPropertyIds.SPEED_LIMIT_WARNING_OFFSET_VALUE -> {
                val text = "${if (offset >= 0) "+" else ""}$offset km/h"
                val tone = when {
                    kotlin.math.abs(offset) >= 15 -> ValueTone.WARN
                    kotlin.math.abs(offset) >= 10 -> ValueTone.WARN
                    else -> ValueTone.OK
                }
                text to tone
            }
            else -> "—" to ValueTone.NEUTRAL
        }
    }

    private fun resolveRain(propertyId: Int, status: String): Pair<String, ValueTone> = when (propertyId) {
        CarExtPropertyIds.AUTO_CLOSE_WINDOW_RAINY ->
            boolTone(status.contains("雨天关窗开"), "开启", "关闭")
        CarExtPropertyIds.AUTO_REAR_WIPING ->
            boolTone(status.contains("后雨刮联动开"), "开启", "关闭")
        CarExtPropertyIds.LOCK_AUTO_CLOSE_WINDOW ->
            boolTone(status.contains("锁车关窗开"), "开启", "关闭")
        else -> "—" to ValueTone.NEUTRAL
    }

    private fun resolveDoor(propertyId: Int, status: String): Pair<String, ValueTone> =
        if (propertyId == CarExtPropertyIds.DOOR_OPEN_WARN_ACTIVE) {
            boolTone(status.contains("已开启"), "已开启", "已关闭")
        } else {
            "—" to ValueTone.NEUTRAL
        }

    private fun resolveOccupant(propertyId: Int, status: String): Pair<String, ValueTone> = when (propertyId) {
        CarExtPropertyIds.CHILD_SAFETY_LOCK ->
            boolTone(status.contains("儿童锁开"), "开启", "关闭")
        CarExtPropertyIds.PAB_SWITCH -> when {
            status.contains("副驾气囊启用") -> "启用" to ValueTone.OK
            else -> "禁用" to ValueTone.WARN
        }
        CarExtPropertyIds.SEAT_OCCUPANCY_STATUS -> when {
            status.contains("有人") -> "有人" to ValueTone.NEUTRAL
            else -> "空" to ValueTone.OK
        }
        else -> "—" to ValueTone.NEUTRAL
    }

    private fun resolveLight(propertyId: Int, status: String): Pair<String, ValueTone> {
        val ext = status.substringAfter("外灯 ").substringBefore(" ·").trim()
        return when (propertyId) {
            CarExtPropertyIds.LAMP_EXTERIOR_LIGHT_CONTROL -> when (ext) {
                "OFF" -> ext to ValueTone.OFF
                "AUTO" -> ext to ValueTone.OK
                else -> ext to ValueTone.NEUTRAL
            }
            CarExtPropertyIds.LAMP_DAYTIME_LIGHT ->
                boolTone(status.contains("DRL 开"), "开启", "关闭")
            CarExtPropertyIds.LAMP_FRONT_POSITION ->
                boolTone(status.contains("前位灯 开"), "开启", "关闭")
            CarExtPropertyIds.LAMP_REAR_POSITION ->
                boolTone(status.contains("后位灯 开"), "开启", "关闭")
            else -> "—" to ValueTone.NEUTRAL
        }
    }

    private fun boolTone(on: Boolean, onLabel: String, offLabel: String): Pair<String, ValueTone> =
        if (on) onLabel to ValueTone.OK else offLabel to ValueTone.OFF
}
