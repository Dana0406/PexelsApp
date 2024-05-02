package com.example.pexelsapp.data.retrofir

import com.example.pexelsapp.domain.models.FeaturedResponse
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.domain.models.PhotoResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val HEADER = "Authorization: rUmU1kYPZfIT2aGg2hqRBds7nCKQnsfeA8a5HvSPBY1NkKIiEtd36oDT"

@ActivityRetainedScoped
interface PhotoApi {
    @Headers(HEADER)
    @GET("photos/{id}")
    fun getPhotoById(@Path("id") id: Int): Call<Photo>

    @Headers(HEADER)
    @GET("curated")
    fun getCuratedPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<PhotoResponse>

    @Headers(HEADER)
    @GET("collections/featured")
    fun getFeaturedCollection(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<FeaturedResponse>

    @Headers(HEADER)
    @GET("search")
    fun getPhotosBySearch(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<PhotoResponse>
}