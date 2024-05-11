package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.domain.repository.AppRepository

class AddPhotosToCacheUseCase(private val appRepository: AppRepository) {
    fun execute(photos: List<NetworkPhoto>) {
        appRepository.addPhotosToCache(photos)
    }
}