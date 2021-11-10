package com.applichic.astronomypicture.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.applichic.astronomypicture.db.model.Entry
import java.util.*

@Dao
interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(entries: List<Entry>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entry: Entry)

    @Query("SELECT * FROM entries WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getFromPeriod(startDate: Calendar, endDate: Calendar): LiveData<List<Entry>>

    @Query("SELECT * FROM entries WHERE date = :date")
    fun getFromDate(date: Calendar): LiveData<Entry>

    @Query("SELECT * FROM entries WHERE is_favorite = 1 ORDER BY date DESC")
    fun getAllFavorites(): LiveData<List<Entry>>

    @Update
    fun update(entry: Entry)
}