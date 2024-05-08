package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.domain.models.FeaturedResponse
import com.example.pexelsapp.domain.repository.AppRepository
import retrofit2.Call

class GetFeaturedCollectionUseCase(private val appRepository: AppRepository) {

    suspend fun execute(page: Int, perPage: Int): Call<FeaturedResponse> =
        appRepository.getFeaturedCollection(page, perPage)
}