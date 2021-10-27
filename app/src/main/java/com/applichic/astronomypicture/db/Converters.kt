package com.applichic.astronomypicture.db

import androidx.room.TypeConverter
import com.applichic.astronomypicture.db.model.MediaType
import com.applichic.astronomypicture.utils.DateConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun dateStringToCalendar(value: String): Calendar {
        return DateConverter.dateStringToCalendar(value)
    }

    @TypeConverter
    fun calendarToDateString(value: Calendar): String {
        return DateConverter.calendarToDateString(value)
    }

    @TypeConverter
    fun stringToMediaType(value: String): MediaType? {
        return try {
            MediaType.valueOf(value.replaceFirstChar { it.titlecase(Locale.getDefault()) })
        } catch (e: Exception) {
            null
        }
    }

    @TypeConverter
    fun mediaTypeToString(value: MediaType): String {
        return value.name
    }
}