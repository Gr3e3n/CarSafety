package com.example.vehtrust.trace

/**
 * CarExt / 车机实车数据与 [TelemetryPoint] 的语义对齐说明（非运行时 API）。
 *
 * `EcarX-CarExt-SDK/info.txt` 中大量条目为 **模块常量、错误码、配置枚举**（如 `SS_ERROR_*`），
 * 与「单帧 EDR 遥测」不是一一对应关系；事故回放用的连续量应来自 **CarProperty** 或厂商扩展信号，
 * 再通过本工程字段落库。
 *
 * ### 与 info.txt 中 IADAS 命名的对应关系（概念层）
 * - [TelemetryPoint.aebActive] ↔ `ecarx.carext.vehicle.module.IADAS#AUTONOMOUS_EMERGENCY_BRAKING`
 *   配置项为功能开关；**实时介入状态** 需以整车制动请求/ESC 日志或 OEM 定义的 AEB active 信号为准，
 *   本 App 在模拟链路中用 [TelemetrySignalSynthesizer] 由纵向减速度+制动深度合成。
 * - [TelemetryPoint.fcwActiveLevel] ↔ `IADAS#FORWARD_COLLISION_WARN_SNVT`（灵敏度档位）及 FCW 报警状态；
 *   info 中为设置类属性，回放里用 0–3 表示「预警强度」占位，实车应映射 FCW 输出枚举或 HMI 等级。
 * - [TelemetryPoint.blinkerCode] ↔ 转向灯 / 危险报警灯相关 CarProperty（各车型 AreaId 不同）。
 *
 * ### 建议实车映射（底盘 / 车身动力学）
 * - 车速、纵向加速度、制动、方向盘转角：标准 CarProperty（PERF_VEHICLE_SPEED、ENGINE_SPEED 等组合推导或 OEM 扩展）。
 * - [TelemetryPoint.ayMS2]、[TelemetryPoint.yawRateDegS]：IMU / VSC 横摆与侧向加速度（若无则可用模型估计）。
 * - [TelemetryPoint.throttlePct]：加速踏板位置或驱动扭矩请求归一化。
 *
 * 接入时可直接构造 [TelemetryPoint] 并跳过 [TelemetrySignalSynthesizer]。
 */
@Suppress("unused")
object CarExtTelemetryMapping
