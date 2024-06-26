package com.example.pexelsapp.data.repositories

import android.content.Context
import com.example.pexelsapp.data.ImageDownloader
import com.example.pexelsapp.data.db.PhotoDao
import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.data.retrofir.PhotoApi
import com.example.pexelsapp.domain.models.Featured
import com.example.pexelsapp.domain.models.FeaturedResponse
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.domain.models.PhotoResponse
import com.example.pexelsapp.domain.repository.AppRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val imageDownloader: ImageDownloader,
    private val photoDao: PhotoDao,
    private val photoApi: PhotoApi,
    private val photoMap: MutableMap<Int, NetworkPhoto>,
    private val featuredMap: MutableMap<String, Featured>,
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

    override fun getCachePhoto(photoId: Int): NetworkPhoto? {
        return photoMap[photoId]
    }

    override fun getAllCachePhotos(): List<NetworkPhoto> {
        return photoMap.values.toList()
    }

    override fun addPhotosToCache(photos: List<NetworkPhoto>) {
        for (photo in photos) {
            photoMap[photo.id] = photo
        }
    }

    override fun getAllCacheFeatured(): List<Featured> {
        return featuredMap.values.toList()
    }

    override fun addFeaturedToCache(featured: List<Featured>) {
        for (feature in featured) {
            featuredMap[feature.id] = feature
        }
    }
}