package com.example.vehtrust

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vehtrust.adapter.ModuleAdapter
import com.example.vehtrust.data.SafetyModule
import com.example.vehtrust.databinding.ActivityMainBinding
import com.example.vehtrust.service.AccidentMonitorService
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        updateHeaderDisplay()

        // 启动前台服务（保活监控，App 退出后继续运行）
        val serviceIntent = Intent(this, AccidentMonitorService::class.java)
        startForegroundService(serviceIntent)
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)

        viewModel = ViewModelProvider(this)[SafetyViewModel::class.java]
        setupRecyclerView()
        observeData()
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
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
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
        )
    }
} 