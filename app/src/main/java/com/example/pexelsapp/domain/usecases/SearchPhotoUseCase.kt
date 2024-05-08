package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.domain.models.PhotoResponse
import com.example.pexelsapp.domain.repository.AppRepository
import retrofit2.Call

class SearchPhotosUseCase(private val appRepository: AppRepository) {

    suspend fun execute(query: String, page: Int, perPage: Int): Call<PhotoResponse> =
        appRepository.getPhotosBySearch(query, page, perPage)
}