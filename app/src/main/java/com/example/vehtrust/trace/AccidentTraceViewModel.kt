package com.example.vehtrust.trace

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

    /** 轻量时序模型推断（后续可替换为真正的 TFLite / PyTorch Mobile 模型） */
    fun analyzeWithDeepLearning(bundle: AccidentDetailBundle): DeepLearningResult {
        val metrics = analyzeMetrics(bundle)
        return DeepLearningAnalyzer.analyze(
            event = bundle.event,
            telemetry = bundle.telemetry10sBefore,
            env = bundle.environmentSnapshot,
            trace = bundle.decisionTrace,
            metrics = metrics,
        )
    }

    /** AI 事故原因分析（优先远端 OpenAI 服务，失败则回退到本地规则摘要） */
    suspend fun analyzeByAi(
        bundle: AccidentDetailBundle,
        metrics: ResponsibilityAnalyzer.DetailedMetrics? = null,
        severityPredictionSummary: String? = null,
    ): AiAccidentAnalysisResult =
        OpenAiAnalysisApi.analyzeAccident(bundle, metrics, severityPredictionSummary)

    /** 事故严重度预测（仅使用训练模型导出的端侧本地参数进行离线推断） */
    suspend fun analyzeCollisionSeverity(bundle: AccidentDetailBundle): CollisionSeverityPredictionResult =
        CollisionSeverityApi.predictCollisionSeverity(getApplication(), bundle)

    /** ↓ 新增：上链存证，返回结果 */
    suspend fun uploadToBlockchain(bundle: AccidentDetailBundle): BlockchainApi.UploadResult =
        BlockchainApi.uploadAccident(bundle)

    /** 批量云端实验：读取 assets 中 50 条样本，按当前 A/B/C 与消融开关逐条请求并保存 JSON */
    suspend fun runCloudExperimentBatchFromAssets(context: android.content.Context): Result<String> =
        ExperimentBatchRunner.runAndSave(context)
}
