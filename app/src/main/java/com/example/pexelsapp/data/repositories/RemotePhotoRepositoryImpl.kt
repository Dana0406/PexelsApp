package com.example.pexelsapp.data.repositories

import com.example.pexelsapp.data.retrofir.PhotoApi
import com.example.pexelsapp.domain.models.FeaturedResponse
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.domain.models.PhotoResponse
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemotePhotoRepositoryImpl @Inject constructor(private val photoApi: PhotoApi) : RemotePhotoRepository {
    override suspend fun getPhotoById(id: Int): Call<Photo> {
        return photoApi.getPhotoById(id)
    }

    override suspend fun getCuratedPhotos(page: Int, perPage: Int): Call<PhotoResponse> {
        return photoApi.getCuratedPhotos(page, perPage)
    }

    override suspend fun getFeaturedCollection(page: Int, perPage: Int): Call<FeaturedResponse> {
        return photoApi.getFeaturedCollection(page, perPage)
    }

    override suspend fun getPhotosBySearch(query: String, page: Int, perPage: Int): Call<PhotoResponse> {
        return photoApi.getPhotosBySearch(query, page, perPage)
    }
}