package com.polly.housecowork.calendar

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.polly.housecowork.dataclass.Calendar

@Dao
interface CalendarDao {

    @Query("SELECT * FROM calendar WHERE user_id = :userId")
    fun getUserCalendar(userId: Int): Calendar

    @Query("SELECT * FROM calendar WHERE user_id = :userId")
    fun getUserAllCalendars(userId: Int): List<Calendar>

    @Insert
    fun insertAllCalendars(calendars: List<Calendar>)

    @Insert
    fun insertCalendar(calendar: Calendar)

    @Delete
    fun deleteCalendar(calendar: Calendar)
}