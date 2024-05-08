package com.example.pexelsapp.data.retrofir

import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.data.utils.Constants
import com.example.pexelsapp.domain.models.FeaturedResponse
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.domain.models.PhotoResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

@ActivityRetainedScoped
interface PhotoApi {

    @GET("photos/{id}")
    suspend fun getPhotoById(@Path("id") id: Int): Response<NetworkPhoto>

    @GET("curated")
    suspend fun getCuratedPhotos(
        @Query("page") page: Int = Constants.DEFAULT_PAGE_NUMBER,
        @Query("per_page") perPage: Int = Constants.DEFAULT_PAGE_SIZE
    ): Response<PhotoResponse>

    @GET("collections/featured")
    suspend fun getFeaturedCollection(
        @Query("page") page: Int = Constants.DEFAULT_PAGE_NUMBER,
        @Query("per_page") perPage: Int = Constants.COLLECTIONS_PAGE_SIZE
    ): Response<FeaturedResponse>

    @GET("search")
    suspend fun getPhotosBySearch(
        @Query("query") query: String,
        @Query("page") page: Int = Constants.DEFAULT_PAGE_NUMBER,
        @Query("per_page") perPage: Int = Constants.DEFAULT_PAGE_SIZE
    ): Response<PhotoResponse>
}