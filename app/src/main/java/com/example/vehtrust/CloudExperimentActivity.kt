package com.example.vehtrust

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.vehtrust.databinding.ActivityCloudExperimentBinding
import com.example.vehtrust.trace.AccidentTraceViewModel
import com.example.vehtrust.trace.ExperimentRuntime
import kotlinx.coroutines.launch

/**
 * 云端 A/B/C 与消融实验配置（从事故溯源列表页移出，避免干扰日常演示）。
 */
class CloudExperimentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCloudExperimentBinding
    private lateinit var viewModel: AccidentTraceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ExperimentRuntime.loadPersisted(this)
        binding = ActivityCloudExperimentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[AccidentTraceViewModel::class.java]

        binding.btnExperimentBack.setOnClickListener { finish() }
        syncExperimentRadiosFromRuntime()

        binding.btnRunCloudBatch.setOnClickListener {
            binding.btnRunCloudBatch.isEnabled = false
            binding.tvBatchStatus.visibility = View.VISIBLE
            binding.tvBatchStatus.text = getString(R.string.experiment_batch_running)
            lifecycleScope.launch {
                val result = viewModel.runCloudExperimentBatchFromAssets(this@CloudExperimentActivity)
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
