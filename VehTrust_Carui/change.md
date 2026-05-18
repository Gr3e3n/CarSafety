# VehTrust UI 适配车机大屏 — 代码级变更指南

> **参照项目**：`CarScreen/` 目录（专为车载中控大屏优化的设计规范）
> **适配目标**：将 VehTrust 原手机端 UI 升级为**车机屏幕可读、可触控**的大屏版本
> **核心原则**：功能逻辑完全不变，仅调整视觉尺寸与布局结构
> **涉及文件总数**：14 个（6 layout + 1 colors + 1 strings + 6 drawable）

---

## 文件 1: `app/src/main/res/values/colors.xml`

### 操作：整个文件替换

**删除全部旧内容**（约 29 行），**替换为以下完整内容**：

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="purple_200">#FFBB86FC</color>
    <color name="purple_500">#FF6200EE</color>
    <color name="purple_700">#FF3700B3</color>
    <color name="teal_200">#FF03DAC5</color>
    <color name="teal_700">#FF018786</color>
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>

    <!-- 页面层级（车机大屏适配） -->
    <color name="surface_page">#E8ECF2</color>
    <color name="background">@color/surface_page</color>
    <color name="status_bar_bg">#E4E8EE</color>
    <color name="status_bar_system">#1E293B</color>
    <color name="surface_card">#FAFBFC</color>
    <color name="divider">#E2E8F0</color>

    <!-- 渐变 -->
    <color name="gradient_start">#E4E9F0</color>
    <color name="gradient_center">#EEF1F6</color>
    <color name="gradient_end">#E0E6EE</color>

    <!-- 文字（6 级层次） -->
    <color name="text_display">#111827</color>
    <color name="text_primary">#1E293B</color>
    <color name="text_body">#334155</color>
    <color name="text_secondary">#64748B</color>
    <color name="text_muted">#94A3B8</color>
    <color name="text_on_accent">#FFFFFF</color>

    <!-- 功能主色 -->
    <color name="accent_info">#2563EB</color>
    <color name="accent_warning">#D97706</color>
    <color name="accent_success">#059669</color>
    <color name="accent_danger">#DC2626</color>
    <color name="accent_purple">#7C3AED</color>

    <!-- 模块图标色 -->
    <color name="module_blue">@color/accent_info</color>
    <color name="module_blue_light">#3B82F6</color>
    <color name="module_green">#10B981</color>
    <color name="module_orange">@color/accent_warning</color>
    <color name="module_purple">@color/accent_purple</color>
    <color name="module_teal">@color/accent_success</color>
    <color name="module_pink">#DB2777</color>
    <color name="module_yellow">#CA8A04</color>
    <color name="warning_red">@color/accent_danger</color>

    <!-- 指标格背景色（6 种） -->
    <color name="metric_bg_reaction">#FFF7ED</color>
    <color name="metric_bg_brake">#EFF6FF</color>
    <color name="metric_bg_decel">#F5F3FF</color>
    <color name="metric_bg_aeb">#ECFDF5</color>
    <color name="metric_bg_ttc">#FEF2F2</color>
    <color name="metric_bg_neutral">#F1F5F9</color>

    <!-- 责任条颜色 -->
    <color name="bar_driver">#EA580C</color>
    <color name="bar_system">@color/accent_info</color>
    <color name="bar_env">#0D9488</color>

    <!-- 首页模块 / 图标 -->
    <color name="module_card_bg">#E8EDF2</color>
    <color name="icon_circle_fill">#E8F0FE</color>

    <!-- 主页顶栏装饰 -->
    <color name="header_chip_bg">#E0E7FF</color>
    <color name="header_chip_text">#1D4ED8</color>
    <color name="header_ornament_circle">#E8EFFF</color>
    <color name="header_ornament_icon_tint">#402563EB</color>
    <color name="header_status_pill_bg">#DCFCE7</color>
    <color name="header_status_pill_text">#047857</color>

    <!-- 状态栏图标 / 风险角标 -->
    <color name="icon_tint_muted">@color/text_muted</color>
    <color name="risk_badge_text">#0F172A</color>
