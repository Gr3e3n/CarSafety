package com.example.carscreen.trace

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class AccidentTraceViewModel(app: Application) : AndroidViewModel(app) {

    init {
        AccidentRepository.initWithContext(app)
    }

    /** 事件列表（Room 历史 + 运行时新触发，自动刷新） */
    val events: LiveData<List<AccidentEvent>> = AccidentRepository.events

    /** 加载事件详情 */
    fun loadDetail(eventId: String): AccidentDetailBundle =
        AccidentRepository.loadDetail(eventId)

    /** 生成可信存证 */
    fun generateEvidence(bundle: AccidentDetailBundle): EvidenceRecord =
        AccidentRepository.generateEvidence(bundle)

    /**
     * 计算详细可解释责任指标。
     * aebTriggerTMs: 真实接入时从 AEB 总线传入触发时刻（ms）；null=自动估算。
     */
    fun analyzeMetrics(
        bundle: AccidentDetailBundle,
        aebTriggerTMs: Int? = null,
    ): ResponsibilityAnalyzer.DetailedMetrics =
        ResponsibilityAnalyzer.analyze(bundle.event, bundle.telemetry10sBefore, aebTriggerTMs)

    /** ↓ 新增：上链存证，返回结果 */
    suspend fun uploadToBlockchain(bundle: AccidentDetailBundle): BlockchainApi.UploadResult =
        BlockchainApi.uploadAccident(bundle)
}