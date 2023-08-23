package kr.rmsxo.customcalendar.ui.day

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.rmsxo.customcalendar.R
import kr.rmsxo.customcalendar.databinding.ItemDayTimeBinding

class DayRecyclerViewAdapter(
    private val timeSlots: List<String>
) : RecyclerView.Adapter<DayRecyclerViewAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemDayTimeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(timeSlot: String) {
            binding.timeSlot.text = timeSlot
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDayTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(timeSlots[position])
    }

    override fun getItemCount() = timeSlots.size
}
