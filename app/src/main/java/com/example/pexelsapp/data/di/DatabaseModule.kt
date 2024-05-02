package com.example.pexelsapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.pexelsapp.data.db.PhotoDao
import com.example.pexelsapp.data.db.PhotoDatabase
import com.example.pexelsapp.data.db.PhotoTypeConverter
import com.example.pexelsapp.data.repositories.LocalPhotoRepository
import com.example.pexelsapp.data.repositories.LocalPhotoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PhotoDatabase {
        return Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            "photo"
        ).build()
    }

    @Provides
    fun provideDao(database: PhotoDatabase): PhotoDao {
        return database.photoDao()
    }

    @Provides
    @Singleton
    fun providePhotoTypeConverter(): PhotoTypeConverter {
        return PhotoTypeConverter()
    }

    @Provides
    @Singleton
    fun provideLocalPhotoRepository(photoDao: PhotoDao): LocalPhotoRepository {
        return LocalPhotoRepositoryImpl(photoDao)
    }
}