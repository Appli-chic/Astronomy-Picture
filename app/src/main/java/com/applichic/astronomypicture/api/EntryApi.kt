package com.applichic.astronomypicture.api

import androidx.lifecycle.LiveData
import com.applichic.astronomypicture.db.model.Entry
import com.applichic.astronomypicture.utils.ApiResponse
import com.applichic.astronomypicture.utils.BASE_URL
import com.applichic.astronomypicture.utils.CalendarFromStringJsonDeserializer
import com.applichic.astronomypicture.utils.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.google.gson.GsonBuilder

import java.util.*

interface EntryApi {

    @GET("apod")
    fun getEntries(
        @Query("api_key") clientId: String = "DEMO_KEY",
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
    ): LiveData<ApiResponse<List<Entry>>>

    companion object {
        fun create(): EntryApi {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .registerTypeAdapter(
                    Calendar::class.java,
                    CalendarFromStringJsonDeserializer()
                )
                .create()

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(EntryApi::class.java)
        }
    }
}