</resources>
```

**关键变化点**：
- 原 `#F8F9FA`(background) → `@color/surface_page` → `#E8ECF2`
- 原 `#1E293B`(text_primary) 保留但新增 display/body/muted/on_accent 四个文字层级
- 新增 accent_info/warning/success/danger/purple 五个功能主色
- 新增 metric_bg_* 六种指标背景色
- 新增 bar_driver/system/env 三方责任颜色
- 新增 header_chip/status_pill/ornament_circle 等装饰色

---

## 文件 2: `app/src/main/res/values/strings.xml`

### 操作：在 `<resources>` 标签内追加以下字符串

**保留原有所有字符串不变**，在末尾 `</resources>` 标签之前**新增**以下内容：

```xml
    <!-- 主页标题区 -->
    <string name="main_header_eyebrow">INTELLIGENT SAFETY · 行车防护中台</string>
    <string name="main_header_title">智能安全中心</string>
    <string name="main_header_subtitle">实时监控 · 事故溯源 · 责任界定</string>
    <string name="main_header_chip_monitor">实时监控</string>
    <string name="main_header_chip_trace">事故取证</string>
    <string name="main_header_chip_evidence">责任存证</string>
    <string name="main_header_status">监控运行中</string>

    <!-- 主页中间衔接带 -->
    <string name="main_bridge_caption">以下为座舱安全相关能力，点按卡片进入详情，长按可查看参数</string>
    <string name="main_bridge_stat_1_big">20 Hz</string>
    <string name="main_bridge_stat_1_small">行车数据采样</string>
    <string name="main_bridge_stat_2_big">本地</string>
    <string name="main_bridge_stat_2_small">事故与证据可追溯</string>
    <string name="main_bridge_stat_3_big">一键</string>
    <string name="main_bridge_stat_3_small">碰撞 / 故障取证</string>
```

---

## 文件 3: `app/src/main/res/layout/activity_main.xml`

### 操作：整个文件替换

