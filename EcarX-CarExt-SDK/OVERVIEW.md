# ECARX CarExt SDK 工程概览文档

> **项目名称**: ECARX CarExt (车机扩展SDK)  
> **厂商**: 亿咖通科技 (ECARX Technology Limited)  
> **目标平台**: Android Automotive  
> **主要车型**: 红旗(Hongqi)、V446K  
> **API版本**: VERSION_INT = 16  
> **开发周期**: 2024年6月 ~ 2024年12月  

---

## 一、项目简介

本项目是从**车机系统提取的ECARX CarExt SDK完整源码及参数定义**，是一个企业级车载Android平台API框架，为车机应用开发者提供完整的车辆硬件控制接口和能力抽象层。覆盖现代智能电动汽车几乎所有的HMI（人机交互）控制场景。

### 1.1 文件清单

| 文件/目录 | 说明 | 规模 |
|-----------|------|------|
| `sources/` | Java SDK 源码 (包名: `ecarx.carext.*`) | 146个 .java 文件 |
| `docs/` | Javadoc API文档 (HTML) | 425个文件 |
| `CarExtAll.jar` | 编译后的SDK JAR包 | - |
| `backup.sql` | 数据库备份(可能为参数存储结构) | - |
| `info.txt` | **可提取参数完整列表** (9020行, ~1000+条记录) | 核心资产 |

---

## 二、架构总览

```
ecarx.carext (顶层包)
├── base/              基础框架 (权限/版本/常量/异常)
├── vehicle/           ★ 核心车辆模块 (21+个子模块)
│   ├── module/        车辆功能接口定义 (I*.java)
│   │   ├── v446k/     V446K车型变体接口
│   ├── CarExt.java    SDK入口类
│   ├── ICarManager.java
│   └── Id2ModuleIndexer.java (345KB, ID索引大表)
├── audio/             音频系统
├── power/             电源策略
├── rvdc/              远程数据采集(RVDC) & 日志上传
├── storage/           持久化存储 & 安全存储(TEE)
├── userprofile/        用户档案管理 (多用户切换)
├── display/            显示系统
│   ├── nightmode/     夜间模式 (6种模式)
│   └── view/          窗口管理
├── connect/            连接模块
│   ├── bt/            蓝牙 (ACL状态/媒体控制/设备信息)
│   ├── oncall/        一键呼叫 (E-Call/救援)
│   └── net/           WiFi热点/工作站 + 移动数据网络
├── interaction/        充电交互配置
├── update/ota/         OTA升级 (下载/安装/错误修复)
└── security/           安全模块
    ├── biometrics/    人脸识别 + DMS/OMS生物特征
    └── pki/           PKI证书链管理
```

---

## 三、核心车辆模块详解 (vehicle/module)

这是整个SDK最核心的部分，定义了所有车辆硬件控制属性。

### 3.1 属性ID体系

每个属性分配唯一 **16进制Vehicle Property ID** (`0xXXXXXX`格式)，兼容Android Vehicle HAL规范：

- **高字节**: 功能区域标识
- **中低字节**: 具体属性标识
- **访问权限**: READ / WRITE / READ_WRITE
- **变更模式**: ONCHANGE(事件驱动) / STATIC(静态) / CONTINUOUS(连续变化)
- **分区支持**: GLOBAL / SEAT / MIRROR / WINDOW

### 3.2 各模块参数一览

