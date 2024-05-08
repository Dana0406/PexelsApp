package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.domain.repository.AppRepository

class DeletePhotoUseCase(private val appRepository: AppRepository) {

    suspend fun execute(photo: DBPhoto) {
        appRepository.deletePhoto(photo)
    }
}