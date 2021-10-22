package com.applichic.astronomypicture.di

import android.content.Context
import com.applichic.astronomypicture.db.AppDatabase
import com.applichic.astronomypicture.db.dao.EntryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideEntryDao(appDatabase: AppDatabase): EntryDao {
        return appDatabase.entryDao()
    }
}