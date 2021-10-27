package com.applichic.astronomypicture.db

import com.applichic.astronomypicture.db.model.MediaType
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar.*

class ConvertersTest {
    private val cal = getInstance().apply {
        set(YEAR, 2021)
        set(MONTH, OCTOBER)
        set(DAY_OF_MONTH, 20)
    }

    @Test
    fun dateStringToCalendar() {
        val convertedDate = Converters().dateStringToCalendar("2021-10-20")
        assertEquals(cal.get(YEAR), convertedDate.get(YEAR))
        assertEquals(cal.get(MONTH), convertedDate.get(MONTH))
        assertEquals(cal.get(DAY_OF_MONTH), convertedDate.get(DAY_OF_MONTH))
    }

    @Test
    fun calendarToDateString() {
        assertEquals("2021-10-20", Converters().calendarToDateString(cal))
    }

    @Test
    fun stringToMediaType() {
        assertEquals(MediaType.Image, Converters().stringToMediaType("image"))
        assertEquals(MediaType.Image, Converters().stringToMediaType("Image"))
        assertEquals(MediaType.Video, Converters().stringToMediaType("video"))
        assertEquals(MediaType.Video, Converters().stringToMediaType("Video"))
        assertEquals(null, Converters().stringToMediaType("something else"))
    }

    @Test
    fun mediaTypeToString() {
        assertEquals("Image", Converters().mediaTypeToString(MediaType.Image))
        assertEquals("Video", Converters().mediaTypeToString(MediaType.Video))
    }
}