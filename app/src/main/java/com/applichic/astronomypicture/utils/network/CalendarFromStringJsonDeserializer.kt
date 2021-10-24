package com.applichic.astronomypicture.utils.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class CalendarFromStringJsonDeserializer : JsonDeserializer<Calendar?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement, typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Calendar {
        val dateString = json.asString
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return Calendar.getInstance().apply {
            val parsedTime = format.parse(dateString)
            time = parsedTime ?: Date()
        }
    }
}