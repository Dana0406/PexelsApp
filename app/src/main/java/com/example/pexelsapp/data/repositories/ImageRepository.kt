package com.example.pexelsapp.data.repositories

import android.graphics.Bitmap

interface ImageRepository {
    suspend fun downloadImage(imageUrl: String)
}
