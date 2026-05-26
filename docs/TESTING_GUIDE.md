# VehTrust 应用功能测试完整指南

根据 `app/src/main/` 代码扫描，本指南详细描述实际功能、操作步骤、截图方案与文档撰写方法。

---

## 一、系统架构与核心模块

### 应用层四大 Activity
1. **MainActivity**：安全中心首页（模块卡片网格）+ 启动前台服务  
2. **AccidentTraceActivity**：事故列表 + 跳转到 CloudExperimentActivity  
3. **CloudExperimentActivity**：A/B/C 与消融 D0~D4 配置 + 批量实验（50 条）  
4. **AccidentTraceDetailActivity**：详情页综合展示（回放、责任、严重度、AI、地图、上链）

### 后端支撑
- **OpenAiAnalysisApi.kt**：`BASE_URL = "http://10.0.2.2:8080"`（模拟器访问宿主），超时 180s（覆盖 LLM 长时间）  
- **BlockchainApi.kt**：`BASE_URL = "http://192.168.119.128:8080"`（可改为宿主机 IP），超时 10s  
- **CollisionSeverityApi.kt**：本地离线推理，不依赖网络，输出三分类 + 概率 + 指标表

### 数据库层
- **Room 四表**：AccidentEventEntity（事件）| TelemetryEntity（遥测点）| ResponsibilityEntity（责任结果）| EvidenceEntity（存证）  
- **持久化**：App 重启后数据仍在  
- **环形缓冲**：AccidentMonitor 20Hz 采样，环形缓冲 25 秒

---

## 二、预编译与环境配置

### 2.1 在 Android Studio 中构建与安装

```powershell
# 在项目根目录 PowerShell 或 AS Terminal 中
.\gradlew.bat :app:assembleDebug
adb install -r app\build\outputs\apk\debug\app-debug.apk
# 或在 AS 中直接 Run (默认调试配置)
```

### 2.2 配置后端地址（若需 AI 联调）

**文件**：`app/src/main/java/com/example/vehtrust/trace/OpenAiAnalysisApi.kt`（第 8-9 行）

```kotlin
private const val BASE_URL = "http://10.0.2.2:8080"  // 模拟器默认
private const val ENDPOINT = "/api/accident/analyze"
```

若用真机/车机，改为：
```kotlin
private const val BASE_URL = "http://192.168.1.100:8080"  // 宿主机局域网 IP
```

**文件**：`app/src/main/java/com/example/vehtrust/trace/BlockchainApi.kt`（第 8 行）

```kotlin
private const val BASE_URL = "http://192.168.119.128:8080"  // 修改为实际网关地址
```

修改后在 AS 中 Build → Rebuild Project，重新安装 APK。

### 2.3 启动后端（可选，用于 AI 分析）

```powershell
cd backend
python -m venv .venv
.\\.venv\\Scripts\\Activate.ps1
pip install -r requirements.txt
python -m uvicorn main:app --host 0.0.0.0 --port 8080 --reload
```

健康检查：`curl http://127.0.0.1:8080/health`

---

## 三、逐模块完整测试流程

### 模块 1：首页与前台服务启动

**实际界面布局**：  
- 顶部状态栏（时间、日期、"监控运行中"徽章）  
- 中部大标题："智能安全中心"  
- 下部网格：6 个模块卡片（驾驶辅助、疲劳监测、车道预警、碰撞预警、雨天安全、乘员安全）  
- 特殊卡片："事故溯源"（宽卡片，点击进入事故列表）

**操作步骤**：
1. 安装 APK 后点击启动。  
2. **验证前台服务**：下拉通知栏，应看到"事故溯源监控运行中 · 20Hz 高频采样 · 保留事故前后各 10 秒数据"。  
3. 浏览各模块卡片（状态由 MockDataProvider 定期更新）。  
4. 点击非"事故溯源"卡片进入 **ModuleDetailActivity**（只读参数展示）。  
5. 点击"事故溯源"进入 **AccidentTraceActivity**。  
6. 返回首页（按 Back），验证导航无错误。

**截图清单**：
- `01_home_overview.png` — 整个首页（模块卡片网格）  
- `02_home_notification.png` — 通知栏"事故溯源监控运行中"  
- `03_home_module_detail.png` — 模块详情页（某一模块的只读参数）

**期望结果**：  
- 无崩溃；通知存在且文案明确；返回按钮正常。

---

### 模块 2：事故列表与实验配置

