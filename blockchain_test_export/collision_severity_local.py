"""
端侧 collision severity 推断（与 CollisionSeverityApi.kt 对齐）。
读取 app/src/main/assets/collision_severity_model.json，不访问网络。
"""
from __future__ import annotations

import json
import math
from dataclasses import dataclass
from datetime import datetime
from pathlib import Path
from typing import Any, Dict, List, Mapping, MutableMapping, Optional

BRAKE_LIGHT_PCT = 20
BRAKE_FULL_PCT = 80
BRAKE_HARD_PCT = 60
AEB_ACCEL_THRESHOLD = -6.0

MODEL_PATH = (
    Path(__file__).resolve().parent.parent
    / "app"
    / "src"
    / "main"
    / "assets"
    / "collision_severity_model.json"
)

_RUNTIME_MODEL: Optional[Dict[str, Any]] = None

# 非 COLLISION：不走伤亡分类模型，用「运营/碰撞风险」分级（与 App 一致）
EVENT_RISK_BASE: Dict[str, int] = {
    "TTC_LOW_RISK": 55,
    "AEB_DELAY_OR_MISS": 52,
    "DRIVER_SLOW_REACTION": 48,
    "MULTI_FACTOR": 50,
    "DRIVER_TAKEOVER_FAIL": 42,
    "AUTOPILOT_FAULT": 38,
    "ENVIRONMENT_DISTURB": 35,
}
LABEL_DELTA = {"HIGH": 18, "MEDIUM": 8, "LOW": -12}
SERIOUS_THRESHOLD = 58


@dataclass
class TelemetryMetrics:
    avg_speed_last3s_kph: float = 0.0
    max_deceleration_ms2: float = 0.0
    ttc_at_brake_start: float = -1.0
    brake_effective: bool = False


def analyze_telemetry(telemetry: List[Mapping[str, Any]]) -> TelemetryMetrics:
    if not telemetry:
        return TelemetryMetrics()
    sorted_pts = sorted(telemetry, key=lambda p: int(p.get("tMs", 0)))
    danger_t = next(
        (int(p["tMs"]) for p in sorted_pts if float(p.get("axMS2", 0)) <= -3.0),
        int(sorted_pts[0]["tMs"]),
    )
    brake_start = next(
        (
            p
            for p in sorted_pts
            if int(p["tMs"]) >= danger_t and int(p.get("brake", 0)) >= BRAKE_LIGHT_PCT
        ),
        None,
    )
    max_decel = min(float(p.get("axMS2", 0)) for p in sorted_pts)
    ttc = -1.0
    if brake_start is not None and float(brake_start.get("speedKph", 0)) >= 5.0:
        v_ms = float(brake_start["speedKph"]) / 3.6
        ax = max(abs(float(brake_start.get("axMS2", 0))), 0.1)
        ttc = v_ms / ax
    last3 = [p for p in sorted_pts if int(p.get("tMs", 0)) >= -3000]
    avg3 = (
        sum(float(p.get("speedKph", 0)) for p in last3) / len(last3) if last3 else 0.0
    )
    brake_effective = False
    if brake_start is not None:
        bs_t = int(brake_start["tMs"])
        hard = next(
            (
                p
                for p in sorted_pts
                if int(p["tMs"]) >= bs_t and int(p.get("brake", 0)) >= BRAKE_HARD_PCT
            ),
            None,
        )
        brake_effective = hard is not None and (int(hard["tMs"]) - danger_t) <= 2000
    return TelemetryMetrics(
        avg_speed_last3s_kph=avg3,
        max_deceleration_ms2=max_decel,
        ttc_at_brake_start=ttc,
        brake_effective=brake_effective,
    )


def _infer_speed_limit(max_speed_kph: float) -> int:
    if max_speed_kph <= 25:
        return 20
    if max_speed_kph <= 40:
        return 30
    if max_speed_kph <= 55:
        return 40
    if max_speed_kph <= 70:
        return 50
    if max_speed_kph <= 90:
        return 60
    return 70


