package com.example.pexelsapp.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.pexelsapp.data.ImageDownloader
import com.example.pexelsapp.data.db.PhotoDao
import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.data.retrofir.PhotoApi
import com.example.pexelsapp.domain.models.FeaturedResponse
import com.example.pexelsapp.domain.models.PhotoResponse
import com.example.pexelsapp.domain.repository.AppRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val imageDownloader: ImageDownloader,
    private val photoDao: PhotoDao,
    private val photoApi: PhotoApi
): AppRepository {

    override suspend fun downloadImage(imageUrl: String, context: Context) {
        imageDownloader.downloadImage(imageUrl, context)
    }

    override suspend fun savePhoto(photo: DBPhoto) {
        photoDao.upsertPhoto(photo)
    }

    override suspend fun deletePhoto(photo: DBPhoto) {
        photoDao.deletePhoto(photo)
    }
    
    override suspend fun getAllPhotos(): List<DBPhoto> = photoDao.getAllPhotos()

    override suspend fun getPhotoById(id: Int): Response<NetworkPhoto> = photoApi.getPhotoById(id)

    override suspend fun getCuratedPhotos(page: Int, perPage: Int): Response<PhotoResponse> =
        photoApi.getCuratedPhotos(page, perPage)

    override suspend fun getFeaturedCollection(page: Int, perPage: Int): Response<FeaturedResponse> =
        photoApi.getFeaturedCollection(page, perPage)

    override suspend fun getPhotosBySearch(
        query: String,
        page: Int,
        perPage: Int
    ): Response<PhotoResponse> = photoApi.getPhotosBySearch(query, page, perPage)
}