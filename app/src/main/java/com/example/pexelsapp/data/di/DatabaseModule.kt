package com.example.pexelsapp.data.di

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
    fun provideDatabase(@ApplicationContext context: Context): PhotoDatabase =
        Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()

    @Provides
    fun provideDao(database: PhotoDatabase): PhotoDao = database.photoDao()

    /*@Provides
    @Singleton
    fun provideGson(): Gson = Gson()*/

    @Provides
    @ActivityRetainedScoped
    fun provideCachePhotoMap()= mutableMapOf<Int, NetworkPhoto>()
    @Provides
    @ActivityRetainedScoped
    fun provideCacheFeaturedMap()= mutableMapOf<String, Featured>()

    @Provides
    @ActivityRetainedScoped
    fun providePhotoTypeConverter(): PhotoTypeConverter =
        PhotoTypeConverter()

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