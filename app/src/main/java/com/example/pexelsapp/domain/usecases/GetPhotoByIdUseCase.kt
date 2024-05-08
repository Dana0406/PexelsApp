package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.domain.repository.AppRepository
import retrofit2.Call

class GetPhotoByIdUseCase(private val appRepository: AppRepository) {

    suspend fun execute(id: Int): Call<NetworkPhoto> = appRepository.getPhotoById(id)
}