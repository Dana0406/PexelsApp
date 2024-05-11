package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.domain.repository.AppRepository

class GetAllCachePhotosUseCase(private val appRepository: AppRepository) {
    fun execute(): List<NetworkPhoto> {
        return appRepository.getAllCachePhotos()
    }
}