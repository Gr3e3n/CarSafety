package com.csa.chesuan

import android.util.Log

/**
 * 轻量性能计时工具，输出到 Logcat，过滤标签 "Perf" 即可查看所有测量数据。
 *
 * 用法：
 *   val t = PerfTimer.start()
 *   // ... do work ...
 *   t.log("标签", "操作描述")
 */
class PerfTimer private constructor(private val t0: Long) {

    fun elapsedMs(): Long = System.currentTimeMillis() - t0

    fun log(tag: String, label: String) {
        Log.d("Perf", "[$tag] $label: ${elapsedMs()}ms")
    }

    companion object {
        fun start(): PerfTimer = PerfTimer(System.currentTimeMillis())
    }
}
