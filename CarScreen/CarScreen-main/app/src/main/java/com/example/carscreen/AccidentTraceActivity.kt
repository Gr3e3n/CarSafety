package com.example.carscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carscreen.databinding.ActivityAccidentTraceBinding
import com.example.carscreen.trace.AccidentEventAdapter
import com.example.carscreen.trace.AccidentTraceViewModel

class AccidentTraceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccidentTraceBinding
    private lateinit var viewModel: AccidentTraceViewModel
    private lateinit var adapter: AccidentEventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccidentTraceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "事故溯源"

        viewModel = ViewModelProvider(this)[AccidentTraceViewModel::class.java]

        adapter = AccidentEventAdapter(emptyList()) { event ->
            startActivity(
                Intent(this, AccidentTraceDetailActivity::class.java)
                    .putExtra(AccidentTraceDetailActivity.EXTRA_EVENT_ID, event.id)
            )
        }

        binding.recyclerViewEvents.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewEvents.adapter = adapter

        // 订阅事件列表（Room 历史 + 运行时新触发事件，自动刷新）
        viewModel.events.observe(this) { events ->
            adapter.submitList(events)
        }
    }
}
