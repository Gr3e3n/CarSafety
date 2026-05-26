package com.csa.chesuan

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.csa.chesuan.trace.AccidentRepository
import com.csa.chesuan.adapter.ModuleAdapter
import com.csa.chesuan.data.SafetyModule
import com.csa.chesuan.databinding.ActivityMainBinding
import com.csa.chesuan.service.AccidentMonitorService
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SafetyViewModel
    private lateinit var adapter: ModuleAdapter

    private var serviceBound = false
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            serviceBound = true
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            serviceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val perfTimer = PerfTimer.start()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        updateHeaderDisplay()

        // Android 13+ 必须请求通知权限，否则前台服务通知不可见
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION,
                )
            }
        }

        // 启动前台服务（保活监控，App 退出后继续运行）
        val serviceIntent = Intent(this, AccidentMonitorService::class.java)
        startForegroundService(serviceIntent)
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)

        AccidentRepository.initWithContext(applicationContext)

        viewModel = ViewModelProvider(this)[SafetyViewModel::class.java]
        setupRecyclerView()
        observeData()

        window.decorView.viewTreeObserver.addOnPreDrawListener(
            object : android.view.ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    perfTimer.log("MainActivity", "首帧加载")
                    @Suppress("DEPRECATION")
                    window.decorView.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            },
        )
    }

    override fun onResume() {
        super.onResume()
        updateHeaderDisplay()
    }

    override fun onDestroy() {
        if (serviceBound) {
            unbindService(serviceConnection)
            serviceBound = false
        }
        super.onDestroy()
    }

    private fun setupRecyclerView() {
        adapter = ModuleAdapter(
            modules = emptyList(),
            onItemClick = { module -> showModuleDetail(module) },
            onItemLongClick = { module -> showModuleSettings(module) }
        )
        val grid = GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (adapter.isFeaturedPosition(position)) 2 else 1
                }
            }
        }
        binding.recyclerView.layoutManager = grid
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.adapter = adapter
    }

    private fun observeData() {
        viewModel.modules.observe(this) { modules ->
            adapter.updateModules(modules)
        }
    }

    private fun updateHeaderDisplay() {
        val now = Date()
        binding.tvClock.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(now)
        val dateText = SimpleDateFormat("yyyy年M月d日", Locale.CHINA).format(now)
        val weekText = SimpleDateFormat("EEEE", Locale.CHINA).format(now)
        binding.tvHeaderDate.text = "$dateText  $weekText"
    }

    private fun showModuleDetail(module: SafetyModule) {
        if (module.id == "trace") {
            startActivity(Intent(this, AccidentTraceActivity::class.java))
            return
        }
        startActivity(
            Intent(this, ModuleDetailActivity::class.java)
                .putExtra(ModuleDetailActivity.EXTRA_MODULE_ID, module.id)
                .putExtra(ModuleDetailActivity.EXTRA_COLOR_RES, module.colorRes),
        )
    }

    private fun showModuleSettings(module: SafetyModule) {
        if (module.id == "trace") {
            startActivity(Intent(this, AccidentTraceActivity::class.java))
            return
        }
        // 由于当前版本仅做“只读展示/判断”，长按统一进入详情页（偏调试/参数视图）
        startActivity(
            Intent(this, ModuleDetailActivity::class.java)
                .putExtra(ModuleDetailActivity.EXTRA_MODULE_ID, module.id)
                .putExtra(ModuleDetailActivity.EXTRA_COLOR_RES, module.colorRes),
        )
    }

    companion object {
        private const val REQUEST_NOTIFICATION_PERMISSION = 101
    }
}