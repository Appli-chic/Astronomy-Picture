package com.applichic.astronomypicture.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

enum class MediaType {
    Image,
    Video
}

@Entity(tableName = "entries")
data class Entry(
    val title: String,

    val explanation: String,

    val copyright: String?,

    @PrimaryKey
    val date: Calendar = Calendar.getInstance(),

    val url: String,

    @ColumnInfo(name = "hd_url")
    @field:SerializedName("hdurl")
    val hdUrl: String?,

    @ColumnInfo(name = "media_type")
    @field:SerializedName("media_type")
    val mediaType: MediaType?,

    @ColumnInfo(name = "thumbnail_url")
    @field:SerializedName("thumbnail_url")
    val thumbnailUrl: String?,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)