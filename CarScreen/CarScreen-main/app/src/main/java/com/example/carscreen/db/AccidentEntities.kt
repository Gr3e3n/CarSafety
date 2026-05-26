package com.example.carscreen.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 事故事件主表（只追加，不删除不更新）
 */
@Entity(tableName = "accident_events")
data class AccidentEventEntity(
    @PrimaryKey val id: String,
    val type: String,           // COLLISION / AUTOPILOT_FAULT
    val timeMillis: Long,
    val locationText: String,
    val triggerReasonsJson: String,  // JSON 数组
    val severity: String,       // LOW / MEDIUM / HIGH
    val autoDrivingState: String,
    val summary: String,
    val createdAt: Long = System.currentTimeMillis(),
)

/**
 * 遥测点表：关联事件ID，批量插入，只追加
 */
@Entity(tableName = "telemetry_points", primaryKeys = ["eventId", "tMs"])
data class TelemetryEntity(
    val eventId: String,
    val tMs: Int,          // 相对事故时刻（毫秒），负值=事故前
    val speedKph: Float,
    val axMS2: Float,
    val brake: Int,
    val steerDeg: Float,
)

/**
 * 责任界定结果表（每个事件一条，只追加）
 */
@Entity(tableName = "responsibility_results")
data class ResponsibilityEntity(
    @PrimaryKey val eventId: String,
    val driverFactor: Int,
    val systemFactor: Int,
    val environmentFactor: Int,
    val conclusion: String,
    val reasonsJson: String,       // JSON 数组
    // 详细可解释指标
    val reactionTimeMs: Int,       // 驾驶员反应时间（ms）
    val brakeRiseTimeMs: Int,      // 制动上升时间 20%→80%（ms）
    val maxDecelerationMS2: Float, // 最大减速度（m/s²）
    val aebDelayMs: Int,           // AEB 触发延迟估算（ms），-1=未触发
    val ttcAtBrakeStart: Float,    // 制动开始时 TTC 估算（s），-1=不可用
    val createdAt: Long = System.currentTimeMillis(),
)

/**
 * 可信存证表（只追加）
 */
@Entity(tableName = "evidence_records")
data class EvidenceEntity(
    @PrimaryKey val evidenceId: String,
    val eventId: String,
    val sha256: String,
    val timestampMillis: Long,
    val blockchainTxId: String,
    val signature: String,
)
