package com.applichic.astronomypicture.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "entries")
data class Entry(
    val title: String,

    val explanation: String,

    val copyright: String,

    @PrimaryKey
    val date: Calendar = Calendar.getInstance(),

    val url: String,

    @ColumnInfo(name = "hd_url")
    @field:SerializedName("hdurl")
    val hdUrl: String
)