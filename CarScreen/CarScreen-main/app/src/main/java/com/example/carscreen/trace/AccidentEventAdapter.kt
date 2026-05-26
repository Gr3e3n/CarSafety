package com.example.carscreen.trace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carscreen.databinding.ItemAccidentEventBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AccidentEventAdapter(
    private var items: List<AccidentEvent>,
    private val onClick: (AccidentEvent) -> Unit,
) : RecyclerView.Adapter<AccidentEventAdapter.VH>() {

    private val formatter = SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault())

    inner class VH(private val binding: ItemAccidentEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AccidentEvent) {
            binding.tvEventId.text = item.id
            binding.tvType.text = when (item.type) {
                AccidentType.COLLISION -> "碰撞事故"
                AccidentType.AUTOPILOT_FAULT -> "自动驾驶故障"
            }
            binding.tvTime.text = formatter.format(Date(item.timeMillis))
            binding.tvLocation.text = item.locationText
            binding.tvTriggers.text = item.triggerReasons.joinToString(" · ")
            binding.tvSummary.text = item.summary
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemAccidentEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<AccidentEvent>) {
        items = newItems
        notifyDataSetChanged()
    }
}

