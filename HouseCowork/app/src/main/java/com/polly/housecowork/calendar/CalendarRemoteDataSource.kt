package com.polly.housecowork.calendar

import com.polly.housecowork.data.network.CalendarApiService
import com.polly.housecowork.dataclass.ApiResult
import com.polly.housecowork.dataclass.Calendar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CalendarRemoteDataSource @Inject constructor(
    private val calendarApiService: CalendarApiService
) {

    suspend fun getUserAllCalendars(userId: Int): Flow<ApiResult<List<Calendar>>> = flow {
        emit(ApiResult.Loading)
        try {
            emit(ApiResult.Success(calendarApiService.getUserAllCalendars(userId)))
        } catch (e: Exception) {
            emit(ApiResult.Error(e))
        }

    }

    suspend fun insertCalendar(calendar: Calendar) {
       calendarApiService.insertCalendar(calendar)
    }

    suspend fun deleteCalendar(calendar: Calendar) {
       calendarApiService.deleteCalendar(calendar)
    }
}