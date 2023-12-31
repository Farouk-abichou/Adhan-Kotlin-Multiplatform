package org.adhan.data

import kotlinx.datetime.*

object CalendarUtil {

    /**
     * Whether a year is a leap year (has 366 days)
     * @param year the year
     * @return whether it's a leap year
     */
    fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }

    /**
     * Date and time with a rounded minute
     * This returns a date with the seconds rounded and added to the minute
     * @param instant the date and time as an Instant
     * @return the date and time with a rounded minute
     */
    fun roundedMinute(instant: Instant): Instant {
        val dateTime = instant.toLocalDateTime(TimeZone.UTC)
        val roundedMinutes = dateTime.minute + if (dateTime.second >= 30) 1 else 0
        val additionalHours = roundedMinutes / 60 + 1
        val hour = (dateTime.hour + additionalHours) % 24
        val minute = roundedMinutes % 60

        return LocalDateTime(
            year = dateTime.year,
            monthNumber = dateTime.monthNumber,
            dayOfMonth = dateTime.dayOfMonth,
            hour = hour,
            minute = minute
        ).toInstant(TimeZone.UTC)
    }

    /**
     * Gets a date for the particular date
     * @param components the date components
     * @return the date with a time set to 00:00:00 at utc
     */
    fun resolveTime(components: DateComponents): Instant {
        return resolveTime(components.year, components.month, components.day)
    }

    /**
     * Add the specified amount of a unit of time to a particular instant
     * @param whenInstant the original instant
     * @param amount the amount to add
     * @param unit the unit of time to add (from [DateTimeUnit])
     * @return the instant with the offset added
     */
    fun add(whenInstant: Instant, amount: Int, unit: DateTimeUnit): Instant {
        return whenInstant.plus(amount.toLong(), unit, TimeZone.UTC)
    }

    /**
     * Gets a date for the particular date
     * @param year the year
     * @param month the month (1-based)
     * @param day the day
     * @return the date with a time set to 00:00:00 at UTC
     */
    private fun resolveTime(year: Int, month: Int, day: Int): Instant {
        return LocalDateTime(year, month, day, 0, 0, 0)
            .toInstant(TimeZone.UTC)
    }

}