| # | 模块接口 | 中文名 | 属性数 | 核心参数举例 |
|---|---------|--------|--------|------------|
| 1 | **ICharging** | 充电管理 | 21 | 目标SOC/充电功率/直流充电/V2L放电/充电枪状态(23种)/充放电状态(29种)/电池类型/无线充电 |
| 2 | **IADAS** | 高级驾驶辅助 | 17 | AEB自动紧急制动/前碰撞预警/DOW开门预警/RCW后碰撞预警/智能驾驶模式(ACC/ICC/HNOA)/领航辅助变道/DMS驾驶员监测/LDW/LKA/ELK/限速警告 |
| 3 | **IBcm** | 车身控制器 | 15 | 一键启动/后视镜调节&折叠/锁车关窗/下雨关窗/自定义按键(10种)/氛围灯/电子手刹/出厂设置/乘客气囊/拖车状态 |
| 4 | **IAudio** | 音频设置 | 12 | 音场模式(6种)/音效补偿/报警音量/触屏音效/三频均衡器/声场衰减平衡/导航静音/媒体静音 |
| 5 | **ICarInfo** | 车辆信息 | 11 | MCU软硬版本/XDSN设备码/IHU ID/车型配置/项目PCODE/供应商代码/车模颜色/动力类型(9种) |
| 6 | **IHUD** | 抬头显示 | 9 | HUD开关/角度/亮度/导航显示/雪地模式/显示模式(简洁/地图/标准/AR)/AR-HUD调节通知 |
| 7 | **IHvac** | 空调系统 | 8 | 温度风量增量/循环模式(内/外/自动)/方向盘加热/风向模式(7种)/香氛槽(5槽)/电动出风口开关及XYZ轴方向 |
| 8 | **ILamp** | 车外灯 | 7 | 伴我回家灯(13级)/接近照明/礼貌灯/外灯选择(位置/近光/AHBC)/前后位置灯/日间行车灯 |
| 9 | **ISensor** | 传感器 | 7 | EV续航里程/剩余电量%/平均能耗/车辆模式(6种)/使用模式(6种)/电源状态(S0-S10共11种)/车内AQI |
| 10 | **ITripData** | 行程数据 | 6 | 平均速度/行驶距离/重置Trip/能耗曲线数组/自动重置选项(4种)/行程时长 |
| 11 | **ISeat** | 座椅调节 | 6 | 方便进出/靠背角度/座椅纵向横向/按摩开关/占位检测 |
| 12 | **IDayMode** | 日夜模式 | 6 | 日夜模式(6种)/屏幕亮度(极值)/分区域亮度/日出日落时间 |
| 13 | **ISafety** | 安全设置 | 4 | 接近解锁/离开锁车/无钥匙解锁(全车门/单门/关)/中控锁 |
| 14 | **IPAS** | 泊车辅助 | 5 | RCTA倒车预警音量/泊车紧急制动/泊车车辆状态/DLC数据采集开关及授权时间 |
| 15 | **IBiometric** | 生物识别 | 3 | OMS手势(8种)/DMS疲劳状态(4种)/面部表情(6种) |
| 16 | **IProfile** | 用车偏好 | 3 | 保存偏好/切换偏好/变更弹框 |
| 17 | **ISceneMode** | 场景模式 | 3 | 展车模式/驻车舒享/小憩模式 |
| 18 | **IAmbienceLight** | 氛围灯 | 2 | 语音联动/亮度(0~100) |
| 19 | **IDriveMode** | 驾驶模式 | 1 | ECO/NORMAL/DYNAMIC/CUSTOM/PURE/HYBRID/OFFROAD |
| 20 | **IAvm** | 360环视 | 1 | 全屏/小窗/关闭 |
| 21 | **IAPA** | 自动泊车 | 1 | - |
| **合计** | | | **~147+** | **不含v446k变体** |

### 3.3 v446k车型额外模块

V446K平台在主目录基础上增加了以下专用模块：

| 模块 | 说明 | 特有参数 |
|------|------|---------|
| **IDigitalKey** | 数字钥匙 | 配对状态(18种)/解配对状态/手机遗留提醒等49个常量 |
| **IAPA(v446k)** | 自动泊车增强版 | 149个常量 (超大模块) |
| **IHardKey** | 硬按键 | 8个属性 |
| **IDisplay** | 显示 | 1个属性 |
| **DIM** | ... | 19个属性 |
| **Tcam** | 摄像头 | 11个属性 |

