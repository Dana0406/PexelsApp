package com.example.pexelsapp.domain.usecases

import android.graphics.Bitmap
import com.example.pexelsapp.data.repositories.ImageRepository
import javax.inject.Inject

class DownloadImageUseCase @Inject constructor(private val imageRepository: ImageRepository) {

    suspend operator fun invoke(imageUrl: String) {
        return imageRepository.downloadImage(imageUrl)
    }
}