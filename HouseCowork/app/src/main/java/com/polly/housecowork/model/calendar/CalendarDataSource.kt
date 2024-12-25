package com.polly.housecowork.model.calendar

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream
import javax.inject.Inject

interface CalendarDataSource {
    fun getMonthDates(monthDate: LocalDate): List<LocalDate>
    fun getCurrentMonth(): LocalDate
    fun getToday(): LocalDate
}


class DefaultCalendarDataSource @Inject constructor(
) : CalendarDataSource {
    override fun getMonthDates(monthDate: LocalDate): List<LocalDate> {
        val firstDayOfMonth = monthDate.withDayOfMonth(1)
        val lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1)

        return getDatesBetween(firstDayOfMonth, lastDayOfMonth.plusDays(1))
    }

    override fun getCurrentMonth(): LocalDate {
        return getToday()
    }

    override fun getToday(): LocalDate {
        return LocalDate.now()
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date -> date.plusDays(1) }
            .limit(numOfDays)
            .collect(Collectors.toList())
    }
}
