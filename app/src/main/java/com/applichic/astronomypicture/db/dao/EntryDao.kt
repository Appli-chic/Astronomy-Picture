package com.applichic.astronomypicture.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.applichic.astronomypicture.db.model.Entry

@Dao
interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entries: List<Entry>)

    @Query("SELECT * FROM entries")
    fun getAll(): LiveData<List<Entry>>
}