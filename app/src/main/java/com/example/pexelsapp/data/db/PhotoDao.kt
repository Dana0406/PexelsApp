package com.example.pexelsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.data.utils.Constants
import com.example.pexelsapp.domain.models.Photo

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPhoto(photo: DBPhoto)

    @Delete
    suspend fun deletePhoto(photo: DBPhoto)

    @Query(Constants.SELECT_ALL)
    fun getAllPhotos(): LiveData<List<DBPhoto>>
}