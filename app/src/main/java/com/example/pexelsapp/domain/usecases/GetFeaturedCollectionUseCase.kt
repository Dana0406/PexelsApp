package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.repositories.RemotePhotoRepository
import com.example.pexelsapp.domain.models.FeaturedResponse
import retrofit2.Call

class GetFeaturedCollectionUseCase(private val remotePhotoRepository: RemotePhotoRepository) {
    suspend fun execute(page: Int, perPage: Int): Call<FeaturedResponse> {
        return remotePhotoRepository.getFeaturedCollection(page, perPage)
    }
}