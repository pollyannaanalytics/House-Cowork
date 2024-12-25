package com.polly.housecowork.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.time.DayOfWeek
import java.time.LocalDate

@Entity
data class Calendar(
    val id: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int,
    val title: String,
    val description: String,

    @ColumnInfo(name = "dt_start")
    val dtStart: Long,

    @ColumnInfo(name = "dt_end")
    val dtEnd: Long,

    @ColumnInfo(name = "guests_can_see_guests")
    val guestsCanSeeGuests: Boolean
)


data class CalendarDate(
    val date: LocalDate,
    val isSelected: Boolean,
    val isToday: Boolean
)

data class CalendarMonth(
    val selectedDate: CalendarDate,
    val visibleDates: List<CalendarDate>
)

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