package com.polly.housecowork.model.calendar

import com.polly.housecowork.dataclass.CalendarUiModel
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CalendarRepository @Inject constructor(
    private val calendarMapper: CalendarMapper,
    private val calendarDataSource: CalendarDataSource
){
    private var currentMonth = calendarDataSource.getCurrentMonth()

    fun getCurrentMonth(): CalendarUiModel{
        val dates = calendarDataSource.getMonthDates(currentMonth)
        return calendarMapper.toUiModel(dates, calendarDataSource.getToday())
    }

    fun nextMonth(): CalendarUiModel{
        currentMonth = currentMonth.plusMonths(1)

        return getCurrentMonth()
    }

    fun previousMonth(): CalendarUiModel{
        currentMonth = currentMonth.minusMonths(1)

        return getCurrentMonth()
    }

    fun getCurrentMonthTitle(pattern: String = "MMMM yyyy"): String {
        return currentMonth.format(DateTimeFormatter.ofPattern(pattern))
    }
}
