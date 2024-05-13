package com.example.pexelsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pexelsapp.data.models.DBPhoto

@Database(entities = [DBPhoto::class], version = 1, exportSchema = false)
@TypeConverters(PhotoTypeConverter::class)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
}