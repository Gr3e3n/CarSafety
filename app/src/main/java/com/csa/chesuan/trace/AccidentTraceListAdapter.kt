package com.csa.chesuan.trace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.csa.chesuan.databinding.ItemAccidentEventBinding
import com.csa.chesuan.databinding.LayoutTraceListHeaderBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 事故溯源列表：首项为可随列表滚动的富信息头部，其余为事件卡片。
 */
class AccidentTraceListAdapter(
    private var events: List<AccidentEvent>,
    private val onEventClick: (AccidentEvent) -> Unit,
    private val onBackClick: () -> Unit,
    private val onExperimentClick: () -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_EVENT = 1
    }

    private val timeFormatter = SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault())
    private val shortFormatter = SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)

    inner class HeaderViewHolder(private val binding: LayoutTraceListHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnTraceBack.setOnClickListener { onBackClick() }
            binding.btnOpenExperimentLab.setOnClickListener { onExperimentClick() }
        }

        fun bind(list: List<AccidentEvent>) {
            val count = list.size
            val last = list.maxByOrNull { it.timeMillis }

            binding.tvTraceEventCount.text = count.toString()
            binding.tvTraceEventCountBadge.text = binding.root.context.getString(
                com.csa.chesuan.R.string.trace_event_count_badge,
                count,
            )

            binding.tvTraceMonitorState.text = if (last == null) {
                binding.root.context.getString(com.csa.chesuan.R.string.trace_monitor_on)
            } else {
                val t = shortFormatter.format(Date(last.timeMillis))
                binding.root.context.getString(com.csa.chesuan.R.string.trace_monitor_last, t)
            }

            if (last != null) {
                binding.tvTraceLastEventTitle.text = last.summary.ifBlank {
                    typeLabel(last.type)
                }
                binding.tvTraceLastEventMeta.text = buildString {
                    append(timeFormatter.format(Date(last.timeMillis)))
                    append("  ·  ")
                    append(last.locationText.ifBlank { "—" })
                    append("\n")
                    append(typeLabel(last.type))
                    if (last.triggerReasons.isNotEmpty()) {
                        append("  ·  ")
                        append(last.triggerReasons.joinToString("、"))
                    }
                }
            } else {
                binding.tvTraceLastEventTitle.text =
                    binding.root.context.getString(com.csa.chesuan.R.string.trace_last_event_none)
                binding.tvTraceLastEventMeta.text =
                    binding.root.context.getString(com.csa.chesuan.R.string.trace_last_event_none_hint)
            }

            binding.tvTraceEmptyHint.visibility = if (count == 0) View.VISIBLE else View.GONE
        }
    }

    inner class EventViewHolder(private val binding: ItemAccidentEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AccidentEvent) {
            binding.tvEventId.text = item.id
            binding.tvType.text = typeLabel(item.type)
            binding.tvTime.text = timeFormatter.format(Date(item.timeMillis))
            binding.tvLocation.text = item.locationText
            binding.tvTriggers.text = item.triggerReasons.joinToString(" · ")
            binding.tvSummary.text = item.summary
            binding.root.setOnClickListener { onEventClick(item) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_EVENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(
                LayoutTraceListHeaderBinding.inflate(inflater, parent, false),
            )
            else -> EventViewHolder(
                ItemAccidentEventBinding.inflate(inflater, parent, false),
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(events)
            is EventViewHolder -> holder.bind(events[position - 1])
        }
    }

    override fun getItemCount(): Int = 1 + events.size

    fun submitEvents(newEvents: List<AccidentEvent>) {
        events = newEvents
        notifyDataSetChanged()
    }

    private fun typeLabel(type: AccidentType): String = when (type) {
        AccidentType.COLLISION -> "碰撞事故"
        AccidentType.AUTOPILOT_FAULT -> "自动驾驶故障"
        AccidentType.DRIVER_SLOW_REACTION -> "驾驶员反应不足"
        AccidentType.AEB_DELAY_OR_MISS -> "AEB触发延迟"
        AccidentType.TTC_LOW_RISK -> "TTC过低风险"
        AccidentType.DRIVER_TAKEOVER_FAIL -> "驾驶员接管不足"
        AccidentType.ENVIRONMENT_DISTURB -> "环境因素干扰"
        AccidentType.MULTI_FACTOR -> "多因素作用"
    }
}
