package com.polly.housecowork.prefs

import android.content.Context
import android.content.SharedPreferences

class PrefsModule(
    val context: Context,
    val name: String,
    val mode: Int) {
    fun provideContext() = context
    fun provideSharedPreference(): SharedPreferences = context.getSharedPreferences(name, mode)
}