package com.applichic.astronomypicture.di

import com.applichic.astronomypicture.api.EntryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideEntryApi(): EntryApi {
        return EntryApi.create()
    }
}