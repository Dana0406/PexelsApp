package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.domain.repository.AppRepository

class GetPhotoFromCacheUseCase(private val appRepository: AppRepository) {
    fun execute(photoId: Int): NetworkPhoto? {
        return appRepository.getCachePhoto(photoId)
    }
}