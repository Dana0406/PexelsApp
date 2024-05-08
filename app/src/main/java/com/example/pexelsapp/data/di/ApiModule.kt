package com.example.pexelsapp.data.di

import com.example.pexelsapp.data.retrofir.PhotoApi
import com.example.pexelsapp.data.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.Interceptor
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
        val interceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", Constants.AUTHORIZATION_KEY)
                .build()

            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ActivityRetainedScoped
    fun providePhotoApi(retrofit: Retrofit): PhotoApi = retrofit.create(PhotoApi::class.java)
}