---

## 四、其他功能模块能力

### 4.1 音频 (audio/)
- 音频内容类型定义 (ContentType): 导航提示音、通话、音乐等
- 使用场景 (Usage): 录制、导航、助手等
- 音频属性扩展 (IAudioAttributes)

### 4.2 电源策略 (power/)
- IPowerPolicy: 电源策略查询与管理
- PowerPolicyManager: 策略管理器入口

### 4.3 远程数据采集 (rvdc/)
- **IRVDC**: 远程车辆数据采集核心接口
  - 开关状态: RVDC采集/数据采集/远程诊断
  - 服务端消息接收与回调
  - 日志上传 (ILogApi)

### 4.4 存储 (storage/)
- **IPersistentStorage**: 持久化文件读写 (read/write/delete)
- **ISecureStorage**: TEE安全加密存储 (支持SPIROM内部存储)
- 存储标志: SS_RD/SS_WR/SS_RDWR/SS_CREATE/SS_PERMANENT/SS_INTERNAL/SS_TRUNC/SS_SHARE

### 4.5 用户档案 (userprofile/)
- **IUserProfile**: 用户CRUD操作 (创建/删除/获取/切换/认证)
- **IProfile**: 当前用户配置
- **IUserLock**: 用户锁定/解锁
- 支持多用户切换与云账号关联

### 4.6 夜间模式 (display/nightmode/)
- **INightMode**: 6种夜间模式
  - 关闭 / 始终 / 自定义(定时) / 日出日落 / 阳光传感器
- 自定义时间段设置 + NightModeListener监听

### 4.7 窗口管理 (display/view/)
- **IWindow**: 车窗生命周期管理
- WindowObserver: 可见性变化观察者

### 4.8 一键呼叫 (connect/oncall/)
- **IOnCall**: E-Call一键呼叫 (呼叫中心/E-Call)
- **IOnCallCenter**: 呼叫中心管理
- Call.Type: 区分呼叫中心/E-Call两种类型
- 救援信息(RescueInfo) + ICallListener事件监听

### 4.9 蓝牙 (connect/bt/)
- **IBtConstant**: ACL连接状态(已连接/断开/失败等)、蓝牙媒体操作(播放/暂停/上下曲)
- **IBtExtension**: 蓝牙扩展能力
- **BtDeviceInfo**: 设备信息模型
- **LocalBluetooth**: 本地蓝牙状态

### 4.10 网络 (connect/net/)
- WiFi热点 (IExternalWifiAp): 热点配置/状态/AP客户端监听
- WiFi工作站 (IExternalWifiSta): 扫描结果/连接状态/断开
- 移动数据网络 (IExternalDataNetwork): 类型/状态/注册监听

### 4.11 充电交互 (interaction/charging/)
- **ICharging**: 充电交互配置 (非车辆充电属性)
  - 预约充电模式 (关闭/单次/循环)
  - 预约充电时间设置
  - 插枪优先满电开关
  - 配置保存与监听

### 4.12 OTA升级 (update/ota/)
- **IOta**: 版本检查/当前版本/目标版本/升级历史
- **IOtaDownload**: OTA下载任务管理 (开始/取消/暂停/进度监听)
- **IOtaInstall**: OTA安装管理 (安装/取消/预约安装/安装进度/ECU级错误修复)
- VersionInfo: 版本详细信息模型

### 4.13 人脸识别 (security/biometrics/)
- **IFaceManager**: 完整的人脸识别流程
  - 认证(authenticate): 人脸登录
  - 注册(enroll): 录入人脸模板
  - 删除(removeAll): 清除人脸数据
  - 查询(hasEnrolledTemplates/isHardwareDetected)
- AuthenticationCallback / EnrollmentCallback / RemovalCallback

### 4.14 PKI证书 (security/pki/)
- **IPKI**: PKI证书链管理
  - RootCA / GlobalPolicyCA / ECUIssuingCA / ServicesIssuingCA
  - 密钥链授权检查
  - 证书准备状态监听

