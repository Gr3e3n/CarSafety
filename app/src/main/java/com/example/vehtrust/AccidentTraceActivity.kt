package com.example.vehtrust

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vehtrust.databinding.ActivityAccidentTraceBinding
import com.example.vehtrust.trace.AccidentEventAdapter
import com.example.vehtrust.trace.AccidentTraceViewModel
import com.example.vehtrust.trace.ExperimentRuntime
import kotlinx.coroutines.launch

class AccidentTraceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccidentTraceBinding
    private lateinit var viewModel: AccidentTraceViewModel
    private lateinit var adapter: AccidentEventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ExperimentRuntime.loadPersisted(this)
        binding = ActivityAccidentTraceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "事故溯源"

        viewModel = ViewModelProvider(this)[AccidentTraceViewModel::class.java]

        adapter = AccidentEventAdapter(emptyList()) { event ->
            startActivity(
                Intent(this, AccidentTraceDetailActivity::class.java)
                    .putExtra(AccidentTraceDetailActivity.EXTRA_EVENT_ID, event.id),
            )
        }

        binding.recyclerViewEvents.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewEvents.adapter = adapter

        syncExperimentRadiosFromRuntime()

        binding.btnRunCloudBatch.setOnClickListener {
            binding.btnRunCloudBatch.isEnabled = false
            binding.tvBatchStatus.visibility = View.VISIBLE
            binding.tvBatchStatus.text = getString(R.string.experiment_batch_running)
            lifecycleScope.launch {
                val result = viewModel.runCloudExperimentBatchFromAssets(this@AccidentTraceActivity)
                binding.btnRunCloudBatch.isEnabled = true
                result.fold(
                    onSuccess = { path ->
                        binding.tvBatchStatus.text =
                            getString(R.string.experiment_batch_title) + "\n" + path
                    },
                    onFailure = { e ->
                        binding.tvBatchStatus.text = "失败: ${e.message ?: e.javaClass.simpleName}"
                    },
                )
            }
        }

        viewModel.events.observe(this) { events ->
            adapter.submitList(events)
        }
    }

    override fun onResume() {
        super.onResume()
        ExperimentRuntime.loadPersisted(this)
        syncExperimentRadiosFromRuntime()
    }

    private fun syncExperimentRadiosFromRuntime() {
        binding.rgExpGroupTrace.setOnCheckedChangeListener(null)
        binding.rgAblTrace.setOnCheckedChangeListener(null)
        when (ExperimentRuntime.normalizedGroup()) {
            "A" -> binding.rbExpATrace.isChecked = true
            "B" -> binding.rbExpBTrace.isChecked = true
            else -> binding.rbExpCTrace.isChecked = true
        }
        when (ExperimentRuntime.normalizedAblation()) {
            "D1" -> binding.rbAblD1Trace.isChecked = true
            "D2" -> binding.rbAblD2Trace.isChecked = true
            "D3" -> binding.rbAblD3Trace.isChecked = true
            "D4" -> binding.rbAblD4Trace.isChecked = true
            else -> binding.rbAblD0Trace.isChecked = true
        }
        binding.rgExpGroupTrace.setOnCheckedChangeListener { _, checkedId ->
            ExperimentRuntime.cloudExperimentGroup = when (checkedId) {
                R.id.rbExpATrace -> "A"
                R.id.rbExpBTrace -> "B"
                else -> "C"
            }
            ExperimentRuntime.persist(this)
        }
        binding.rgAblTrace.setOnCheckedChangeListener { _, checkedId ->
            ExperimentRuntime.cloudAblationMode = when (checkedId) {
                R.id.rbAblD1Trace -> "D1"
                R.id.rbAblD2Trace -> "D2"
                R.id.rbAblD3Trace -> "D3"
                R.id.rbAblD4Trace -> "D4"
                else -> "D0"
            }
            ExperimentRuntime.persist(this)
        }
    }
}
