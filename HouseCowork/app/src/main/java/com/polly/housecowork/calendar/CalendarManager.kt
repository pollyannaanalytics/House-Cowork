package com.polly.housecowork.calendar

import android.content.Context
import android.database.Cursor
import android.provider.CalendarContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.polly.housecowork.dataclass.Calendar
import com.polly.housecowork.permission.PermissionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class CalendarManager(private val permissionUtils: PermissionUtils) {

    private val requiredPermissions = permissionUtils.getFeatureRequiredPermissions(
        PermissionUtils.RequiredPermissions.CalendarPermissions
    )
//
//    fun syncUpCalendarContract(
//        context: Context,
//    ): Flow<Calendar>  = flow{
//        context.checkSelfPermission(requiredPermissions)
//
//        val uri = CalendarContract.Calendars.CONTENT_URI
//
//        val proj = arrayOf(
//            CalendarContract.Events.CALENDAR_ID,
//            CalendarContract.Events.TITLE,
//            CalendarContract.Events.DESCRIPTION,
//            CalendarContract.Events.DTSTART,
//            CalendarContract.Events.DTEND,
//            CalendarContract.Events.GUESTS_CAN_SEE_GUESTS
//        )
//
//        val cursor: Cursor? = context.contentResolver.query(uri, proj, null, null, null)
//
//        cursor?.let {
//            while (cursor.moveToNext()) {
//                val idIndex = cursor.getColumnIndex(CalendarContract.Events.CALENDAR_ID)
//                val titleIndex = cursor.getColumnIndex(CalendarContract.Events.TITLE)
//                val descriptionIndex = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION)
//                val dtStartIndex = cursor.getColumnIndex(CalendarContract.Events.DTSTART)
//                val dtEndIndex = cursor.getColumnIndex(CalendarContract.Events.DTEND)
//                val canSeeGuestIndex = cursor.getColumnIndex(CalendarContract.Events.GUESTS_CAN_SEE_GUESTS)
//
////                emit(
////                    Calendar(
////                        id = cursor.getInt(idIndex),
////                        userId =
////                        title = cursor.getString(titleIndex),
////                        description = cursor.getString(descriptionIndex),
////                        dtStart = cursor.getLong(dtStartIndex),
////                        dtEnd = cursor.getLong(dtEndIndex),
////                        guestsCanSeeGuests = cursor.getInt(canSeeGuestIndex) == 1
////                    )
////                )
//            }
//            cursor.close()
//        }
//    }.flowOn(Dispatchers.IO)

    fun writeEventToCalendar(
        context: Context,
        calendar: Calendar
    ) {


    }
    fun getCreateEventIntent() {
        // Add an event to the calendar
    }
}