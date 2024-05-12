package com.example.pexelsapp.domain.repository

import android.content.Context
import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.domain.models.Featured
import com.example.pexelsapp.domain.models.FeaturedResponse
import com.example.pexelsapp.domain.models.PhotoResponse
import retrofit2.Response

interface AppRepository {
    suspend fun downloadImage(imageUrl: String, context: Context)
    suspend fun savePhoto(photo: DBPhoto)
    suspend fun deletePhoto(photo: DBPhoto)
    suspend fun getAllPhotos(): List<DBPhoto>
    suspend fun getPhotoById(id: Int): Response<NetworkPhoto>
    suspend fun getCuratedPhotos(page: Int, perPage: Int): Response<PhotoResponse>
    suspend fun getFeaturedCollection(page: Int, perPage: Int): Response<FeaturedResponse>
    suspend fun getPhotosBySearch(query: String, page: Int, perPage: Int): Response<PhotoResponse>
    fun getCachePhoto(photoId: Int): NetworkPhoto?
    fun getAllCachePhotos(): List<NetworkPhoto>
    fun addPhotosToCache(photos: List<NetworkPhoto>)
    fun getAllCacheFeatured(): List<Featured>
    fun addFeaturedToCache(featured: List<Featured>)
}