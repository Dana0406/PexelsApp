package com.example.pexelsapp.data.repositories

import androidx.lifecycle.LiveData
import com.example.pexelsapp.domain.models.Photo

interface LocalPhotoRepository {
    suspend fun savePhoto(photo: Photo)
    suspend fun deletePhoto(photo: Photo)
    fun getAllPhotos(): LiveData<List<Photo>>
}