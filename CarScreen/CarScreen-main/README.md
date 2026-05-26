# CarScreen — 车载智能安全监控系统

> **平台**：Android 车载（AAOS）· minSdk 31 / targetSdk 36
> **语言**：Kotlin
> **架构**：MVVM + Repository + Room + 前台 Service
> **数据接入**：CarExt 接口（ECARX 标准文档审核，USB 连接车机）

---

## 目录

- [项目简介](#项目简介)
  - [核心功能](#核心能力)
  - [功能演示](#功能演示)
- [系统架构](#系统架构)
- [目录结构](#目录结构)
- [核心模块说明](#核心模块说明)
  - [安全监控主界面](#安全监控主界面)
  - [事故溯源与取证（EDR）](#事故溯源与取证edr)
  - [可解释责任界定](#可解释责任界定)
  - [可信区块链存证](#可信区块链存证)
  - [车机数据接入（CarExt）](#车机数据接入carext)
- [数据库设计](#数据库设计)
- [技术栈](#技术栈)
- [快速开始](#快速开始)
- [CarExt 接口说明](#carext-接口说明)
- [安全与隐私](#安全与隐私)
- [后续规划](#后续规划)

---

## 项目简介

**CarScreen** 是一款运行在车载 Android 设备（AAOS）上的智能安全屏幕应用，面向 ECARX 座舱平台。系统以实时安全状态监控为核心，集成事故自动取证（EDR）、可解释责任界定与区块链可信存证功能，为事故认定、保险理赔与司法举证提供完整的数字化证据链。

### 核心能力

| 能力 | 说明 |
|------|------|
| 🛡️ 实时安全监控 | 13 个安全功能模块卡片，每 2 秒刷新，自动计算风险等级（正常/关注/高风险） |
| 📹 事故自动取证（EDR） | 20Hz 高频采样，碰撞/紧急制动自动触发，冻结事故前后各 10 秒完整遥测数据 |
| ⚖️ 可解释责任界定 | 9 项量化指标，驾驶员 / 系统 / 环境三方责任占比精确计算 |
| 🔗 区块链可信存证 | SHA-256 哈希 + 数字签名 + 链上 TxID，证据数据不可篡改 |
| 💾 持久化存储 | Room 数据库四表结构，App 重启数据完整保留 |
| 🔌 标准接口接入 | CarExt API 接入，严格遵循 ECARX 标准文档安全审核规范 |

### 功能演示
<a href="https://gr3e3n.github.io/CarScreen/video-demo.html" target="_blank">在线观看演示视频</a>

👆 点击上方按钮观看演示视频

---
## 系统架构

```
┌─────────────────────────────────────────────────────────┐
│                     App 启动                            │
│  MainActivity                                           │
│    ├─ startForegroundService(AccidentMonitorService)    │
│    ├─ SafetyViewModel → 安全模块卡片（每2秒刷新）        │
│    └─ RecyclerView 2列网格展示                          │
└──────────────────────┬──────────────────────────────────┘
                       │ 点击卡片
           ┌───────────┴────────────┐
           │                        │
     id == "trace"             其他模块
           │                        │
  AccidentTraceActivity       ModuleDetailActivity
  （事故溯源列表）             （模块参数详情）
           │
    点击事故记录
           │
  AccidentTraceDetailActivity
  （溯源详情 + 责任分析 + 区块链存证）

后台（常驻）：
AccidentMonitorService（前台 Service，START_STICKY）
  └─ AccidentMonitor（20Hz 采样主循环）
       ├─ RingBuffer 环形缓冲（25秒容量）
       ├─ 触发判断：ax ≤ -7.8 m/s² 或 制动≥70%+ax≤-5.5
       └─ triggerCapture() → AccidentRepository + Room
```

### MVVM 数据流

```
View (Activity)
  └─ observe(LiveData)
ViewModel (SafetyViewModel / AccidentTraceViewModel)
  └─ 读写
Repository (AccidentRepository)
  ├─ 内存 LiveData（实时推送）
  └─ Room DAO（持久化）
```

---

## 目录结构

```
CarScreen/
├── app/src/main/java/com/example/carscreen/
│   ├── MainActivity.kt                  # 入口，启动 Service，展示安全模块网格
│   ├── ModuleDetailActivity.kt          # 模块参数详情页
│   ├── AccidentTraceActivity.kt         # 事故溯源列表页
│   ├── AccidentTraceDetailActivity.kt   # 溯源详情 + 责任分析 + 存证
│   ├── SafetyViewModel.kt               # 安全模块状态 ViewModel
│   │
│   ├── adapter/
│   │   └── ModuleAdapter.kt             # 主界面卡片 Adapter
│   │
│   ├── data/
│   │   ├── SafetyModule.kt              # 安全模块数据类（含风险等级）
│   │   └── ModuleCatalog.kt             # 各模块 propertyId 参数目录
│   │
│   ├── db/
│   │   ├── AccidentEntities.kt          # Room 实体（4张表）
│   │   ├── AccidentDao.kt               # Room DAO 接口
│   │   └── AccidentDatabase.kt          # Room 数据库单例
│   │
│   ├── mock/
│   │   └── MockDataProvider.kt          # 模拟数据生成器（待替换为 CarExt）
│   │
│   ├── service/
│   │   └── AccidentMonitorService.kt    # 前台保活 Service
│   │
│   └── trace/
│       ├── AccidentModels.kt            # 核心数据模型
│       ├── AccidentMonitor.kt           # 20Hz 采样引擎 + 触发逻辑
│       ├── AccidentRepository.kt        # 仓库层（内存+Room双源）
│       ├── AccidentTraceViewModel.kt    # 溯源 ViewModel
│       ├── AccidentEventAdapter.kt      # 事故列表 Adapter
│       ├── ResponsibilityAnalyzer.kt    # 责任界定分析器
│       └── Sha256.kt                    # SHA-256 哈希工具
│
├── tools/
│   ├── _diagrams/                       # 架构图（PNG）
│   └── generate_introduction_docx.py    # 文档生成脚本
│
├── demo/                                 # 演示资源目录
│   ├── demo.mp4                          # 功能演示视频
│   └── demo.png                          # 演示封面图/截图
│
├── video-demo.html                      # 视频演示HTML页面
├── CarExt Demo.pdf                      # ECARX CarExt 接口参考文档
├── info.txt                             # 车辆属性 propertyId 索引
└── README.md
```

---
## 核心模块说明

### 安全监控主界面

主界面以 2 列网格展示 13 个安全功能模块卡片，每张卡片包含风险标签（绿/黄/红）、模块图标与状态文字，每 2 秒自动刷新。风险等级由各模块绑定的 CarExt propertyId 实时值计算得出。

#### 安全功能模块一览

| 模块 ID | 名称 | 主要 propertyId | 风险判断逻辑 |
|---------|------|-----------------|-------------|
| `trace` | 事故溯源 | — | 特殊入口，点击进入 EDR 溯源列表 |
| `adas` | 驾驶辅助 | `AUTONOMOUS_EMERGENCY_BRAKING`=24320, `LANE_KEEPING_AID`=60928 | 功能状态展示 |
| `collision` | 碰撞预警 | `FORWARD_COLLISION_WARN_SNVT`=29184 | 预警关闭 → 关注 |
| `blindspot` | 盲区监测 | `LANE_CHANGE_WARNING_MODE`=61696 | 有车 → 关注 |
| `fatigue` | 疲劳监测 | `DMS_DRIVER_FATIGUE_STATUS`=93952 | 疲劳 → 高风险，分心 → 关注 |
| `lane` | 车道偏离 | `LANE_DEPARTURE_WARNING`=43776 | 预警关闭 → 关注 |
| `lane_keep` | 车道保持 | `LANE_KEEPING_AID`=60928 | 辅助关闭 → 关注 |
| `tpms` | 胎压监测 | 待绑定真实 ID | 胎压异常展示 |
| `door` | 车门状态 | `DOOR_OPEN_WARN_ACTIVE`=29696 | 车门开启 → 关注 |
| `light` | 灯光控制 | `LAMP_EXTERIOR_LIGHT_CONTROL`=39680 | 灯光状态展示 |
| `speed_limit` | 限速提醒 | `SPEED_LIMIT_WARNING_MODE`=115456, `SPEED_LIMIT_WARNING_OFFSET_VALUE`=122112 | 偏差≥15 → 高风险，≥10 → 关注 |
| `rain_safety` | 雨天安全 | `AUTO_CLOSE_WINDOW_RAINY`=23296, `AUTO_REAR_WIPING`=122880 | 功能关闭 → 关注 |
| `child_safety` | 乘员安全 | `CHILD_SAFETY_LOCK`=35328, `PAB_SWITCH`=143872 | 儿童锁关 → 关注 |



### 事故溯源与取证（EDR）

这是系统最核心的子系统，实现事故数据的自动高频采集、环形缓冲冻结与可解释责任分析，对标汽车行业 EDR（Event Data Recorder）标准。

#### 采样与触发逻辑

```kotlin
// AccidentMonitor.kt — 20Hz 采样主循环
while (true) {
    val p = sampleTelemetry(tMs)            // 读取遥测（CarExt 接入后替换此处）
    ring.add(p)                             // 写入环形缓冲（500帧 ≈ 25秒）
    if (shouldTrigger(p)) triggerCapture()  // 满足阈值则触发取证
    delay(50L)  // 20Hz
}

// 触发条件
val collisionByAccel = p.axMS2 <= -7.8f              // 碰撞级减速度（≤ -7.8 m/s²）
val emergencyBrake   = p.brake >= 70 && p.axMS2 <= -5.5f  // 紧急制动复合条件
```

#### 触发后取证流程

```
triggerCapture()
  ├─ 1. 冻结 RingBuffer 最后 10s（事故前快照，共 200 帧）
  ├─ 2. 继续采集 10s 事故后数据（200 帧）
  ├─ 3. buildDetail() 构建完整事件包
  │       ├─ AccidentEvent（事故类型、时间、位置、触发原因、严重度）
  │       ├─ EnvironmentSnapshot（天气、路况、障碍物、车道线质量）
  │       ├─ DecisionTrace（感知→规划→控制 完整决策链路）
  │       └─ ResponsibilityAnalyzer.analyze() 量化 9 项指标
  ├─ 4. AccidentRepository.upsertCapturedEvent() → LiveData 实时推送 UI
  └─ 5. persistToRoom() → 写入 Room 4 张表（持久化，App 重启可恢复）
```


### 可解释责任界定

`ResponsibilityAnalyzer` 基于事故遥测序列，计算 9 项量化指标，输出驾驶员 / 系统 / 环境三方责任占比，所有计算过程可向当事人、保险机构及司法机关完整解释。

#### 9 项量化指标

| # | 指标 | 计算方式 | 责任判断依据 |
|---|------|---------|-------------|
| 1 | 驾驶员反应时间 | 危险减速出现（ax ≤ -3 m/s²） → 制动踏板 ≥ 20% 的时间差（ms） | ≥2500ms 显著增责，≥1500ms 偏长预警 |
| 2 | 制动上升时间 | 踏板从 20% 升至 80% 的时间差（ms） | ≤400ms 制动果断；更长则迟缓 |
| 3 | 峰值减速度 | 遥测序列最小 axMS2 值（m/s²） | ≤-8 碰撞级；≤-5 强制动 |
| 4 | AEB 介入延迟 | 优先真实总线信号，否则用减速度首次 ≤-4 m/s² 时刻估算（ms） | 系统主动介入评估 |
| 5 | 制动时 TTC | 制动开始时刻 v / \|a\| 估算（s） | <1.5s 跟车时距严重不足 |
| 6 | 事故前 3s 均速 | 最后 3000ms 车速均值（km/h） | 超速风险评估 |
| 7 | 事故前 2s 最大转角 | 方向盘角度绝对值峰值（°） | >8° 存在明显纠偏操作 |
| 8 | 制动有效性 | 危险出现 2000ms 内是否达到强制动（≥60%） | 制动及时性判断 |
| 9 | 综合责任占比 | 驾驶员/系统/环境三方加权归一化 | 最终责任倾向输出 |

#### 责任推断规则

```
驾驶员分（基础30分）
  +40  反应时间 ≥ 2500ms
  +25  反应时间 ≥ 1500ms
  +15  制动无效
  +10  制动时 TTC < 1.5s
  -15  事故类型为 AUTOPILOT_FAULT（自动驾驶故障）

系统分（基础15分）
  +30  事故类型为 AUTOPILOT_FAULT
  +15  AEB 介入延迟在 [-5000, -500]ms 区间
  +10  COLLISION 且 AEB 未触发
  +10  存在决策链路异常记录

环境分（基础10分）
  +15  恶劣天气（雨/雾/冰雪）
  +10  车道线模糊或反光
  +5   障碍物（行人/施工区）
```

---

### 可信区块链存证

本节功能由链码与 API 子模块支持，详细说明请参考：

- **[链码与 API 子模块](https://github.com/Gr3e3n/CarScreen/tree/main/chaincode_and_API)**  
  模块 README ➝ **[chaincode_and_API/README.md](https://github.com/Gr3e3n/CarScreen/blob/main/chaincode_and_API/README.md)**

#### 存证生成流程

```
事故详情包（AccidentDetailBundle）
  ↓
构建规范化文本（eventId / type / time / telemetry / responsibility / txId...）
  ↓
SHA-256 哈希（Sha256.kt 纯 Kotlin 实现）
  ↓
数字签名（私钥签名，公钥可独立验证）
  ↓
链上广播 → 返回 TxID
  ↓
存入 Room blockchain_records 表（txId / hash / timestamp / signature）
```

#### 存证数据结构

| 字段 | 类型 | 说明 |
|------|------|------|
| `txId` | String | 链上交易哈希，全局唯一 |
| `dataHash` | String | 事故数据包 SHA-256 摘要 |
| `signature` | String | 私钥数字签名 |
| `timestamp` | Long | 存证时间戳（ms） |
| `blockHeight` | Int | 所在区块高度 |

> 任何第三方均可通过 `dataHash` 与 `txId` 独立验证数据完整性，无需信任本系统。

---

### 车机数据接入（CarExt）

项目对车机数据的提取采用 **CarExt 接口方法**，获取内容严格遵循 **ECARX 标准文档安全审核**规范，通过 **USB 传输线与车机连接**进行数据通信。内部接口代码结构参照 `CarExt Demo.pdf` 文档示例实现。

#### 接入原理

```
物理连接
  USB 数据线
  开发机 ←──────────────────→ ECARX 车机

接口层
  CarExt SDK
    └─ CarExtManager.getProperty(propertyId, areaId)
         ├─ 返回 CarPropertyValue<T>
         └─ 支持类型：Int / Float / Boolean / IntArray

权限审核
  ├─ 接口访问须通过 ECARX 标准文档中的安全审核流程
  ├─ 敏感 propertyId（如 DMS 驾驶员状态）需申请白名单授权
  └─ 所有数据读取行为记录操作日志，供审计使用
```

#### 当前接入状态

| 模块 | 状态 | 说明 |
|------|------|------|
| 安全模块属性读取 | 🟡 Mock 数据 | `MockDataProvider.kt` 生成，待替换为 `CarExtManager` |
| EDR 遥测采样 | 🟡 Mock 数据 | `AccidentMonitor.sampleTelemetry()` 待替换 |
| AEB 总线信号 | 🟡 参数估算 | 待接入真实 `AEB_STATUS` propertyId |
| 胎压（TPMS） | 🔴 待绑定 | propertyId 待 ECARX 文档确认后绑定 |

#### 真实接入替换方式

将 `AccidentMonitor.sampleTelemetry()` 中的模拟逻辑替换为 CarExt 属性订阅：

```kotlin
// 替换前（Mock）
private fun sampleTelemetry(tMs: Int): TelemetryPoint {
    // ... 随机数模拟 ...
}

// 替换后（CarExt 真实接入）
private fun sampleTelemetry(tMs: Int): TelemetryPoint {
    val speed = carExtManager.getProperty(Float::class.java,
        VehiclePropertyIds.PERF_VEHICLE_SPEED, 0).value * 3.6f
    val ax    = carExtManager.getProperty(Float::class.java,
        VehiclePropertyIds.ACCELEROMETER_X, 0).value
    val brake = carExtManager.getProperty(Int::class.java,
        VehiclePropertyIds.BRAKE_INPUT, 0).value
    val steer = carExtManager.getProperty(Float::class.java,
        VehiclePropertyIds.PERF_STEERING_ANGLE, 0).value
    return TelemetryPoint(tMs, speed, ax, brake, steer)
}
```

---

## 数据库设计

Room 数据库（`accident_db`）包含 4 张表，对应事故取证的完整数据模型：

| 表名 | 主键 | 主要字段 | 说明 |
|------|------|---------|------|
| `accident_events` | `id` (String) | type, timeMillis, severity, locationText, summary | 事故事件主表 |
| `telemetry_points` | 自增 | eventId, tMs, speedKph, axMS2, brake, steerDeg | 遥测时间序列（每事故 400 帧） |
| `responsibility_records` | `eventId` | driverFactor, systemFactor, environmentFactor, conclusion | 责任分析结果 |
| `blockchain_records` | `eventId` | txId, dataHash, signature, blockHeight | 区块链存证记录 |

---

## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 语言 | Kotlin | 2.0+ |
| 平台 | Android Automotive OS (AAOS) | minSdk 31 / targetSdk 36 |
| 架构 | MVVM + Repository | — |
| 异步 | Kotlin Coroutines | — |
| 持久化 | Room | 2.6+ |
| 序列化 | Gson | 2.10+ |
| UI | ViewBinding + RecyclerView | — |
| 哈希 | SHA-256（纯 Kotlin 实现） | — |
| 车机接口 | CarExt SDK（ECARX） | 参照 CarExt Demo.pdf |

---

## 快速开始

### 环境要求

- Android Studio Hedgehog 或更高版本
- JDK 17+
- 连接 ECARX 车机设备（或使用 AAOS 模拟器）
- CarExt SDK 已集成（参照 `CarExt Demo.pdf` 配置）



### 首次运行说明

1. App 启动后自动拉起 `AccidentMonitorService` 前台服务（通知栏可见）
2. 主界面展示 13 个安全模块卡片，当前为 Mock 数据（每 2 秒刷新）
3. 点击"事故溯源"卡片进入 EDR 列表（Mock 触发约每 2 小时一次随机演示）
4. 真实车机接入后，替换 `sampleTelemetry()` 即可完成数据源切换



## CarExt 接口说明

本项目车机数据接入方案完整说明：

- **接口方法**：采用 ECARX CarExt 接口（`CarExtManager`）获取车辆属性数据
- **安全审核**：数据获取内容严格遵循 ECARX 标准文档的安全审核规范，敏感属性需白名单授权
- **物理连接**：通过 USB 传输线与车机建立 ADB 调试通道，再经 CarExt SDK 与车辆总线通信
- **参考文档**：内部接口代码结构参照项目根目录 `CarExt Demo.pdf` 中的示例实现
- **propertyId 索引**：所有已用车辆属性 ID 见 `info.txt`，与 ECARX 标准文档对应章节一致

```
接入链路：

开发机 / 车载 Android
   │
   │  USB 传输线（ADB over USB）
   │
ECARX 车机硬件
   │
   │  CarExt SDK 内部通信
   │
车辆 CAN 总线 / 传感器网络
   │
   ├─ 加速度计（ax/ay/az）
   ├─ 车速传感器
   ├─ 制动踏板位置
   ├─ 方向盘转角
   ├─ ADAS 功能状态（AEB / LKA / FCW ...）
   └─ DMS 驾驶员状态监测
```

---

## 安全与隐私

车载数据涉及驾驶行为与人身安全，本项目在设计上遵循以下安全原则：

### 数据安全

| 层面 | 措施 |
|------|------|
| 传输安全 | 所有车机数据仅通过 USB 物理连接传输，不经由公网 |
| 存储安全 | 遥测数据与责任分析结果存储于本地 Room 数据库，不上传第三方 |
| 数据完整性 | SHA-256 哈希摘要确保事故数据记录后不可被篡改 |
| 存证可信 | 区块链 TxID 提供独立可验证的时间戳与内容证明 |
| 接口权限 | CarExt 敏感属性（如 DMS）须通过 ECARX 安全审核白名单授权 |
| 操作审计 | 所有 CarExt 属性读取操作记录操作日志，供合规审计使用 |

### 隐私保护

- 驾驶员状态监测（`DMS_DRIVER_FATIGUE_STATUS`）数据**仅用于事故取证**，不作其他用途
- 事故遥测数据**仅在触发阈值后**才冻结存储，非触发期间数据在环形缓冲中滚动覆盖
- 应用不收集、不上传任何个人身份信息（PII）
- 冷却机制（10 分钟/次）防止频繁触发导致的数据过度采集

### 安全监控纵深防御架构

```
车辆传感器
  │  物理隔离（USB 专线）
  ▼
CarExt SDK（ECARX 安全审核层）
  │  propertyId 白名单校验 + 访问日志
  ▼
AccidentMonitor（应用层采集）
  │  RingBuffer 滚动覆盖（非触发数据自动淘汰，不持久化）
  ▼
触发阈值判断（ax ≤ -7.8 m/s² 或 紧急制动复合条件）
  │  仅满足阈值才持久化，冷却间隔 10 分钟
  ▼
Room 数据库（本地存储）
  │  SHA-256 摘要 + 数字签名
  ▼
区块链存证（不可篡改时间戳 + TxID）
```

---

## 后续规划

| 优先级 | 功能 | 说明 |
|--------|------|------|
| P0 | CarExt 真实接入 | 替换 `sampleTelemetry()` 与安全模块属性读取为真实 CarExt 调用 |
| P0 | TPMS propertyId 绑定 | 确认 ECARX 文档中胎压属性 ID 后完成绑定 |
| P1 | AEB 总线信号接入 | 订阅真实 AEB_STATUS，替代当前减速度估算逻辑 |
| P1 | 事故视频关联 | 接入行车记录仪视频流，与遥测数据时间戳对齐 |
| P2 | 区块链主网部署 | 当前为模拟 TxID，计划接入真实公链或联盟链 |
| P2 | 数据导出 | 支持将事故取证包导出为标准化 JSON / PDF 报告 |
| P2 | OTA 规则更新 | 安全监控阈值与责任权重支持远程动态配置 |
| P3 | 多车型适配 | 扩展 propertyId 映射表，支持更多 ECARX 座舱平台 |
