package com.applichic.astronomypicture.utils.network

import com.applichic.astronomypicture.utils.network.Status.SUCCESS
import com.applichic.astronomypicture.utils.network.Status.ERROR
import com.applichic.astronomypicture.utils.network.Status.LOADING

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}