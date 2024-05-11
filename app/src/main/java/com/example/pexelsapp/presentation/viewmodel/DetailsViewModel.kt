package com.example.pexelsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.domain.usecases.DeletePhotoUseCase
import com.example.pexelsapp.domain.usecases.GetPhotoByIdUseCase
import com.example.pexelsapp.domain.usecases.GetPhotoFromCacheUseCase
import com.example.pexelsapp.domain.usecases.SavePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase,
    private val savePhotoUseCase: SavePhotoUseCase,
    private val deletePhotoUseCase: DeletePhotoUseCase,
    private val getPhotoFromCacheUseCase: GetPhotoFromCacheUseCase
) : ViewModel() {

    var photoDetailLiveData = MutableLiveData<NetworkPhoto?>()

    fun getPhotoDetail(id: Int) {

        val cachedPhoto = getPhotoFromCacheUseCase.execute(id)
        if(cachedPhoto != null){
            photoDetailLiveData.postValue(cachedPhoto)
            return
        }

        viewModelScope.launch {
            try {
                val response: Response<NetworkPhoto> = coroutineScope {
                    getPhotoByIdUseCase.execute(id)
                }
                if (response.isSuccessful) {
                    photoDetailLiveData.value = response.body()
                } else {
                    Log.d("DetailFragment", "Error")
                }
            } catch (e: IOException) {
                Log.d("DetailFragment", e.message.toString())
            }
        }
    }

    fun insertPhoto(photo: DBPhoto) {
        viewModelScope.launch {
            try {
                savePhotoUseCase.execute(photo)
            } catch (e: Exception) {
                Log.d("DetailFragment", e.message.toString())
            }
        }
    }

    fun deletePhoto(photo: DBPhoto) {
        viewModelScope.launch {
            try {
                deletePhotoUseCase.execute(photo)
            } catch (e: Exception) {
                Log.d("DetailFragment", e.message.toString())
            }
        }
    }
}