def _infer_weather(weather: Optional[str]) -> int:
    if not weather:
        return 1
    if "雾" in weather:
        return 7
    if "雪" in weather:
        return 3
    if "雨" in weather and "风" in weather:
        return 5
    if "雨" in weather:
        return 2
    if "风" in weather:
        return 4
    return 1


def _infer_road_surface(weather_code: int) -> int:
    if weather_code in (2, 5, 7):
        return 2
    if weather_code == 3:
        return 3
    return 1


def _weather_label(code: int) -> str:
    return {
        1: "晴/多云",
        2: "雨天",
        3: "雪天",
        4: "大风晴天",
        5: "雨天伴大风",
        7: "雾天",
        8: "其他天气",
        9: "未知天气",
    }.get(code, "未知")


def _softmax(logits: List[float]) -> List[float]:
    m = max(logits)
    exps = [math.exp(x - m) for x in logits]
    s = max(sum(exps), 1e-4)
    return [e / s for e in exps]


def _load_model() -> Dict[str, Any]:
    global _RUNTIME_MODEL
    if _RUNTIME_MODEL is None:
        with MODEL_PATH.open(encoding="utf-8-sig") as f:
            _RUNTIME_MODEL = json.load(f)
    return _RUNTIME_MODEL


def _evaluate_runtime(
    model: Dict[str, Any],
    numeric: Dict[str, float],
    categorical: Dict[str, str],
) -> List[float]:
    logits = list(model["base_logits"])
    for name, feat in model["numeric_features"].items():
        val = numeric.get(name, feat["base_value"])
        scale = feat["scale"] if feat["scale"] != 0 else 1.0
        delta = (val - feat["base_value"]) / scale
        for i, coef in enumerate(feat["coefficients"]):
            logits[i] += coef * delta
    for name, feat in model["categorical_features"].items():
        val = categorical.get(name, feat["default_value"])
        delta = feat["deltas"].get(val) or feat["deltas"].get(feat["default_value"])
        if not delta:
            delta = [0.0] * len(logits)
        for i, contrib in enumerate(delta):
            logits[i] += contrib
    return logits


def _operational_risk_score(
    sample: Mapping[str, Any], metrics: TelemetryMetrics
) -> int:
    event_type = sample.get("eventType") or ""
    base = EVENT_RISK_BASE.get(event_type, 40)
    base += LABEL_DELTA.get(sample.get("severity") or "MEDIUM", 0)

    ds = sample.get("derivedSignals") or {}
    rt = ds.get("reactionTimeMs")
    if isinstance(rt, (int, float)):
        if rt > 1200:
            base += 12
        elif rt > 800:
            base += 6

    ttc = ds.get("ttcAtBrakeSeconds")
    if ttc is None and metrics.ttc_at_brake_start >= 0:
        ttc = metrics.ttc_at_brake_start
    if isinstance(ttc, (int, float)):
        if ttc < 1.5:
            base += 10
        elif ttc < 2.5:
            base += 5

    if metrics.max_deceleration_ms2 <= -8.0:
        base += 8
    if metrics.avg_speed_last3s_kph >= 60.0:
        base += 6
    if metrics.brake_effective:
        base -= 4

    return max(25, min(72, base))


def _probs_non_collision(score: int) -> Dict[str, float]:
    """非碰撞不输出 Fatal 主类，仅保留低比例占位。"""
    if score >= SERIOUS_THRESHOLD:
        serious = min(0.72, 0.48 + (score - SERIOUS_THRESHOLD) * 0.025)
        fatal = 0.05
        slight = max(0.15, 1.0 - serious - fatal)
    else:
        slight = min(0.88, 0.68 + (SERIOUS_THRESHOLD - score) * 0.008)
        serious = max(0.08, 0.28 - (SERIOUS_THRESHOLD - score) * 0.006)
        fatal = max(0.04, 1.0 - slight - serious)
    total = slight + serious + fatal
    return {
        "Fatal": round(fatal / total, 6),
        "Serious": round(serious / total, 6),
        "Slight": round(slight / total, 6),
    }


