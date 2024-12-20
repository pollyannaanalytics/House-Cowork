package com.polly.housecowork.prefs

import android.content.SharedPreferences
import com.polly.housecowork.MainApplication
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsLicense @Inject constructor(
    private val pref: SharedPreferences
) {

    var userId: Int
        get() = pref.getInt(PREF_KEY_AUTH_USER_ID, 0)
        set(value) = pref.edit().putInt(PREF_KEY_AUTH_USER_ID, value).apply()

    var token: String
        get() = pref.getString(PREF_KEY_AUTH_ACCESS_TOEKN, "") ?: ""
        set(value) = pref.edit().putString(PREF_KEY_AUTH_ACCESS_TOEKN, value).apply()

    var houseId: Int
        get() = pref.getInt(PREF_KEY_HOUSE_ID, 0)
        set(value) = pref.edit().putInt(PREF_KEY_HOUSE_ID, value).apply()

    companion object {
        private const val PREF_KEY_AUTH_USER_ID = "auth_user_id"
        private const val PREF_KEY_AUTH_ACCESS_TOEKN = "auth_access_token"
        private const val PREF_KEY_HOUSE_ID = "house_id"

    }
}