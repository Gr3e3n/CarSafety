package com.example.vehtrust.trace

import kotlin.random.Random

/**
 * 与事故事实、类型一致的环境快照与决策链文案生成（演示 / 种子数据用）。
 * 实车接入后可用路侧天气 API、高精地图或 V2X 替换。
 */
object AccidentContextGenerator {

    fun environmentFor(event: AccidentEvent): EnvironmentSnapshot {
        val weather = pickWeather(event)
        val road = pickRoad(event)
        val obstacle = pickObstacle(event)
        val lane = pickLaneMarking(weather, event)
        return EnvironmentSnapshot(weather = weather, road = road, obstacle = obstacle, laneMarking = lane)
    }

    fun decisionTraceFor(event: AccidentEvent, env: EnvironmentSnapshot): DecisionTrace {
        val vis = when (env.weather) {
            "雾", "大雨" -> "能见度下降，视觉测距噪声增大"
            "小雨" -> "风挡水渍导致车道线对比度波动"
            else -> "光照与对比度正常"
        }
        val laneHint = when (env.laneMarking) {
            "车道线模糊", "车道线被水渍反光干扰" -> "车道线几何置信度偏低，横向约束松弛"
            else -> "车道线几何稳定"
        }
        val obs = env.obstacle
        return DecisionTrace(
            sensorInput = buildString {
                append("雷达: 多目标跟踪; 最近威胁距离约 22–35m; ")
                append(vis)
                append("。摄像头: ")
                append(laneHint)
                append("。IMU: 纵向加速度在 -2.5~-0.5 m/s² 间波动后陡降。")
            },
            perception = buildString {
                append("融合轨迹将「")
                append(obs)
                append("」分类为关键障碍；在 ")
                append(if (event.severity == Severity.HIGH) "-2.1~-1.4s" else "-2.6~-1.8s")
                append(" 区间出现类别跳变与速度估计抖动。")
            },
            planning = buildString {
                append("规划在 ")
                append(env.road)
                append(" 场景下保持车道并请求减速；目标时距在 ")
                append(if (event.severity == Severity.MEDIUM) "1.4~1.9s" else "1.2~1.7s")
                append(" 间振荡，再规划间隔偶发拉长。")
            },
            control = buildString {
                append("横纵向解耦控制：横向力矩在 ")
                append(if (event.severity == Severity.MEDIUM) "160~240ms" else "180~320ms")
                append(" 延迟后到位；制动请求在 -1.5~-0.9s 爬升偏慢，与感知抖动同相位。")
            },
        )
    }

    private fun pickWeather(event: AccidentEvent): String = when (event.type) {
        AccidentType.AUTOPILOT_FAULT, AccidentType.DRIVER_TAKEOVER_FAIL -> weightedRandom(
            "晴" to 18,
            "多云" to 22,
            "小雨" to 20,
            "雾" to 16,
            "大雨" to 14,
        )
        AccidentType.COLLISION,
        AccidentType.DRIVER_SLOW_REACTION,
        AccidentType.AEB_DELAY_OR_MISS,
        AccidentType.TTC_LOW_RISK,
        AccidentType.ENVIRONMENT_DISTURB,
        AccidentType.MULTI_FACTOR,
        -> weightedRandom(
            "晴" to 28,
            "多云" to 26,
            "小雨" to 18,
            "雾" to 12,
            "大雨" to 8,
        )
    }

    private fun pickRoad(event: AccidentEvent): String = when (event.type) {
        AccidentType.AUTOPILOT_FAULT, AccidentType.DRIVER_TAKEOVER_FAIL -> weightedRandom(
            "快速路" to 22,
            "高架匝道" to 20,
            "城市主干道" to 18,
            "隧道入口" to 14,
            "长下坡接弯" to 12,
        )
        AccidentType.COLLISION,
        AccidentType.DRIVER_SLOW_REACTION,
        AccidentType.AEB_DELAY_OR_MISS,
        AccidentType.TTC_LOW_RISK,
        AccidentType.ENVIRONMENT_DISTURB,
        AccidentType.MULTI_FACTOR,
        -> weightedRandom(
            "城市主干道" to 26,
            "快速路" to 24,
            "高架匝道" to 18,
            "隧道入口" to 12,
            "施工借道段" to 10,
        )
    }

    private fun pickObstacle(event: AccidentEvent): String = when (event.type) {
        AccidentType.AUTOPILOT_FAULT, AccidentType.DRIVER_TAKEOVER_FAIL -> weightedRandom(
            "施工锥桶与临时分道" to 22,
            "前方慢车切入" to 20,
            "相邻车道大型车压迫" to 16,
            "路面抛洒物" to 14,
            "无明显静态障碍" to 18,
        )
        AccidentType.COLLISION,
        AccidentType.DRIVER_SLOW_REACTION,
        AccidentType.AEB_DELAY_OR_MISS,
        AccidentType.TTC_LOW_RISK,
        AccidentType.ENVIRONMENT_DISTURB,
        AccidentType.MULTI_FACTOR,
        -> when (event.severity) {
            Severity.HIGH -> weightedRandom(
                "前车急刹制动灯亮起" to 30,
                "静止排队车龙末端" to 22,
                "右侧车辆强行切入" to 18,
                "外卖非机动车占道" to 14,
            )
            Severity.MEDIUM -> weightedRandom(
                "前方车辆减速未打灯" to 26,
                "相邻车道变道挤压" to 22,
                "行人靠近路缘" to 16,
            )
            Severity.LOW -> weightedRandom(
                "前方异物（纸箱）" to 24,
                "施工锥桶偏移" to 20,
                "慢车占道" to 18,
                "无明显障碍" to 22,
            )
        }
    }

    private fun pickLaneMarking(weather: String, event: AccidentEvent): String {
        val stress = when (event.severity) {
            Severity.HIGH -> 0.35f
            Severity.MEDIUM -> 0.28f
            Severity.LOW -> 0.18f
        }
        val weatherStress = when (weather) {
            "雾", "大雨" -> 0.42f
            "小雨" -> 0.22f
            else -> 0f
        }
        val p = (stress + weatherStress).coerceIn(0f, 0.85f)
        return if (Random.nextFloat() < p) {
            weightedRandom(
                "车道线模糊" to 20,
                "车道线被水渍反光干扰" to 18,
                "新旧标线重叠" to 12,
            )
        } else {
            "车道线清晰"
        }
    }

    private fun weightedRandom(vararg pairs: Pair<String, Int>): String {
        val total = pairs.sumOf { it.second }
        var r = Random.nextInt(total)
        for ((value, w) in pairs) {
            if (r < w) return value
            r -= w
        }
        return pairs.first().first
    }
}
