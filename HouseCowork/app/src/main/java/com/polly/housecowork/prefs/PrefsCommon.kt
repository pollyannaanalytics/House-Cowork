package com.polly.housecowork.prefs

import android.content.Context
import com.polly.housecowork.MainApplication
import javax.inject.Inject

class PrefsCommon @Inject constructor(
    private val application: MainApplication
): Prefs{
    override fun getContext(): Context {
        return application.applicationContext
    }

    override fun getRepo(): PrefsDefaultRepository = PrefsDefaultRepository(
        PrefsModule(application.applicationContext, PREF_NAME, Context.MODE_PRIVATE)
    )

    companion object {
        private const val PREF_NAME = "shared_prefs"
    }


}