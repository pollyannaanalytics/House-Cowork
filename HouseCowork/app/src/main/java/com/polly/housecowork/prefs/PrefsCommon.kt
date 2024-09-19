package com.polly.housecowork.prefs

import android.content.Context
import android.content.SharedPreferences
import com.polly.housecowork.MainApplication
import javax.inject.Inject

class PrefsCommon @Inject constructor(
    private val context: Context,
    private val prefs: SharedPreferences
): Prefs{
    override fun getContext(): Context {
        return context
    }

    override fun getRepo(): PrefsDefaultRepository = PrefsDefaultRepository(prefs)

}