**实际界面**：  
- 顶部：返回按钮 + 标题  
- 事故列表：每项展示事件 ID、时间、位置、事故类型、严重度标签  
- 底部操作栏：按钮跳转到 **CloudExperimentActivity**（用于批量实验配置）

**核心功能**：实验配置在 CloudExperimentActivity 中（从 AccidentTraceActivity 跳转去）。

**操作步骤**：
1. 进入事故列表。  
2. 观察列表中的事件项（若无，系统会预生成 seedEvents）。  
3. 点击底部"云端实验配置"或对应按钮 → 进入 **CloudExperimentActivity**。  
4. 在实验页面选择 A/B/C 与消融 D0~D4。  
5. 按后退或"返回"按钮回到事故列表。  
6. 杀进程重启 App，进入实验页验证配置已持久化（SharedPreferences）。

**截图清单**：
- `10_trace_list_overview.png` — 事故列表全景（含若干事件）  
- `11_cloud_experiment_page.png` — CloudExperimentActivity（A/B/C 与 D0~D4 单选框）

**期望结果**：  
- 列表与实验页可互相导航；配置选项可更改；重启后设置保留。

---

### 模块 3：事故详情与回放

**实际界面**：ScrollView 纵向布局，包含多个Section：
- 顶部概览卡片（事件 ID、类型、时间、位置）  
- 事发位置地图（WebView，Leaflet + OSM 瓦片）  
- 责任分析指标区（反应时间、制动上升、峰值减速三卡片）  
- 遥测回放区（SeekBar + 播放/重播 + 曲线图表）  
- 深度学习分析结果  
- 本地严重度按钮与结果展示区  
- AI 分析按钮与结果展示区  
- 上链按钮与结果展示区

**操作步骤**：
1. 从列表点击一条事故进入详情页。  
2. **回放验证**：  
   - 使用 SeekBar 拖动，观察事故前后 10 秒内遥测数据（速度、制动、加速度等）是否更新。  
   - 点击"播放"，观察 SeekBar 自动向后移动，曲线实时更新。  
   - 验证无卡顿或崩溃。

3. **责任指标验证**：  
   - 查看三个指标卡片（反应时间、制动上升、峰值减速），数据应从 DetailedMetrics 计算得出。  
   - 数值应为正整数或浮点数，无 NaN 或无穷值。

4. **本地严重度推理**：  
   - 点击"生成本地严重度研判"。  
   - 观察加载态（按钮变灰、显示"加载中…"）。  
   - 完成后展示：  
     - 预测结果（如"严重" / "中等" / "轻微"）  
     - 置信度百分比  
     - 严重度指数（0~100）  
     - 概率分布表  
     - 关键因素列表  
     - 模型说明

5. **AI 分析（需后端）**：  
   - 后端启动且 `OPENAI_API_KEY` 有效时，点击"生成 AI 事故分析"。  
   - 等待 5~30 秒，观察加载提示。  
   - 返回结果展示：  
     - 事故摘要  
     - 根因判断  
     - 综合分析  
     - 证据点列表  
     - 改进建议  
     - 模型说明

   - **断网场景**：关闭后端或网络后再点击，应看到回退提示（如"当前已回退到本地规则分析"）+ 本地替代内容。

6. **地图验证**：  
   - 地图区展示位置信息或占位（取决于 WebView 网络可用性）。  
   - 点击"在外部地图应用中打开…"应唤起系统地图应用。

7. **上链操作**：  
   - 网关正常时：点击"上链" → 加载 → 显示返回的 Hash + 成功状态区。  
   - 网关关闭时：显示红色错误文案 + "重新上链"按钮。

**截图清单**：
- `20_detail_hero.png` — 详情页顶部概览卡片  
- `21_detail_replay.png` — 遥测回放 SeekBar + 曲线（播放状态）  
- `22_detail_responsibility_metrics.png` — 三个责任指标卡片  
- `23_detail_severity_result.png` — 本地严重度完整结果（含概率表）  
- `24_detail_ai_success.png` — AI 分析成功返回（完整字段）  
- `25_detail_ai_fallback.png` — AI 分析失败/回退（本地替代）  
- `26_detail_map.png` — 事发位置地图区  
- `27_detail_chain_success.png` — 上链成功（Hash + 时间戳）  
- `28_detail_chain_fail.png` — 上链失败（错误信息 + 重试按钮）

**期望结果**：  
- 回放平滑无卡顿；所有数据字段完整；异常网络场景有清晰提示与回退。

