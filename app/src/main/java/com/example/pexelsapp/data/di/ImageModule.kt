package com.example.pexelsapp.data.di

import android.content.Context
import com.example.pexelsapp.data.ImageDownloader
import com.example.pexelsapp.data.repositories.ImageRepository
import com.example.pexelsapp.data.repositories.ImageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageModule {
    @Provides
    @Singleton
    fun provideImageRepository(imageDownloader: ImageDownloader,@ApplicationContext context: Context): ImageRepository {
        return ImageRepositoryImpl(imageDownloader, context)
    }
}