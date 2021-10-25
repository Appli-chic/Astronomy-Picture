package com.applichic.astronomypicture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.db.repository.EntryRepository
import com.applichic.astronomypicture.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EntryListViewModel @Inject internal constructor(
    entryRepository: EntryRepository,
) : ViewModel() {
    private val startDate = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2021)
        set(Calendar.MONTH, Calendar.AUGUST)
        set(Calendar.DAY_OF_MONTH, 1)
        set(Calendar.HOUR, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    private val endDate = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2021)
        set(Calendar.MONTH, Calendar.OCTOBER)
        set(Calendar.DAY_OF_MONTH, 25)
        set(Calendar.HOUR, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    val entries: LiveData<Resource<List<Entry>>> = entryRepository.getAll(startDate, endDate)
}