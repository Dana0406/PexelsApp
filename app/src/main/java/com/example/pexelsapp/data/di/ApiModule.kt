package com.example.pexelsapp.data.di

import com.example.pexelsapp.data.repositories.RemotePhotoRepository
import com.example.pexelsapp.data.repositories.RemotePhotoRepositoryImpl
import com.example.pexelsapp.data.retrofir.PhotoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
object ApiModule {
    @Provides
    @ActivityRetainedScoped
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ActivityRetainedScoped
    fun providePhotoApi(retrofit: Retrofit): PhotoApi {
        return retrofit.create(PhotoApi::class.java)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRemotePhotoRepository(photoApi: PhotoApi): RemotePhotoRepository {
        return RemotePhotoRepositoryImpl(photoApi)
    }
}