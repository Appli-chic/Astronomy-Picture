package com.applichic.astronomypicture.utils.network

import com.applichic.astronomypicture.db.model.MediaType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.util.*

class MediaTypeFromStringJsonDeserializer : JsonDeserializer<MediaType?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement, typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MediaType? {
        val value = json.asString
        return try {
            MediaType.valueOf(value.replaceFirstChar { it.titlecase(Locale.getDefault()) })
        } catch (e: Exception) {
            null
        }
    }
}