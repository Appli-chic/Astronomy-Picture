package com.applichic.astronomypicture.db.repository

import androidx.lifecycle.LiveData
import com.applichic.astronomypicture.api.EntryApi
import com.applichic.astronomypicture.db.dao.EntryDao
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.di.AppExecutors
import com.applichic.astronomypicture.utils.NetworkBoundResource
import com.applichic.astronomypicture.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntryRepository @Inject constructor(
    private val entryDao: EntryDao,
    private val appExecutors: AppExecutors,
    private val entryApi: EntryApi
) {

    fun getAll(): LiveData<Resource<List<Entry>>> {
        return object : NetworkBoundResource<List<Entry>, List<Entry>>(appExecutors) {
            override fun saveCallResult(item: List<Entry>) {
                entryDao.insertAll(item)
            }

            override fun shouldFetch(data: List<Entry>?) = true

            override fun loadFromDb() = entryDao.getAll()

            override fun createCall() = entryApi.getEntries(startDate = "2021-10-21", endDate = "2021-10-22")
        }.asLiveData()
    }
}