**删除全部旧内容**（原 NestedScrollView 包裹的紧凑布局），**替换为以下完整车机大屏版本**：

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@drawable/bg_main_gradient">

    ╔══════════════════════════════════════════════════════════╗
    ║  【区域 1】顶部标题卡片 — CardView                      ║
    ╚══════════════════════════════════════════════════════════╝

    <androidx.cardview.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="18dp"     ← 原 12dp
        android:layout_marginTop="10dp"
        android:layout_marginBottom="6dp"          ← 原 4dp
        app:cardCornerRadius="24dp"               ← 原 22dp
        app:cardElevation="5dp"                   ← 原 3dp
        app:cardMaxElevation="8dp"                ← 新增
        app:cardBackgroundColor="@color/surface_card">  ← 原 #F7F9FC

        <LinearLayout ... android:paddingStart="20dp"   ← 原 18dp
            android:paddingEnd="20dp"             ← 原 18dp
            android:paddingTop="18dp"             ← 原 14dp
            android:paddingBottom="18dp">         ← 原 14dp

            ┌─ 时钟行 ─────────────────────────────┐
            │ tvClock: textSize=28sp (原16sp)       │
            │ 图标: 34dp (原18dp), 间距12dp(原8dp) │
            │ tint=@color/icon_tint_muted(原#94A3B8硬编码)│
            └───────────────────────────────────────┘

            ┌─ 主标题区（含左侧渐变竖条 + 右侧图标圆）─┐
            │ 左竖条: 8dp宽×164dp高 (原5dp×112dp)      │
            │   背景=bg_header_accent_strip (新增drawable)│
            │ Eyebrow: 14sp (原9sp)                      │
            │ 日期: 17sp (原9sp)                         │
            │ 状态角标: bg_header_status_pill (新增)      │
            │ 主标题: 50sp bold (原23sp)                  │
            │ 副标题: 25sp (原14sp)                      │
            │ 右侧圆: 118dp (原82dp)                     │
            │ 圆内图标: 58dp (原34dp)                    │
            └───────────────────────────────────────────┘

            ┌─ 三个功能角标 ─────────────────────────┐
            │ 容器: weightSum=3                        │
            │ 背景: bg_header_chip (新增drawable)      │
            │ 字号: 18sp (原12sp)                      │
            │ padding: 8dp×12dp                       │
            │ 引用字符串: main_header_chip_*           │
            └─────────────────────────────────────────┘

            底部分隔线: 1dp, @color/divider
        </LinearLayout>
    </androidx.cardview.widget.CardView>

    ╔══════════════════════════════════════════════════════════╗
    ║  【区域 2】中间统计带（全新区域）— LinearLayout        ║
    ╚══════════════════════════════════════════════════════════╝

    <LinearLayout
        android:layout_marginHorizontal="18dp"
        android:layout_marginBottom="8dp"
        android:background="@drawable/bg_mid_bridge"  ← 新增 drawable
        android:paddingHorizontal="18dp"
        android:paddingTop="16dp" android:paddingBottom="16dp">

        标题: 19sp, @color/text_body
        三栏等分(weightSum=3):
          - "20Hz" / "行车数据采样"  (26sp/16sp)
          - "本地" / "事故与证据可追溯"
          - "一键" / "碰撞/故障取证"
        分割线: 1dp×44dp, @color/divider
    </LinearLayout>

    ╔══════════════════════════════════════════════════════════╗
    ║  【区域 3】底部列表 RecyclerView                        ║
    ╚══════════════════════════════════════════════════════════╝

    <RecyclerView android:id="@+id/recyclerView"
        android:layout_height="0dp"              ← 原 wrap_content
        android:layout_weight="1"                ← 新增
        android:paddingHorizontal="16dp"         ← 原 10dp
        android:paddingTop="4dp"                 ← 新增
        android:paddingBottom="24dp"             ← 原 16dp
        android:scrollbars="vertical" />         ← 新增

</LinearLayout>  ← 根容器改为 LinearLayout (原 NestedScrollView)
```

**结构层面核心变化总结**：
| 项目 | 旧代码 | 新代码 |
|------|--------|--------|
| 根容器 | `NestedScrollView` | `LinearLayout(vertical)` |
| 列表容器高度 | `wrap_content` | `0dp` + `weight=1` |
| 是否有中间统计带 | **无** | **有**（bg_mid_bridge） |
| 卡片引用颜色 | `#F7F9FC` 硬编码 | `@color/surface_card` |
| 图标颜色 | `#94A3B8` 硬编码 | `@color/icon_tint_muted` |

---

## 文件 4: `app/src/main/res/layout/activity_accident_trace.xml`

### 操作：整个文件替换

**旧代码**：
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout ... android:background="@color/background">
    <LinearLayout
        android:layout_height="48dp"               ← 改为 72dp
        android:paddingHorizontal="16dp">          ← 改为 26dp
        <TextView android:textSize="16sp" .../>   ← 页面标题，改为 26sp
        <TextView android:textSize="12sp" .../>   ← 副标题，改为 20sp
    </LinearLayout>
    <RecyclerView
        android:padding="12dp" .../>               ← 改为 22dp
</LinearLayout>
```

**新代码**：
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/background">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="72dp"                 ← 48dp → 72dp (+50%)
        android:paddingHorizontal="26dp"            ← 16dp → 26dp (+63%)
        android:gravity="center_vertical"
        android:background="@color/status_bar_bg">

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="事故溯源事件"
            android:textColor="@color/text_primary"
            android:textSize="26sp"                  ← 16sp → 26sp (+63%)
            android:textStyle="bold" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="模拟数据"
            android:textColor="@color/text_secondary"
            android:textSize="20sp" />                ← 12sp → 20sp (+67%)
    </LinearLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerViewEvents"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="22dp"                       ← 12dp → 22dp (+83%)
        android:clipToPadding="false" />

</LinearLayout>
```

**变化点汇总**：顶栏高 48→72dp，内边距 16→26dp，标题字号 16→26sp，副标题 12→20sp，列表 padding 12→22dp。

---

## 文件 5: `app/src/main/res/layout/activity_accident_trace_detail.xml`

### 操作：整个文件替换

此文件较长，以下是**逐区块的代码对照**：

#### 区块 A — 根容器与外层设置

| 属性 | 旧值 | 新值 |
|------|------|------|
| 根容器 padding | `16dp` | **`26dp`** |
| 区块间 View 间隔 marginTop | `12dp` | **`18dp`** |
| 区块内 padding | `16dp` | **`26dp`** |
| 区块背景色 | `#FFFFFF` (硬编码) | **`@color/surface_card`** |
| 分隔线颜色 | `#E8ECF2` (硬编码) | **`@color/divider`** |

#### 区块 B — 事故信息标题区

**旧代码**：
```xml
<TextView android:text="事故信息" android:textColor="#94A3B8"
    android:textSize="11sp" android:letterSpacing="0.08" />
<TextView android:id="@+id/tvHeader" android:textColor="#1E293B"
    android:textSize="13sp" />
```

**替换为**：
```xml
<TextView android:text="事故信息"
    android:textColor="@color/text_muted"           ← #94A3B8 → @color
    android:textSize="18sp"                          ← 11sp → 18sp
    android:letterSpacing="0.08" />
<TextView android:id="@+id/tvHeader"
    android:textColor="@color/text_primary"          ← #1E293B → @color
    android:textSize="22sp"                          ← 13sp → 22sp
    android:lineSpacingMultiplier="1.55" />
```

#### 区块 C — 责任分析指标（6 宫格）

每个指标格子统一调整：

**旧代码（单格示例）**：
```xml
<LinearLayout android:layout_height="76dp"          ← 改为 122dp
    android:background="#FFF8F2"                    ← 改为 @color/metric_bg_reaction
    android:layout_marginEnd="5dp">                 ← 改为 10dp
    <TextView android:text="反应时间"
        android:textColor="#94A3B8"                 ← 改为 @color/text_muted
        android:textSize="10sp" />                   ← 改为 17sp
    <TextView android:id="@+id/tvReactionTime"
        android:textColor="#F5A623"                 ← 改为 @color/bar_driver
        android:textStyle="bold"
        android:textSize="18sp" />                   ← 改为 30sp
    <TextView android:id="@+id/tvReactionTimeLabel"
        android:textSize="9sp" />                    ← 改为 16sp
</LinearLayout>
```

6 个格子的背景色映射：
| 格子 | 旧背景色(硬编码) | 新背景色(@color引用) |
|------|-----------------|---------------------|
| 反应时间 | `#FFF8F2` | `@color/metric_bg_reaction` |
| 制动上升 | `#F0F5FF` | `@color/metric_bg_brake` |
| 峰值减速 | `#F5F0FF` | `@color/metric_bg_decel` |
| AEB介入 | `#EDFFF8` | `@color/metric_bg_aeb` |
| 制动时TTC | `#FFF2F2` | `@color/metric_bg_ttc` |
| 制动有效性 | `#F8F9FA` | `@color/metric_bg_neutral` |

6 个格子的数值文字颜色映射：
| 格子 | 旧颜色(硬编码) | 新颜色(@color引用) |
|------|---------------|-------------------|
| 反应时间数值 | `#F5A623` | `@color/bar_driver` |
| 制动上升数值 | `#4A90E2` | `@color/accent_info` |
| 峰值减速数值 | `#9B51E0` | `@color/accent_purple` |
| AEB介入数值 | `#2DC7A0` | `@color/accent_success` |
| TTC数值 | `#E53935` | `@color/accent_danger` |

#### 区块 D — 责任界定进度条

**旧代码**：
```xml
<!-- 进度条 -->
<LinearLayout android:layout_height="14dp">        ← 改为 26dp
    <View android:id="@+id/barDriver" ... android:background="#F5A623" />  ← @color/bar_driver
    <View android:id="@+id/barSystem" ... android:background="#4A90E2" />  ← @color/bar_system
    <View android:id="@+id/barEnv" ... android:background="#2DC7A0" />    ← @color/bar_env
</LinearLayout>
<!-- 百分比标签 -->
<TextView android:id="@+id/tvBarDriverPct" android:textColor="#F5A623"
    android:textSize="12sp" />                       ← 改为 20sp, @color/bar_driver
<TextView android:id="@+id/tvBarSystemPct" android:textColor="#4A90E2"
    android:textSize="12sp" />                       ← 改为 20sp, @color/bar_system
<TextView android:id="@+id/tvBarEnvPct" android:textColor="#2DC7A0"
    android:textSize="12sp" />                       ← 改为 20sp, @color/bar_env
```

标签文字也需同步更新：
```xml
<TextView android:id="@+id/tvBarDriverLabel" android:text="驾驶员"
    android:textColor="@color/bar_driver"            ← 原 #F5A623
    android:textSize="19sp" />                       ← 原 11sp
<!-- 同理 barSystemLabel / barEnvLabel -->
```

#### 区块 E — 深度学习时序分析

与区块 C 类似，3 个风险指标的格式统一为：
- 格子高度：`72dp` → **`122dp`**
- 标签字号：`10sp` → **`17sp`**
- 数值字号：`18sp` → **`30sp`**
- 背景色改用 `@color/metric_bg_*` 引用
- 数值颜色改用 `@color/bar_driver` / `@color/accent_info` / `@color/accent_success`
- 分析文字 `tvDriverAnalysis`：`12sp` → **`20sp`**

#### 区块 F — 严重度预测 & AI 分析按钮区

| 元素 | 旧值 | 新值 |
|------|------|------|
| 区块标题字号 | `13sp` | **`22sp`** |
| 描述文字字号 | `11sp` | **`18sp`** |
| 描述文字颜色 | `#94A3B8` | **`@color/text_muted`** |
| 按钮高度 | `48dp` | **`56dp`** |
| 按钮字号 | `14sp` | **`20sp`** |
| 状态/结果文字 | `12~14sp` | **`17~20sp`** |
| 结果面板 padding | `12dp` | **`16~17dp`** |

#### 区块 G — 关键数据 / 决策链路 / 区块链上链

| 元素 | 旧值 | 新值 |
|------|------|------|
| 区块标题字号 | `11sp` | **`18sp`** |
| 遥测文字字号 | `11sp` | **`18sp`** |
| 系统追踪文字 | `12sp` | **`20sp`** |
| 上链按钮高度 | `48dp` | **`56dp`** |
| 上链按钮字号 | `14sp` | **`20sp`** |
| 成功/失败提示字号 | `12~13sp` | **`18~20sp`** |

---

## 文件 6: `app/src/main/res/layout/item_safety_module.xml`

### 操作：整个文件替换

**旧代码**：
```xml
<CardView android:layout_height="148dp"           ← 改为 minHeight=304dp
    android:layout_margin="6dp"                    ← 改为 14dp
    app:cardCornerRadius="18dp"                    ← 改为 28dp
    app:cardElevation="2dp"                        ← 改为 5dp/MaxElevation=8dp
    app:cardBackgroundColor="#EEF0F3">             ← 改为 @color/module_card_bg
    <LinearLayout android:padding="14dp">          ← 改为 22dp
        <TextView (tvRiskBadge) android:textSize="10sp"  ← 改为 17sp
            android:paddingHorizontal="10dp"       ← 改为 16dp
            android:paddingVertical="4dp"          ← 改为 8dp
            android:textColor="#0F172A">           ← 改为 @color/risk_badge_text
        <FrameLayout android:layout_width="52dp"   ← 改为 96dp
            android:layout_height="52dp"            ← 改为 96dp
            android:layout_marginBottom="10dp">     ← 改为 18dp
            <ImageView android:layout_width="26dp"  ← 改为 52dp
                android:layout_height="26dp">       ← 改为 52dp
        <TextView (tvTitle) android:textSize="13sp"← 改为 27sp
            android:textColor="#1E293B">            ← 改为 @color/text_primary
        <TextView (tvStatus) android:textSize="11sp"← 改为 20sp
            android:textColor="#94A3B8">            ← 改为 @color/text_muted
```

**新代码**：
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/cardContainer"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:minHeight="304dp"                              ← 原148dp，翻倍+
    android:layout_margin="14dp"                            ← 原6dp
    app:cardCornerRadius="28dp"                            ← 原18dp
    app:cardElevation="5dp"                                ← 原2dp
    app:cardMaxElevation="8dp"                             ← 新增
    app:cardBackgroundColor="@color/module_card_bg"        ← 原 #EEF0F3
    android:foreground="?android:attr/selectableItemBackground"
    android:clickable="true"
    android:focusable="true">

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="22dp">                            ← 原14dp

        <TextView
            android:id="@+id/tvRiskBadge"
            android:layout_gravity="top|end"
            android:paddingHorizontal="16dp"                ← 原10dp
            android:paddingVertical="8dp"                   ← 原4dp
            android:textSize="17sp"                         ← 原10sp
            android:textColor="@color/risk_badge_text"      ← 原 #0F172A
            android:visibility="gone" />

        <LinearLayout
            android:layout_gravity="center"
            android:gravity="center"
            android:orientation="vertical">

            <FrameLayout
                android:layout_width="96dp"                 ← 原52dp
                android:layout_height="96dp"                ← 原52dp
                android:layout_marginBottom="18dp">         ← 原10dp
                <View ... android:background="@drawable/bg_icon_circle" />
                <ImageView
                    android:id="@+id/ivIcon"
                    android:layout_width="52dp"             ← 原26dp
                    android:layout_height="52dp" />          ← 原26dp
            </FrameLayout>

            <TextView
                android:id="@+id/tvTitle"
                android:textSize="27sp"                     ← 原13sp
                android:textColor="@color/text_primary"     ← 原 #1E293B
                android:textStyle="bold"
                android:gravity="center" />

            <TextView
                android:id="@+id/tvStatus"
                android:textSize="20sp"                     ← 原11sp
                android:textColor="@color/text_muted"       ← 原 #94A3B8
                android:layout_marginTop="10dp" />
        </LinearLayout>
    </FrameLayout>
</androidx.cardview.widget.CardView>
```

---

## 文件 7: `app/src/main/res/layout/item_accident_event.xml`

### 操作：整个文件替换

**旧代码**关键参数 vs **新代码**：

| 参数 | 旧值 | 新值 |
|------|------|------|
| CardView margin | `6dp` | **`12dp`** |
| CardView cornerRadius | `16dp` | **`20dp`** |
| CardView elevation | `2dp` | **`3dp`** |
| CardView backgroundColor | `@android:color/white` | **`@color/surface_card`** |
| 内部 padding | `14dp` | **`24dp`** |
| 类型(tvType) 字号 | `14sp` | **`24sp`** |
| 时间(tvTime) 字号 | `12sp` | **`20sp`** |
| 事件ID(tvEventId) 字号 | `12sp` | **`20sp`** |
| 地点(tvLocation) 字号 | `13sp` | **`22sp`** |
| 触发条件(tvTriggers) 字号 | `12sp` | **`20sp`** |
| 摘要(tvSummary) 字号 | `13sp` | **`22sp`** |
| 时间左边距 | `10dp` | **`16dp`** |
| 各项 marginTop | `6~8dp` | **`10~14dp`** |

**新代码全文**：
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="12dp"
    app:cardCornerRadius="20dp"
    app:cardElevation="3dp"
    app:cardBackgroundColor="@color/surface_card"
    android:foreground="?android:attr/selectableItemBackground"
    android:clickable="true"
    android:focusable="true">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="24dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center_vertical">

            <TextView
                android:id="@+id/tvType"
                android:textColor="@color/text_primary"
                android:textStyle="bold"
                android:textSize="24sp" />

            <TextView
                android:id="@+id/tvTime"
                android:layout_marginStart="16dp"
                android:textColor="@color/text_secondary"
                android:textSize="20sp" />
        </LinearLayout>

        <TextView
            android:id="@+id/tvEventId"
            android:layout_marginTop="10dp"
            android:textColor="@color/text_secondary"
            android:textSize="20sp" />

        <TextView
            android:id="@+id/tvLocation"
            android:layout_marginTop="10dp"
            android:textColor="@color/text_primary"
            android:textSize="22sp" />

        <TextView
            android:id="@+id/tvTriggers"
            android:layout_marginTop="10dp"
            android:textColor="@color/text_secondary"
            android:textSize="20sp"
            android:maxLines="2"
            android:ellipsize="end" />

        <TextView
            android:id="@+id/tvSummary"
            android:layout_marginTop="14dp"
            android:textColor="@color/text_primary"
            android:textSize="22sp"
            android:maxLines="2"
            android:ellipsize="end" />

    </LinearLayout>

</androidx.cardview.widget.CardView>
```

---

## 文件 8: `app/src/main/res/layout/activity_module_detail.xml`

### 操作：整个文件替换

**旧代码关键参数 vs 新代码**：

| 参数 | 旧值 | 新值 |
|------|------|------|
| 页面 padding | `18dp` | **`32dp`** |
| 顶部图标(ivIcon) 尺寸 | `44dp` | **`66dp`** |
| 图标颜色 | `#0F172A` (硬编码) | **`@color/text_display`** |
| 标题间距 marginStart | `12dp` | **`20dp`** |
| 标题(tvTitle) 字号 | `20sp` | **`33sp`** |
| 标题颜色 | `#0F172A` (硬编码) | **`@color/text_display`** |
| 状态间距 marginTop | `4dp` | **`8dp`** |
| 状态(tvStatus) 字号 | `12sp` | **`20sp`** |
| 状态颜色 | `#4A90E2` (硬编码) | **`@color/accent_info`** |
| 描述 marginTop | `14dp` | **`22dp`** |
| 描述(tvDescription) 字号 | `13sp` | **`22sp`** |
| 描述颜色 | `#334155` (硬编码) | **`@color/text_body`** |
| 描述 lineSpacingExtra | `3dp` | **`6dp`** |
| 区块标题字号 | `14sp` | **`24sp`** |
| 区块标题颜色 | `#0F172A` (硬编码) | **`@color/text_display`** |
| 参数面板 padding | `14dp` | **`22dp`** |
| 参数面板背景 | `@drawable/bg_detail_gradient` | **`@drawable/bg_param_panel`** (**新增**) |
| 参数(tvParams) 字号 | `12sp` | **`20sp`** |
| 参数颜色 | `#0F172A` (硬编码) | **`@color/text_primary`** |
| 使用建议面板 | 同上 | 同参数面板 |
| 建议(tvTips) 字号 | `12sp` | **`20sp`** |
| 建议颜色 | `#334155` (硬编码) | **`@color/text_body`** |

---

## 文件 9~14: Drawable 资源（新增 6 个文件）

### 操作：在 `app/src/main/res/drawable/` 目录下新建以下 6 个文件

---

#### 文件 9: `app/src/main/res/drawable/bg_header_accent_strip.xml` （新建）

用途：主页标题卡片左侧的蓝绿渐变竖条

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <gradient
        android:type="linear"
        android:angle="270"
        android:startColor="@color/accent_info"
        android:endColor="@color/accent_success" />
    <corners android:radius="5dp" />
</shape>
```

---

#### 文件 10: `app/src/main/res/drawable/bg_header_status_pill.xml` （新建）

用途："监控运行中"状态角标的绿色胶囊背景

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="@color/header_status_pill_bg" />
    <corners android:radius="20dp" />
</shape>
```

---

#### 文件 11: `app/src/main/res/drawable/bg_header_chip.xml` （新建）

用途："实时监控 / 事故取证 / 责任存证" 三个功能标签的浅蓝色背景

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="@color/header_chip_bg" />
    <corners android:radius="10dp" />
</shape>
```

---

#### 文件 12: `app/src/main/res/drawable/bg_header_ornament_circle.xml` （新建）

用途：主页右侧圆形装饰底（碰撞图标的背景圈）

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="oval">
    <solid android:color="@color/header_ornament_circle" />
</shape>
```

---

#### 文件 13: `app/src/main/res/drawable/bg_mid_bridge.xml` （新建）

用途：主页标题与模块列表之间的统计数据过渡带

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="@color/surface_card" />
    <corners android:radius="20dp" />
    <stroke
        android:width="1dp"
        android:color="@color/divider" />
</shape>
```

---

#### 文件 14: `app/src/main/res/drawable/bg_param_panel.xml` （新建）

用途：模块详情页中"关键参数"和"使用建议"两个面板的背景

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="@color/surface_card" />
    <corners android:radius="18dp" />
    <stroke
        android:width="1dp"
        android:color="@color/divider" />
</shape>
```

---

## 全局修改模式速查表

以下是在多个文件中反复出现的**通用替换规则**，可用 IDE 的全局搜索替换批量执行：

| 搜索目标（旧值） | 替换为（新值） | 出现位置 |
|------------------|--------------|---------|
| `textColor="#1E293B"` | `textColor="@color/text_primary"` | 几乎所有 TextView |
| `textColor="#94A3B8"` | `textColor="@color/text_muted"` | 标题/标签类文字 |
| `textColor="#64748B"` | `textColor="@color/text_secondary"` | 辅助说明文字 |
| `textColor="#334155"` | `textColor="@color/text_body"` | 正文/描述类 |
| `textColor="#0F172A"` | `textColor="@color/text_display"` | 大标题/图标 |
| `background="#FFFFFF"` | `background="@color/surface_card"` | 卡片/区块背景 |
| `background="#E8ECF2"` | `background="@color/divider"` | 分隔线 |
| `#4A90E2` (功能蓝) | `@color/accent_info` | 高亮/系统相关 |
| `#F5A623` (橙色) | `@color/bar_driver` 或 `@color/accent_warning` | 驾驶员/警告 |
| `#2DC7A0` (青绿) | `@color/accent_success` | 成功/AEB |
| `#E53935` (红色) | `@color/accent_danger` | 危险/TTC |
| `#9B51E0` (紫色) | `@color/accent_purple` | 峰值减速 |

---

## 设计原则参考

| 维度 | 手机端原设计 | 车机适配后 |
|------|-------------|-----------|
| 视距 | 手持 25~50cm | 座椅距离 50~100cm |
| 最小触控区 | 44dp | ≥66dp |
| 字号基准 | 正文 11~14sp | 17~27sp |
| 间距节奏 | 4/8/12dp | 8/14/18~26dp |
| 圆角风格 | 中等圆润 16~18dp | 大圆角 24~28dp |
| 阴影层次 | 轻 2~3dp | 中等 5~8dp |
| 颜色管理 | 硬编码为主 | 统一 @color 引用 |
| 内容密度 | 信息密集 | 宽松留白 |

---

> **重要提醒**：以上 14 个文件的修改**仅涉及 UI 资源层**（XML 布局 + 颜色 + 字符串 + shape drawable），**所有 Kotlin/Java 业务代码无需任何修改**。
