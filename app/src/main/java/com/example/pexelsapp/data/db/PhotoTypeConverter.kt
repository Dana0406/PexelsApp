package com.example.pexelsapp.data.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.pexelsapp.domain.models.PhotoSource
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@TypeConverters
@Singleton
class PhotoTypeConverter @Inject constructor(){
    private val gson = Gson()

    @TypeConverter
    fun fromPhotoSource(photoSource: PhotoSource): String {
        return gson.toJson(photoSource)
    }

    @TypeConverter
    fun toPhotoSource(json: String): PhotoSource {
        return gson.fromJson(json, PhotoSource::class.java)
    }
}