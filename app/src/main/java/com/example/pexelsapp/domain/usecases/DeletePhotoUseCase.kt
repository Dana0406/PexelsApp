package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.repositories.LocalPhotoRepository
import com.example.pexelsapp.domain.models.Photo

class DeletePhotoUseCase(private val localPhotoRepository: LocalPhotoRepository) {
    suspend fun execute(photo: Photo) {
        localPhotoRepository.deletePhoto(photo)
    }
}