def _predict_non_collision(
    sample: Mapping[str, Any],
    metrics: TelemetryMetrics,
    derived: Dict[str, Any],
) -> Dict[str, Any]:
    event_type = sample.get("eventType") or ""
    score = _operational_risk_score(sample, metrics)
    probabilities = _probs_non_collision(score)
    predicted = max(probabilities, key=probabilities.get)
    fatal_pct = int(round(probabilities["Fatal"] * 100))
    serious_pct = int(round(probabilities["Serious"] * 100))
    slight_pct = int(round(probabilities["Slight"] * 100))

    narrative = (
        "当前事件类型并非碰撞（COLLISION），伤亡分类模型不参与主判；"
        "以下为基于事件子类、模拟等级（HIGH/MEDIUM/LOW）与遥测/派生信号（反应时间、TTC、减速度等）"
        "计算的运营风险严重度，用于近失、AEB、跟车等场景的风险分层展示。"
        f" 运营风险指数 {score}/100，映射为 {predicted}（Fatal {fatal_pct}% / "
        f"Serious {serious_pct}% / Slight {slight_pct}%）。"
    )
    factors = [
        f"推断模式：operational_risk（非碰撞），事件子类={event_type}",
        f"模拟触发等级={sample.get('severity', 'MEDIUM')}",
    ]
    ds = sample.get("derivedSignals") or {}
    if ds.get("reactionTimeMs"):
        factors.append(f"反应时间 {ds['reactionTimeMs']}ms")
    if ds.get("ttcAtBrakeSeconds") is not None:
        factors.append(f"TTC 约 {ds['ttcAtBrakeSeconds']}s")
    if metrics.max_deceleration_ms2 <= -8:
        factors.append(f"峰值减速度 {metrics.max_deceleration_ms2:.2f}m/s²")
    factors.append(
        f"概率分布：Fatal {fatal_pct}% / Serious {serious_pct}% / Slight {slight_pct}%"
    )

    return {
        "inferenceMode": "operational_risk",
        "predictedSeverity": predicted,
        "probabilities": probabilities,
        "modelHint": (
            "collision_severity · operational_risk-v1 · 非碰撞运营风险分级 · 端侧离线"
        ),
        "derivedFeatures": derived,
        "severityScore": score,
        "narrative": narrative,
        "keyFactors": factors[:6],
    }


