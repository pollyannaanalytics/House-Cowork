package com.polly.housecowork.prefs

import com.polly.housecowork.MainApplication
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class PrefsMain @Inject constructor(
//    application: MainApplication
//) : Prefs by PrefsCommon(application) {
//
//    var lastTaskUpdateTime : Long
//        get() = getRepo().getLong(PREF_KEY_LAST_TASK_UPDATE_TIME, 0L) ?: 0L
//        set(value) = getRepo().apply(PREF_KEY_LAST_TASK_UPDATE_TIME, value)
//
//    companion object {
//        private const val PREF_KEY_LAST_TASK_UPDATE_TIME = "last_task_update_time"
//        private const val PREF_KEY_LAST_OPEN_APP_TIME = "last_open_app_time"
//    }
//
//}