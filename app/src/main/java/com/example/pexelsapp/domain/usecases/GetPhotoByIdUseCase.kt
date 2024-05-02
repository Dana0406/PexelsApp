package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.repositories.RemotePhotoRepository
import com.example.pexelsapp.domain.models.Photo
import retrofit2.Call

class GetPhotoByIdUseCase(private val remotePhotoRepository: RemotePhotoRepository) {
    suspend fun execute(id: Int): Call<Photo> {
        return remotePhotoRepository.getPhotoById(id)
    }
}