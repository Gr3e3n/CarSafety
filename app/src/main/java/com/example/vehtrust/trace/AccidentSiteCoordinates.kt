package com.example.vehtrust.trace

/**
 * 将 [AccidentEvent.locationText] 解析为地图用经纬度（演示 / 无 GNSS 时的回退）。
 * 实车接入：请在事件中写入 GNSS 或从车机导航模块读取，并替换本解析器。
 */
data class AccidentSiteCoordinate(
    val latitude: Double,
    val longitude: Double,
    /** 是否由关键词表推断（非真实 GNSS） */
    val isApproximate: Boolean,
)

object AccidentSiteCoordinates {

    /** 基于城市关键词的参考点 + 由 [AccidentEvent.id] 决定的微小偏移，避免多条记录完全重叠 */
    fun resolve(event: AccidentEvent): AccidentSiteCoordinate {
        val text = event.locationText
        val (baseLat, baseLon) = when {
            text.contains("南京") -> 32.0570 to 118.7780
            text.contains("上海") && text.contains("浦东") -> 31.2033 to 121.5857
            text.contains("上海") -> 31.2304 to 121.4737
            text.contains("杭州") -> 30.2109 to 120.2126
            text.contains("北京") -> 39.8064 to 116.4939
            text.contains("深圳") -> 22.5431 to 114.0579
            text.contains("广州") -> 23.1291 to 113.2644
            text.contains("成都") -> 30.5728 to 104.0668
            else -> 32.0570 to 118.7780
        }
        val h = event.id.hashCode()
        val dLat = ((h and 0xFF) - 128) / 55_000.0
        val dLon = (((h shr 8) and 0xFF) - 128) / 55_000.0
        return AccidentSiteCoordinate(
            latitude = (baseLat + dLat).coerceIn(-85.0, 85.0),
            longitude = (baseLon + dLon).coerceIn(-180.0, 180.0),
            isApproximate = true,
        )
    }
}
