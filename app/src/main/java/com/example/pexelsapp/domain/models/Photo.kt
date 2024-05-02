package com.example.pexelsapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.pexelsapp.data.db.PhotoTypeConverter
import kotlinx.parcelize.Parcelize

@Entity(tableName = "photo_information")
@TypeConverters(PhotoTypeConverter::class)
data class Photo(
    @PrimaryKey
    val id: Int,
    val width: Int?,
    val height: Int?,
    val url: String?,
    val photographer: String?,
    val photographerUrl: String?,
    val photographerId: Int?,
    val avgColor: String?,
    val src: PhotoSource?,
    var liked: Boolean? = false,
    val alt: String?
)