package com.example.pexelsapp.data.retrofir

import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.data.utils.Constants
import com.example.pexelsapp.domain.models.FeaturedResponse
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.domain.models.PhotoResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

@ActivityRetainedScoped
interface PhotoApi {

    @GET("photos/{id}")
    fun getPhotoById(@Path("id") id: Int): Call<NetworkPhoto>

    @GET("curated")
    fun getCuratedPhotos(
        @Query("page") page: Int = Constants.DEFAULT_PAGE_NUMBER,
        @Query("per_page") perPage: Int = Constants.DEFAULT_PAGE_SIZE
    ): Call<PhotoResponse>

    @GET("collections/featured")
    fun getFeaturedCollection(
        @Query("page") page: Int = Constants.DEFAULT_PAGE_NUMBER,
        @Query("per_page") perPage: Int = Constants.COLLECTIONS_PAGE_SIZE
    ): Call<FeaturedResponse>

    @GET("search")
    fun getPhotosBySearch(
        @Query("query") query: String,
        @Query("page") page: Int = Constants.DEFAULT_PAGE_NUMBER,
        @Query("per_page") perPage: Int = Constants.DEFAULT_PAGE_SIZE
    ): Call<PhotoResponse>
}