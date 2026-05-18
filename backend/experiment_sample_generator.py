#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
云端事故复盘实验 - 样本生成脚本
生成30个（或更多）符合实验要求的结构化事故样本（每种类型5个）
支持直接用于 /api/accident/analyze 的 payload
实验分组：通过 experimentGroup=A/B/C 切换
"""

import json
from typing import List, Dict, Any

EXPERIMENT_TYPES = [
    "驾驶员反应不足",
    "AEB触发延迟或未触发",
    "TTC过低导致碰撞风险",
    "驾驶员接管不足",
    "环境因素干扰",
    "多因素共同作用",
]

def generate_sample(event_id: str, event_type: str, idx: int) -> Dict[str, Any]:
    """生成单个实验样本 payload"""
    base = {
        "eventId": event_id,
        "eventType": event_type,
        "timeMillis": 1740000000000 + idx * 100000,
        "location": f"测试路段{idx % 5 + 1}号",
        "summary": f"{event_type}导致的{ '追尾' if '反应' in event_type or 'AEB' in event_type else '碰撞' }事故",
        "triggerReasons": ["TTC过低", "制动延迟"] if "TTC" in event_type else ["驾驶员未及时响应"],
        "severity": ["LOW", "MEDIUM", "HIGH"][idx % 3],
        "autoDrivingState": "L2_ASSIST" if idx % 2 == 0 else "AUTONOMOUS",
        "telemetry": [
            {"tMs": -1500, "speedKph": 65.0, "axMS2": -2.1, "brake": 30, "steerDeg": 2.5},
            {"tMs": -800, "speedKph": 58.0, "axMS2": -4.5, "brake": 65, "steerDeg": 1.8},
            {"tMs": -200, "speedKph": 42.0, "axMS2": -7.2, "brake": 90, "steerDeg": 0.5},
        ],
        "responsibility": {
            "driverFactor": 60 if "驾驶员" in event_type else 30,
            "systemFactor": 25 if "AEB" in event_type or "TTC" in event_type else 40,
            "environmentFactor": 15,
            "conclusion": f"主要责任在{'驾驶员' if '驾驶员' in event_type else '系统'}",
            "reasons": ["反应时间过长", "AEB未及时触发"] if "AEB" in event_type else ["注意力分散"]
        },
        "environment": {
            "weather": "雨天" if "环境" in event_type else "晴天",
            "road": "湿滑" if "环境" in event_type else "干燥",
            "obstacle": "前车",
            "laneMarking": "清晰"
        },
        "decisionTrace": {
            "sensorInput": "前向雷达+摄像头融合",
            "perception": "前车距离过近",
            "planning": "紧急制动决策",
            "control": "AEB介入或驾驶员接管"
        },
        "experimentGroup": "C"  # 默认C组，可手动改为 A 或 B
    }
    return base


def generate_all_samples(count_per_type: int = 5) -> List[Dict[str, Any]]:
    samples = []
    for t_idx, etype in enumerate(EXPERIMENT_TYPES):
        for i in range(count_per_type):
            eid = f"S{t_idx+1:02d}-{i+1:02d}"
            samples.append(generate_sample(eid, etype, t_idx * count_per_type + i))
    return samples


if __name__ == "__main__":
    samples = generate_all_samples(5)
    print(f"共生成 {len(samples)} 个实验样本")
    # 输出前3个示例
    for s in samples[:3]:
        print(json.dumps(s, ensure_ascii=False, indent=2))
    # 保存完整文件
    with open("experiment_samples.json", "w", encoding="utf-8") as f:
        json.dump(samples, f, ensure_ascii=False, indent=2)
    print("\n已保存至 experiment_samples.json，可用于批量调用API进行A/B/C三组实验")