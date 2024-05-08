package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.domain.models.PhotoResponse
import com.example.pexelsapp.domain.repository.AppRepository
import retrofit2.Call

class GetCuratedPhotosUseCase(private val appRepository: AppRepository) {

    suspend fun execute(page: Int, perPage: Int): Call<PhotoResponse> =
        appRepository.getCuratedPhotos(page, perPage)
}