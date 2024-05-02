package com.example.pexelsapp.data.repositories

import androidx.lifecycle.LiveData
import com.example.pexelsapp.data.db.PhotoDao
import com.example.pexelsapp.domain.models.Photo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalPhotoRepositoryImpl @Inject constructor(private val photoDao: PhotoDao): LocalPhotoRepository {
    override suspend fun savePhoto(photo: Photo) {
        photoDao.upsertPhoto(photo)
    }

    override suspend fun deletePhoto(photo: Photo) {
        photoDao.deletePhoto(photo)
    }

    override fun getAllPhotos(): LiveData<List<Photo>>{
        return photoDao.getAllPhotos()
    }
}