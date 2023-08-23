package kr.rmsxo.customcalendar.ui.day

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.rmsxo.customcalendar.R
import kr.rmsxo.customcalendar.databinding.ItemDayDateBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DayViewPagerAdapter(
    private val dates: List<Date>,
    private val timeSlots: List<String>
) : RecyclerView.Adapter<DayViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDayDateBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(date: Date, timeSlots: List<String>) {
            binding.dayOfWeek.text = SimpleDateFormat("EEE", Locale.KOREA).format(date)
            binding.date.text = SimpleDateFormat("MM/dd", Locale.KOREA).format(date)

            val recyclerViewAdapter = DayRecyclerViewAdapter(timeSlots)
            binding.timeSlotsRecyclerView.adapter = recyclerViewAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDayDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dates[position], timeSlots)
        holder.binding.timeSlotsRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
        holder.binding.timeSlotsRecyclerView.addItemDecoration(DividerItemDecoration(holder.itemView.context, DividerItemDecoration.VERTICAL))
    }

    override fun getItemCount() = dates.size
}
