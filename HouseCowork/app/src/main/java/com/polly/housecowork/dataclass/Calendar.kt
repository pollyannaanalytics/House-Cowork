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
) {
    val startDate: Date by lazy { visibleDates.firstOrNull() ?: selectedDate }
    val endDate: Date by lazy { visibleDates.lastOrNull() ?: selectedDate }
    val month: String get() = selectedDate.month

    data class Date(
        val date: LocalDate,
        val isSelected: Boolean = false,
        val isToday: Boolean
    ) {
        // 使用 companion object 存放常數
        companion object {
            private val DAY_MAPPING = mapOf(
                DayOfWeek.SUNDAY to "S",
                DayOfWeek.MONDAY to "M",
                DayOfWeek.TUESDAY to "T",
                DayOfWeek.WEDNESDAY to "W",
                DayOfWeek.THURSDAY to "T",
                DayOfWeek.FRIDAY to "F",
                DayOfWeek.SATURDAY to "S"
            )
        }

        val day: String by lazy { DAY_MAPPING[date.dayOfWeek] ?: "" }
        val month: String by lazy { date.month.toString() }

        fun formatDate(pattern: String = "yyyy-MM-dd"): String {
            return date.format(DateTimeFormatter.ofPattern(pattern))
        }
    }
}