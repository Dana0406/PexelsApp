package com.example.pexelsapp.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.pexelsapp.data.ImageDownloader
import com.example.pexelsapp.data.db.PhotoDao
import com.example.pexelsapp.data.db.PhotoDatabase
import com.example.pexelsapp.data.db.PhotoTypeConverter
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.data.repositories.AppRepositoryImpl
import com.example.pexelsapp.data.retrofir.PhotoApi
import com.example.pexelsapp.data.utils.Constants
import com.example.pexelsapp.domain.models.Featured
import com.example.pexelsapp.domain.repository.AppRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object DatabaseModule {

    @Provides
    @ActivityRetainedScoped
    fun provideDatabase(context: Context): PhotoDatabase =
        Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            Constants.DATABASE_NAME
        ).addTypeConverter(PhotoTypeConverter(Gson())).build()

    @Provides
    @ActivityRetainedScoped
    fun provideContext(application: Application): Context = application

    @Provides
    fun provideDao(database: PhotoDatabase): PhotoDao = database.photoDao()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @ActivityRetainedScoped
    fun provideCachePhotoMap()= mutableMapOf<Int, NetworkPhoto>()
    @Provides
    @ActivityRetainedScoped
    fun provideCacheFeaturedMap()= mutableMapOf<String, Featured>()

    @Provides
    @Singleton
    fun providePhotoTypeConverter(gson: Gson): PhotoTypeConverter = PhotoTypeConverter(gson)

    @Provides
    @ActivityRetainedScoped
    fun provideAppRepository(
        imageDownloader: ImageDownloader,
        photoDao: PhotoDao,
        photoApi: PhotoApi,
        photoMap: MutableMap<Int, NetworkPhoto>,
        featuredMap: MutableMap<String, Featured>
    ): AppRepository {
        return AppRepositoryImpl(imageDownloader, photoDao, photoApi, photoMap, featuredMap)
    }
}