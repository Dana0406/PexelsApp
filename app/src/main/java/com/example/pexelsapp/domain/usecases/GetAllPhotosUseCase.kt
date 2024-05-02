package com.example.pexelsapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.pexelsapp.data.repositories.LocalPhotoRepository
import com.example.pexelsapp.domain.models.Photo

class GetAllPhotosUseCase(private val localPhotoRepository: LocalPhotoRepository) {
    fun execute(): LiveData<List<Photo>>{
        return localPhotoRepository.getAllPhotos()
    }
}