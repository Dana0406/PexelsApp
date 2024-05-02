package com.example.pexelsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pexelsapp.domain.models.Photo
import javax.inject.Inject

@Database(entities = [Photo::class], version = 1, exportSchema = false)
@TypeConverters(PhotoTypeConverter::class)
abstract class PhotoDatabase: RoomDatabase(){
    abstract fun photoDao(): PhotoDao
}