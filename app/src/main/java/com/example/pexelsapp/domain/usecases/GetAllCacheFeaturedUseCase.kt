package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.domain.models.Featured
import com.example.pexelsapp.domain.repository.AppRepository

class GetAllCacheFeaturedUseCase(private val appRepository: AppRepository) {

    fun execute(): List<Featured> {
        return appRepository.getAllCacheFeatured()
    }
}