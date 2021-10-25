package com.applichic.astronomypicture.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.applichic.astronomypicture.db.AppDatabase
import com.applichic.astronomypicture.db.dao.EntryDao
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.db.model.MediaType
import com.applichic.astronomypicture.utils.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.equalTo
import java.util.*

@RunWith(AndroidJUnit4::class)
class EntryDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var entryDao: EntryDao

    private val dateA = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2021)
        set(Calendar.MONTH, 5)
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    private val dateB = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2021)
        set(Calendar.MONTH, 9)
        set(Calendar.DAY_OF_MONTH, 20)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    private val dateC = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2020)
        set(Calendar.MONTH, 8)
        set(Calendar.DAY_OF_MONTH, 6)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    private val dateE = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2021)
        set(Calendar.MONTH, 8)
        set(Calendar.DAY_OF_MONTH, 12)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    private val entryA = Entry(
        "Pluto in Enhanced Color",
        "Pluto is more colorful than we can see.",
        null,
        dateA,
        "https://apod.nasa.gov/apod/image/2108/PlutoEnhancedHiRes_NewHorizons_960.jpg",
        "https://apod.nasa.gov/apod/image/2108/PlutoEnhancedHiRes_NewHorizons_5000.jpg",
        MediaType.Image,
        null
    )

    private val entryB = Entry(
        "The Hubble Ultra Deep Field in Light and Sound",
        "Have you heard about the Hubble Ultra-Deep Field?",
        null,
        dateB,
        "https://apod.nasa.gov/apod/image/1803/AstroSoM/hudf.html",
        null,
        MediaType.Video,
        null
    )

    private val entryC = Entry(
        "A Perseid Fireball and the Milky Way",
        "It was bright and green and flashed as it moved quickly along the Milky Way.",
        "Dandan Huang",
        dateC,
        "https://apod.nasa.gov/apod/image/2108/PerseusFireball_Dandan_960.jpg",
        "https://apod.nasa.gov/apod/image/2108/PerseusFireball_Dandan_1125.jpg",
        MediaType.Image,
        null
    )

    private val entryD = Entry(
        "Pluto in Enhanced Color",
        "Pluto is more colorful than we can see.",
        null,
        dateA,
        "https://apod.nasa.gov/apod/image/2108/PlutoEnhancedHiRes_NewHorizons_960.jpg",
        null,
        MediaType.Image,
        null
    )

    private val entryE = Entry(
        "EHT Resolves Central Jet from Black Hole in Cen A",
        "How do supermassive black holes create powerful jets?",
        null,
        dateE,
        "https://apod.nasa.gov/apod/image/2108/PlutoEnhancedHiRes_NewHorizons_960.jpg",
        null,
        MediaType.Image,
        null
    )

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        entryDao = database.entryDao()

        // Insert entries not sorted by date
        entryDao.insertAll(listOf(entryA, entryB, entryC))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getFromPeriod() = runBlocking {
        var entries = entryDao.getFromPeriod(dateC, dateB).getOrAwaitValue()
        assertThat(entries.size, equalTo(3))

        // Make sure the order by date is respected
        assertThat(entries[0], equalTo(entryB))
        assertThat(entries[1], equalTo(entryA))
        assertThat(entries[2], equalTo(entryC))

        // Make sure it retrieve the entries in the period
        entries = entryDao.getFromPeriod(dateA, dateB).getOrAwaitValue()
        assertThat(entries.size, equalTo(2))
        assertThat(entries[0], equalTo(entryB))
        assertThat(entries[1], equalTo(entryA))
    }

    @Test
    fun insertAll() = runBlocking {
        entryDao.insertAll(listOf(entryD, entryE))
        val entries = entryDao.getFromPeriod(dateC, dateB).getOrAwaitValue()

        // Make sure the entryD doesn't count like a new entry because it has the same date as entry A
        assertThat(entries.size, equalTo(4))
    }
}