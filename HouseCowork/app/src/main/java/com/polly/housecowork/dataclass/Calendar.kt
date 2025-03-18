package com.polly.housecowork.dataclass

import java.time.DayOfWeek
import java.time.LocalDate


data class CalendarUiModel(
    val selectedDate: Date,
    val visibleDates: List<Date>
) {
    data class Date(
        val date: LocalDate,
        val isSelected: Boolean = false,
        val isToday: Boolean
    ) {
        companion object {
            val DAY_MAPPING = mapOf(
                DayOfWeek.SUNDAY to "S",
                DayOfWeek.MONDAY to "M",
                DayOfWeek.TUESDAY to "T",
                DayOfWeek.WEDNESDAY to "W",
                DayOfWeek.THURSDAY to "T",
                DayOfWeek.FRIDAY to "F",
                DayOfWeek.SATURDAY to "S"
            )
        }
    }
}