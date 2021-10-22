package com.applichic.astronomypicture.db

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    private val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    @TypeConverter
    fun dateStringToCalendar(value: String): Calendar {
        return Calendar.getInstance().apply {
            val parsedTime = format.parse(value)
            time = parsedTime ?: Date()
        }
    }

    @TypeConverter
    fun calendarToDateString(value: Calendar): String {
        return format.format(value.time)
    }
}