package com.example.pexelsapp.domain.usecases

import android.content.Context
import com.example.pexelsapp.domain.repository.AppRepository
import javax.inject.Inject

class DownloadImageUseCase @Inject constructor(private val appRepository: AppRepository) {

    suspend operator fun invoke(imageUrl: String, context: Context) = appRepository.downloadImage(imageUrl, context)
}