package com.applichic.astronomypicture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VideoViewerViewModel : ViewModel() {
    private val _url = MutableLiveData<String?>(null)
    var url: LiveData<String?> = _url

    fun setUrl(value: String) {
        _url.value = value
    }
}