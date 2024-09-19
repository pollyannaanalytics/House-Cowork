package com.polly.housecowork.prefs

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PrefsModule {

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(
        PREF_NAME, PREF_MODE)

    companion object {
        const val PREF_NAME = "shared_prefs"
        const val PREF_MODE = Context.MODE_PRIVATE
    }
}