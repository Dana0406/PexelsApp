package com.example.pexelsapp.data.repositories

import com.example.pexelsapp.domain.models.FeaturedResponse
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.domain.models.PhotoResponse
import retrofit2.Call

interface RemotePhotoRepository {
    suspend fun getPhotoById(id: Int): Call<Photo>
    suspend fun getCuratedPhotos(page: Int, perPage: Int): Call<PhotoResponse>
    suspend fun getFeaturedCollection(page: Int, perPage: Int): Call<FeaturedResponse>
    suspend fun getPhotosBySearch(query: String, page: Int, perPage: Int): Call<PhotoResponse>
}