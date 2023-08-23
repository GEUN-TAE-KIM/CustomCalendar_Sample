package kr.rmsxo.customcalendar.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import kr.rmsxo.customcalendar.R
import kr.rmsxo.customcalendar.databinding.FragmentHomeBinding
import kr.rmsxo.customcalendar.util.BaseFragment
import kr.rmsxo.customcalendar.util.Dates
import kr.rmsxo.customcalendar.util.Dates.generateDatesForMonths
import kr.rmsxo.customcalendar.util.Dates.isToday
import kr.rmsxo.customcalendar.ui.day.DayViewPagerAdapter
import kr.rmsxo.customcalendar.ui.month.CalendarPagerAdapter
import kr.rmsxo.customcalendar.util.CustomDividerItemDecoration
import java.util.Calendar
import java.util.Date

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private var calendar = Calendar.getInstance()
    private val timeSlots = Dates.generateTimeSlots()
    private val startCalendar: Calendar = Calendar.getInstance().apply {
        time = Date()
    }
    private var monthDates = generateDatesForMonths(calendar, 12, 12) // 이전 달과 다음 달을 포함 1년 정도 -> 더 하고 싶으면 추가하면 됨

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCalendar.time = calendar.time

        binding.leftButton.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateCalendar()

        }

        binding.rightButton.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateCalendar()
        }


        // 일별 달력 좌우 버튼 들
        binding.dayLeftButton.setOnClickListener {
            val currentItem = binding.dayCalendarViewPager.currentItem
            if (currentItem > 0) {
                binding.dayCalendarViewPager.setCurrentItem(currentItem - 1, true)
            }
            dayTextView()
        }

        binding.dayRightButton.setOnClickListener {
            val currentItem = binding.dayCalendarViewPager.currentItem
            if (currentItem < monthDates.size - 1) {
                binding.dayCalendarViewPager.setCurrentItem(currentItem + 1, true)
            }
            dayTextView()
        }


        // 일별 달력
        binding.dayCalendarViewPager.adapter = DayViewPagerAdapter(monthDates, timeSlots)
        binding.dayCalendarViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.dayCalendarViewPager.addItemDecoration(CustomDividerItemDecoration(requireContext()))

        // 오늘 날짜로 스크롤
        val todayIndex = findTodayIndex(monthDates)
        binding.dayCalendarViewPager.setCurrentItem(todayIndex, false)
        binding.dayCalendarViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // 현재 페이지의 날짜를 startCalendar에 설정
                startCalendar.time = monthDates[position]
                dayTextView() // 텍스트뷰 업데이트
            }
        })


        // 경계선 생성
        // 월 달력
        binding.calendarViewPager.addItemDecoration(CustomDividerItemDecoration(requireContext()))
        binding.calendarViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.calendarViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 12 + position)
                updateCalendar()
            }
        })


        // 오늘로 돌아가기
        binding.btnTodayDate.setOnClickListener {
            calendar = Calendar.getInstance()
            startCalendar.time = calendar.time
            monthDates = generateDatesForMonths(calendar, 12, 12) // 이전 달과 다음 달을 포함
            updateCalendar()

            // 오늘 날짜로 스크롤
            val todayIndex = findTodayIndex(monthDates)
            binding.dayCalendarViewPager.setCurrentItem(todayIndex, false)
        }

        binding.btnMonthDate.setOnClickListener {

            binding.calendarViewPager.visibility = View.VISIBLE
            binding.calendarDate.visibility = View.VISIBLE
            binding.dayCalendarViewPager.visibility = View.GONE
            binding.calendarDayDate.visibility = View.GONE
        }

        updateCalendar() // 초기 달력 업데이트
        dayTextView()

    }


    private fun findTodayIndex(dates: List<Date>): Int {
        for (i in dates.indices) {
            if (isToday(dates[i])) {
                return i
            }
        }
        return 0 // 기본값 반환, 오늘이 리스트에 없을 경우 0을 반환
    }

    private fun dayTextView() {

        val startCalendarClone = startCalendar.clone() as Calendar // 복제된 객체 생성
        val endCalendar = startCalendarClone.clone() as Calendar
        endCalendar.add(Calendar.DATE, 6)

        // 일요일까지의 날짜 차이를 계산
        val differenceToSunday =
            (Calendar.SUNDAY + 7) - startCalendarClone.get(Calendar.DAY_OF_WEEK)
        val endSunDayCalendar = startCalendarClone.clone() as Calendar
        endSunDayCalendar.add(Calendar.DATE, differenceToSunday)


        val startYear = startCalendarClone.get(Calendar.YEAR)
        val startMonth = startCalendarClone.get(Calendar.MONTH) + 1
        val startDate = startCalendarClone.get(Calendar.DAY_OF_MONTH)

        binding.dayTextView.text = String.format(
            "%d년 %d월 %d일",
            startYear,
            startMonth,
            startDate

        )
    }


    private fun updateCalendar() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        binding.yearMonthTextView.text = "${year}년 ${month + 1}월"

        val months = generateMonths(calendar)
        val pagerAdapter = CalendarPagerAdapter(months, month) { date ->
            Log.d("Calendar", "Selected date: $date") // 선택한 날짜 로그로 출력

            // 클릭한 날짜에 대한 처리
            startCalendar.time = date
            updateDayCalendarView(date)

            // 월별 달력을 가리고 일별 달력을 보이게 처리
            binding.calendarViewPager.visibility = View.GONE
            binding.calendarDate.visibility = View.GONE
            binding.dayCalendarViewPager.visibility = View.VISIBLE
            binding.calendarDayDate.visibility = View.VISIBLE
        }
        binding.calendarViewPager.adapter = pagerAdapter
        binding.calendarViewPager.setCurrentItem(months.size / 2, false)
    }

    private fun updateDayCalendarView(date: Date) {
        val position = monthDates.indexOf(date)
        Log.d("Calendar", "Position in monthDates: $position") // position 로그로 출력

        if (position != -1) {
            binding.dayCalendarViewPager.setCurrentItem(position, false)
            dayTextView()
        }
    }

    private fun generateMonths(calendar: Calendar): List<List<Date>> {

        val months = mutableListOf<List<Date>>()

        for (i in -12..12) {
            val cal = calendar.clone() as Calendar
            cal.add(Calendar.MONTH, i)
            months.add(Dates.generateDates(cal))
        }

        return months
    }


}