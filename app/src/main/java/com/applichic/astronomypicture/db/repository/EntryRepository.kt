package com.applichic.astronomypicture.db.repository

import androidx.lifecycle.LiveData
import com.applichic.astronomypicture.api.EntryApi
import com.applichic.astronomypicture.db.dao.EntryDao
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.di.AppExecutors
import com.applichic.astronomypicture.utils.ApiResponse
import com.applichic.astronomypicture.utils.ApiSuccessResponse
import com.applichic.astronomypicture.utils.DateConverter
import com.applichic.astronomypicture.utils.network.NetworkBoundResource
import com.applichic.astronomypicture.utils.network.Resource
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntryRepository @Inject constructor(
    private val entryDao: EntryDao,
    private val appExecutors: AppExecutors,
    private val entryApi: EntryApi
) {
    fun getFromPeriod(startDate: Calendar, endDate: Calendar): LiveData<Resource<List<Entry>>> {
        return object : NetworkBoundResource<List<Entry>, List<Entry>>(appExecutors) {
            override fun saveCallResult(item: List<Entry>) {
                entryDao.insertAll(item)
            }

            override fun shouldFetch(data: List<Entry>?) = true

            override fun loadFromDb() = entryDao.getFromPeriod(startDate, endDate)

            override fun createCall(): LiveData<ApiResponse<List<Entry>>> {
                return entryApi.getEntriesByPeriod(
                    startDate = DateConverter.calendarToDateString(startDate),
                    endDate = DateConverter.calendarToDateString(endDate),
                )
            }
        }.asLiveData()
    }

    fun getFromDate(date: Calendar): LiveData<Resource<Entry>> {
        return object : NetworkBoundResource<Entry, Entry>(appExecutors) {
            override fun saveCallResult(item: Entry) {
                entryDao.insert(item)
            }

            override fun shouldFetch(data: Entry?) = true

            override fun loadFromDb() = entryDao.getFromDate(date)

            override fun createCall(): LiveData<ApiResponse<Entry>> {
                return entryApi.getEntryByDate(
                    date = DateConverter.calendarToDateString(date),
                )
            }

        }.asLiveData()
    }
}