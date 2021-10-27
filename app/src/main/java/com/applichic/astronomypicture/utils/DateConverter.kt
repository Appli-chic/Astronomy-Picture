package com.applichic.astronomypicture.utils

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object {
        private val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        fun dateStringToCalendar(value: String): Calendar {
            return Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                val parsedTime = format.parse(value)
                time = parsedTime ?: Date()
            }
        }

        fun calendarToDateString(value: Calendar): String {
            return format.format(value.time)
        }

        fun adaptToNasaTimeZone(date: Calendar) {
            val timeZone = date.timeZone
            val nasaTimeZone = TimeZone.getTimeZone("GMT-4")
            date.add(
                Calendar.HOUR_OF_DAY, (nasaTimeZone.getOffset(Calendar.getInstance().timeInMillis) -
                        timeZone.getOffset(Calendar.getInstance().timeInMillis)) / 1000 / 60 / 60
            )
        }
    }
}