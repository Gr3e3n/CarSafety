package com.example.vehtrust.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.vehtrust.MainActivity
import com.example.vehtrust.R
import com.example.vehtrust.db.AccidentDatabase
import com.example.vehtrust.trace.AccidentMonitor

/**
 * 前台服务：保活 AccidentMonitor 采样循环。
 * App 退出后继续监控，一旦触发事故自动写入 Room。
 */
class AccidentMonitorService : Service() {

    inner class LocalBinder : Binder() {
        fun getService(): AccidentMonitorService = this@AccidentMonitorService
    }

    private val binder = LocalBinder()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIF_ID, buildNotification())
        // 传入应用 Context 和 Room DAO，启动高频采样
        val dao = AccidentDatabase.getInstance(applicationContext).accidentDao()
        AccidentMonitor.start(applicationContext, dao)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 被系统杀死后自动重启
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder = binder

    override fun onDestroy() {
        AccidentMonitor.stop()
        super.onDestroy()
    }

    // ── Notification ─────────────────────────────────────────────────

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "事故溯源监控",
            NotificationManager.IMPORTANCE_LOW,
        ).apply {
            description = "实时采样车辆数据，事故发生时自动存证"
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun buildNotification(): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE,
        )
        return Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("事故溯源监控运行中")
            .setContentText("20Hz 高频采样 · 保留事故前后各 10 秒数据")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    companion object {
        const val CHANNEL_ID = "accident_monitor_channel"
        const val NOTIF_ID = 1001
    }
}