---

### 模块 4：批量实验（50 条样本）

**前置**：后端已启动、API Key 配置、CloudExperimentActivity 已打开。

**操作步骤**：
1. 在 CloudExperimentActivity 中选择实验组别（如 C）与消融模式（如 D0）。  
2. 点击"运行云端 50 条样本实验"。  
3. 按钮变灰，提示"批量运行中…"。  
4. 等待 30~120 秒（取决于后端 LLM 响应速度）。  
5. 完成后显示输出文件路径（如 `/storage/emulated/0/Android/data/.../files/cloud_experiment_results_C_D0_20260523_120456Z.json`）。  
6. 可在 AS Device File Explorer 或 `adb pull` 中验证文件存在与格式。

**异常场景**：  
- 后端返回错误（500/timeout）→ ExperimentBatchRunner 降级为逐条请求。  
- 网络中断 → 显示清晰错误提示。

**截图清单**：
- `30_experiment_config.png` — 实验页面（A/B/C 与 D0~D4 选项）  
- `31_experiment_running.png` — 批量运行中（加载提示）  
- `32_experiment_complete.png` — 完成后的文件路径显示

**期望结果**：  
- 文件正常生成；JSON 格式正确；可反复运行不同配置。

---

### 模块 5：车机大屏适配版（可选）

**实际变化**：仅 UI 资源层（字号、间距、触控区），业务逻辑完全相同。

**操作步骤**：
1. 安装 `VehTrust_Carui/VehTrust_CarScreen.apk`。  
2. 重复模块 1~3 中的关键路径（首页 → 列表 → 详情）。  
3. 对比标准版，观察字体是否更大、触控区是否更宽（至少 66dp）、间距是否充足。

**截图清单**：
- `40_carui_home.png` — 车机版首页  
- `41_carui_detail_replay.png` — 车机版详情页（回放）  
- `42_phone_vs_carui_comparison.png` — 并排对比（标准版 vs 车机版）

**期望结果**：  
- 车机版可视性与触控友好性优于标准版。

---

## 四、异常与可靠性测试

### 4.1 网络异常场景

| 场景 | 操作 | 预期结果 | 截图 |
|------|------|----------|------|
| AI 接口超时 | 后端关闭或响应慢 > 180s，点击"生成 AI 分析" | 显示超时错误 + 回退本地规则 | `err_ai_timeout.png` |
| AI 接口 500 | 后端返回 500，再次点击 AI 分析 | 显示"HTTP 500"或服务错误提示 | `err_ai_500.png` |
| 上链接口不可达 | 网关 IP 错误或未启动，点击"上链" | 显示"连接超时"或"无法连接"，按钮可重试 | `err_chain_unreachable.png` |
| 完全断网 | 断开宿主机网络，生成 AI 或上链 | 不崩溃；显示清晰错误提示 | `err_no_network.png` |

### 4.2 快速操作与防抖

| 场景 | 操作 | 预期结果 | 截图 |
|------|------|----------|------|
| 连续点击 AI | 快速点击"生成 AI 分析" 10 次 | 仅一次请求发出；后续点击排队或忽略；不崩溃 | `stress_ai_rapid_clicks.png` |
| 连续点击上链 | 快速点击"上链" 5 次 | 仅一次请求；界面无状态混乱 | `stress_chain_rapid_clicks.png` |
| 回放快速拖动 | 快速拖动 SeekBar 左右 | 曲线平滑更新；无卡顿或崩溃 | `stress_replay_drag.png` |

### 4.3 存储与性能

| 场景 | 操作 | 预期结果 | 备注 |
|------|------|----------|------|
| 批量 50 条写文件 | 运行批量实验，观察磁盘 I/O | 内存占用 < 200MB；写入正常完成 | 使用 AS Profiler 监控 |
| App 后台与前台切换 | 播放回放中按 Home，后台 5 min，再打开 | 前台服务继续运行；可继续播放 | 检查通知栏 |

---

## 五、文档撰写模板与规范

### 5.1 操作步骤部分（示例）

