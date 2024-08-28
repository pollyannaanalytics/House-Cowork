package com.polly.housecowork.prefs

import android.content.Context

interface Prefs {
    fun getContext(): Context
    fun getRepo(): PrefsDefaultRepository
}