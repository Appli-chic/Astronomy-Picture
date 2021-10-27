package com.applichic.astronomypicture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.db.repository.EntryRepository
import com.applichic.astronomypicture.di.AppExecutors
import com.applichic.astronomypicture.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EntryDetailViewModel @Inject internal constructor(
    private val entryRepository: EntryRepository,
    private val appExecutors: AppExecutors
) : ViewModel() {
    private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    private val _date = MutableLiveData<Calendar>(null)

    private val _isLoading = MutableLiveData(true)
    var isLoading: LiveData<Boolean> = _isLoading

    private val _entry = MutableLiveData<Entry?>(null)
    var entry: LiveData<Entry?> = _entry

    val entryQuery: LiveData<Resource<Entry>> = Transformations
        .switchMap(_date) { date ->
            entryRepository.getFromDate(date)
        }

    fun setDate(value: Calendar) {
        _isLoading.value = true
        _date.value = value
    }

    fun setEntry(value: Entry) {
        _entry.value = value
    }

    fun setLoading(value: Boolean) {
        _isLoading.value = value
    }

    val dateString: LiveData<String> = Transformations
        .switchMap(_entry) { value ->
            if (value?.date?.time != null) {
                MutableLiveData(dateFormat.format(value.date.time))
            } else {
                MutableLiveData("")
            }
        }

    fun updateFavorite(isFavorite: Boolean) {
        appExecutors.diskIO().execute {
            val updatedEntry = entry.value?.copy(isFavorite = isFavorite)
            if (updatedEntry != null) {
                entryRepository.updateEntry(updatedEntry)

                appExecutors.mainThread().execute {
                    _entry.value = updatedEntry
                }
            }
        }
    }
}