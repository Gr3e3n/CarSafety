#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
云端事故复盘实验 — 结构化样本生成器（与 VehTrust 工程对齐）

- 事故类型覆盖 AccidentModels.kt 中全部 8 种枚举（含碰撞、智驾故障与六类实验子类）。
- 默认生成 50 条（对齐《云端事故复盘能力实验说明文档》§4.1 推荐规模）。
- 每条含 telemetry / responsibility / environment / decisionTrace，及可选 derivedSignals（对齐 §4.3）。
- 另含 scenarioId、scenarioName 仅作实验元数据（后端 Pydantic 忽略未知顶层字段；App 批量 POST 原样透传）。

用法:
  python generate_experiment_samples.py
  python generate_experiment_samples.py --count 500 --output experiment_samples_500.json
  # 默认 50 条 -> experiment_samples_realistic_50.json（可复制到 app/assets，仅 App 批量实验需要）
"""

from __future__ import annotations

import argparse
import json
import random
from collections import Counter
from datetime import datetime, timezone
from pathlib import Path
from typing import Any, Dict, List, Tuple

REACTION_DANGER_MS = 1200
REACTION_WARNING_MS = 800

WEATHER_WEIGHTS_COLLISION = [
    ("晴", 22),
    ("多云", 24),
    ("小雨", 18),
    ("雾", 14),
    ("大雨", 12),
    ("冰雪", 10),
]
WEATHER_WEIGHTS_AUTOPILOT = [
    ("晴", 16),
    ("多云", 20),
    ("小雨", 18),
    ("雾", 18),
    ("大雨", 16),
    ("侧风偏大", 12),
]

ROAD_WEIGHTS = [
    ("城市主干道", 22),
    ("快速路", 20),
    ("高架匝道", 14),
    ("隧道入口", 10),
    ("施工借道段", 12),
    ("长下坡接弯", 8),
    ("园区内部路", 6),
    ("收费站广场", 8),
]

OBSTACLE_HIGH = [
    ("前车急刹制动灯亮起", 26),
    ("静止排队车龙末端", 20),
    ("右侧车辆强行切入", 16),
    ("外卖非机动车占道", 14),
    ("行人突然横穿", 14),
    ("施工区域未封闭完全", 10),
]
OBSTACLE_MEDIUM = [
    ("前方车辆减速未打灯", 24),
    ("相邻车道变道挤压", 20),
    ("行人靠近路缘", 14),
    ("施工锥桶偏移", 16),
    ("慢车占道", 16),
    ("匝道汇流冲突", 10),
]
OBSTACLE_LOW = [
    ("前方异物（纸箱）", 20),
    ("施工锥桶偏移", 18),
    ("慢车占道", 16),
    ("无明显障碍", 18),
    ("路面抛洒物", 16),
    ("积水反光误判", 12),
]

LANE_MARKING = ["清晰", "模糊", "反光", "施工临时标线", "雨天反光", "夜间磨损不清"]

LOCATIONS = [
    "南京·雨花台区",
    "上海·浦东新区",
    "北京·亦庄",
    "杭州·滨江区",
    "深圳·南山区",
    "广州·黄埔区",
    "成都·高新区",
    "武汉·东湖高新区",
    "西安·高新区",
    "苏州·工业园区",
]

# 8 类 × 条数 = 50
TYPE_PLAN: List[Tuple[str, int]] = [
    ("COLLISION", 7),
    ("AUTOPILOT_FAULT", 6),
    ("DRIVER_SLOW_REACTION", 6),
    ("AEB_DELAY_OR_MISS", 6),
    ("TTC_LOW_RISK", 6),
    ("DRIVER_TAKEOVER_FAIL", 6),
    ("ENVIRONMENT_DISTURB", 6),
    ("MULTI_FACTOR", 7),
]

SCENARIO_NAMES: Dict[str, List[str]] = {
    "COLLISION": [
        "追尾碰撞（跟车过近）",
        "侧向刮擦（变道冲突）",
        "静止障碍碰撞",
        "倒车碰撞非机动车",
        "环岛内连环碰撞",
        "匝道末端减速不及",
        "隧道内低速追尾",
    ],
    "AUTOPILOT_FAULT": [
        "NOA 异常退出未提示",
        "横向控制振荡诱发偏离",
        "感知误检导致误制动",
        "规划轨迹突变",
        "传感器融合短时失效",
        "接管请求延迟",
    ],
    "DRIVER_SLOW_REACTION": [
        "前车急刹反应滞后",
        "夜间眩光下制动偏晚",
        "分心使用手机",
        "跟车距离过近",
        "雨雾视距不足仍高速接近",
        "匝道汇流未预留时距",
    ],
    "AEB_DELAY_OR_MISS": [
        "AEB 触发过晚",
        "静态目标未识别",
        "FCW 报警后 AEB 未跟进",
        "低速行人场景漏检",
        "施工区域未减速",
        "相邻车道侵入未抑制",
    ],
    "TTC_LOW_RISK": [
        "拥堵跟车时距 <1.2s",
        "cut-in 后 TTC 骤降",
        "下坡车速过快导致 TTC 不足",
        "弯道出口前车减速",
        "绿灯起步抢行导致 TTC 不足",
        "大车遮挡后突然出现的慢车",
    ],
    "DRIVER_TAKEOVER_FAIL": [
        "系统请求接管后 5s 内未接管",
        "双手脱离方向盘",
        "接管后方向修正过量",
        "L3 降级时驾驶员未响应",
        "拥堵跟车系统退出未制动",
        "驾驶员误踩加速踏板",
    ],
    "ENVIRONMENT_DISTURB": [
        "团雾突发能见度骤降",
        "路面结冰侧滑",
        "强光逆光相机失效",
        "横风高架桥面偏移",
        "积水水滑",
        "夜间无照明行人横穿",
    ],
    "MULTI_FACTOR": [
        "人-车-路-环境多因素叠加",
        "施工改道+感知降级+驾驶员分心",
        "恶劣天气+跟车过近+AEB 保守策略",
        "匝道+大车遮挡+前车急刹",
        "夜间+眩光+非机动车占道",
        "系统提示弱+环境复杂+接管犹豫",
        "多车交互+信号延迟+制动不足",
    ],
}


def weighted_choice(weights: List[Tuple[str, int]]) -> str:
    total = sum(w for _, w in weights)
    r = random.uniform(0, total)
    upto = 0.0
    for choice, weight in weights:
        if upto + weight >= r:
            return choice
        upto += weight
    return weights[-1][0]


def generate_telemetry(event_type: str, severity: str) -> List[Dict[str, Any]]:
    points: List[Dict[str, Any]] = []
    base_speed = random.uniform(48, 92)
    t = -4200

    if severity == "HIGH":
        decel_profile = [-1.8, -3.2, -5.8, -8.9, -9.7, -7.5]
    elif severity == "MEDIUM":
        decel_profile = [-1.2, -2.4, -4.1, -6.3, -5.8]
    else:
        decel_profile = [-0.9, -1.8, -3.5, -4.2]

    steer_heavy = event_type in ("TTC_LOW_RISK", "DRIVER_SLOW_REACTION", "MULTI_FACTOR", "COLLISION")
    for i, ax in enumerate(decel_profile):
        t_ms = t + i * 650
        brake = min(100, int(20 + abs(ax) * 9))
        steer = random.uniform(-4.2, 4.5) if steer_heavy else random.uniform(-2.0, 2.4)
        points.append(
            {
                "tMs": t_ms,
                "speedKph": round(max(15.0, base_speed - i * 9.2), 1),
                "axMS2": round(ax, 2),
                "brake": brake,
                "steerDeg": round(steer, 1),
            }
        )

    if severity == "HIGH":
        points.append(
            {
                "tMs": points[-1]["tMs"] + 180,
                "speedKph": round(points[-1]["speedKph"] * 0.35, 1),
                "axMS2": round(random.uniform(-11.5, -9.2), 2),
                "brake": 100,
                "steerDeg": round(random.uniform(-2.5, 3.8), 1),
            }
        )
    return points


def _conclusion_for(event_type: str) -> str:
    return {
        "COLLISION": "责任倾向：碰撞后果以纵向安全距离不足或突发障碍为主，需结合行车记录仪与路权认定",
        "AUTOPILOT_FAULT": "责任倾向：智驾系统异常/降级为主，驾驶员接管响应为辅",
        "DRIVER_SLOW_REACTION": "责任倾向：驾驶员主要责任（反应不足/跟车过近）",
        "AEB_DELAY_OR_MISS": "责任倾向：系统与驾驶员共同责任",
        "TTC_LOW_RISK": "责任倾向：驾驶员与系统共同责任",
        "DRIVER_TAKEOVER_FAIL": "责任倾向：驾驶员接管不足为主",
        "ENVIRONMENT_DISTURB": "责任倾向：环境扰动显著，与人因/系统策略交织（多因素）",
        "MULTI_FACTOR": "责任倾向：多因素共同作用（需结合更多证据）",
    }.get(event_type, "责任倾向：需结合人-车-路-环境综合认定")


def generate_responsibility(
    event_type: str,
    severity: str,
    telemetry: List[Dict[str, Any]],
    reaction_ms: int,
    brake_rise_ms: int,
    aeb_delay_ms: int,
    ttc: float,
    brake_effective: bool,
) -> Dict[str, Any]:
    driver = 28
    if reaction_ms >= REACTION_DANGER_MS:
        driver += 42
    elif reaction_ms >= REACTION_WARNING_MS:
        driver += 26
    else:
        driver += 12
    if not brake_effective:
        driver += 14
    if 0 < ttc <= 1.5:
        driver += 12

    system = 14
    if event_type in ("AUTOPILOT_FAULT", "AEB_DELAY_OR_MISS", "DRIVER_TAKEOVER_FAIL"):
        system += 32
    if -5000 <= aeb_delay_ms <= -400:
        system += 14
    if aeb_delay_ms == -1 and event_type in ("COLLISION", "AEB_DELAY_OR_MISS"):
        system += 12
    if event_type == "AUTOPILOT_FAULT":
        system += 18

    env = 10
    if event_type == "ENVIRONMENT_DISTURB":
        env += 22
    if event_type == "MULTI_FACTOR":
        env += 10
    if event_type == "COLLISION" and severity == "HIGH":
        env += 4

    if event_type in ("AUTOPILOT_FAULT", "DRIVER_TAKEOVER_FAIL"):
        driver = max(18, driver - 18)

    total = max(1, driver + system + env)
    d = int(driver * 100 / total)
    s = int(system * 100 / total)
    e = max(0, 100 - d - s)

    peak_ax = max(p["axMS2"] for p in telemetry)
    aeb_txt = f"{aeb_delay_ms}ms" if aeb_delay_ms != -1 else "未检测到系统主动介入"
    ttc_txt = "跟车时距不足，风险极高" if ttc < 1.5 else "时距尚可"
    peak_line = (
        f"【峰值减速度】{round(peak_ax, 2)} m/s² — 极强制动（碰撞级）"
        if severity == "HIGH"
        else "强制动"
    )

    reasons = [
        f"【反应时间】{reaction_ms}ms — "
        + (
            "过长（>1200ms），显著增加碰撞风险"
            if reaction_ms >= REACTION_DANGER_MS
            else "偏长（>800ms），建议关注注意力状态"
            if reaction_ms >= REACTION_WARNING_MS
            else "处于可接受区间，仍建议复盘人因"
        ),
        f"【制动上升时间】{brake_rise_ms}ms（踏板 20%→80%）— "
        + ("制动果断" if brake_effective else "制动迟缓，上升过慢"),
        peak_line,
        f"【AEB/系统介入】{aeb_txt}",
        f"【制动时 TTC 估算】{ttc}s — {ttc_txt}",
        f"【事故前 3s 均速】{round(sum(p['speedKph'] for p in telemetry[-5:]) / max(1, min(5, len(telemetry))), 1)} km/h",
    ]

    return {
        "driverFactor": d,
        "systemFactor": s,
        "environmentFactor": e,
        "conclusion": _conclusion_for(event_type),
        "reasons": reasons,
    }


def generate_environment(event_type: str, severity: str) -> Dict[str, str]:
    wsrc = WEATHER_WEIGHTS_AUTOPILOT if event_type in ("AUTOPILOT_FAULT", "DRIVER_TAKEOVER_FAIL") else WEATHER_WEIGHTS_COLLISION
    weather = weighted_choice(wsrc)
    road = weighted_choice(ROAD_WEIGHTS)
    obstacle = weighted_choice(
        OBSTACLE_HIGH if severity == "HIGH" else (OBSTACLE_MEDIUM if severity == "MEDIUM" else OBSTACLE_LOW)
    )
    lane = random.choice(LANE_MARKING)
    return {"weather": weather, "road": road, "obstacle": obstacle, "laneMarking": lane}


def generate_decision_trace(event_type: str, env: Dict[str, str]) -> Dict[str, str]:
    road = env["road"]
    if event_type == "AUTOPILOT_FAULT":
        return {
            "sensorInput": "11V5R+前向激光雷达融合（含高精地图车道）",
            "perception": "车道线置信度波动；目标跟踪 ID 切换导致纵向目标丢失 120ms",
            "planning": "规划请求最小风险轨迹重算；触发 MRM 前的横向振荡抑制不足",
            "control": "横向力矩与制动协调延迟；降级提示与制动力建立不同步",
        }
    if event_type == "COLLISION":
        return {
            "sensorInput": "前向毫米波雷达 + 摄像头融合",
            "perception": f"本车与目标相对运动估计；{env['obstacle']} 场景下 TTC 估计更新频率正常",
            "planning": f"规划在{road}保持车道并请求舒适制动；未触发紧急换道",
            "control": "纵向制动请求建立；碰撞前约 0.3s 达到最大制动能力",
        }
    if event_type in ("DRIVER_SLOW_REACTION", "AEB_DELAY_OR_MISS", "TTC_LOW_RISK"):
        return {
            "sensorInput": "前向毫米波雷达 + 摄像头融合",
            "perception": f"前车距离与相对速度估计；{env['obstacle']} 风险权重上调",
            "planning": f"规划在{road}场景下保持车道并请求减速；目标时距{random.uniform(1.0, 1.75):.2f}s 振荡",
            "control": "横纵向解耦控制；制动请求爬升速率受舒适性与 AEB 仲裁影响",
        }
    return {
        "sensorInput": "多传感器融合（含侧向雷达/视觉）",
        "perception": "复杂交互场景下多目标关联；短时遮挡恢复",
        "planning": "规划请求人工接管并执行风险最小化减速",
        "control": "制动与转向执行机构响应；接管后控制权重切换",
    }


def _trigger_reasons(event_type: str) -> List[str]:
    m = {
        "COLLISION": ["已发生接触或显著减速碰撞", "行车记录仪/EDR 可佐证碰撞瞬间车速与制动"],
        "AUTOPILOT_FAULT": ["智驾功能异常退出或控制振荡", "系统日志记录降级原因码"],
        "DRIVER_SLOW_REACTION": ["制动反应延迟", "驾驶员注意力分散或跟车过近"],
        "AEB_DELAY_OR_MISS": ["AEB 触发偏晚或未触发", "FCW 与 AEB 仲裁链可追溯"],
        "TTC_LOW_RISK": ["跟车时距持续低于安全阈值", "相对速度未充分消化"],
        "DRIVER_TAKEOVER_FAIL": ["系统请求接管后响应不足", "方向盘脱手或接管动作不当"],
        "ENVIRONMENT_DISTURB": ["能见度/附着系数突变", "道路几何或临时交通组织变化"],
        "MULTI_FACTOR": ["人因、系统策略与环境因素同时显著", "单一归因不足以解释全链路"],
    }
    base = m.get(event_type, ["综合风险上升"])
    extra = "车载边缘节点已上传摘要哈希（模拟链上存证索引）"
    return base + [extra]


def _auto_state(event_type: str) -> str:
    if event_type == "AUTOPILOT_FAULT":
        return random.choice(["AUTONOMOUS", "AUTONOMOUS", "L2_ASSIST"])
    if event_type == "DRIVER_TAKEOVER_FAIL":
        return random.choice(["AUTONOMOUS", "L2_ASSIST"])
    return random.choice(["L2_ASSIST", "AUTONOMOUS", "MANUAL"])


def generate_sample(global_idx: int, event_type: str, scenario_name: str) -> Dict[str, Any]:
    severity = random.choice(["HIGH", "MEDIUM", "LOW"]) if event_type != "MULTI_FACTOR" else random.choice(["HIGH", "HIGH", "MEDIUM"])
    telemetry = generate_telemetry(event_type, severity)
    environment = generate_environment(event_type, severity)
    decision = generate_decision_trace(event_type, environment)

    reaction_ms = (
        random.randint(720, 1480)
        if event_type in ("DRIVER_SLOW_REACTION", "DRIVER_TAKEOVER_FAIL", "COLLISION")
        else random.randint(420, 980)
    )
    if event_type == "DRIVER_TAKEOVER_FAIL":
        reaction_ms = max(reaction_ms, 920)
    brake_rise_ms = random.randint(280, 640)
    if event_type == "AEB_DELAY_OR_MISS":
        aeb_delay_ms = random.choice([-320, -680, -950, -1450, -1])
    elif event_type == "AUTOPILOT_FAULT":
        aeb_delay_ms = random.choice([-280, -540, -880, -1])
    else:
        aeb_delay_ms = random.randint(-820, -140)

    ttc = round(random.uniform(0.65, 1.45), 2) if event_type == "TTC_LOW_RISK" else round(random.uniform(1.5, 3.15), 2)
    brake_effective = (
        random.random() > 0.55
        if event_type in ("DRIVER_SLOW_REACTION", "TTC_LOW_RISK", "COLLISION")
        else random.random() > 0.35
    )

    responsibility = generate_responsibility(
        event_type, severity, telemetry, reaction_ms, brake_rise_ms, aeb_delay_ms, ttc, brake_effective
    )

    derived_signals: Dict[str, Any] = {
        "reactionTimeMs": reaction_ms,
        "brakeRiseTimeMs": brake_rise_ms,
        "aebDelayMs": aeb_delay_ms,
        "ttcAtBrakeSeconds": ttc,
        "brakeEffective": brake_effective,
        "maxSteerLast2sDeg": round(random.uniform(2.1, 9.5), 1),
        "driverTakeoverSummary": (
            "系统发出接管请求后驾驶员未及时握盘"
            if event_type == "DRIVER_TAKEOVER_FAIL"
            else "无明确接管请求或已恢复手动"
        ),
        "riskPredictionSummary": f"端侧风险模型：{event_type} 置信偏高，建议云端复盘侧重证据链一致性",
    }

    dt = datetime.now(timezone.utc).strftime("%Y%m%d")
    event_id = f"EXP-{dt}-{global_idx:03d}"

    summary = (
        f"【{scenario_name}】{event_type}，{severity}等级；"
        f"发生于{environment['road']}（{environment['weather']}），涉及{environment['obstacle']}"
    )

    return {
        "eventId": event_id,
        "eventType": event_type,
        "scenarioId": f"SCH-{event_type}-{global_idx:03d}",
        "scenarioName": scenario_name,
        "timeMillis": 1_740_000_000_000 + global_idx * 139_000,
        "location": random.choice(LOCATIONS),
        "summary": summary,
        "triggerReasons": _trigger_reasons(event_type),
        "severity": severity,
        "autoDrivingState": _auto_state(event_type),
        "telemetry": telemetry,
        "responsibility": responsibility,
        "environment": environment,
        "decisionTrace": decision,
        "derivedSignals": derived_signals,
        "experimentGroup": "C",
    }


def generate_all_samples() -> List[Dict[str, Any]]:
    """默认 50 条：与 TYPE_PLAN 一致（8 类固定配比）。"""
    samples: List[Dict[str, Any]] = []
    idx = 1
    for etype, count in TYPE_PLAN:
        names = SCENARIO_NAMES[etype]
        for j in range(count):
            scenario_name = names[j % len(names)]
            samples.append(generate_sample(idx, etype, scenario_name))
            idx += 1
    return samples


def generate_n_samples(total: int, seed: int) -> List[Dict[str, Any]]:
    """按 8 类事故轮换模拟 total 条，eventId 为 SIM-日期-序号。"""
    random.seed(seed)
    type_cycle: List[str] = []
    for etype, n in TYPE_PLAN:
        type_cycle.extend([etype] * max(1, n))
    samples: List[Dict[str, Any]] = []
    dt = datetime.now(timezone.utc).strftime("%Y%m%d")
    for i in range(1, total + 1):
        etype = type_cycle[(i - 1) % len(type_cycle)]
        names = SCENARIO_NAMES[etype]
        scenario = names[(i - 1) % len(names)]
        if total > 50:
            scenario = f"{scenario}（#{i}）"
        s = generate_sample(i, etype, scenario)
        s["eventId"] = f"SIM-{dt}-{i:04d}"
        s["scenarioId"] = f"SIM-{etype}-{i:04d}"
        s["experimentGroup"] = "C"
        samples.append(s)
    return samples


def main() -> None:
    ap = argparse.ArgumentParser(description="模拟事故样本（可直接喂给 /api/accident/analyze）")
    ap.add_argument("--count", type=int, default=50, help="条数，默认 50；大批量用 500")
    ap.add_argument("--seed", type=int, default=20260519)
    ap.add_argument(
        "--output",
        type=Path,
        default=None,
        help="输出 JSON 路径，默认 50 条为 experiment_samples_realistic_50.json",
    )
    args = ap.parse_args()
    random.seed(args.seed)

    if args.count == 50 and args.output is None:
        samples = generate_all_samples()
        out = Path(__file__).resolve().parent / "experiment_samples_realistic_50.json"
    else:
        samples = generate_n_samples(args.count, args.seed)
        out = args.output or Path(__file__).resolve().parent / f"experiment_samples_{args.count}.json"

    out = out if out.is_absolute() else Path(__file__).resolve().parent / out
    out.write_text(json.dumps(samples, ensure_ascii=False, indent=2), encoding="utf-8")
    ex0 = Path(__file__).resolve().parent / "experiment_sample_example_first.json"
    ex0.write_text(json.dumps(samples[0], ensure_ascii=False, indent=2), encoding="utf-8")
    print("已生成", len(samples), "条样本 ->", out)
    print("类型分布:", dict(Counter(s["eventType"] for s in samples)))


if __name__ == "__main__":
    main()
