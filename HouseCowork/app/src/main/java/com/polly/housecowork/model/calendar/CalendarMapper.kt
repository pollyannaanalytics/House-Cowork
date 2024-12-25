package com.polly.housecowork.model.calendar

import com.polly.housecowork.dataclass.CalendarUiModel
import java.time.LocalDate
import javax.inject.Inject

class CalendarMapper @Inject constructor(){
    fun toUiModel(dates: List<LocalDate>, lastSelectedDate: LocalDate): CalendarUiModel {
        val today = LocalDate.now()
        return  CalendarUiModel(
            selectedDate = mapToUiDate(lastSelectedDate, true, today),
            visibleDates = dates.map { mapToUiDate(it, it == lastSelectedDate, today) }
        )
    }
    private fun mapToUiDate(date: LocalDate, isSelected: Boolean, today: LocalDate) = CalendarUiModel.Date(
        date = date,
        isSelected = isSelected,
        isToday = date == today
    )
}

data class CalendarState(
    val monthData: CalendarUiModel,
    val monthTitle: String
)
