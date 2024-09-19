package com.polly.housecowork.data.network

import com.polly.housecowork.dataclass.Calendar

interface CalendarApiService {

    suspend fun getUserAllCalendars(userId: Int): List<Calendar>

    suspend fun insertCalendar(calendar: Calendar)

    suspend fun deleteCalendar(calendar: Calendar)
}