package com.csa.chesuan.data

/**
 * 从 [EcarX-CarExt-SDK/info.txt] 摘取、与首页安全卡片相关的 Vehicle Property ID。
 * 实车接入时与 `ecarx.carext.vehicle.module.*` 常量一致，便于 CarProperty 订阅。
 */
object CarExtPropertyIds {

    // ── IADAS ─────────────────────────────────────────────────────────
    const val AUTONOMOUS_EMERGENCY_BRAKING = 24320
    const val FORWARD_COLLISION_WARN_SNVT = 29184
    const val LANE_DEPARTURE_WARNING = 43776
    const val LANE_KEEPING_AID = 60928
    const val LANE_KEEPING_AID_WARNING = 61184
    const val EMERGENCY_LANE_KEEP_AID = 115200
    const val LANE_CHANGE_WARNING_MODE = 61696
    const val REAR_CROSS_TRAFFIC_ALERT = 61440
    const val REAR_COLLISION_WARNING = 30208
    const val SPEED_LIMIT_WARNING_MODE = 115456
    const val SPEED_LIMIT_WARNING_OFFSET_VALUE = 122112
    const val DOOR_OPEN_WARN_ACTIVE = 29696

    // v446k 倒车侧向报警状态（读）
    const val RCTA_WARNING_LEFT = 206592
    const val RCTA_WARNING_RIGHT = 206848

    // ── IBcm ──────────────────────────────────────────────────────────
    const val AUTO_CLOSE_WINDOW_RAINY = 23296
    const val LOCK_AUTO_CLOSE_WINDOW = 23040
    const val AUTO_REAR_WIPING = 122880
    const val CHILD_SAFETY_LOCK = 35328

    // ── ILamp ─────────────────────────────────────────────────────────
    const val LAMP_EXTERIOR_LIGHT_CONTROL = 39680
    const val LAMP_DAYTIME_LIGHT = 101376
    const val LAMP_FRONT_POSITION = 100864
    const val LAMP_REAR_POSITION = 101120

    // ── IBiometric / ISafety ──────────────────────────────────────────
    const val DMS_DRIVER_FATIGUE_STATUS = 93952
    const val DMS_DRIVER_FACIAL_EXPRESSION = 94208
    const val PAB_SWITCH = 143872

    // ── ISeat ─────────────────────────────────────────────────────────
    const val SEAT_OCCUPANCY_STATUS = 126464

    fun hex(id: Int): String = if (id == 0) "—" else "0x${id.toString(16).uppercase()}"
}
