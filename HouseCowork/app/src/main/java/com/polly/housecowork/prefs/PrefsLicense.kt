package com.polly.housecowork.prefs

import com.polly.housecowork.MainApplication
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsLicense @Inject constructor(
    application: MainApplication
) : Prefs by PrefsCommon(application) {

    var userId: Int
        get() = getRepo().getInt(PREF_KEY_AUTH_USER_ID, 0) ?: 0
        set(value) = getRepo().apply(PREF_KEY_AUTH_USER_ID, value)

    var houseId: Int
        get() = getRepo().getInt(PREF_KEY_HOUSE_ID, 0) ?: 0
        set(value) = getRepo().apply(PREF_KEY_HOUSE_ID, value)

    companion object {
        private const val PREF_KEY_AUTH_USER_ID = "auth_user_id"
        private const val PREF_KEY_HOUSE_ID = "house_id"

    }
}