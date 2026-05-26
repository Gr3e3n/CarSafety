package com.csa.chesuan.trace

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RadialGradient
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.sin

class AccidentReplayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val telemetry = mutableListOf<TelemetryPoint>()
    private val distanceTimeline = mutableListOf<DistanceSample>()
    private var playheadMs: Int = 0
    private var frame: ReplayFrame = ReplayFrame.Empty
    private var sceneSpec: AccidentSceneSpec = AccidentSceneSpec.Default
    /** 航向低通，减少逐帧跳变 */
    private var smoothedOwnCarYawDeg = 0f

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply { color = Color.rgb(236, 240, 245) }
    private val cardPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply { color = Color.rgb(252, 253, 255) }
    private val cardStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dp(1f)
        color = Color.argb(55, 15, 23, 42)
    }
    private val hudBackdropPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.argb(200, 252, 253, 255) }
    private val sceneVignettePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply { style = Paint.Style.FILL }
    private val skyPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val groundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.rgb(30, 41, 59) }
    private val roadPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.rgb(51, 65, 85) }
    private val shoulderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.rgb(100, 116, 139) }
    private val lanePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(226, 232, 240)
        strokeWidth = dp(2f)
        style = Paint.Style.STROKE
    }
    private val roadEdgePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(241, 245, 249)
        strokeWidth = dp(2f)
        style = Paint.Style.STROKE
    }
    private val guardPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(148, 163, 184)
        strokeWidth = dp(3f)
        style = Paint.Style.STROKE
    }
    private val carPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply { color = Color.rgb(42, 48, 58) }
    private val carDarkPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply { color = Color.rgb(24, 28, 36) }
    private val glassPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply { color = Color.rgb(120, 132, 148) }
    private val tirePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.rgb(15, 23, 42) }
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.argb(80, 15, 23, 42) }
    private val brakePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.rgb(239, 68, 68) }
    private val blinkerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.rgb(245, 158, 11) }
    private val aebOutlinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(6, 182, 212)
        strokeWidth = dp(2.2f)
        style = Paint.Style.STROKE
    }
    private val debrisPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.rgb(251, 146, 60) }
    private val skidPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(150, 15, 23, 42)
        strokeWidth = dp(3f)
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val rainPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(70, 226, 232, 240)
        strokeWidth = dp(1f)
    }
    private val reflectionPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.argb(42, 226, 232, 240) }
    private val warningPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.argb(42, 239, 68, 68) }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.SUBPIXEL_TEXT_FLAG).apply {
        color = Color.rgb(30, 41, 59)
        textSize = dp(12f)
    }
    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.SUBPIXEL_TEXT_FLAG).apply {
        color = Color.rgb(100, 116, 139)
        textSize = dp(10f)
    }
    private val speedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(37, 99, 235)
        strokeWidth = dp(2.6f)
        style = Paint.Style.STROKE
    }
    private val brakeLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(217, 119, 6)
        strokeWidth = dp(2.5f)
        style = Paint.Style.STROKE
    }
    private val accelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(220, 38, 38)
        strokeWidth = dp(2.5f)
        style = Paint.Style.STROKE
    }
    private val ayPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(124, 58, 237)
        strokeWidth = dp(2.3f)
        style = Paint.Style.STROKE
    }
    private val throttlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(71, 85, 105)
        strokeWidth = dp(2.1f)
        style = Paint.Style.STROKE
        pathEffect = android.graphics.DashPathEffect(floatArrayOf(dp(7f), dp(5f)), 0f)
    }
    private val playheadPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(15, 23, 42)
        strokeWidth = dp(2f)
    }
    private val chartPlayheadGlowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.argb(200, 255, 255, 255)
        strokeWidth = dp(5f)
    }
    private val chartLaneEvenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.rgb(255, 255, 255)
    }
    private val chartLaneOddPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.rgb(241, 245, 249)
    }
    private val chartLaneTopLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dp(1f)
        color = Color.argb(70, 148, 163, 184)
    }
    private val chartBaselinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dp(1.1f)
        color = Color.argb(120, 100, 116, 139)
    }
    private val chartGridPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dp(1f)
        color = Color.argb(95, 148, 163, 184)
        pathEffect = android.graphics.DashPathEffect(floatArrayOf(dp(4f), dp(6f)), 0f)
    }
    private val chartSwatchStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dp(0.75f)
        color = Color.argb(90, 15, 23, 42)
    }
    private val chartAxisLabelPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.SUBPIXEL_TEXT_FLAG).apply {
        textSize = dp(8.5f)
        color = Color.rgb(71, 85, 105)
        textAlign = Paint.Align.CENTER
    }
    private val chartTitlePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.SUBPIXEL_TEXT_FLAG).apply {
        textSize = dp(11f)
        color = Color.rgb(30, 41, 59)
        textAlign = Paint.Align.LEFT
    }
    private val chartFillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val fcwFanPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val smokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val microLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dp(0.55f)
    }
    private val silhouetteStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dp(1.1f)
        color = Color.argb(200, 15, 23, 42)
    }
    private val chartPlotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.rgb(235, 239, 245) }
    /** 俯视车轮毂心点（局部函数内显式用类成员，避免个别编译器版本解析异常） */
    private val wheelHubPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.rgb(55, 65, 78) }

    fun setTelemetry(points: List<TelemetryPoint>) {
        telemetry.clear()
        telemetry += points.sortedBy { it.tMs }
        rebuildDistanceTimeline()
        smoothedOwnCarYawDeg = 0f
        val firstTime = telemetry.firstOrNull()?.tMs ?: 0
        setPlayhead(firstTime)
    }

    fun setAccidentContext(event: AccidentEvent, environment: EnvironmentSnapshot?) {
        sceneSpec = AccidentSceneSpec.from(event, environment)
        smoothedOwnCarYawDeg = 0f
        invalidate()
    }

    fun setPlayhead(tMs: Int) {
        playheadMs = tMs
        frame = interpolate(tMs)
        val rawYaw = targetOwnCarDisplayYawDeg()
        val delta = abs(rawYaw - smoothedOwnCarYawDeg)
        if (delta > 14f) {
            smoothedOwnCarYawDeg = rawYaw
        } else {
            smoothedOwnCarYawDeg += (rawYaw - smoothedOwnCarYawDeg) * 0.42f
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val outer = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(outer, dp(14f), dp(14f), bgPaint)
        val inset = dp(5f)
        val card = outer.insetCopy(inset)
        canvas.drawRoundRect(card, dp(12f), dp(12f), cardPaint)
        canvas.drawRoundRect(card, dp(12f), dp(12f), cardStrokePaint)

        if (telemetry.isEmpty()) {
            drawCenteredText(canvas, "暂无可回放遥测数据")
            return
        }

        val scene = RectF(dp(16f), dp(18f), width - dp(16f), height * 0.58f)
        val chart = RectF(dp(16f), scene.bottom + dp(20f), width - dp(16f), height - dp(14f))

        drawScene(canvas, scene)
        drawSceneVignette(canvas, scene)
        drawHud(canvas, scene)
        drawChart(canvas, chart)
    }

    private fun drawScene(canvas: Canvas, area: RectF) {
        val impact = impactIntensity()
        val shakeBlend = impact * collisionShakeEnvelope()
        val phase = frame.progressToImpact * (PI * 4f).toFloat()
        val shakeX = sin(phase) * dp(1.85f) * shakeBlend
        val shakeY = cos(phase * 0.88f) * dp(1.15f) * shakeBlend

        canvas.save()
        canvas.clipRect(area)
        val pitchSquash = ((-frame.axMS2).coerceIn(0f, 9.5f) / 9.5f) * 0.028f
        canvas.translate(shakeX, shakeY)
        canvas.scale(1f, 1f - pitchSquash, area.centerX(), area.centerY())
        drawAerialEnvironment(canvas, area)
        drawAerialRoad(canvas, area)
        drawAerialSkidMarks(canvas, area)
        drawBrakeDustAndHeat(canvas, area)
        drawAerialTrafficAndObstacle(canvas, area)
        drawAerialVehicle(canvas, area)
        drawAerialImpactEffects(canvas, area, impact)
        drawRainAndCameraNoise(canvas, area)
        canvas.restore()
    }

    /** 场景边缘轻压暗，弱化矢量块的“贴图感” */
    private fun drawSceneVignette(canvas: Canvas, area: RectF) {
        sceneVignettePaint.shader = RadialGradient(
            area.centerX(),
            area.centerY(),
            max(area.width(), area.height()) * 0.72f,
            intArrayOf(Color.TRANSPARENT, Color.argb(28, 15, 23, 42), Color.argb(52, 15, 23, 42)),
            floatArrayOf(0.42f, 0.82f, 1f),
            Shader.TileMode.CLAMP,
        )
        canvas.drawRoundRect(area, dp(14f), dp(14f), sceneVignettePaint)
        sceneVignettePaint.shader = null
    }

    private fun drawHud(canvas: Canvas, area: RectF) {
        val hudBar = RectF(area.left + dp(8f), area.top + dp(6f), area.right - dp(8f), area.top + dp(70f))
        canvas.drawRoundRect(hudBar, dp(10f), dp(10f), hudBackdropPaint)
        textPaint.textSize = dp(13f)
        canvas.drawText(sceneSpec.title, area.left + dp(14f), area.top + dp(22f), textPaint)
        labelPaint.textSize = dp(10f)
        canvas.drawText(sceneSpec.subtitle, area.left + dp(14f), area.top + dp(38f), labelPaint)

        val x = area.right - dp(128f)
        canvas.drawText("${frame.speedKph.roundToInt()} km/h", x, area.top + dp(22f), textPaint)
        canvas.drawText("制动 ${frame.brake.roundToInt()}%  油门 ${frame.throttlePct.roundToInt()}%", x, area.top + dp(38f), labelPaint)
        canvas.drawText(
            "aₓ ${formatOne(frame.axMS2)}  aᵧ ${formatOne(frame.ayMS2)}  横摆 ${formatOne(frame.yawRateDegS)}°/s",
            x,
            area.top + dp(54f),
            labelPaint,
        )
        val adas = buildString {
            append("FCW L").append(frame.fcwActiveLevel)
            append("  ·  AEB ").append(if (frame.aebActive) "ON" else "off")
            append("  ·  转向灯 ")
            append(
                when (frame.blinkerCode) {
                    1 -> "←"
                    2 -> "→"
                    3 -> "!"
                    else -> "—"
                },
            )
        }
        labelPaint.textSize = dp(9f)
        canvas.drawText(adas, area.left + dp(14f), area.top + dp(56f), labelPaint)
        labelPaint.textSize = dp(10f)
    }

    private fun drawAerialEnvironment(canvas: Canvas, area: RectF) {
        skyPaint.shader = LinearGradient(
            area.left,
            area.top,
            area.right,
            area.bottom,
            intArrayOf(
                if (sceneSpec.lowVisibility) Color.rgb(18, 22, 34) else Color.rgb(22, 28, 44),
                if (sceneSpec.lowVisibility) Color.rgb(48, 56, 72) else Color.rgb(52, 62, 82),
                if (sceneSpec.lowVisibility) Color.rgb(72, 80, 96) else Color.rgb(88, 98, 114),
            ),
            floatArrayOf(0f, 0.52f, 1f),
            Shader.TileMode.CLAMP,
        )
        canvas.drawRoundRect(area, dp(14f), dp(14f), skyPaint)
        skyPaint.shader = null

        if (sceneSpec.wetRoad) {
            reflectionPaint.color = Color.argb(28, 226, 232, 240)
            repeat(9) { index ->
                val y = area.top + dp(48f) + index * area.height() * 0.075f
                canvas.drawOval(RectF(area.left + dp(20f), y, area.right - dp(20f), y + dp(9f)), reflectionPaint)
            }
        }

        if (sceneSpec.lowVisibility) {
            reflectionPaint.color = Color.argb(38, 203, 213, 225)
            canvas.drawOval(
                RectF(area.left + dp(8f), area.top + dp(12f), area.right - dp(8f), area.bottom - dp(18f)),
                reflectionPaint,
            )
        }

        labelPaint.color = Color.argb(115, 226, 232, 240)
        canvas.drawText("TOP VIEW / EDR REPLAY", area.right - dp(132f), area.bottom - dp(12f), labelPaint)
        labelPaint.color = Color.rgb(100, 116, 139)
    }

    private fun drawAerialRoad(canvas: Canvas, area: RectF) {
        val road = aerialRoadRect(area)
        shadowPaint.color = Color.argb(72, 0, 0, 0)
        canvas.drawRoundRect(RectF(road.left, road.top + dp(8f), road.right, road.bottom + dp(12f)), dp(22f), dp(22f), shadowPaint)

        roadPaint.shader = LinearGradient(
            road.left,
            road.top,
            road.left,
            road.bottom,
            intArrayOf(Color.rgb(44, 48, 54), Color.rgb(56, 60, 66), Color.rgb(40, 44, 50)),
            floatArrayOf(0f, 0.5f, 1f),
            Shader.TileMode.CLAMP,
        )
        canvas.drawRoundRect(road, dp(20f), dp(20f), roadPaint)
        roadPaint.shader = null

        drawAsphaltMicroTexture(canvas, road)

        val upperShoulder = RectF(road.left, road.top - dp(20f), road.right, road.top - dp(5f))
        val lowerShoulder = RectF(road.left, road.bottom + dp(5f), road.right, road.bottom + dp(20f))
        shoulderPaint.shader = LinearGradient(
            road.left,
            upperShoulder.top,
            road.left,
            upperShoulder.bottom,
            Color.rgb(92, 98, 106),
            Color.rgb(68, 74, 82),
            Shader.TileMode.CLAMP,
        )
        canvas.drawRoundRect(upperShoulder, dp(10f), dp(10f), shoulderPaint)
        shoulderPaint.shader = LinearGradient(
            road.left,
            lowerShoulder.top,
            road.left,
            lowerShoulder.bottom,
            Color.rgb(68, 74, 82),
            Color.rgb(92, 98, 106),
            Shader.TileMode.CLAMP,
        )
        canvas.drawRoundRect(lowerShoulder, dp(10f), dp(10f), shoulderPaint)
        shoulderPaint.shader = null

        roadEdgePaint.strokeWidth = dp(1.4f)
        roadEdgePaint.color = Color.argb(210, 248, 250, 252)
        canvas.drawLine(road.left + dp(10f), road.top + dp(8f), road.right - dp(10f), road.top + dp(8f), roadEdgePaint)
        canvas.drawLine(road.left + dp(10f), road.bottom - dp(8f), road.right - dp(10f), road.bottom - dp(8f), roadEdgePaint)

        val centerY = road.centerY()
        lanePaint.strokeWidth = dp(1.85f)
        lanePaint.color = Color.argb(235, 250, 250, 246)
        lanePaint.pathEffect = android.graphics.DashPathEffect(
            floatArrayOf(dp(10f), dp(8f)),
            (frame.progressToImpact * dp(52f)) % dp(18f),
        )
        lanePaint.alpha = 238
        canvas.drawLine(road.left + dp(18f), centerY, road.right - dp(18f), centerY, lanePaint)
        lanePaint.pathEffect = null
        lanePaint.alpha = 255

        drawWheelWearLanes(canvas, road, centerY)

        guardPaint.strokeWidth = dp(2.2f)
        guardPaint.color = Color.rgb(180, 188, 198)
        canvas.drawLine(road.left + dp(8f), upperShoulder.centerY(), road.right - dp(8f), upperShoulder.centerY(), guardPaint)
        canvas.drawLine(road.left + dp(8f), lowerShoulder.centerY(), road.right - dp(8f), lowerShoulder.centerY(), guardPaint)
        guardPaint.strokeWidth = dp(1.1f)
        guardPaint.color = Color.argb(160, 71, 85, 105)
        canvas.drawLine(road.left + dp(8f), upperShoulder.centerY() - dp(1.2f), road.right - dp(8f), upperShoulder.centerY() - dp(1.2f), guardPaint)
        canvas.drawLine(road.left + dp(8f), lowerShoulder.centerY() + dp(1.2f), road.right - dp(8f), lowerShoulder.centerY() + dp(1.2f), guardPaint)
        guardPaint.strokeWidth = dp(2.2f)
        guardPaint.color = Color.argb(140, 148, 163, 184)
        repeat(5) { index ->
            val px = road.left + dp(28f) + index * (road.width() - dp(56f)) / 4f
            canvas.drawLine(px, upperShoulder.top, px, upperShoulder.bottom, guardPaint)
            canvas.drawLine(px, lowerShoulder.top, px, lowerShoulder.bottom, guardPaint)
        }

        drawDistanceScale(canvas, area)
    }

    /** 沥青颗粒感：低密度斜向纹理，避免横条纹像扫描线 */
    private fun drawAsphaltMicroTexture(canvas: Canvas, road: RectF) {
        var y = road.top + dp(10f)
        var row = 0
        while (y < road.bottom - dp(10f)) {
            microLinePaint.color = Color.argb((2 + (row % 4)).coerceIn(2, 5), 255, 255, 255)
            var x = road.left + dp(8f) + (row % 3) * dp(11f)
            while (x < road.right - dp(8f)) {
                canvas.drawLine(x, y, x + dp(18f), y + dp(1.6f), microLinePaint)
                x += dp(38f)
            }
            y += dp(11f)
            row++
        }
    }

    /** 轮迹带略深（重车辙示意） */
    private fun drawWheelWearLanes(canvas: Canvas, road: RectF, centerY: Float) {
        reflectionPaint.shader = LinearGradient(
            road.centerX() - road.width() * 0.22f,
            road.top,
            road.centerX() - road.width() * 0.22f,
            road.bottom,
            intArrayOf(Color.TRANSPARENT, Color.argb(18, 0, 0, 0), Color.TRANSPARENT),
            floatArrayOf(0f, 0.5f, 1f),
            Shader.TileMode.CLAMP,
        )
        canvas.drawRoundRect(
            RectF(road.centerX() - road.width() * 0.32f, road.top + dp(4f), road.centerX() - road.width() * 0.12f, road.bottom - dp(4f)),
            dp(8f),
            dp(8f),
            reflectionPaint,
        )
        reflectionPaint.shader = LinearGradient(
            road.centerX() + road.width() * 0.12f,
            road.top,
            road.centerX() + road.width() * 0.12f,
            road.bottom,
            intArrayOf(Color.TRANSPARENT, Color.argb(18, 0, 0, 0), Color.TRANSPARENT),
            floatArrayOf(0f, 0.5f, 1f),
            Shader.TileMode.CLAMP,
        )
        canvas.drawRoundRect(
            RectF(road.centerX() + road.width() * 0.12f, road.top + dp(4f), road.centerX() + road.width() * 0.32f, road.bottom - dp(4f)),
            dp(8f),
            dp(8f),
            reflectionPaint,
        )
        reflectionPaint.shader = null
    }

    private fun drawAerialTrafficAndObstacle(canvas: Canvas, area: RectF) {
        when (sceneSpec.type) {
            ReplaySceneType.RearEnd -> drawRearEndScene(canvas, area)
            ReplaySceneType.LaneDeparture -> drawLaneDepartureScene(canvas, area)
            ReplaySceneType.NearMiss -> drawNearMissScene(canvas, area)
        }
    }

    /** 目标俯视图航向（未平滑），供低通滤波使用 */
    private fun targetOwnCarDisplayYawDeg(): Float {
        val steerYaw = (frame.steerDeg * 0.48f).coerceIn(-14f, 14f)
        val dynamicYaw = (frame.yawRateDegS * 0.24f).coerceIn(-10f, 10f)
        val wobble = impactIntensity() * collisionShakeEnvelope() *
            sin(frame.progressToImpact * (PI * 3f).toFloat()) * 2.4f
        return (steerYaw + dynamicYaw + wobble).coerceIn(-22f, 22f)
    }

    private fun ownCarDisplayYawDeg(): Float = smoothedOwnCarYawDeg

    /** 间距线颜色：融合车间距、近似 TTC、FCW 等级 */
    private fun followingGapRiskColor(gapMeters: Float): Int {
        val speedMs = max(frame.speedKph / 3.6f, 0.15f)
        val ttc = gapMeters / speedMs
        val fcwFactor = frame.fcwActiveLevel / 3f
        val risk = maxOf(
            if (gapMeters <= 7f) 0.95f else if (gapMeters <= 12f) 0.62f else if (gapMeters <= 18f) 0.28f else 0f,
            if (ttc < 1.4f && frame.speedKph > 22f) 0.88f else if (ttc < 2.6f && frame.speedKph > 22f) 0.55f else if (ttc < 4f && frame.speedKph > 30f) 0.22f else 0f,
            fcwFactor * (if (gapMeters < 24f) 0.62f else 0.18f),
        ).coerceIn(0f, 1f)
        val r = (203 + (248 - 203) * risk).roundToInt().coerceIn(0, 255)
        val gCol = (213 + (113 - 213) * risk).roundToInt().coerceIn(0, 255)
        val b = (225 + (113 - 225) * risk).roundToInt().coerceIn(0, 255)
        return Color.rgb(r, gCol, b)
    }

    /** 前车 FCW 探测扇（示意，朝向 +X 行驶） */
    private fun drawFcwForwardFan(canvas: Canvas, apexX: Float, apexY: Float) {
        val level = frame.fcwActiveLevel
        if (level <= 0) return
        val reach = dp(26f + level * 20f)
        val halfDeg = 11f + level * 6.5f
        val steps = 14
        fcwFanPaint.shader = null
        fcwFanPaint.style = Paint.Style.FILL
        fcwFanPaint.color = Color.argb((22 + level * 16).coerceIn(22, 82), 251, 191, 36)
        val path = Path()
        path.moveTo(apexX, apexY)
        for (i in 0..steps) {
            val t = i / steps.toFloat()
            val deg = -halfDeg + t * 2f * halfDeg
            val rad = deg * PI.toFloat() / 180f
            path.lineTo(apexX + cos(rad) * reach, apexY + sin(rad) * reach * 0.34f)
        }
        path.close()
        canvas.drawPath(path, fcwFanPaint)
        fcwFanPaint.style = Paint.Style.STROKE
        fcwFanPaint.strokeWidth = dp(1.1f)
        fcwFanPaint.color = Color.argb((45 + level * 18).coerceIn(45, 108), 245, 158, 11)
        canvas.drawPath(path, fcwFanPaint)
        fcwFanPaint.style = Paint.Style.FILL
    }

    private fun drawRearEndScene(canvas: Canvas, area: RectF) {
        val road = aerialRoadRect(area)
        val laneY = road.centerY() - road.height() * 0.22f
        val leadX = impactX(area)
        val leadNoseX = leadX + dp(38f)
        drawFcwForwardFan(canvas, leadNoseX, laneY)
        drawAerialCar(
            canvas = canvas,
            cx = leadX,
            cy = laneY,
            length = dp(82f),
            width = dp(42f),
            yaw = 0f,
            bodyPaint = shoulderPaint,
            brakeOn = true,
            isOwnCar = false,
            blinkerCode = 0,
            aebHighlight = false,
            fcwPulse = frame.fcwActiveLevel > 0,
        )

        val conesY = road.centerY() + road.height() * 0.22f
        drawRoadDebris(canvas, impactX(area) - dp(36f), conesY - dp(14f), 1f)
        drawRoadDebris(canvas, impactX(area) - dp(4f), conesY + dp(8f), 0.9f)
        drawRoadDebris(canvas, impactX(area) + dp(28f), conesY + dp(20f), 0.78f)

        warningPaint.color = Color.argb(35, 239, 68, 68)
        canvas.drawRoundRect(RectF(leadX - dp(62f), laneY - dp(34f), leadX + dp(50f), laneY + dp(34f)), dp(18f), dp(18f), warningPaint)
        warningPaint.color = Color.argb(42, 239, 68, 68)

        drawFollowingDistance(canvas, area, leadX, laneY)
    }

    private fun drawLaneDepartureScene(canvas: Canvas, area: RectF) {
        val road = aerialRoadRect(area)
        val hazardX = impactX(area)
        val hazardY = road.bottom + dp(15f)
        warningPaint.color = Color.argb(38, 239, 68, 68)
        canvas.drawRoundRect(
            RectF(hazardX - dp(76f), road.bottom - dp(10f), hazardX + dp(58f), road.bottom + dp(36f)),
            dp(16f),
            dp(16f),
            warningPaint,
        )
        warningPaint.color = Color.argb(42, 239, 68, 68)

        guardPaint.strokeWidth = dp(6f)
        guardPaint.color = Color.rgb(203, 213, 225)
        canvas.drawLine(hazardX - dp(88f), hazardY, hazardX + dp(72f), hazardY, guardPaint)
        guardPaint.color = Color.rgb(148, 163, 184)
        guardPaint.strokeWidth = dp(3f)

        repeat(4) { index ->
            drawRoadDebris(canvas, hazardX - dp(48f) + index * dp(28f), hazardY + dp(14f + index % 2 * 6f), 0.7f)
        }

        roadEdgePaint.color = Color.rgb(248, 113, 113)
        roadEdgePaint.strokeWidth = dp(1.8f)
        val x = ownCarX(area) + dp(38f)
        val y = ownCarY(area)
        canvas.drawLine(x, y, hazardX - dp(20f), hazardY - dp(8f), roadEdgePaint)
        labelPaint.color = Color.rgb(248, 113, 113)
        canvas.drawText("偏离 ${formatOne(lateralDeviationMeters())}m", (x + hazardX) / 2f - dp(26f), y + dp(24f), labelPaint)
        roadEdgePaint.color = Color.rgb(241, 245, 249)
        labelPaint.color = Color.rgb(100, 116, 139)
    }

    private fun drawNearMissScene(canvas: Canvas, area: RectF) {
        val road = aerialRoadRect(area)
        val laneY = road.centerY() - road.height() * 0.22f
        val leadX = impactX(area) + dp(18f)
        drawFcwForwardFan(canvas, leadX + dp(35f), laneY)
        drawAerialCar(
            canvas = canvas,
            cx = leadX,
            cy = laneY,
            length = dp(78f),
            width = dp(40f),
            yaw = 0f,
            bodyPaint = shoulderPaint,
            brakeOn = true,
            isOwnCar = false,
            blinkerCode = 0,
            aebHighlight = false,
            fcwPulse = frame.fcwActiveLevel > 0,
        )
        val avoidY = road.centerY() + road.height() * 0.2f
        drawRoadDebris(canvas, impactX(area) - dp(12f), avoidY, 1f)
        drawRoadDebris(canvas, impactX(area) + dp(25f), avoidY + dp(15f), 0.76f)

        roadEdgePaint.color = Color.rgb(45, 212, 191)
        roadEdgePaint.strokeWidth = dp(1.8f)
        val x = ownCarX(area) + dp(36f)
        val y = ownCarY(area)
        canvas.drawLine(x, y, leadX - dp(46f), laneY + dp(42f), roadEdgePaint)
        labelPaint.color = Color.rgb(45, 212, 191)
        canvas.drawText("近失避让", leadX - dp(82f), laneY + dp(58f), labelPaint)
        roadEdgePaint.color = Color.rgb(241, 245, 249)
        labelPaint.color = Color.rgb(100, 116, 139)
        drawFollowingDistance(canvas, area, leadX, laneY)
    }

    private fun drawAerialSkidMarks(canvas: Canvas, area: RectF) {
        if (frame.brake < 28f) return
        val road = aerialRoadRect(area)
        val strength = (frame.brake / 100f).coerceIn(0f, 1f)
        val carX = ownCarX(area)
        val carY = ownCarY(area)
        val lengthMultiplier = when (sceneSpec.type) {
            ReplaySceneType.NearMiss -> 0.7f
            ReplaySceneType.LaneDeparture -> 1.12f
            ReplaySceneType.RearEnd -> 1f
        }
        val length = dp(48f + 150f * strength) * lengthMultiplier
        skidPaint.alpha = (80 + 120 * strength).roundToInt()
        skidPaint.strokeWidth = dp(3f)
        for (side in listOf(-1f, 1f)) {
            val y = carY + side * dp(14f)
            val path = Path().apply {
                moveTo(carX - dp(32f), y)
                cubicTo(
                    carX - length * 0.38f,
                    y + side * frame.steerDeg * dp(0.25f) + side * frame.yawRateDegS * dp(0.055f),
                    carX - length * 0.72f,
                    y - side * dp(4f) + side * frame.yawRateDegS * dp(0.03f),
                    (carX - length).coerceAtLeast(road.left + dp(12f)),
                    y + side * dp(3f),
                )
            }
            canvas.drawPath(path, skidPaint)
        }
        skidPaint.alpha = 255
    }

    /** 重刹时胎后尘雾与路面热扰动（俯视示意） */
    private fun drawBrakeDustAndHeat(canvas: Canvas, area: RectF) {
        val intensity = ((frame.brake / 100f) * 0.52f +
            ((-frame.axJerkMS3).coerceIn(0f, 28f) / 28f) * 0.48f).coerceIn(0f, 1f)
        if (intensity < 0.1f) return
        val rearX = ownCarX(area) - dp(46f)
        val cy = ownCarY(area)
        repeat(18) { i ->
            val phase = frame.progressToImpact * 5.5f + i * 0.7f
            val ox = sin(phase) * dp(5f) * intensity + (i % 5 - 2) * dp(2.2f)
            val oy = cos(phase * 1.1f) * dp(4f) * intensity + (i / 5 - 1) * dp(3f)
            smokePaint.color = Color.argb(
                (28 + 85 * intensity).roundToInt(),
                148 + i * 2,
                162 + i,
                184,
            )
            canvas.drawCircle(rearX + ox, cy + oy, dp(1.1f + intensity * 4f), smokePaint)
        }
        if (intensity > 0.45f && sceneSpec.wetRoad) {
            reflectionPaint.color = Color.argb((22 + 40 * intensity).roundToInt(), 226, 232, 240)
            canvas.drawOval(
                RectF(rearX - dp(28f), cy - dp(10f), rearX + dp(12f), cy + dp(10f)),
                reflectionPaint,
            )
        }
    }

    private fun drawAerialVehicle(canvas: Canvas, area: RectF) {
        val carX = ownCarX(area)
        val carY = ownCarY(area)
        val yaw = ownCarDisplayYawDeg()
        val massShift = ((-frame.axMS2).coerceIn(0f, 12f) / 12f).coerceIn(0f, 1f)
        drawAerialCar(
            canvas = canvas,
            cx = carX,
            cy = carY,
            length = dp(92f),
            width = dp(48f),
            yaw = yaw,
            bodyPaint = carPaint,
            brakeOn = frame.brake >= 28f,
            isOwnCar = true,
            blinkerCode = frame.blinkerCode,
            aebHighlight = frame.aebActive,
            fcwPulse = false,
            longitudinalMassShift = massShift,
            drawForwardGlow = sceneSpec.lowVisibility || sceneSpec.wetRoad,
        )
    }

    private fun drawAerialCar(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        length: Float,
        width: Float,
        yaw: Float,
        bodyPaint: Paint,
        brakeOn: Boolean,
        isOwnCar: Boolean,
        blinkerCode: Int,
        aebHighlight: Boolean,
        fcwPulse: Boolean,
        longitudinalMassShift: Float = 0f,
        drawForwardGlow: Boolean = false,
    ) {
        canvas.save()
        canvas.rotate(yaw, cx, cy)
        if (longitudinalMassShift > 0.02f) {
            val k = longitudinalMassShift.coerceIn(0f, 1f)
            canvas.scale(1f + 0.048f * k, 1f - 0.04f * k, cx, cy)
        }
        val body = RectF(cx - length / 2f, cy - width / 2f, cx + length / 2f, cy + width / 2f)
        val cornerR = (width * 0.11f).coerceAtMost(length * 0.14f)

        shadowPaint.shader = RadialGradient(
            cx,
            cy + width * 0.14f,
            max(length, width) * 0.58f,
            intArrayOf(Color.argb(78, 8, 10, 16), Color.argb(30, 8, 10, 16), Color.TRANSPARENT),
            floatArrayOf(0f, 0.4f, 1f),
            Shader.TileMode.CLAMP,
        )
        canvas.drawOval(RectF(cx - length * 0.51f, cy + width * 0.12f, cx + length * 0.51f, cy + width * 0.56f), shadowPaint)
        shadowPaint.shader = null

        val hull = Path().apply { addRoundRect(body, cornerR, cornerR, Path.Direction.CW) }

        bodyPaint.shader = if (isOwnCar) {
            LinearGradient(
                body.left,
                body.top,
                body.right,
                body.bottom,
                intArrayOf(
                    Color.rgb(62, 72, 88),
                    Color.rgb(36, 42, 54),
                    Color.rgb(52, 60, 76),
                    Color.rgb(28, 32, 42),
                ),
                floatArrayOf(0f, 0.38f, 0.62f, 1f),
                Shader.TileMode.CLAMP,
            )
        } else {
            LinearGradient(
                body.left,
                body.top,
                body.right,
                body.bottom,
                intArrayOf(Color.rgb(96, 102, 110), Color.rgb(122, 128, 136), Color.rgb(78, 84, 92), Color.rgb(58, 64, 72)),
                floatArrayOf(0f, 0.36f, 0.68f, 1f),
                Shader.TileMode.CLAMP,
            )
        }
        canvas.drawPath(hull, bodyPaint)
        bodyPaint.shader = null

        silhouetteStrokePaint.strokeWidth = dp(0.75f)
        silhouetteStrokePaint.color = Color.argb(130, 5, 8, 14)
        canvas.drawPath(hull, silhouetteStrokePaint)
        silhouetteStrokePaint.strokeWidth = dp(1.1f)

        val cabin = RectF(
            body.left + length * 0.14f,
            body.top + width * 0.14f,
            body.right - length * 0.26f,
            body.bottom - width * 0.14f,
        )
        carDarkPaint.shader = LinearGradient(
            cabin.left,
            cabin.top,
            cabin.right,
            cabin.bottom,
            intArrayOf(Color.rgb(16, 24, 36), Color.rgb(34, 44, 58), Color.rgb(22, 30, 42)),
            floatArrayOf(0f, 0.5f, 1f),
            Shader.TileMode.CLAMP,
        )
        canvas.drawRoundRect(cabin, width * 0.07f, width * 0.07f, carDarkPaint)
        carDarkPaint.shader = null

        val windshield = Path().apply {
            moveTo(body.right - length * 0.32f, body.top + width * 0.18f)
            lineTo(body.right - length * 0.05f, body.centerY() - width * 0.05f)
            lineTo(body.right - length * 0.05f, body.centerY() + width * 0.05f)
            lineTo(body.right - length * 0.32f, body.bottom - width * 0.18f)
            lineTo(body.right - length * 0.41f, body.bottom - width * 0.20f)
            lineTo(body.right - length * 0.41f, body.top + width * 0.20f)
            close()
        }
        glassPaint.shader = LinearGradient(
            body.right - length * 0.42f,
            body.top,
            body.right - length * 0.02f,
            body.bottom,
            intArrayOf(Color.argb(140, 96, 110, 124), Color.argb(85, 18, 24, 34), Color.argb(115, 48, 58, 72)),
            floatArrayOf(0f, 0.52f, 1f),
            Shader.TileMode.CLAMP,
        )
        canvas.drawPath(windshield, glassPaint)
        glassPaint.shader = null

        fun drawWheel(wx: Float, wy: Float, rw: Float, rh: Float) {
            val tireRect = RectF(wx - rw, wy - rh, wx + rw, wy + rh)
            tirePaint.shader = RadialGradient(
                wx,
                wy - rh * 0.2f,
                max(rw, rh),
                intArrayOf(Color.rgb(46, 50, 56), Color.rgb(10, 12, 16)),
                floatArrayOf(0f, 1f),
                Shader.TileMode.CLAMP,
            )
            canvas.drawOval(tireRect, tirePaint)
            tirePaint.shader = null
            canvas.drawCircle(wx, wy, dp(1.8f), this@AccidentReplayView.wheelHubPaint)
        }
        val rw = width * 0.11f
        val rh = width * 0.19f
        drawWheel(body.left + length * 0.22f, body.top + width * 0.02f, rw, rh)
        drawWheel(body.right - length * 0.22f, body.top + width * 0.02f, rw, rh)
        drawWheel(body.left + length * 0.22f, body.bottom - width * 0.02f, rw, rh)
        drawWheel(body.right - length * 0.22f, body.bottom - width * 0.02f, rw, rh)

        if (brakeOn) {
            val pulse = if (fcwPulse) (0.55f + 0.45f * sin(playheadMs / 90f)).coerceIn(0.35f, 1f) else 1f
            brakePaint.alpha = ((if (isOwnCar) (95 + frame.brake * 1.6f) else 220f) * pulse).roundToInt().coerceIn(80, 255)
            canvas.drawRoundRect(RectF(body.left + dp(4f), body.top + width * 0.24f, body.left + dp(10f), body.top + width * 0.42f), dp(2f), dp(2f), brakePaint)
            canvas.drawRoundRect(RectF(body.left + dp(4f), body.bottom - width * 0.42f, body.left + dp(10f), body.bottom - width * 0.24f), dp(2f), dp(2f), brakePaint)
            brakePaint.alpha = 255
        }

        if (blinkerCode == 1 || blinkerCode == 3) {
            blinkerPaint.alpha = (160 + 95 * sin(playheadMs / 110f)).roundToInt().coerceIn(120, 255)
            canvas.drawCircle(body.left + dp(8f), body.top + width * 0.32f, dp(3.2f), blinkerPaint)
            blinkerPaint.alpha = 255
        }
        if (blinkerCode == 2 || blinkerCode == 3) {
            blinkerPaint.alpha = (160 + 95 * sin(playheadMs / 110f + 1.2f)).roundToInt().coerceIn(120, 255)
            canvas.drawCircle(body.left + dp(8f), body.bottom - width * 0.32f, dp(3.2f), blinkerPaint)
            blinkerPaint.alpha = 255
        }

        if (aebHighlight) {
            aebOutlinePaint.alpha = (140 + 95 * sin(playheadMs / 70f)).roundToInt().coerceIn(100, 255)
            canvas.drawPath(hull, aebOutlinePaint)
            aebOutlinePaint.alpha = 255
        }

        if (drawForwardGlow && isOwnCar) {
            val head = Path().apply {
                moveTo(body.right - length * 0.02f, body.centerY() - width * 0.14f)
                lineTo(body.right + length * 0.52f, body.centerY() - width * 0.32f)
                lineTo(body.right + length * 0.52f, body.centerY() + width * 0.32f)
                lineTo(body.right - length * 0.02f, body.centerY() + width * 0.14f)
                close()
            }
            glassPaint.shader = LinearGradient(
                body.right,
                body.centerY(),
                body.right + length * 0.48f,
                body.centerY(),
                Color.argb(if (sceneSpec.lowVisibility) 72 else 48, 254, 252, 231),
                Color.TRANSPARENT,
                Shader.TileMode.CLAMP,
            )
            canvas.drawPath(head, glassPaint)
            glassPaint.shader = null
        }

        if (isOwnCar) {
            reflectionPaint.color = Color.argb(55, 110, 168, 220)
            canvas.drawLine(body.right - length * 0.08f, body.top + width * 0.12f, body.right - length * 0.02f, body.bottom - width * 0.12f, reflectionPaint)
        }
        reflectionPaint.color = Color.argb(28, 255, 255, 255)
        canvas.drawLine(body.left + length * 0.18f, body.top + width * 0.16f, body.right - length * 0.28f, body.top + width * 0.12f, reflectionPaint)
        canvas.restore()
    }

    private fun drawRoadDebris(canvas: Canvas, cx: Float, cy: Float, scale: Float) {
        val w = dp(28f) * scale
        val h = dp(13f) * scale
        canvas.save()
        canvas.rotate(-12f, cx, cy)
        debrisPaint.color = Color.rgb(71, 85, 105)
        canvas.drawRoundRect(RectF(cx - w / 2f, cy - h / 2f, cx + w / 2f, cy + h / 2f), dp(3f), dp(3f), debrisPaint)
        reflectionPaint.color = Color.argb(55, 226, 232, 240)
        canvas.drawLine(cx - w * 0.35f, cy - h * 0.2f, cx + w * 0.28f, cy - h * 0.2f, reflectionPaint)
        canvas.restore()
    }

    private fun drawAerialImpactEffects(canvas: Canvas, area: RectF, intensity: Float) {
        val scenarioIntensity = when (sceneSpec.type) {
            ReplaySceneType.NearMiss -> intensity * 0.22f
            ReplaySceneType.LaneDeparture -> intensity * 0.78f
            ReplaySceneType.RearEnd -> intensity
        }
        if (scenarioIntensity <= 0.02f) return
        val road = aerialRoadRect(area)
        val cx = impactX(area)
        val cy = when (sceneSpec.type) {
            ReplaySceneType.LaneDeparture -> road.bottom + dp(12f)
            else -> road.centerY() - road.height() * 0.22f
        }
        val radius = dp(42f + 70f * scenarioIntensity)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = RadialGradient(
                cx,
                cy,
                radius,
                intArrayOf(
                    Color.argb((120 * scenarioIntensity).roundToInt(), 248, 250, 252),
                    Color.argb((95 * scenarioIntensity).roundToInt(), 248, 113, 113),
                    Color.TRANSPARENT,
                ),
                floatArrayOf(0f, 0.36f, 1f),
                Shader.TileMode.CLAMP,
            )
        }
        canvas.drawCircle(cx, cy, radius, paint)

        debrisPaint.color = Color.rgb(251, 146, 60)
        repeat(22) { index ->
            val angle = index * 0.54f + frame.progressToImpact * (PI * 2.2f).toFloat()
            val dist = dp(12f + (index % 6) * 10f) * scenarioIntensity
            val x = cx + cos(angle) * dist * 1.18f
            val y = cy + sin(angle) * dist * 0.72f
            canvas.drawCircle(x, y, dp(1.5f + index % 3), debrisPaint)
        }
    }

    private fun drawDistanceScale(canvas: Canvas, area: RectF) {
        val road = aerialRoadRect(area)
        val y = road.bottom + dp(35f)
        val startX = road.left + dp(34f)
        val endX = impactX(area)
        roadEdgePaint.strokeWidth = dp(1.3f)
        roadEdgePaint.alpha = 145
        canvas.drawLine(startX, y, endX, y, roadEdgePaint)
        val impactDistance = impactDistanceMeters().coerceAtLeast(1f)
        repeat(5) { index ->
            val ratio = index / 4f
            val x = startX + (endX - startX) * ratio
            canvas.drawLine(x, y - dp(4f), x, y + dp(4f), roadEdgePaint)
            labelPaint.color = Color.argb(150, 226, 232, 240)
            canvas.drawText("${(impactDistance * ratio).roundToInt()}m", x - dp(10f), y + dp(17f), labelPaint)
        }
        roadEdgePaint.alpha = 255
        labelPaint.color = Color.rgb(100, 116, 139)
    }

    private fun drawFollowingDistance(canvas: Canvas, area: RectF, leadX: Float, laneY: Float) {
        val ownNoseX = ownCarX(area) + dp(48f)
        val leadRearX = leadX - dp(41f)
        if (leadRearX <= ownNoseX) return
        val gapMeters = remainingImpactDistanceMeters().coerceAtLeast(0f)
        roadEdgePaint.strokeWidth = dp(1.6f)
        roadEdgePaint.color = followingGapRiskColor(gapMeters)
        canvas.drawLine(ownNoseX, laneY - dp(34f), leadRearX, laneY - dp(34f), roadEdgePaint)
        canvas.drawLine(ownNoseX, laneY - dp(39f), ownNoseX, laneY - dp(29f), roadEdgePaint)
        canvas.drawLine(leadRearX, laneY - dp(39f), leadRearX, laneY - dp(29f), roadEdgePaint)
        labelPaint.color = roadEdgePaint.color
        canvas.drawText("间距 ${formatOne(gapMeters)}m", (ownNoseX + leadRearX) / 2f - dp(24f), laneY - dp(42f), labelPaint)
        roadEdgePaint.color = Color.rgb(241, 245, 249)
        labelPaint.color = Color.rgb(100, 116, 139)
    }

    private fun drawEnvironment(canvas: Canvas, area: RectF) {
        skyPaint.shader = LinearGradient(
            0f,
            area.top,
            0f,
            area.bottom,
            Color.rgb(30, 41, 59),
            Color.rgb(148, 163, 184),
            Shader.TileMode.CLAMP,
        )
        canvas.drawRoundRect(area, dp(14f), dp(14f), skyPaint)
        skyPaint.shader = null

        val horizon = area.top + area.height() * 0.34f
        canvas.drawRect(area.left, horizon, area.right, area.bottom, groundPaint)

        reflectionPaint.color = Color.argb(48, 226, 232, 240)
        repeat(7) { index ->
            val y = horizon + dp(18f) + index * area.height() * 0.08f
            canvas.drawOval(RectF(area.left + dp(16f), y, area.right - dp(16f), y + dp(12f)), reflectionPaint)
        }

        labelPaint.color = Color.argb(120, 226, 232, 240)
        canvas.drawText("EDR REPLAY", area.right - dp(84f), area.bottom - dp(10f), labelPaint)
        labelPaint.color = Color.rgb(100, 116, 139)
    }

    private fun drawPerspectiveRoad(canvas: Canvas, area: RectF) {
        val topY = area.top + area.height() * 0.33f
        val bottomY = area.bottom
        val road = Path().apply {
            moveTo(roadLeft(area, topY), topY)
            lineTo(roadRight(area, topY), topY)
            lineTo(roadRight(area, bottomY), bottomY)
            lineTo(roadLeft(area, bottomY), bottomY)
            close()
        }
        canvas.drawPath(road, roadPaint)

        val leftShoulder = Path().apply {
            moveTo(area.left + dp(18f), bottomY)
            lineTo(roadLeft(area, bottomY), bottomY)
            lineTo(roadLeft(area, topY), topY)
            lineTo(area.centerX() - area.width() * 0.2f, topY)
            close()
        }
        val rightShoulder = Path().apply {
            moveTo(roadRight(area, bottomY), bottomY)
            lineTo(area.right - dp(18f), bottomY)
            lineTo(area.centerX() + area.width() * 0.2f, topY)
            lineTo(roadRight(area, topY), topY)
            close()
        }
        canvas.drawPath(leftShoulder, shoulderPaint)
        canvas.drawPath(rightShoulder, shoulderPaint)

        canvas.drawLine(roadLeft(area, topY), topY, roadLeft(area, bottomY), bottomY, roadEdgePaint)
        canvas.drawLine(roadRight(area, topY), topY, roadRight(area, bottomY), bottomY, roadEdgePaint)

        for (ratio in listOf(-0.34f, 0f, 0.34f)) {
            drawLaneDashes(canvas, area, topY, bottomY, ratio)
        }

        drawGuardRails(canvas, area, topY, bottomY)
    }

    private fun drawLaneDashes(canvas: Canvas, area: RectF, topY: Float, bottomY: Float, laneRatio: Float) {
        var y = topY + dp(10f)
        while (y < bottomY - dp(12f)) {
            val nextY = (y + dp(22f) + (y - topY) * 0.08f).coerceAtMost(bottomY)
            val x1 = laneX(area, y, laneRatio)
            val x2 = laneX(area, nextY, laneRatio)
            lanePaint.strokeWidth = dp(1.2f + 2.4f * roadDepth(area, y))
            canvas.drawLine(x1, y, x2, nextY, lanePaint)
            y = nextY + dp(18f) + (y - topY) * 0.06f
        }
    }

    private fun drawGuardRails(canvas: Canvas, area: RectF, topY: Float, bottomY: Float) {
        canvas.drawLine(area.left + dp(14f), bottomY - dp(2f), area.centerX() - area.width() * 0.24f, topY, guardPaint)
        canvas.drawLine(area.right - dp(14f), bottomY - dp(2f), area.centerX() + area.width() * 0.24f, topY, guardPaint)
        repeat(8) { index ->
            val y = topY + (bottomY - topY) * (index / 7f)
            val depth = roadDepth(area, y)
            val post = dp(4f + 10f * depth)
            val lx = area.left + dp(14f) + (area.centerX() - area.width() * 0.24f - area.left - dp(14f)) * (1f - depth)
            val rx = area.right - dp(14f) - (area.right - dp(14f) - area.centerX() - area.width() * 0.24f) * (1f - depth)
            canvas.drawLine(lx, y, lx, y + post, guardPaint)
            canvas.drawLine(rx, y, rx, y + post, guardPaint)
        }
    }

    private fun drawTrafficAndObstacle(canvas: Canvas, area: RectF) {
        val leadProgress = (0.18f + frame.progressToImpact * 0.42f).coerceIn(0.18f, 0.62f)
        val leadY = area.top + area.height() * leadProgress
        val depth = roadDepth(area, leadY)
        val leadWidth = dp(38f + 58f * depth)
        val leadHeight = dp(22f + 44f * depth)
        val leadX = laneX(area, leadY, 0f)
        val lead = RectF(leadX - leadWidth / 2f, leadY - leadHeight / 2f, leadX + leadWidth / 2f, leadY + leadHeight / 2f)

        shadowPaint.color = Color.argb(65, 15, 23, 42)
        canvas.drawOval(RectF(lead.left, lead.bottom - dp(2f), lead.right, lead.bottom + dp(8f)), shadowPaint)
        shoulderPaint.color = Color.rgb(71, 85, 105)
        canvas.drawRoundRect(lead, dp(5f + 8f * depth), dp(5f + 8f * depth), shoulderPaint)
        glassPaint.color = Color.rgb(203, 213, 225)
        canvas.drawRoundRect(
            RectF(lead.left + leadWidth * 0.18f, lead.top + leadHeight * 0.18f, lead.right - leadWidth * 0.18f, lead.top + leadHeight * 0.48f),
            dp(4f),
            dp(4f),
            glassPaint,
        )
        brakePaint.color = Color.rgb(248, 113, 113)
        canvas.drawCircle(lead.left + leadWidth * 0.22f, lead.bottom - leadHeight * 0.18f, dp(3f + 3f * depth), brakePaint)
        canvas.drawCircle(lead.right - leadWidth * 0.22f, lead.bottom - leadHeight * 0.18f, dp(3f + 3f * depth), brakePaint)
        shoulderPaint.color = Color.rgb(100, 116, 139)
        brakePaint.color = Color.rgb(239, 68, 68)

        val coneY = area.top + area.height() * 0.48f
        drawCone(canvas, laneX(area, coneY, -0.48f), coneY, roadDepth(area, coneY))
        drawCone(canvas, laneX(area, coneY + dp(26f), -0.42f), coneY + dp(26f), roadDepth(area, coneY + dp(26f)))
    }

    private fun drawCone(canvas: Canvas, cx: Float, cy: Float, depth: Float) {
        val size = dp(10f + 18f * depth)
        val cone = Path().apply {
            moveTo(cx, cy - size)
            lineTo(cx - size * 0.65f, cy + size)
            lineTo(cx + size * 0.65f, cy + size)
            close()
        }
        debrisPaint.color = Color.rgb(249, 115, 22)
        canvas.drawPath(cone, debrisPaint)
        roadEdgePaint.strokeWidth = dp(1.2f)
        canvas.drawLine(cx - size * 0.35f, cy, cx + size * 0.35f, cy, roadEdgePaint)
    }

    private fun drawSkidMarks(canvas: Canvas, area: RectF) {
        if (frame.brake < 28f) return
        val strength = (frame.brake / 100f).coerceIn(0f, 1f)
        val carY = area.bottom - dp(58f)
        val length = dp(44f + 106f * strength)
        val carX = laneX(area, carY, (frame.steerDeg / 38f).coerceIn(-0.42f, 0.42f))
        skidPaint.alpha = (70 + 120 * strength).roundToInt()
        for (side in listOf(-1f, 1f)) {
            val x = carX + side * dp(16f)
            val path = Path().apply {
                moveTo(x, carY + dp(28f))
                cubicTo(
                    x - side * dp(8f),
                    carY - length * 0.35f,
                    x + side * dp(12f),
                    carY - length * 0.72f,
                    x + side * frame.steerDeg * dp(0.5f),
                    carY - length,
                )
            }
            canvas.drawPath(path, skidPaint)
        }
        skidPaint.alpha = 255
    }

    private fun drawVehicle(canvas: Canvas, area: RectF) {
        val carY = area.bottom - dp(58f)
        val lateral = (frame.steerDeg / 38f).coerceIn(-0.42f, 0.42f)
        val carX = laneX(area, carY, lateral)
        val carWidth = dp(72f)
        val carHeight = dp(110f)
        val yaw = frame.steerDeg.coerceIn(-22f, 22f) + impactIntensity() * sin(playheadMs / 18f) * 8f

        shadowPaint.color = Color.argb(92, 15, 23, 42)
        canvas.drawOval(RectF(carX - carWidth * 0.62f, carY + carHeight * 0.32f, carX + carWidth * 0.62f, carY + carHeight * 0.52f), shadowPaint)
        canvas.save()
        canvas.rotate(yaw, carX, carY)
        val body = RectF(carX - carWidth / 2f, carY - carHeight / 2f, carX + carWidth / 2f, carY + carHeight / 2f)
        canvas.drawRoundRect(body, dp(18f), dp(18f), carPaint)
        canvas.drawRoundRect(
            RectF(body.left + dp(8f), body.top + dp(12f), body.right - dp(8f), body.top + dp(42f)),
            dp(10f),
            dp(10f),
            glassPaint,
        )
        canvas.drawRoundRect(
            RectF(body.left + dp(10f), body.top + dp(48f), body.right - dp(10f), body.bottom - dp(28f)),
            dp(12f),
            dp(12f),
            carDarkPaint,
        )
        canvas.drawRoundRect(RectF(body.left - dp(5f), body.top + dp(18f), body.left + dp(7f), body.bottom - dp(20f)), dp(5f), dp(5f), tirePaint)
        canvas.drawRoundRect(RectF(body.right - dp(7f), body.top + dp(18f), body.right + dp(5f), body.bottom - dp(20f)), dp(5f), dp(5f), tirePaint)

        val brakeAlpha = (80 + frame.brake * 1.7f).roundToInt().coerceIn(80, 255)
        brakePaint.alpha = brakeAlpha
        canvas.drawRoundRect(RectF(body.left + dp(9f), body.bottom - dp(18f), body.left + dp(25f), body.bottom - dp(7f)), dp(4f), dp(4f), brakePaint)
        canvas.drawRoundRect(RectF(body.right - dp(25f), body.bottom - dp(18f), body.right - dp(9f), body.bottom - dp(7f)), dp(4f), dp(4f), brakePaint)
        brakePaint.alpha = 255

        reflectionPaint.color = Color.argb(70, 255, 255, 255)
        canvas.drawLine(body.left + dp(12f), body.top + dp(14f), body.right - dp(16f), body.bottom - dp(18f), reflectionPaint)
        canvas.restore()
    }

    private fun drawImpactEffects(canvas: Canvas, area: RectF, intensity: Float) {
        if (intensity <= 0.02f) return
        val cx = laneX(area, area.bottom - dp(102f), 0f)
        val cy = area.bottom - dp(104f)
        val radius = dp(44f + 72f * intensity)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = RadialGradient(
                cx,
                cy,
                radius,
                intArrayOf(Color.argb((180 * intensity).roundToInt(), 255, 255, 255), Color.argb((110 * intensity).roundToInt(), 239, 68, 68), Color.TRANSPARENT),
                floatArrayOf(0f, 0.32f, 1f),
                Shader.TileMode.CLAMP,
            )
        }
        canvas.drawCircle(cx, cy, radius, paint)
        warningPaint.color = Color.argb((60 * intensity).roundToInt(), 239, 68, 68)
        canvas.drawCircle(cx, cy, radius * 1.22f, warningPaint)
        warningPaint.color = Color.argb(42, 239, 68, 68)

        debrisPaint.color = Color.rgb(251, 146, 60)
        repeat(18) { index ->
            val angle = index * 0.62f + frame.progressToImpact * (PI * 2f).toFloat()
            val dist = dp(14f + (index % 5) * 9f) * intensity
            val x = cx + cos(angle) * dist
            val y = cy + sin(angle) * dist * 0.72f
            canvas.drawCircle(x, y, dp(1.6f + index % 3), debrisPaint)
        }
    }

    private fun drawRainAndCameraNoise(canvas: Canvas, area: RectF) {
        val w = area.width().roundToInt().coerceAtLeast(1)
        val h = area.height().roundToInt().coerceAtLeast(1)
        val drift = distanceAt(playheadMs) * 3.8f + playheadMs * 0.015f
        if (sceneSpec.wetRoad) {
            rainPaint.color = Color.argb(34, 226, 232, 240)
            repeat(11) { index ->
                val xi = ((index * 37 + drift).toInt() % w + w) % w
                val yi = ((index * 53 + drift * 0.62f).toInt() % h + h) % h
                val x = area.left + xi
                val y = area.top + yi
                canvas.drawLine(x, y, x - dp(4f), y + dp(12f), rainPaint)
            }
        }
        if (sceneSpec.lowVisibility && !sceneSpec.wetRoad) {
            rainPaint.color = Color.argb(22, 203, 213, 225)
            repeat(9) { index ->
                val xi = ((index * 41 + drift * 0.55f).toInt() % w + w) % w
                val yi = ((index * 47 + drift * 0.38f).toInt() % h + h) % h
                val x = area.left + xi
                val y = area.top + yi
                canvas.drawLine(x, y, x - dp(3f), y + dp(9f), rainPaint)
            }
            rainPaint.color = Color.argb(70, 226, 232, 240)
        }
        reflectionPaint.color = Color.argb(16, 255, 255, 255)
        canvas.drawRoundRect(area, dp(14f), dp(14f), reflectionPaint)
    }

    private fun drawChart(canvas: Canvas, area: RectF) {
        canvas.drawRoundRect(area, dp(12f), dp(12f), bgPaint)
        val plotBg = area.insetCopy(dp(4f))
        canvas.drawRoundRect(plotBg, dp(10f), dp(10f), chartPlotPaint)

        val titleTop = area.top + dp(10f)
        canvas.drawText("遥测曲线（分轨）", area.left + dp(14f), titleTop + dp(13f), chartTitlePaint)
        chartAxisLabelPaint.textAlign = Paint.Align.LEFT
        chartAxisLabelPaint.textSize = dp(9f)
        chartAxisLabelPaint.color = Color.argb(230, 71, 85, 105)
        canvas.drawText(
            "各轨纵轴为 0～100% 相对幅度 · 独立缩放便于对比波形",
            area.left + dp(14f),
            titleTop + dp(28f),
            chartAxisLabelPaint,
        )
        chartAxisLabelPaint.textAlign = Paint.Align.CENTER
        chartAxisLabelPaint.textSize = dp(8.5f)
        chartAxisLabelPaint.color = Color.rgb(71, 85, 105)

        val timeAxisH = dp(17f)
        val plot = RectF(
            area.left + dp(12f),
            titleTop + dp(36f),
            area.right - dp(12f),
            area.bottom - dp(10f),
        )
        val innerPlot = RectF(plot.left, plot.top, plot.right, plot.bottom - timeAxisH)
        val labelColW = dp(52f)
        val laneGap = dp(8f)
        val lanes = 5
        val laneBody = (innerPlot.height() - laneGap * (lanes - 1)) / lanes
        val minT = telemetry.first().tMs
        val maxT = telemetry.last().tMs
        val seriesRect = RectF(innerPlot.left + labelColW, innerPlot.top, innerPlot.right, innerPlot.bottom)

        val gridFractions = listOf(0f, 0.25f, 0.5f, 0.75f, 1f)
        for (gf in gridFractions) {
            val tMs = (minT + (maxT - minT) * gf).toInt()
            val gx = timeToX(tMs, minT, maxT, seriesRect)
            canvas.drawLine(gx, innerPlot.top, gx, innerPlot.bottom, chartGridPaint)
        }

        val laneLabels = listOf("速度 km/h", "制动 %", "|aₓ|/10", "|aᵧ|/8", "油门 %")
        val swatchColors = listOf(
            Color.rgb(37, 99, 235),
            Color.rgb(217, 119, 6),
            Color.rgb(220, 38, 38),
            Color.rgb(124, 58, 237),
            Color.rgb(71, 85, 105),
        )
        val configs: List<Triple<String, Paint, (TelemetryPoint) -> Float>> = listOf(
            Triple(laneLabels[0], speedPaint, { it.speedKph / maxSpeed() }),
            Triple(laneLabels[1], brakeLinePaint, { it.brake / 100f }),
            Triple(laneLabels[2], accelPaint, { ((-it.axMS2) / 10f).coerceIn(0f, 1f) }),
            Triple(laneLabels[3], ayPaint, { (abs(it.ayMS2) / 8f).coerceIn(0f, 1f) }),
            Triple(laneLabels[4], throttlePaint, { it.throttlePct / 100f }),
        )
        var y = innerPlot.top
        repeat(lanes) { index ->
            val (laneTitle, paint, valueOf) = configs[index]
            val fullLane = RectF(innerPlot.left, y, innerPlot.right, y + laneBody)
            canvas.drawRect(
                fullLane,
                if (index % 2 == 0) chartLaneEvenPaint else chartLaneOddPaint,
            )
            if (index > 0) {
                canvas.drawLine(fullLane.left, fullLane.top, fullLane.right, fullLane.top, chartLaneTopLinePaint)
            }
            val lane = RectF(innerPlot.left + labelColW, y, innerPlot.right, y + laneBody)
            val swatch = RectF(
                innerPlot.left + dp(4f),
                fullLane.centerY() - dp(6f),
                innerPlot.left + dp(13f),
                fullLane.centerY() + dp(6f),
            )
            chartFillPaint.color = swatchColors[index]
            canvas.drawRoundRect(swatch, dp(2.5f), dp(2.5f), chartFillPaint)
            canvas.drawRoundRect(swatch, dp(2.5f), dp(2.5f), chartSwatchStrokePaint)
            chartAxisLabelPaint.textAlign = Paint.Align.LEFT
            chartAxisLabelPaint.textSize = dp(9.5f)
            chartAxisLabelPaint.color = Color.rgb(30, 41, 59)
            canvas.drawText(laneTitle, innerPlot.left + dp(18f), fullLane.centerY() + dp(4f), chartAxisLabelPaint)
            chartAxisLabelPaint.textAlign = Paint.Align.CENTER
            chartAxisLabelPaint.textSize = dp(8.5f)
            chartAxisLabelPaint.color = Color.rgb(71, 85, 105)

            canvas.drawLine(lane.left, lane.bottom, lane.right, lane.bottom, chartBaselinePaint)
            drawSeriesInLane(canvas, lane, paint, valueOf, swatchColors[index])
            y += laneBody + laneGap
        }

        for (gf in gridFractions) {
            val tMs = (minT + (maxT - minT) * gf).toInt()
            val gx = timeToX(tMs, minT, maxT, seriesRect)
            canvas.drawText(formatSignedSeconds(tMs), gx, plot.bottom - dp(2f), chartAxisLabelPaint)
        }

        val x = timeToX(playheadMs, minT, maxT, seriesRect)
        canvas.drawLine(x, innerPlot.top, x, innerPlot.bottom, chartPlayheadGlowPaint)
        playheadPaint.color = Color.rgb(30, 41, 59)
        playheadPaint.strokeWidth = dp(2.2f)
        canvas.drawLine(x, innerPlot.top, x, innerPlot.bottom, playheadPaint)
        chartFillPaint.color = Color.rgb(30, 41, 59)
        canvas.drawCircle(x, innerPlot.top + dp(5f), dp(3.2f), chartFillPaint)
    }

    private fun drawSeriesInLane(
        canvas: Canvas,
        lane: RectF,
        paint: Paint,
        valueOf: (TelemetryPoint) -> Float,
        seriesRgb: Int,
    ) {
        if (telemetry.size < 2) return
        val minT = telemetry.first().tMs
        val maxT = telemetry.last().tMs
        val linePath = Path()
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
        val pad = dp(4f)
        val innerH = (lane.height() - pad * 2f).coerceAtLeast(dp(5f))
        val innerTop = lane.top + pad
        telemetry.forEachIndexed { index, point ->
            val px = timeToX(point.tMs, minT, maxT, lane)
            val py = innerTop + innerH * (1f - valueOf(point).coerceIn(0f, 1f))
            if (index == 0) linePath.moveTo(px, py) else linePath.lineTo(px, py)
        }
        val xLast = timeToX(telemetry.last().tMs, minT, maxT, lane)
        val xFirst = timeToX(telemetry.first().tMs, minT, maxT, lane)
        val baseY = lane.bottom - pad
        val fillPath = Path(linePath)
        fillPath.lineTo(xLast, baseY)
        fillPath.lineTo(xFirst, baseY)
        fillPath.close()
        chartFillPaint.color = Color.argb(
            58,
            Color.red(seriesRgb),
            Color.green(seriesRgb),
            Color.blue(seriesRgb),
        )
        canvas.drawPath(fillPath, chartFillPaint)

        val under = Paint(paint).apply {
            style = Paint.Style.STROKE
            color = Color.argb(52, 15, 23, 42)
            strokeWidth = paint.strokeWidth + dp(3.4f)
            pathEffect = null
        }
        canvas.drawPath(linePath, under)
        canvas.drawPath(linePath, paint)
    }

    private fun interpolate(tMs: Int): ReplayFrame {
        if (telemetry.isEmpty()) return ReplayFrame.Empty
        val clamped = tMs.coerceIn(telemetry.first().tMs, telemetry.last().tMs)
        val nextIndex = telemetry.indexOfFirst { it.tMs >= clamped }.takeIf { it >= 0 } ?: telemetry.lastIndex
        val prev = telemetry[(nextIndex - 1).coerceAtLeast(0)]
        val next = telemetry[nextIndex]
        val span = (next.tMs - prev.tMs).takeIf { it != 0 } ?: 1
        val ratioRaw = ((clamped - prev.tMs).toFloat() / span).coerceIn(0f, 1f)
        val ratio = smoothstep01(ratioRaw)
        val minT = telemetry.first().tMs
        val maxT = telemetry.last().tMs
        val progress = if (maxT == minT) 1f else ((clamped - minT).toFloat() / (maxT - minT)).coerceIn(0f, 1f)
        val dtSec = (span / 1000f).coerceAtLeast(0.05f)
        val axJerk = (next.axMS2 - prev.axMS2) / dtSec
        return ReplayFrame(
            speedKph = lerp(prev.speedKph, next.speedKph, ratio),
            axMS2 = lerp(prev.axMS2, next.axMS2, ratio),
            brake = lerp(prev.brake.toFloat(), next.brake.toFloat(), ratio),
            steerDeg = lerp(prev.steerDeg, next.steerDeg, ratio),
            ayMS2 = lerp(prev.ayMS2, next.ayMS2, ratio),
            yawRateDegS = lerp(prev.yawRateDegS, next.yawRateDegS, ratio),
            throttlePct = lerp(prev.throttlePct.toFloat(), next.throttlePct.toFloat(), ratio),
            aebActive = lerp(if (prev.aebActive) 1f else 0f, if (next.aebActive) 1f else 0f, ratioRaw) >= 0.5f,
            blinkerCode = if (ratioRaw >= 0.5f) next.blinkerCode else prev.blinkerCode,
            fcwActiveLevel = lerp(prev.fcwActiveLevel.toFloat(), next.fcwActiveLevel.toFloat(), ratio).roundToInt().coerceIn(0, 3),
            progressToImpact = progress,
            axJerkMS3 = axJerk,
        )
    }

    private fun rebuildDistanceTimeline() {
        distanceTimeline.clear()
        if (telemetry.isEmpty()) return
        var distanceMeters = 0f
        distanceTimeline += DistanceSample(telemetry.first().tMs, distanceMeters)
        for (index in 1 until telemetry.size) {
            val prev = telemetry[index - 1]
            val current = telemetry[index]
            val dtSeconds = ((current.tMs - prev.tMs).coerceAtLeast(0)) / 1000f
            val prevSpeed = prev.speedKph / 3.6f
            val currentSpeed = current.speedKph / 3.6f
            distanceMeters += ((prevSpeed + currentSpeed) / 2f) * dtSeconds
            distanceTimeline += DistanceSample(current.tMs, distanceMeters)
        }
    }

    private fun distanceAt(tMs: Int): Float {
        if (distanceTimeline.isEmpty()) return 0f
        val clamped = tMs.coerceIn(distanceTimeline.first().tMs, distanceTimeline.last().tMs)
        val nextIndex = distanceTimeline.indexOfFirst { it.tMs >= clamped }.takeIf { it >= 0 } ?: distanceTimeline.lastIndex
        val prev = distanceTimeline[(nextIndex - 1).coerceAtLeast(0)]
        val next = distanceTimeline[nextIndex]
        val span = (next.tMs - prev.tMs).takeIf { it != 0 } ?: 1
        val ratio = ((clamped - prev.tMs).toFloat() / span).coerceIn(0f, 1f)
        return lerp(prev.distanceMeters, next.distanceMeters, ratio)
    }

    private fun timeToX(tMs: Int, minT: Int, maxT: Int, area: RectF): Float {
        if (minT == maxT) return area.left
        val progress = ((tMs - minT).toFloat() / (maxT - minT)).coerceIn(0f, 1f)
        return area.left + progress * area.width()
    }

    private fun impactIntensity(): Float {
        val byTime = (1f - abs(playheadMs) / 900f).coerceIn(0f, 1f)
        val byDecel = ((-frame.axMS2 - 6f) / 3.5f).coerceIn(0f, 1f)
        val byYaw = (abs(frame.yawRateDegS) / 95f).coerceIn(0f, 1f) * 0.35f
        val byJerk = ((-frame.axJerkMS3).coerceIn(0f, 35f) / 35f).coerceIn(0f, 1f) * 0.22f
        val severityScale = when (sceneSpec.severity) {
            Severity.LOW -> 0.25f
            Severity.MEDIUM -> 0.65f
            Severity.HIGH -> 1f
        }
        val aebBoost = if (frame.aebActive) 0.14f else 0f
        return (maxOf(byTime, byDecel * 0.82f, byYaw, byJerk) + aebBoost) * severityScale
    }

    private fun roadTop(area: RectF): Float = area.top + area.height() * 0.33f

    private fun roadDepth(area: RectF, y: Float): Float {
        val top = roadTop(area)
        return ((y - top) / (area.bottom - top)).coerceIn(0f, 1f)
    }

    private fun roadHalfWidth(area: RectF, y: Float): Float {
        val depth = roadDepth(area, y)
        return area.width() * (0.12f + 0.39f * depth)
    }

    private fun roadLeft(area: RectF, y: Float): Float = area.centerX() - roadHalfWidth(area, y)

    private fun roadRight(area: RectF, y: Float): Float = area.centerX() + roadHalfWidth(area, y)

    private fun laneX(area: RectF, y: Float, laneRatio: Float): Float {
        val half = roadHalfWidth(area, y)
        return area.centerX() + laneRatio.coerceIn(-0.85f, 0.85f) * half
    }

    private fun aerialRoadRect(area: RectF): RectF {
        val top = area.top + area.height() * 0.32f
        val bottom = area.top + area.height() * 0.72f
        return RectF(area.left + dp(16f), top, area.right - dp(16f), bottom)
    }

    private fun ownCarX(area: RectF): Float {
        val road = aerialRoadRect(area)
        val startX = road.left + dp(44f)
        val impactX = impactX(area) - dp(62f)
        val progress = (distanceAt(playheadMs) / impactDistanceMeters().coerceAtLeast(1f)).coerceIn(0f, 1.04f)
        val brakingLag = (frame.brake / 100f).coerceIn(0f, 1f) * dp(10f)
        val slipSway = collisionShakeEnvelope() * sin(frame.progressToImpact * (PI * 2f).toFloat()) *
            dp(0.55f) * (frame.brake / 100f).coerceIn(0f, 1f) *
            ((-frame.axMS2) / 8f).coerceIn(0f, 1.2f)
        val progressEased = smoothstep01(progress.coerceIn(0f, 1.04f))
        return startX + (impactX - startX) * progressEased - brakingLag + slipSway
    }

    private fun ownCarY(area: RectF): Float {
        val road = aerialRoadRect(area)
        val laneCenter = road.centerY() - road.height() * 0.22f
        val steeringOffset = (frame.steerDeg / 28f).coerceIn(-1f, 1f) * road.height() * 0.18f
        val lateralFromAy = (frame.ayMS2 / 9f).coerceIn(-1f, 1f) * road.height() * 0.05f
        val scenarioOffset = when (sceneSpec.type) {
            ReplaySceneType.LaneDeparture -> road.height() * 0.38f * frame.progressToImpact
            ReplaySceneType.NearMiss -> road.height() * 0.18f * sin(frame.progressToImpact * 3.14f)
            ReplaySceneType.RearEnd -> 0f
        }
        return laneCenter + steeringOffset + lateralFromAy + scenarioOffset
    }

    private fun lateralDeviationMeters(): Float {
        if (sceneSpec.type != ReplaySceneType.LaneDeparture) return 0f
        return (frame.progressToImpact * 2.4f + abs(frame.steerDeg) / 35f).coerceIn(0f, 3.2f)
    }

    private fun impactX(area: RectF): Float = aerialRoadRect(area).right - dp(96f)

    private fun impactDistanceMeters(): Float {
        if (distanceTimeline.isEmpty()) return 1f
        val impactSample = distanceTimeline.lastOrNull { it.tMs <= 0 }
        return (impactSample?.distanceMeters ?: distanceTimeline.last().distanceMeters).coerceAtLeast(1f)
    }

    private fun remainingImpactDistanceMeters(): Float =
        (impactDistanceMeters() - distanceAt(playheadMs)).coerceAtLeast(0f)

    private fun maxSpeed(): Float = telemetry.maxOfOrNull { it.speedKph }?.coerceAtLeast(1f) ?: 1f

    private fun drawCenteredText(canvas: Canvas, value: String) {
        textPaint.textAlign = Paint.Align.CENTER
        canvas.drawText(value, width / 2f, height / 2f, textPaint)
        textPaint.textAlign = Paint.Align.LEFT
    }

    private fun RectF.insetCopy(inset: Float): RectF = RectF(left + inset, top + inset, right - inset, bottom - inset)

    private fun dp(value: Float): Float = value * resources.displayMetrics.density

    /** 0～1 平滑插值，减轻稀疏遥测点之间的折线感 */
    private fun smoothstep01(x: Float): Float {
        val t = x.coerceIn(0f, 1f)
        return t * t * (3f - 2f * t)
    }

    private fun smoothstep(edge0: Float, edge1: Float, x: Float): Float {
        val denom = edge1 - edge0
        if (abs(denom) < 1e-5f) return if (x >= edge1) 1f else 0f
        val t = ((x - edge0) / denom).coerceIn(0f, 1f)
        return t * t * (3f - 2f * t)
    }

    /**
     * 仅在时间轴接近碰撞点（tMs → 0⁻）时渐强，避免重刹全程高频晃屏。
     */
    private fun collisionShakeEnvelope(): Float {
        val t = playheadMs.coerceAtMost(0).toFloat()
        return smoothstep(-480f, -90f, t) * (1f - smoothstep(-40f, 120f, playheadMs.toFloat()))
    }

    private fun lerp(start: Float, end: Float, ratio: Float): Float = start + (end - start) * ratio

    private fun formatOne(value: Float): String = String.format("%.1f", value)

    private fun formatSignedSeconds(tMs: Int): String {
        val seconds = tMs / 1000f
        return if (seconds >= 0f) "+${formatOne(seconds)}s" else "${formatOne(seconds)}s"
    }

    private data class ReplayFrame(
        val speedKph: Float,
        val axMS2: Float,
        val brake: Float,
        val steerDeg: Float,
        val ayMS2: Float,
        val yawRateDegS: Float,
        val throttlePct: Float,
        val aebActive: Boolean,
        val blinkerCode: Int,
        val fcwActiveLevel: Int,
        val progressToImpact: Float,
        /** 纵向急动度 m/s³（插值段内估算，用于尘雾等视觉） */
        val axJerkMS3: Float,
    ) {
        companion object {
            val Empty = ReplayFrame(0f, 0f, 0f, 0f, 0f, 0f, 0f, false, 0, 0, 0f, 0f)
        }
    }

    private data class DistanceSample(
        val tMs: Int,
        val distanceMeters: Float,
    )

    private enum class ReplaySceneType {
        RearEnd,
        LaneDeparture,
        NearMiss,
    }

    private data class AccidentSceneSpec(
        val type: ReplaySceneType,
        val title: String,
        val subtitle: String,
        val wetRoad: Boolean,
        val lowVisibility: Boolean,
        val severity: Severity,
    ) {
        companion object {
            val Default = AccidentSceneSpec(
                type = ReplaySceneType.RearEnd,
                title = "类3D俯视事故复盘",
                subtitle = "左 → 右行进 · EDR遥测重建",
                wetRoad = false,
                lowVisibility = false,
                severity = Severity.MEDIUM,
            )

            fun from(event: AccidentEvent, environment: EnvironmentSnapshot?): AccidentSceneSpec {
                val text = (event.summary + " " + event.triggerReasons.joinToString(" ") + " " + (environment?.obstacle ?: "")).lowercase()
                val blob = listOfNotNull(
                    event.locationText,
                    environment?.weather,
                    environment?.road,
                    environment?.laneMarking,
                ).joinToString(" ")
                val wet = blob.contains("雨") || blob.contains("雾") || blob.contains("水") || blob.contains("湿")
                val lowVis = wet || blob.contains("雾") || blob.contains("大雨") || blob.contains("阴") || text.contains("隧")
                val type = when {
                    event.type == AccidentType.AUTOPILOT_FAULT ||
                        event.type == AccidentType.DRIVER_TAKEOVER_FAIL ||
                        text.contains("偏离") || text.contains("退出") || text.contains("控制") ->
                        ReplaySceneType.LaneDeparture
                    event.severity == Severity.LOW || text.contains("避让") || text.contains("无明显") ->
                        ReplaySceneType.NearMiss
                    else -> ReplaySceneType.RearEnd
                }
                return when (type) {
                    ReplaySceneType.RearEnd -> AccidentSceneSpec(
                        type = type,
                        title = "追尾碰撞复盘",
                        subtitle = "${severityText(event.severity)} · 前车急停/跟车距离不足 · 左 → 右",
                        wetRoad = wet,
                        lowVisibility = lowVis,
                        severity = event.severity,
                    )
                    ReplaySceneType.LaneDeparture -> AccidentSceneSpec(
                        type = type,
                        title = "自动驾驶退出/车道偏离复盘",
                        subtitle = "${severityText(event.severity)} · 横向偏移/护栏风险 · 左 → 右",
                        wetRoad = wet,
                        lowVisibility = lowVis,
                        severity = event.severity,
                    )
                    ReplaySceneType.NearMiss -> AccidentSceneSpec(
                        type = type,
                        title = "近失避让与急制动复盘",
                        subtitle = "${severityText(event.severity)} · 未形成直接碰撞 · 左 → 右",
                        wetRoad = wet,
                        lowVisibility = lowVis,
                        severity = event.severity,
                    )
                }
            }

            private fun severityText(severity: Severity): String = when (severity) {
                Severity.LOW -> "低严重度"
                Severity.MEDIUM -> "中严重度"
                Severity.HIGH -> "高严重度"
            }
        }
    }
}
