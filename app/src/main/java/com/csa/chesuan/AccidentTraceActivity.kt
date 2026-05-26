package com.csa.chesuan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.csa.chesuan.databinding.ActivityAccidentTraceBinding
import com.csa.chesuan.trace.AccidentTraceListAdapter
import com.csa.chesuan.trace.AccidentTraceViewModel

class AccidentTraceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccidentTraceBinding
    private lateinit var viewModel: AccidentTraceViewModel
    private lateinit var adapter: AccidentTraceListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val perfTimer = PerfTimer.start()
        binding = ActivityAccidentTraceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[AccidentTraceViewModel::class.java]

        adapter = AccidentTraceListAdapter(
            events = emptyList(),
            onEventClick = { event ->
                startActivity(
                    Intent(this, AccidentTraceDetailActivity::class.java)
                        .putExtra(AccidentTraceDetailActivity.EXTRA_EVENT_ID, event.id),
                )
            },
            onBackClick = { finish() },
            onExperimentClick = {
                startActivity(Intent(this, CloudExperimentActivity::class.java))
            },
        )

        binding.recyclerViewEvents.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewEvents.adapter = adapter

        viewModel.events.observe(this) { events ->
            adapter.submitEvents(events)
        }

        window.decorView.viewTreeObserver.addOnPreDrawListener(
            object : android.view.ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    perfTimer.log("AccidentTraceActivity", "首帧加载")
                    @Suppress("DEPRECATION")
                    window.decorView.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            },
        )
    }
}
