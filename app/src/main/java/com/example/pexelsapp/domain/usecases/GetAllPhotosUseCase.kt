package com.example.pexelsapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.domain.repository.AppRepository

class GetAllPhotosUseCase(private val appRepository: AppRepository) {

    suspend fun execute(): List<DBPhoto> = appRepository.getAllPhotos()
}