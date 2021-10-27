package com.applichic.astronomypicture.viewmodel

import androidx.lifecycle.*
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.db.repository.EntryRepository
import com.applichic.astronomypicture.utils.DateConverter
import com.applichic.astronomypicture.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class EntryListViewModel @Inject internal constructor(
    entryRepository: EntryRepository,
) : ViewModel() {
    private val today = Calendar.getInstance()

    private val _isLoading = MutableLiveData(true)
    var isLoading: LiveData<Boolean> = _isLoading

    private val _page = MutableLiveData(1)
    val page: LiveData<Int> = _page

    val entries = ArrayList<Entry>()
    val entriesQuery: LiveData<Resource<List<Entry>>> = Transformations
        .switchMap(_page) { page ->
            val startDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, today.get(Calendar.YEAR))
                set(Calendar.MONTH, today.get(Calendar.MONTH) - page)
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }

            var endDate = today.clone() as Calendar
            if (page > 1) {
                endDate = startDate.clone() as Calendar
                endDate.apply {
                    set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DATE))
                    set(Calendar.HOUR_OF_DAY, 23)
                    set(Calendar.MINUTE, 59)
                    set(Calendar.SECOND, 59)
                }
            } else {
                // Adapt the date of today with the time zone of NASA
                DateConverter.adaptToNasaTimeZone(endDate)
            }


            entryRepository.getFromPeriod(startDate, endDate)
        }

    fun reloadEntries() {
        _isLoading.value = true
        _page.value = page.value
    }

    fun loadMore() {
        _isLoading.value = true
        _page.value = page.value?.plus(1)
    }

    fun stopLoading() {
        _isLoading.value = false
    }
}