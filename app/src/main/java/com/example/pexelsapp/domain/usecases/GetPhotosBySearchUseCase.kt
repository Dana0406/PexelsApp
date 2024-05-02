package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.repositories.RemotePhotoRepository
import com.example.pexelsapp.domain.models.PhotoResponse
import retrofit2.Call

class GetPhotosBySearchUseCase(private val remotePhotoRepository: RemotePhotoRepository) {
    suspend fun execute(query: String, page: Int, perPage: Int): Call<PhotoResponse> {
        return remotePhotoRepository.getPhotosBySearch(query, page, perPage)
    }
}