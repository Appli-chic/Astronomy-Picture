package com.applichic.astronomypicture.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.applichic.astronomypicture.db.dao.EntryDao
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.utils.DATABASE_NAME

@Database(entities = [Entry::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        /**
         * Get the instance of the database.
         *
         * If the database doesn't exist yet, it creates it
         */
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}