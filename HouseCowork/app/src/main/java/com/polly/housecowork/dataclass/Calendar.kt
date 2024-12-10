package com.polly.housecowork.dataclass

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

data class CalendarUiModel(
    val selectedDate: Date, // the date selected by the User. by default is Today.
    val visibleDates: List<Date>, // the dates shown on the screen,
    val month: String
) {

    val startDate: Date = visibleDates.first() // the first of the visible dates
    val endDate: Date = visibleDates.last() // the last of the visible dates


    data class Date(
        val date: LocalDate,
        val isSelected: Boolean = false,
        val isToday: Boolean
    ) {
        val day: String = when (date.dayOfWeek) {
            DayOfWeek.SUNDAY -> "S"
            DayOfWeek.MONDAY -> "M"
            DayOfWeek.TUESDAY -> "T"
            DayOfWeek.WEDNESDAY -> "W"
            DayOfWeek.THURSDAY -> "T"
            DayOfWeek.FRIDAY -> "F"
            DayOfWeek.SATURDAY -> "S"
        }

        val month = date.month.toString()
    }
}