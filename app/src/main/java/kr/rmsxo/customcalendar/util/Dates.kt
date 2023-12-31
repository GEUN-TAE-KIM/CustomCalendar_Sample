package kr.rmsxo.customcalendar.util

import java.util.Calendar
import java.util.Date

object Dates {

    fun generateDates(calendar: Calendar): List<Date> {
        val dates = mutableListOf<Date>()
        val cal = calendar.clone() as Calendar
        cal.set(Calendar.DAY_OF_MONTH, 1)

        // 달의 첫 번째 날의 요일 계산 (월요일이 시작일인 경우)
        val firstDayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7

        // 전월의 마지막 일로 채우기
        cal.add(Calendar.DAY_OF_MONTH, -firstDayOfWeek)
        for (i in 0 until firstDayOfWeek) {
            dates.add(cal.time)
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }

        // 현재 달의 일자 추가
        val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 0 until daysInMonth) {
            dates.add(cal.time)
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }

        // 다음 달의 처음 일로 채우기
        val lastDayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY + 6) % 7
        val remainingDays = 6 - lastDayOfWeek

        // 마지막 날이 일요일이 아니거나, 마지막 날이 일요일이지만 다음 달 1일이 월요일이 아닌 경우에만 채우기
        if (lastDayOfWeek != 6 || (remainingDays == 0 && cal.get(Calendar.DAY_OF_MONTH) != 1)) {
            for (i in 0 until remainingDays) {
                dates.add(cal.time)
                cal.add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        return dates
    }

    fun generateTimeSlots(): List<String> {
        val timeSlots = mutableListOf<String>()
        for (hour in 0 until 24) {
            timeSlots.add(String.format("%02d:00", hour))
            timeSlots.add(String.format("%02d:30", hour))
        }
        return timeSlots
    }


    fun isToday(date: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date
        cal2.time = Date()
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    fun generateDatesForMonths(calendar: Calendar, monthsBefore: Int, monthsAfter: Int): List<Date> {
        val dates = mutableListOf<Date>()
        for (i in -monthsBefore..monthsAfter) {
            val cal = calendar.clone() as Calendar
            cal.add(Calendar.MONTH, i)
            dates.addAll(Dates.generateDates(cal))
        }
        return dates
    }


}