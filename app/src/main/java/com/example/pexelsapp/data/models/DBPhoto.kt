package com.example.pexelsapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pexelsapp.domain.models.PhotoSource
@Entity(tableName = "photo_information")
data class DBPhoto(
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