```markdown
#### （1）安全中心首页与前台服务

**功能说明**：  
- 首页展示 6 个安全模块卡片，分别代表驾驶辅助、疲劳监测、车道预警、碰撞预警、雨天安全、乘员安全。  
- 启动时自动拉起 `AccidentMonitorService` 前台服务，通知栏显示"事故溯源监控运行中"。  
- 20Hz 高频采样车辆数据到环形缓冲（参见 `AccidentMonitor.kt`），事故触发时冻结事故前 10 秒并继续采集后 10 秒。

**操作过程**：  
1. 安装 APK 并启动应用。  
2. 在通知栏查看"事故溯源监控"前台服务通知，验证文案包含"20Hz"和"事故前后各 10 秒"。  
3. 浏览首页各模块卡片，点击某一模块进入只读参数详情页，返回首页。  
4. 点击"事故溯源"卡片进入事故列表页。  
5. 从事故列表返回首页（按 Back）。

**预期结果**：  
- 前台服务通知正常显示。  
- 模块卡片点击无崩溃；详情页加载正常；导航返回按钮可用。  

**截图**：  
- 图 1：首页概览（含模块卡片网格）— 文件 `01_home_overview.png`  
- 图 2：通知栏服务通知 — 文件 `02_home_notification.png`  
```

### 5.2 测试用例表（Markdown）

```markdown
| 编号 | 功能模块 | 测试步骤 | 预期结果 | 实际结果 | 是否通过 | 备注 |
|------|---------|---------|----------|----------|----------|------|
| F1 | 前台服务 | 启动 App → 查看通知栏 | 显示"事故溯源监控运行中"，内容包含"20Hz" | | [ ] 通过 / [ ] 未通过 | |
| F2 | 事故列表 | 进入列表 → 点击事件 → 进入详情 | 正常加载详情页，数据显示完整 | | [ ] 通过 / [ ] 未通过 | |
| F3 | 回放 | 详情页 → 拖动 SeekBar | 遥测曲线实时更新，无卡顿 | | [ ] 通过 / [ ] 未通过 | |
| F4 | 本地严重度 | 详情页 → 点击"生成本地严重度研判" | 返回三分类、置信度、指数、概率表、关键因素 | | [ ] 通过 / [ ] 未通过 | 不需网络 |
| F5 | AI 分析（有网） | 后端启动 → 详情页 → 点击"生成 AI 分析" | 返回摘要、根因、分析、证据点、建议、模型说明 | | [ ] 通过 / [ ] 未通过 | 超时 180s |
| F6 | AI 回退 | 断网 → 点击"生成 AI 分析" | 显示回退提示 + 本地替代内容 | | [ ] 通过 / [ ] 未通过 | |
| F7 | 上链成功 | 网关启动 → 详情页 → 点击"上链" | 显示返回 Hash + 成功时间戳 | | [ ] 通过 / [ ] 未通过 | |
| F8 | 上链失败 | 网关关闭 → 点击"上链" | 显示红色错误 + "重新上链"按钮 | | [ ] 通过 / [ ] 未通过 | |
| R1 | 防抖 | 快速点击"AI 分析" 10 次 | 仅一次请求；后续忽略或排队；不崩溃 | | [ ] 通过 / [ ] 未通过 | |
| R2 | 超时稳定性 | 后端慢响应 > 180s，点击 AI | 等待 180s 后显示超时错误；不崩溃 | | [ ] 通过 / [ ] 未通过 | |
```

### 5.3 截图插入方法

在文档中每个主要操作后插入 Markdown 图片引用：

```markdown
#### 截图：首页与前台服务

![](../screenshots/01_home_overview.png)  
**图 1-1**：安全中心首页，展示 6 个模块卡片及"监控运行中"徽章。

![](../screenshots/02_home_notification.png)  
**图 1-2**：通知栏显示"事故溯源监控运行中 · 20Hz 高频采样 · 保留事故前后各 10 秒数据"。
```

---

## 六、截图采集方法

### 6.1 使用 Android Studio 截图

在 AS 底部窗口打开 Device File Explorer 或使用菜单：  
- **Logcat** → 右上 **Device Screen Capture** → 保存 PNG

或使用 `adb`：
```bash
adb shell screencap -p /sdcard/screen.png
adb pull /sdcard/screen.png docs/screenshots/01_home_overview.png
```

### 6.2 截图命名与存放

- **目录**：`docs/screenshots/`  
- **命名规范**：`<序号>_<模块>_<步骤>.png`  
- **示例**：  
  - `01_home_overview.png`  
  - `10_trace_list_overview.png`  
  - `20_detail_hero.png`  
  - `24_detail_ai_success.png`

### 6.3 截图标注

推荐使用 Windows Paint 或第三方工具在关键元素上标注：  
- 红色圆圈标注按钮、输入框  
- 蓝色箭头指向关键数值  
- 黄色高亮框突出测试结果

