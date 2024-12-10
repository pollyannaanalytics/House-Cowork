package com.polly.housecowork.calendar

import com.polly.housecowork.dataclass.CalendarUiModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream

class CalendarDataSource {

    private val today: LocalDate
        get() = LocalDate.now()

    val sundayToSaturdayWeek: CalendarUiModel
        get() {
            val sunday = today.with(DayOfWeek.SUNDAY)
            return getData(
                startDate = sunday,
                lastSelectedDate = today
            )
        }


    private fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
        val endDayOfWeek = startDate.plusDays(7)
        val visibleDates = getDatesBetween(startDate, endDayOfWeek)
        return toUiModel(visibleDates, lastSelectedDate)
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(/* daysToAdd = */ 1)
        }
            .limit(numOfDays)
            .collect(Collectors.toList())
    }

    private fun toUiModel(
        dateList: List<LocalDate>,
        lastSelectedDate: LocalDate
    ): CalendarUiModel {
        return CalendarUiModel(
            selectedDate = toItemUiModel(lastSelectedDate, true),
            visibleDates = dateList.map {
                toItemUiModel(it, it.isEqual(lastSelectedDate))
            },
            month = today.month.name
        )
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean) = CalendarUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
    )

}