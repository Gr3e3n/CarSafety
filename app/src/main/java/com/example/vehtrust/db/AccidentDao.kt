package com.example.vehtrust.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AccidentDao {

    // ── 事故事件 ──────────────────────────────────────────

    /** 只追加，已有则忽略（IGNORE 保证只追加语义） */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: AccidentEventEntity)

    @Query("SELECT * FROM accident_events ORDER BY timeMillis DESC")
    fun observeEvents(): LiveData<List<AccidentEventEntity>>

    @Query("SELECT * FROM accident_events ORDER BY timeMillis DESC")
    suspend fun listEvents(): List<AccidentEventEntity>

    @Query("SELECT * FROM accident_events WHERE id = :id LIMIT 1")
    suspend fun getEvent(id: String): AccidentEventEntity?

    // ── 遥测点 ────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTelemetry(points: List<TelemetryEntity>)

    @Query("SELECT * FROM telemetry_points WHERE eventId = :eventId ORDER BY tMs ASC")
    suspend fun getTelemetry(eventId: String): List<TelemetryEntity>

    // ── 责任界定 ──────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponsibility(r: ResponsibilityEntity)

    @Query("SELECT * FROM responsibility_results WHERE eventId = :eventId LIMIT 1")
    suspend fun getResponsibility(eventId: String): ResponsibilityEntity?

    // ── 可信存证 ──────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvidence(e: EvidenceEntity)

    @Query("SELECT * FROM evidence_records WHERE eventId = :eventId LIMIT 1")
    suspend fun getEvidence(eventId: String): EvidenceEntity?
}
