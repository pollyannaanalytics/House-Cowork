package com.polly.housecowork.calendar

import com.polly.housecowork.dataclass.ApiResult
import com.polly.housecowork.dataclass.Calendar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CalendarRepository @Inject constructor(
    private val remoteDataSource: CalendarRemoteDataSource,
    private val localDataSource: CalendarDao
) {

    suspend fun getUserAllCalendars(userId: Int, fetchRemote: Boolean): Flow<List<Calendar>> = flow {
        if(!fetchRemote) {
            emit(localDataSource.getUserAllCalendars(userId))
        }

        remoteDataSource.getUserAllCalendars(userId).collect { result ->
            when(result) {
                is ApiResult.Success -> {
                    val calendars = result.data
                    emit(calendars)
                    localDataSource.insertAllCalendars(calendars)
                }
                is ApiResult.Error -> {
                    emit(localDataSource.getUserAllCalendars(userId))
                }
                else -> {}
            }
        }

    }

    suspend fun insertCalendar(calendar: Calendar) {
        remoteDataSource.insertCalendar(calendar)
    }

    suspend fun deleteCalendar(calendar: Calendar) {
        remoteDataSource.deleteCalendar(calendar)
    }
}