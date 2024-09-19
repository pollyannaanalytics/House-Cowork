package com.polly.housecowork.prefs

import android.content.SharedPreferences
import javax.inject.Inject

interface PrefsRepository{
fun getString(key: String, def: String? = null): String?
    fun getInt(key: String, def: Int? = null): Int
    fun getLong(key: String, def: Long? = null): Long
    fun getBoolean(key: String, def: Boolean? = null): Boolean
    fun <T> apply(key: String, value: T)

}


class PrefsDefaultRepository @Inject constructor(private val sharedPreferences: SharedPreferences): PrefsRepository {


    override fun getString(key: String, def: String?): String? = sharedPreferences.getString(key, def?:"")

    override fun getInt(key: String, def: Int?): Int = sharedPreferences.getInt(key, def?: 0)

    override fun getLong(key: String, def: Long?): Long = sharedPreferences.getLong(key, def?: 0L)

    override fun getBoolean(key: String, def: Boolean?): Boolean = sharedPreferences.getBoolean(key, def?: false)

    private fun <T> SharedPreferences.Editor.put(key: String, value: T): SharedPreferences.Editor {
        when (value) {
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Int -> putInt(key, value)
            else -> throw UnsupportedOperationException("[PrefsRepository] apply unsupported type: $key, $value")
        }
        return this
    }

    override fun <T> apply(key: String, value: T) {
        sharedPreferences.edit().put(key, value).apply()
    }

}