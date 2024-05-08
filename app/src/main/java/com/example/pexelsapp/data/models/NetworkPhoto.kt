package com.example.pexelsapp.data.models

import androidx.room.TypeConverters
import com.example.pexelsapp.data.db.PhotoTypeConverter
import com.example.pexelsapp.domain.models.PhotoSource
import com.google.gson.annotations.SerializedName

@TypeConverters(PhotoTypeConverter::class)
data class NetworkPhoto(
    val id: Int,
    val width: Int?,
    val height: Int?,
    val url: String?,
    val photographer: String?,
    @SerializedName("photographer_url") val photographerUrl: String?,
    @SerializedName("photographer_id") val photographerId: Int?,
    @SerializedName("avg_color") val avgColor: String?,
    val src: PhotoSource?,
    var liked: Boolean? = false,
    val alt: String?
)