---

## 七、完整测试用例清单

### 基本功能测试（F1~F9）

1. **F1**：前台服务启动 ✓ 
2. **F2**：事故列表加载 ✓
3. **F3**：回放 SeekBar 交互 ✓
4. **F4**：本地严重度推理（离线） ✓
5. **F5**：AI 分析（在线） ✓
6. **F6**：AI 回退（离线） ✓
7. **F7**：上链成功 ✓
8. **F8**：上链失败与重试 ✓
9. **F9**：批量 50 条实验 ✓

### 可靠性测试（R1~R6）

1. **R1**：快速连续操作防抖 ✓
2. **R2**：超时与错误提示稳定 ✓
3. **R3**：批量接口降级逐条 ✓
4. **R4**：错误网关地址处理 ✓
5. **R5**：低存储空间（可选） ✓
6. **R6**：WebView 销毁与内存释放 ✓

### 可用性测试（U1~U5）

1. **U1**：首页模块名称可理解 ✓
2. **U2**：详情页各区块标题清晰 ✓
3. **U3**：车机版触控区域 ≥66dp ✓
4. **U4**：横竖屏布局合理 ✓
5. **U5**：系统字体放大下可读 ✓

---

## 八、测试结论与提交检查清单

### 8.1 结论模板

```markdown
### 测试结论

**功能完整性**：通过（9/9）  
- 所有关键功能均可用；回放、严重度、AI、上链无功能缺陷。

**稳定性**：部分通过（6/6）  
- 网络异常与快速操作防抖正常。
- **遗留问题**：当后端应答 > 150s 时，界面加载提示可更明确（建议增加"已等待 Xs"提示）。

**体验**：通过（5/5）  
- 文案清晰，操作路径合理。  
- 车机版字体与触控区满足设计要求。

**复测建议**：  
1. 改进 AI 长等待时加载提示。  
2. 增加批量任务进度日志（已运行 x/50）。  
3. 在车机版测试更多极端分辨率。

**测试状态**：✅ 通过  
```

### 8.2 提交前检查

- [ ] 所有 45+ 张截图已采集并命名  
- [ ] 截图存放在 `docs/screenshots/` 中  
- [ ] 测试用例表已填写"实际结果"与"是否通过"  
- [ ] 异常场景截图已采集（AI 超时、上链失败、断网等）  
- [ ] 车机版（若测试）截图已对比提交  
- [ ] 文档中每张图片都有 Markdown 引用与图题说明  
- [ ] 无明显错别字或格式错误  
- [ ] 后端与网关地址已根据实际环境修改（不使用默认硬编码）  

---

## 九、快速命令参考

### 编译与安装

```powershell
# 仅编译（生成 APK）
.\gradlew.bat :app:assembleDebug

# 编译并安装到连接的设备/模拟器
.\gradlew.bat :app:installDebug

# 或使用 adb
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

### 后端启动

```powershell
cd backend
python -m venv .venv
.\\.venv\\Scripts\\Activate.ps1
pip install -r requirements.txt
python -m uvicorn main:app --host 0.0.0.0 --port 8080 --reload

# 验证
curl http://127.0.0.1:8080/health
```

### 日志查看

```bash
# 过滤 VehTrust 包名
adb logcat | grep -i "vehtrust\|accident\|openai\|blockchain"

# 或在 AS Logcat 中输入过滤
com.example.vehtrust
```

### 数据库导出（Android Studio）

1. 打开 Device File Explorer（AS 右侧）  
2. 导航到 `data/data/com.example.vehtrust/databases/`  
3. 右键 `AccidentDatabase` → **Save As** 到本地  
4. 使用 SQLite 工具（如 DBeaver）查看表结构与数据

---

## 十、后续改进建议

1. **UI 加载状态**：在 AI/上链长等待时增加"已等待 Xs…"或进度百分比。  
2. **批量任务进度**：显示"已完成 x/50"让用户了解进度。  
3. **错误重试**：为 AI 和上链失败提供更智能的自动重试机制。  
4. **离线模式指示**：在 AI/上链不可用时，首页或详情页顶部显示"离线模式"或"仅使用本地分析"。  
5. **性能监控**：新增内存/电量监控小部件（对标真实车机场景）。

---

**文档版本**：1.0  
**最后更新**：2026-05-23  
**基于代码扫描**：app/ + backend/ 完整源码  
