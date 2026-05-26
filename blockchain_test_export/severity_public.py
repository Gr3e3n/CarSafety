"""
将端侧严重度推断结果转为对外/上链用的精简视图（不暴露模型管线、推断模式等内部信息）。
"""
from __future__ import annotations

from typing import Any, Dict, List, Mapping

_SEVERITY_CN = {
    "Fatal": "致命",
    "Serious": "重伤",
    "Slight": "轻微",
}


def _top_confidence_pct(probabilities: Mapping[str, Any]) -> int:
    if not probabilities:
        return 0
    return int(round(max(float(v) for v in probabilities.values()) * 100))


def _business_highlights(sev: Mapping[str, Any]) -> List[str]:
    """仅从派生/遥测提炼可读要点，不含模型映射说明。"""
    out: List[str] = []
    df = sev.get("derivedFeatures") or {}
    if df.get("avgSpeedLast3sKph") is not None:
        out.append(f"事故前3秒均速 {df['avgSpeedLast3sKph']} km/h")
    if df.get("maxDecelerationMS2") is not None and float(df["maxDecelerationMS2"]) <= -6:
        out.append(f"峰值减速度 {df['maxDecelerationMS2']} m/s²")
    if df.get("brakePeak") is not None and int(df["brakePeak"]) >= 60:
        out.append(f"峰值制动 {df['brakePeak']}%")
    ttc = df.get("ttcAtBrakeStart")
    if ttc is not None and float(ttc) >= 0:
        out.append(f"制动时距约 {ttc} s")
    weather = df.get("weatherConditions")
    if weather in (2, 3, 5, 7):
        labels = {2: "雨天", 3: "雪天", 5: "雨伴大风", 7: "雾天"}
        out.append(f"天气：{labels.get(weather, '不利')}")
    if df.get("isNight"):
        out.append("夜间行驶")
    return out[:5]


def to_public_severity(sev: Mapping[str, Any]) -> Dict[str, Any]:
    predicted = sev.get("predictedSeverity") or ""
    label = _SEVERITY_CN.get(predicted, predicted)
    score = sev.get("severityScore", 0)
    confidence = _top_confidence_pct(sev.get("probabilities") or {})
    is_collision = sev.get("inferenceMode") == "injury_model"

    if is_collision:
        summary = f"碰撞严重度评估为「{label}」，综合指数 {score}/100（置信度约 {confidence}%）。"
    else:
        summary = f"运营风险等级为「{label}」，风险指数 {score}/100（置信度约 {confidence}%）。"

    return {
        "predictedSeverity": predicted,
        "severityScore": score,
        "summary": summary,
        "highlights": _business_highlights(sev),
    }
