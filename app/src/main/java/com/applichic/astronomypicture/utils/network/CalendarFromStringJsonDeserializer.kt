package com.applichic.astronomypicture.utils.network

import com.applichic.astronomypicture.utils.DateConverter
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.util.*

class CalendarFromStringJsonDeserializer : JsonDeserializer<Calendar?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement, typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Calendar {
        return DateConverter.dateStringToCalendar(json.asString)
    }
}