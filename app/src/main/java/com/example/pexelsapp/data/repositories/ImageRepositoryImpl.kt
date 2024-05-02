package com.example.pexelsapp.data.repositories

import android.content.Context
import android.graphics.Bitmap
import com.example.pexelsapp.data.ImageDownloader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepositoryImpl @Inject constructor(
    private val imageDownloader: ImageDownloader,
    private val context: Context
) : ImageRepository {
    override suspend fun downloadImage(imageUrl: String) {
        imageDownloader.downloadImage(imageUrl, context) { bitmap, error ->

        }
    }
}