package com.example.pexelsapp.domain.di

import com.example.pexelsapp.data.repositories.ImageRepository
import com.example.pexelsapp.data.repositories.LocalPhotoRepository
import com.example.pexelsapp.data.repositories.RemotePhotoRepository
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
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPhotoByIdUseCase(remotePhotoRepository: RemotePhotoRepository): GetPhotoByIdUseCase {
        return GetPhotoByIdUseCase(remotePhotoRepository)
    }

    @Provides
    fun provideSavePhotoUseCase(localPhotoRepository: LocalPhotoRepository): SavePhotoUseCase {
        return SavePhotoUseCase(localPhotoRepository)
    }

    @Provides
    fun provideDeletePhotoUseCase(localPhotoRepository: LocalPhotoRepository): DeletePhotoUseCase {
        return DeletePhotoUseCase(localPhotoRepository)
    }

    @Provides
    fun provideGetAllPhotosUseCase(localPhotoRepository: LocalPhotoRepository): GetAllPhotosUseCase {
        return GetAllPhotosUseCase(localPhotoRepository)
    }

    @Provides
    fun provideGetCuratedPhotosUseCase(remotePhotoRepository: RemotePhotoRepository): GetCuratedPhotosUseCase {
        return GetCuratedPhotosUseCase(remotePhotoRepository)
    }

    @Provides
    fun provideGetFeaturedCollectionUseCase(remotePhotoRepository: RemotePhotoRepository): GetFeaturedCollectionUseCase {
        return GetFeaturedCollectionUseCase(remotePhotoRepository)
    }

    @Provides
    fun provideSearchPhotosUseCase(remotePhotoRepository: RemotePhotoRepository): SearchPhotosUseCase {
        return SearchPhotosUseCase(remotePhotoRepository)
    }

    @Provides
    fun provideGetPhotosBySearchUseCase(remotePhotoRepository: RemotePhotoRepository): GetPhotosBySearchUseCase {
        return GetPhotosBySearchUseCase(remotePhotoRepository)
    }

    @Provides
    fun provideDownloadImageUseCase(imageRepository: ImageRepository): DownloadImageUseCase {
        return DownloadImageUseCase(imageRepository)
    }
}
