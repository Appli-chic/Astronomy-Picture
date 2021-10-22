package com.applichic.astronomypicture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.db.repository.EntryRepository
import com.applichic.astronomypicture.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EntryListViewModel @Inject internal constructor(
    entryRepository: EntryRepository,
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()

    val entries: LiveData<Resource<List<Entry>>> = Transformations
        .switchMap(_isLoading) {
            entryRepository.getAll()
        }

    fun startLoading() {
        _isLoading.value = true
    }
}