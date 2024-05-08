package com.example.pexelsapp.domain.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.domain.models.FeaturedResponse
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.domain.models.PhotoResponse
import retrofit2.Call
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
}