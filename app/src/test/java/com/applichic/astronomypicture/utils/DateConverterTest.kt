package com.applichic.astronomypicture.utils

import org.junit.Assert
import org.junit.Test
import java.util.*

class DateConverterTest {
    private val cal = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2021)
        set(Calendar.MONTH, Calendar.OCTOBER)
        set(Calendar.DAY_OF_MONTH, 20)
    }

    @Test
    fun dateStringToCalendar() {
        val convertedDate = DateConverter.dateStringToCalendar("2021-10-20")
        Assert.assertEquals(cal.get(Calendar.YEAR), convertedDate.get(Calendar.YEAR))
        Assert.assertEquals(cal.get(Calendar.MONTH), convertedDate.get(Calendar.MONTH))
        Assert.assertEquals(
            cal.get(Calendar.DAY_OF_MONTH),
            convertedDate.get(Calendar.DAY_OF_MONTH)
        )
    }

    @Test
    fun calendarToDateString() {
        Assert.assertEquals("2021-10-20", DateConverter.calendarToDateString(cal))
    }
}