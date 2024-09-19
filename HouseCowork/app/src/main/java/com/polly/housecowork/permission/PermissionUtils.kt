package com.polly.housecowork.permission

import android.content.Context
import android.content.pm.PackageManager
import com.polly.housecowork.dataclass.ApiResult

class PermissionUtils {

    private val allRequiredPermissions = listOf(
       CALENDAR_PERMISSION
    )

    fun getAllRequiredPermissions() = allRequiredPermissions

    fun getFeatureRequiredPermissions(permission: RequiredPermissions) : String {
        return when (permission) {
            is RequiredPermissions.CalendarPermissions -> CALENDAR_PERMISSION
            else -> ""
        }
    }

    fun Context.checkUserPermission(permission: RequiredPermissions){
        val requiredPermissions = getFeatureRequiredPermissions(permission)

        if(this.checkSelfPermission(requiredPermissions) != PackageManager.PERMISSION_GRANTED) {
             throw SecurityException("Permission $requiredPermissions is required")
        }
    }

    sealed class RequiredPermissions {
        data object CalendarPermissions : RequiredPermissions()
    }

    companion object {
        private const val CALENDAR_PERMISSION = "android.permission-group.CALENDAR"
    }
}