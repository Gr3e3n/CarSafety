package com.example.vehtrust

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vehtrust.databinding.ActivityAccidentTraceBinding
import com.example.vehtrust.trace.AccidentTraceListAdapter
import com.example.vehtrust.trace.AccidentTraceViewModel

class AccidentTraceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccidentTraceBinding
    private lateinit var viewModel: AccidentTraceViewModel
    private lateinit var adapter: AccidentTraceListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }
}
