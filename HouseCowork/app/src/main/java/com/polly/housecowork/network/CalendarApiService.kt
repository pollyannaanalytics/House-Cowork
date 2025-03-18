package com.polly.housecowork.network

import com.polly.housecowork.local.model.Calendar


interface CalendarApiService {

    suspend fun getUserAllCalendars(userId: Int): List<Calendar>

    suspend fun insertCalendar(calendar: Calendar)

    suspend fun deleteCalendar(calendar: Calendar)
}