package com.example.pexelsapp.domain.di

import com.example.pexelsapp.domain.repository.AppRepository
import com.example.pexelsapp.domain.usecases.DeletePhotoUseCase
import com.example.pexelsapp.domain.usecases.DownloadImageUseCase
import com.example.pexelsapp.domain.usecases.GetAllPhotosUseCase
import com.example.pexelsapp.domain.usecases.GetCuratedPhotosUseCase
import com.example.pexelsapp.domain.usecases.GetFeaturedCollectionUseCase
import com.example.pexelsapp.domain.usecases.GetPhotoByIdUseCase
import com.example.pexelsapp.domain.usecases.GetPhotosBySearchUseCase
import com.example.pexelsapp.domain.usecases.SavePhotoUseCase
import com.example.pexelsapp.domain.usecases.SearchPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPhotoByIdUseCase(appRepository: AppRepository): GetPhotoByIdUseCase =
        GetPhotoByIdUseCase(appRepository)

    @Provides
    fun provideSavePhotoUseCase(appRepository: AppRepository): SavePhotoUseCase =
        SavePhotoUseCase(appRepository)

    @Provides
    fun provideDeletePhotoUseCase(appRepository: AppRepository): DeletePhotoUseCase =
        DeletePhotoUseCase(appRepository)

    @Provides
    fun provideGetAllPhotosUseCase(appRepository: AppRepository): GetAllPhotosUseCase {
        return GetAllPhotosUseCase(appRepository)
    }

    @Provides
    fun provideGetCuratedPhotosUseCase(appRepository: AppRepository): GetCuratedPhotosUseCase =
        GetCuratedPhotosUseCase(appRepository)

    @Provides
    fun provideGetFeaturedCollectionUseCase(appRepository: AppRepository): GetFeaturedCollectionUseCase =
        GetFeaturedCollectionUseCase(appRepository)

    @Provides
    fun provideSearchPhotosUseCase(appRepository: AppRepository): SearchPhotosUseCase =
        SearchPhotosUseCase(appRepository)

    @Provides
    fun provideGetPhotosBySearchUseCase(appRepository: AppRepository): GetPhotosBySearchUseCase =
        GetPhotosBySearchUseCase(appRepository)

    @Provides
    fun provideDownloadImageUseCase(appRepository: AppRepository): DownloadImageUseCase =
        DownloadImageUseCase(appRepository)
}
