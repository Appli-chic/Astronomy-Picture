package com.applichic.astronomypicture.utils

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object {
        private val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        fun dateStringToCalendar(value: String): Calendar {
            return Calendar.getInstance().apply {
                val parsedTime = format.parse(value)
                time = parsedTime ?: Date()
            }
        }

        fun calendarToDateString(value: Calendar): String {
            return format.format(value.time)
        }
    }
}