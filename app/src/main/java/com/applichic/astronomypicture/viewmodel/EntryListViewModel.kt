package com.applichic.astronomypicture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.db.repository.EntryRepository
import com.applichic.astronomypicture.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EntryListViewModel @Inject internal constructor(
    entryRepository: EntryRepository,
) : ViewModel() {
    val entries: LiveData<Resource<List<Entry>>> = entryRepository.getAll()
}