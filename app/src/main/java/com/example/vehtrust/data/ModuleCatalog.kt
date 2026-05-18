package com.example.vehtrust.data

import com.example.vehtrust.R

/**
 * 模块详情页需要展示的“只读参数”清单。
 * 说明：这里只做展示/判断（不做控制），propertyId 对应 info.txt 中的 VALUE。
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
        val description: String,
        val params: List<Param>,
        val tips: List<String>,
    )

    // ====== info.txt 里查到的 propertyId（摘取核心用于展示） ======
    private const val AUTONOMOUS_EMERGENCY_BRAKING = 24320
    private const val LANE_DEPARTURE_WARNING = 43776
    private const val LANE_KEEPING_AID = 60928
    private const val LANE_KEEPING_AID_WARNING = 61184
    private const val FORWARD_COLLISION_WARN_SNVT = 29184
    private const val LANE_CHANGE_WARNING_MODE = 61696
    private const val DOOR_OPEN_WARN_ACTIVE = 29696
    private const val DMS_DRIVER_FATIGUE_STATUS = 93952

    private const val SPEED_LIMIT_WARNING_MODE = 115456
    private const val SPEED_LIMIT_WARNING_OFFSET_VALUE = 122112

    private const val AUTO_CLOSE_WINDOW_RAINY = 23296
    private const val AUTO_REAR_WIPING = 122880
    private const val LOCK_AUTO_CLOSE_WINDOW = 23040

    private const val CHILD_SAFETY_LOCK = 35328
    private const val PAB_SWITCH = 143872

    private const val LAMP_EXTERIOR_LIGHT_CONTROL = 39680
    private const val LAMP_DAYTIME_LIGHT = 101376
    private const val LAMP_FRONT_POSITION = 100864
    private const val LAMP_REAR_POSITION = 101120

    fun detailFor(moduleId: String): ModuleDetail {
        return when (moduleId) {
            "adas" -> ModuleDetail(
                title = "驾驶辅助",
                iconRes = R.drawable.ic_auto_brake,
                description = "展示 AEB/车道辅助等关键开关与状态，仅用于展示与风险提示。",
                params = listOf(
                    Param(
                        name = "AUTONOMOUS_EMERGENCY_BRAKING",
                        propertyId = AUTONOMOUS_EMERGENCY_BRAKING,
                        valueType = "boolean",
                        meaning = "自动紧急制动(AEB)功能开关"
                    ),
                    Param(
                        name = "LANE_DEPARTURE_WARNING",
                        propertyId = LANE_DEPARTURE_WARNING,
                        valueType = "boolean",
                        meaning = "车道偏离预警开关"
                    ),
                    Param(
                        name = "LANE_KEEPING_AID",
                        propertyId = LANE_KEEPING_AID,
                        valueType = "boolean",
                        meaning = "车道保持辅助开关"
                    ),
                    Param(
                        name = "LANE_KEEPING_AID_WARNING",
                        propertyId = LANE_KEEPING_AID_WARNING,
                        valueType = "int",
                        meaning = "车道保持辅助警告类别/模式（枚举定义见 IADAS.LaneKeepingAidWarningMode）"
                    ),
                ),
                tips = listOf(
                    "辅助系统为风险缓解手段，不能替代驾驶员注意力。",
                    "若多项辅助长期关闭，可在“风险总览”中提示关注。"
                )
            )

            "collision" -> ModuleDetail(
                title = "碰撞预警",
                iconRes = R.drawable.ic_collision,
                description = "展示前向碰撞预警灵敏度设置/状态，用于判断风险偏好。",
                params = listOf(
                    Param(
                        name = "FORWARD_COLLISION_WARN_SNVT",
                        propertyId = FORWARD_COLLISION_WARN_SNVT,
                        valueType = "int",
                        meaning = "前碰撞预警灵敏等级（关闭/低/中/高）"
                    )
                ),
                tips = listOf(
                    "城市拥堵建议更高灵敏度，降低追尾风险。",
                    "误报增多时可调低，但不建议长期关闭。"
                )
            )

            "blindspot" -> ModuleDetail(
                title = "盲区监测",
                iconRes = R.drawable.ic_blind_spot,
                description = "展示变道警示模式，用于提醒当前提示方式（视觉/声音）。",
                params = listOf(
                    Param(
                        name = "LANE_CHANGE_WARNING_MODE",
                        propertyId = LANE_CHANGE_WARNING_MODE,
                        valueType = "int",
                        meaning = "变道警示模式（关闭/视觉/声音/视觉+声音）"
                    )
                ),
                tips = listOf(
                    "高速建议开启“视觉+声音”。",
                    "雨雾夜间建议更强提示策略。"
                )
            )

            "fatigue" -> ModuleDetail(
                title = "疲劳监测",
                iconRes = R.drawable.ic_fatigue_monitor,
                description = "展示 DMS 疲劳/分心识别结果，用于风险提示。",
                params = listOf(
                    Param(
                        name = "DMS_DRIVER_FATIGUE_STATUS",
                        propertyId = DMS_DRIVER_FATIGUE_STATUS,
                        valueType = "int",
                        meaning = "驾驶员疲劳状态（正常/分心/疲劳/未知等）"
                    )
                ),
                tips = listOf(
                    "疲劳/分心提示出现时，建议尽快休息或切换驾驶员。",
                    "长途驾驶可配合限速提醒降低风险。"
                )
            )

            "door" -> ModuleDetail(
                title = "车门状态",
                iconRes = R.drawable.ic_door,
                description = "展示车门开启提醒开关，仅用于状态展示与提醒。",
                params = listOf(
                    Param(
                        name = "DOOR_OPEN_WARN_ACTIVE",
                        propertyId = DOOR_OPEN_WARN_ACTIVE,
                        valueType = "boolean",
                        meaning = "车门开启提醒(DOW)开关"
                    )
                ),
                tips = listOf(
                    "儿童乘坐建议开启相关提醒与儿童锁。",
                    "行车中车门未关好应立即提示。"
                )
            )

            "speed_limit" -> ModuleDetail(
                title = "限速提醒",
                iconRes = R.drawable.ic_speed_limit,
                description = "展示限速提醒模式与偏差值，用于判断是否存在超速风险偏好。",
                params = listOf(
                    Param(
                        name = "SPEED_LIMIT_WARNING_MODE",
                        propertyId = SPEED_LIMIT_WARNING_MODE,
                        valueType = "int",
                        meaning = "限速警告模式（不同车型枚举定义）"
                    ),
                    Param(
                        name = "SPEED_LIMIT_WARNING_OFFSET_VALUE",
                        propertyId = SPEED_LIMIT_WARNING_OFFSET_VALUE,
                        valueType = "int",
                        meaning = "限速偏差值（正负整数，具体由业务约定）"
                    )
                ),
                tips = listOf(
                    "偏差值设置过大可能增加超速风险。",
                    "建议与驾驶辅助/疲劳监测联动提示。"
                )
            )

            "rain_safety" -> ModuleDetail(
                title = "雨天安全",
                iconRes = R.drawable.ic_rain,
                description = "展示雨天自动关窗、倒车自动后雨刮、锁车关窗等状态，用于雨天行车/驻车风险提示。",
                params = listOf(
                    Param(
                        name = "AUTO_CLOSE_WINDOW_RAINY",
                        propertyId = AUTO_CLOSE_WINDOW_RAINY,
                        valueType = "boolean",
                        meaning = "下雨自动关窗"
                    ),
                    Param(
                        name = "AUTO_REAR_WIPING",
                        propertyId = AUTO_REAR_WIPING,
                        valueType = "boolean",
                        meaning = "倒车+前雨刮开启时自动后雨刮"
                    ),
                    Param(
                        name = "LOCK_AUTO_CLOSE_WINDOW",
                        propertyId = LOCK_AUTO_CLOSE_WINDOW,
                        valueType = "boolean",
                        meaning = "锁车自动关窗"
                    )
                ),
                tips = listOf(
                    "雨天建议开启自动关窗与雨刮相关功能，降低视野与进水风险。",
                    "若检测到雨天且自动关窗关闭，可在首页卡片标黄/标红提示。"
                )
            )

            "child_safety" -> ModuleDetail(
                title = "乘员安全",
                iconRes = R.drawable.ic_child_lock,
                description = "展示儿童锁与副驾安全气囊使能状态，用于乘员安全提示（不提供控制）。",
                params = listOf(
                    Param(
                        name = "CHILD_SAFETY_LOCK",
                        propertyId = CHILD_SAFETY_LOCK,
                        valueType = "boolean",
                        meaning = "儿童安全锁（info.txt 标记 Deprecated；Android 14 建议使用原生 ID）"
                    ),
                    Param(
                        name = "PAB_SWITCH",
                        propertyId = PAB_SWITCH,
                        valueType = "boolean",
                        meaning = "副驾安全气囊使能开关（仅展示当前状态）"
                    )
                ),
                tips = listOf(
                    "儿童乘坐时建议启用儿童锁并正确使用安全座椅。",
                    "副驾无人/安装儿童座椅等场景需要特别关注气囊状态。"
                )
            )

            "lane" -> ModuleDetail(
                title = "车道偏离",
                iconRes = R.drawable.ic_lane_departure,
                description = "展示车道偏离预警开关状态，用于风险提示（只读）。",
                params = listOf(
                    Param(
                        name = "LANE_DEPARTURE_WARNING",
                        propertyId = LANE_DEPARTURE_WARNING,
                        valueType = "boolean",
                        meaning = "车道偏离预警开关"
                    )
                ),
                tips = listOf(
                    "高速行驶建议开启车道偏离预警。",
                    "夜间/雨雾等低可见度场景建议开启更强提示策略。"
                )
            )

            "lane_keep" -> ModuleDetail(
                title = "车道保持",
                iconRes = R.drawable.ic_lane_keep,
                description = "展示车道保持辅助开关与警告模式，用于状态展示与风险提示（只读）。",
                params = listOf(
                    Param(
                        name = "LANE_KEEPING_AID",
                        propertyId = LANE_KEEPING_AID,
                        valueType = "boolean",
                        meaning = "车道保持辅助开关"
                    ),
                    Param(
                        name = "LANE_KEEPING_AID_WARNING",
                        propertyId = LANE_KEEPING_AID_WARNING,
                        valueType = "int",
                        meaning = "车道保持辅助警告类别/模式（枚举定义见 IADAS.LaneKeepingAidWarningMode）"
                    ),
                ),
                tips = listOf(
                    "车道保持仅为辅助，驾驶员需保持注意力并随时接管。",
                    "若车道线置信度低（雨雾/反光），应降低对辅助的依赖。"
                )
            )

            "light" -> ModuleDetail(
                title = "灯光控制",
                iconRes = R.drawable.ic_light,
                description = "展示外灯选择与关键灯状态（只读展示/判断）。",
                params = listOf(
                    Param(
                        name = "LAMP_EXTERIOR_LIGHT_CONTROL",
                        propertyId = LAMP_EXTERIOR_LIGHT_CONTROL,
                        valueType = "int",
                        meaning = "外灯控制选择（OFF/AUTO/近光/自动远光等，枚举见 ILamp.ExteriorLightControlValue）"
                    ),
                    Param(
                        name = "LAMP_DAYTIME_LIGHT",
                        propertyId = LAMP_DAYTIME_LIGHT,
                        valueType = "boolean",
                        meaning = "日间行车灯状态"
                    ),
                    Param(
                        name = "LAMP_FRONT_POSITION",
                        propertyId = LAMP_FRONT_POSITION,
                        valueType = "boolean",
                        meaning = "前位置灯状态"
                    ),
                    Param(
                        name = "LAMP_REAR_POSITION",
                        propertyId = LAMP_REAR_POSITION,
                        valueType = "boolean",
                        meaning = "后位置灯状态"
                    ),
                ),
                tips = listOf(
                    "夜间/隧道建议 AUTO 或近光，提升可见性。",
                    "若长期 OFF 且外界低可见度，可在首页提示关注。"
                )
            )

            "tpms" -> ModuleDetail(
                title = "胎压监测",
                iconRes = R.drawable.ic_tpms,
                description = "当前工程内可展示胎压四轮数值（模拟）。注意：在 info.txt 中未检索到明确的“胎压 propertyId”，因此这里暂不绑定真实 propertyId。",
                params = listOf(
                    Param(
                        name = "（未在 info.txt 中找到胎压 propertyId）",
                        propertyId = 0,
                        valueType = "float[]",
                        meaning = "四轮胎压（模拟展示）；真实接入时请替换为车型对应的胎压属性 ID（常见按 VehicleAreaWheel 分区）"
                    )
                ),
                tips = listOf(
                    "可在真实接入后按四轮（左前/右前/左后/右后）分别展示与告警阈值。",
                    "若胎压低于阈值建议标黄/标红并提示检查。"
                )
            )

            else -> ModuleDetail(
                title = "模块详情",
                iconRes = R.drawable.ic_collision,
                description = "暂无该模块的参数定义。",
                params = emptyList(),
                tips = listOf("请在 ModuleCatalog 中补充参数映射。")
            )
        }
    }
}

