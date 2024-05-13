package com.example.pexelsapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.data.utils.Constants

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPhoto(photo: DBPhoto)

    @Delete
    suspend fun deletePhoto(photo: DBPhoto)

    @Query(Constants.SELECT_ALL)
    suspend fun getAllPhotos(): List<DBPhoto>
}