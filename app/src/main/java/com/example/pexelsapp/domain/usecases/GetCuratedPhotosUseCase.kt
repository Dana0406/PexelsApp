package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.repositories.RemotePhotoRepository
import com.example.pexelsapp.domain.models.PhotoResponse
import retrofit2.Call

class GetCuratedPhotosUseCase(private val remotePhotoRepository: RemotePhotoRepository) {
    suspend fun execute(page: Int, perPage: Int): Call<PhotoResponse> {
        return remotePhotoRepository.getCuratedPhotos(page, perPage)
    }
}