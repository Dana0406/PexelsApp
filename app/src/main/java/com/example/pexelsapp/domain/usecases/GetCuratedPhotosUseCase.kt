package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.domain.models.PhotoResponse
import com.example.pexelsapp.domain.repository.AppRepository
import retrofit2.Call
import retrofit2.Response

class GetCuratedPhotosUseCase(private val appRepository: AppRepository) {

    suspend fun execute(page: Int, perPage: Int): Response<PhotoResponse> =
        appRepository.getCuratedPhotos(page, perPage)
}