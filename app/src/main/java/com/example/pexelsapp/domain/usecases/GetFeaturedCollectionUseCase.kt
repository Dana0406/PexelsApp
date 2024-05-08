package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.domain.models.FeaturedResponse
import com.example.pexelsapp.domain.repository.AppRepository
import retrofit2.Call
import retrofit2.Response

class GetFeaturedCollectionUseCase(private val appRepository: AppRepository) {

    suspend fun execute(page: Int, perPage: Int): Response<FeaturedResponse> =
        appRepository.getFeaturedCollection(page, perPage)
}