---

## 五、info.txt 参数说明

`info.txt` 是本工程的**核心资产文件**，包含从SDK源码中自动提取的全部 **1000+ 条可提取参数记录**。

### 5.1 数据格式

每条记录采用固定结构：
```
---
NAME: {常量名}
PATH: {完整类路径#成员名}
VALUE: {值}
作用: {分类标签}
意义: {中文说明}
---
```

### 5.2 分类体系 ("作用"字段)

| 分类标签 | 含义 | 示例数量 |
|----------|------|---------|
| 一般常量 | 通用基础常量 | File flag, Seek flag, Mode值 |
| 错误码或异常状态 | 错误码/异常值 (~25个) | SS_ERROR_*, SS_OFFLINE |
| 权限或访问控制标识 | Android权限字符串 (~30+) | ACCESS_BIOMETRIC, MANAGE_ONCALL |
| 类型/模式/速率/优先级相关常量 | 枚举/模式常量 | ContentType, Usage, SoundFieldMode |
| 版本号 | API版本 | VERSION_INT = 16 |
| 状态或过程阶段标识 | 状态机/阶段标识 | ACL_CONNECTED, ChargingPlugState |
| 连接/无线/OTA/媒体相关 | 通信/媒体操作 | BLUETOOTH_MEDIA_PLAY, WPC_CHARGE_STATES |

### 5.3 参数规模统计

| 参数类别 | 数量 |
|----------|------|
| Vehicle Property ID (0x属性) | 147+ (主目录), 500+ (含v446k) |
| 枚举/状态常量 | 300+ |
| 权限字符串 | 30+ |
| 错误码 | 25+ |
| 基础常量(Flag等) | 15+ |
| Bluetooth常量 | 50+ |
| **合计** | **~1000+ 条记录** |

---

## 六、技术特点

### 6.1 架构设计
- **接口驱动设计**: 所有模块采用Java Interface定义，配合default方法实现向后兼容
- **@IntDef注解约束**: 类型安全的枚举替代（类似Android原生风格）
- **@since N版本标注**: 每个API标注引入版本，当前VERSION_INT=16
- **UnimplementedException优雅降级**: 未实现方法抛出异常而非崩溃

### 6.2 安全体系
- **声明式权限**: `@RequiresPermission` 注解 + Permissions类定义30+权限
- **两级权限**: `ACCESS_*`(读取) / `MANAGE_*`(管理/写入)
- **保护级别**: `signature|privileged`(系统应用) / `normal`(普通应用)
- **自定义权限命名空间**: `ecarx.openapi.permission.*`
- **TEE安全存储**: ISecureStorage支持SPIROM内部存储

### 6.3 回调与观察者模式
- 统一Listener/Callback接口进行事件通知
- BaseCallBack<T>泛型异步回调
- 支持注册/注销监听器的标准观察者模式

### 6.4 多车型支持
- 主目录通用接口 + `v446k/` 子目录车型变体接口
- Id2ModuleIndexer (345KB) 作为属性ID到模块的映射表

---

## 七、使用方式 (伪代码示例)

```java
// 1. 创建CarExt实例并连接服务
ICarManager carManager = CarExt.create(context, handler, 
    CarExtModule.ICharging, // 模块名
    CarExt.CAR_WAIT_TIMEOUT_WAIT_FOREVER,
    (manager, ready) -> {
        if (ready) {
            // 车机就绪，初始化业务逻辑
        }
    });

// 2. 获取充电管理器
ICharging charging = carManager.getManager(ICharging.class, ICharging.class);

// 3. 读取当前充电状态
int plugState = charging.getInt(ICharging.CHARGING_PLUG_STATE, 
    VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL);

// 4. 设置目标充电SOC
charging.setFloat(ICharging.CHARGING_SOC, 80.0f);
```

