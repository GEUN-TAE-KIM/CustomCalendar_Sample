package kr.rmsxo.customcalendar.ui.month

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.rmsxo.customcalendar.R
import kr.rmsxo.customcalendar.databinding.CalendarPageBinding
import java.util.Date

class CalendarPagerAdapter(
    private val datesList: List<List<Date>>,
    private val currentMonth: Int,
    private val onDateClickListener: (Date) -> Unit
) : RecyclerView.Adapter<CalendarPagerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CalendarPageBinding) : RecyclerView.ViewHolder(binding.root) {
        val calendarRecyclerView: RecyclerView = binding.calendarViewPager
        val dayOfTheWeekRecyclerView: RecyclerView = binding.dayOfTheWeekRecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CalendarPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //각 페이지의 RecyclerView에 CalendarAdapter를 설정하고, 해당 월의 날짜 데이터를 연결
    // GridLayoutManager를 사용하여 달력을 그리드 형식으로 표시
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dates = datesList[position]
        val adapter = CalendarAdapter(dates, currentMonth, onDateClickListener)

        holder.calendarRecyclerView.adapter = adapter
        holder.calendarRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context, 7)

        // 경계선 추가
        holder.calendarRecyclerView.addItemDecoration(DividerItemDecoration(holder.itemView.context, DividerItemDecoration.VERTICAL))
        holder.calendarRecyclerView.addItemDecoration(DividerItemDecoration(holder.itemView.context, DividerItemDecoration.HORIZONTAL))

        val daysOfWeek = listOf("월", "화", "수", "목", "금", "토", "일")
        val dayOfWeekAdapter = DayOfTheWeekAdapter(daysOfWeek)
        holder.dayOfTheWeekRecyclerView.adapter = dayOfWeekAdapter
        holder.dayOfTheWeekRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context, 7)

        // 경계선 추가
        holder.dayOfTheWeekRecyclerView.addItemDecoration(DividerItemDecoration(holder.itemView.context, DividerItemDecoration.VERTICAL))

        // 요일 리사이클러뷰의 경계선 설정
        val dividerItemDecoration = DividerItemDecoration(holder.itemView.context, DividerItemDecoration.HORIZONTAL)
        val drawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.divider)
        dividerItemDecoration.setDrawable(drawable!!)
        holder.dayOfTheWeekRecyclerView.addItemDecoration(dividerItemDecoration)

    }

    override fun getItemCount(): Int {
        return datesList.size
    }
}
