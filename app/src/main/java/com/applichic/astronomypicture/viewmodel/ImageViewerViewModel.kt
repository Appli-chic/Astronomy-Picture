package com.applichic.astronomypicture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageViewerViewModel : ViewModel() {
    private val _url = MutableLiveData<String?>(null)
    var url: LiveData<String?> = _url

    private val _hdUrl = MutableLiveData<String?>(null)
    var hdUrl: LiveData<String?> = _hdUrl

    private val _isLoading = MutableLiveData(false)
    var isLoading: LiveData<Boolean> = _isLoading

    fun setUrl(value: String) {
        _url.value = value
    }

    fun setHdUrl(value: String?) {
        _hdUrl.value = value
    }

    fun setLoading(value: Boolean) {
        _isLoading.value = value
    }
}