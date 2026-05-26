# 应用功能测试指南（VehTrust）

本文件根据仓库内容（`app/`, `backend/`, `chaincode_and_API/`, `demo/` 等）整理，便于执行完整的功能、可靠性与可用性测试并产出截图与结果记录。

## 一、目标
- 验证客户端（标准 APK / 车机 APK）在常规与异常场景下完成“监控 → 事故取证 → 回放 → 责任与严重度分析 → AI 报告 → 上链”的闭环。 
- 输出标准截图集、测试结果表与复测建议。

## 二、测试环境准备
- 主机：Windows 10/11 或 Linux/macOS（用于启动后端和运行 adb）。
- Android：模拟器（Android 11+）或设备；adb 可访问。
- Python：3.9+（用于后端和简单脚本）。

建议在仓库根目录执行以下步骤：

后端（可选，用于 AI 分析联调）：
```powershell
cd backend
python -m venv .venv
.\.venv\Scripts\Activate.ps1
pip install -r requirements.txt
python -m uvicorn main:app --host 0.0.0.0 --port 8080 --reload
```
健康检查：`http://127.0.0.1:8080/health`

安装 APK（模拟器）：
```bash
adb install -r VehTrust.apk
# 车机版（可选）
adb install -r VehTrust_Carui/VehTrust_CarScreen.apk
```

若使用模拟器，确保 `OpenAiAnalysisApi.kt` 中后端地址为 `10.0.2.2:8080`（默认）。

## 三、关键测试任务与操作步骤（逐模块）
- 首页（安全中心）
  1. 启动 App，确认前台服务通知显示（“事故溯源监控运行中”）。
  2. 浏览模块卡片，打开 `ModuleDetail` 检查参数一致性。
  3. 截图：`home_overview.png`, `home_notification.png`, `module_detail.png`。

- 事故列表与实验配置（A/B/C 与 D0~D4）
  1. 打开事故列表，切换实验组别与消融选项，重启 App 验证持久化。
  2. 若后端可用，点击“批量跑 50 条并保存结果”。
  3. 截图：`trace_list.png`, `experiment_config.png`, `batch_complete.png`。

- 事故详情（回放、责任、严重度、AI、上链）
  1. 打开详情页：播放/暂停回放、移动 SeekBar，记录遥测曲线同步性。
  2. 触发本地严重度判定与 AI 分析（先后测试有网/断网场景）。
  3. 执行上链操作（网关正常/异常两种情况）。
  4. 截图：`detail_replay.png`, `detail_responsibility.png`, `detail_severity.png`, `detail_ai_success.png`, `detail_ai_fallback.png`, `detail_chain_success.png`, `detail_chain_fail.png`。

- 车机大屏适配测试（可选）
  1. 在大屏或车机模拟器上安装车机 APK，重复核心路径，验证可视性与触控区。
  2. 截图对比：`phone_vs_carui.png`。

## 四、测试用例矩阵（样例，复制到测试记录表）
CSV 列头：`case_id,module,precondition,steps,expected,actual,result,notes,screenshots`

示例几行（说明）
- F1,Foreground service,App installed,Start app -> background->check notification,Notification present, ,PASS, ,home_notification.png
- F4,Local severity,Network off,Open detail -> Generate local severity,Severity result shown without network, ,PASS, ,detail_severity.png

## 五、截图规范
- 存放目录：`docs/screenshots/`。
- 分辨率建议：1280×720 或更高；格式 PNG。
- 命名规则：`<module>_<step>_<index>.png`（例如 `trx_input_01.png`）。
- 标注：推荐使用系统截图工具或 `mspaint` 在图上做红色圈注要点。

## 六、自动化与辅助命令（便于复现）
- 后端批量运行示例（将 `experiment_sample_generator.py` 生成的 payload POST 到 `/api/accident/analyze/batch`）：
```bash
# 生成示例 payload（示例脚本位于 backend/experiment_samples_realistic_50.json)
curl -X POST http://127.0.0.1:8080/api/accident/analyze/batch \
  -H 'Content-Type: application/json' \
  --data-binary @backend/experiment_samples_realistic_50.json
```

- 单条分析请求（调试）：
```bash
curl -s -X POST http://127.0.0.1:8080/api/accident/analyze \
  -H 'Content-Type: application/json' \
  -d @backend/experiment_sample_example_first.json
```

- 使用 `adb` 捕获设备屏幕（示例）：
```bash
adb shell screencap -p /sdcard/screen.png
adb pull /sdcard/screen.png docs/screenshots/home_overview.png
```

- 在桌面浏览器截取 `video-demo.html`（headless Chromium 示例，需已安装 `chromium`）：
```bash
# Linux/macOS 示例
chromium --headless --disable-gpu --screenshot=docs/screenshots/video_demo_home.png file://$PWD/video-demo.html
```

## 七、可靠性与可用性检查点（必须验证）
- 错误输入与异常场景
  - 无效哈希/地址：界面应提示并不崩溃。
  - 后端 500/timeout：AI 按超时策略回退到本地规则并提示。
  - 连续重复点击：接口应有防抖或排队机制（界面不应崩溃）。

- 性能/稳定性
  - 回放控件滑动平滑（任意滑动无卡死）
  - 批量 50 条任务不导致 App 崩溃（监控内存/磁盘写入）。

- 可用性
  - 车机模式下触控区域与字体大小满足设计标准。

## 八、测试记录与结论模板
- 用例通过率统计：总用例数 / 通过数 / 失败数；列出高优先级失败项与复测计划。
- 记录示例（Markdown）：
  - 功能完整性：通过（xx/yy）
  - 稳定性：部分通过（enumerate 问题）
  - 建议：增加网络超时显示、对批量任务增加进度日志等。

---

## 九、下一步（我可以代办）
- a) 将本文档内容合并覆盖 `文档_应用功能测试_VehTrust.md`，并在 `docs/screenshots/` 创建占位目录与 README。  
- b) 在本机启动后端并自动调用示例批量接口，收集并汇总返回 JSON（需要你授权我运行网络/进程）。  
- c) 自动在模拟器上安装 APK 并通过 `adb` 采集一组示例截图（需允许我运行 adb）。

---

文件生成于仓库扫描后，基于现有 `README.md` 与 `文档_应用功能测试_VehTrust.md` 内容精简整理，供直接执行与复用。