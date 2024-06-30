package com.polly.housecowork.utils

import java.util.Calendar


object DateUtils{
    fun getCurrentHour(): Int {
        val calendar = Calendar.getInstance()
        return calendar[Calendar.HOUR_OF_DAY]
    }

    fun getCurrentMinute(): Int {
        val calendar = Calendar.getInstance()
        return calendar[Calendar.MINUTE]
    }
}