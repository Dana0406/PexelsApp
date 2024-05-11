package com.example.pexelsapp.domain.usecases

import com.example.pexelsapp.domain.models.Featured
import com.example.pexelsapp.domain.repository.AppRepository

class AddFeaturedToCacheUseCase(private val appRepository: AppRepository) {
    fun execute(featured: List<Featured>) {
        appRepository.addFeaturedToCache(featured)
    }
}