def predict_collision_severity(sample: Mapping[str, Any]) -> Dict[str, Any]:
    """输入 experiment sample 字典，返回与 App CollisionSeverityPredictionResult 同构的 dict。"""
    event_type = sample.get("eventType") or "COLLISION"
    telemetry = list(sample.get("telemetry") or [])
    env = sample.get("environment") or {}
    time_ms = int(sample.get("timeMillis") or 0)
    metrics = analyze_telemetry(telemetry)
    max_speed = max((float(p.get("speedKph", 0)) for p in telemetry), default=0.0)
    brake_peak = max((int(p.get("brake", 0)) for p in telemetry), default=0)
    speed_limit = _infer_speed_limit(max_speed)
    vehicle_count = 2 if event_type == "COLLISION" else 1
    weather_code = _infer_weather(env.get("weather"))
    dt = datetime.fromtimestamp(time_ms / 1000.0)
    is_night = dt.hour in list(range(0, 6)) + list(range(22, 24))
    derived = {
        "vehicleCount": vehicle_count,
        "speedLimit": speed_limit,
        "weatherConditions": weather_code,
        "isNight": is_night,
        "avgSpeedLast3sKph": round(metrics.avg_speed_last3s_kph, 2),
        "maxDecelerationMS2": round(metrics.max_deceleration_ms2, 2),
        "brakePeak": brake_peak,
        "ttcAtBrakeStart": round(metrics.ttc_at_brake_start, 2)
        if metrics.ttc_at_brake_start >= 0
        else None,
        "brakeEffective": metrics.brake_effective,
    }

    if event_type != "COLLISION":
        return _predict_non_collision(sample, metrics, derived)

    model = _load_model()
    dow = dt.isoweekday() % 7 + 1  # Calendar: Sun=1 .. Sat=7
    is_weekend = 1 if dt.weekday() >= 5 else 0
    road_surface = _infer_road_surface(weather_code)
    numeric = {
        "number_of_vehicles": float(vehicle_count),
        "speed_limit": float(speed_limit),
        "collision_month": float(dt.month),
        "collision_day": float(dt.day),
        "collision_hour": float(dt.hour),
        "collision_minute": float(dt.minute),
        "is_weekend": float(is_weekend),
        "is_night": 1.0 if is_night else 0.0,
        "vehicle_record_count": float(vehicle_count),
    }
    categorical = {
        "day_of_week": str(dow),
        "weather_conditions": str(weather_code),
        "light_conditions": "4" if is_night else "1",
        "road_surface_conditions": str(road_surface),
        "urban_or_rural_area": "2" if speed_limit >= 50 else "1",
    }
    logits = _evaluate_runtime(model, numeric, categorical)
    probs_arr = _softmax(logits)
    classes = model["output_classes"]
    probabilities = {classes[i]: round(probs_arr[i], 6) for i in range(len(classes))}
    predicted = max(probabilities, key=probabilities.get)
    severity_score = int(
        round(
            probs_arr[0] * 100 + probs_arr[1] * 65 + probs_arr[2] * 25
        )
    )
    severity_score = max(0, min(100, severity_score))
    fatal_pct = int(round(probabilities["Fatal"] * 100))
    serious_pct = int(round(probabilities["Serious"] * 100))
    slight_pct = int(round(probabilities["Slight"] * 100))
    narrative = (
        "本次严重度结果由训练完成的 collision severity 表格模型导出参数在端侧离线计算完成，不依赖后端或网络。"
        f" 当前样本映射得到限速 {speed_limit}km/h、天气 {_weather_label(weather_code)}、"
        f"{'夜间光照' if is_night else '白天光照'}；"
        f"模型输出 Fatal {fatal_pct}% / Serious {serious_pct}% / Slight {slight_pct}% ，"
        f"最终判定为{predicted}，严重度指数 {severity_score}/100。"
        f" 事故前 3 秒均速 {metrics.avg_speed_last3s_kph:.1f}km/h、"
        f"峰值减速度 {metrics.max_deceleration_ms2:.2f}m/s²、峰值制动 {brake_peak}%。"
    )
    factors = [
        "本次结论来自训练好的 collision severity 模型导出参数，推断过程完全在本地执行。",
        f"模型输入映射包含：涉事车辆数={vehicle_count}、限速={speed_limit}km/h、"
        f"天气={_weather_label(weather_code)}、{'夜间' if is_night else '白天'}。",
    ]
    if max_speed >= 90:
        factors.append(f"事故前峰值速度 {max_speed:.1f}km/h，动能水平偏高。")
    if metrics.max_deceleration_ms2 <= -8:
        factors.append(
            f"峰值减速度 {metrics.max_deceleration_ms2:.2f}m/s²，达到碰撞级减速区间。"
        )
    if 0 <= metrics.ttc_at_brake_start <= 1.5:
        factors.append(f"制动时 TTC 仅 {metrics.ttc_at_brake_start:.1f}s，碰撞余度不足。")
    if brake_peak >= 80:
        factors.append(f"峰值制动 {brake_peak}%，事故前已出现高强度制动。")
    if weather_code in (2, 3, 5, 7):
        factors.append(f"天气被映射为 {_weather_label(weather_code)}，模型会提高风险权重。")
    if is_night:
        factors.append("夜间光照条件会抬升高严重度类别的本地预测概率。")
    factors.append(
        f"当前概率分布：Fatal {fatal_pct}% / Serious {serious_pct}% / Slight {slight_pct}%。"
    )
    return {
        "inferenceMode": "injury_model",
        "predictedSeverity": predicted,
        "probabilities": probabilities,
        "modelHint": f"{model['model_name']} · joblib-export-v1 · 训练模型导出参数 · 端侧离线推断",
        "derivedFeatures": derived,
        "severityScore": severity_score,
        "narrative": narrative,
        "keyFactors": factors[:6],
    }
