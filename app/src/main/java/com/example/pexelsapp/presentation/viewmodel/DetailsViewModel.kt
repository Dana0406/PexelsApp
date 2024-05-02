package com.example.pexelsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.data.db.PhotoDatabase
import com.example.pexelsapp.data.repositories.LocalPhotoRepository
import com.example.pexelsapp.data.repositories.RemotePhotoRepository
import com.example.pexelsapp.data.retrofir.PhotoApi
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.domain.usecases.DeletePhotoUseCase
import com.example.pexelsapp.domain.usecases.GetPhotoByIdUseCase
import com.example.pexelsapp.domain.usecases.SavePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getPhotoByIdUseCase: GetPhotoByIdUseCase,
    private val savePhotoUseCase: SavePhotoUseCase,
    private val deletePhotoUseCase: DeletePhotoUseCase
): ViewModel() {
    private var photoDetailLiveData = MutableLiveData<Photo>()

    @Inject
    lateinit var photoApi: PhotoApi

    fun getPhotoDetail(id: Int){
        viewModelScope.launch {
            val call: Call<Photo> = getPhotoByIdUseCase.execute(id)
            call.enqueue(object : Callback<Photo> {
                override fun onResponse(call: Call<Photo>, response: Response<Photo>) {
                    if (response.isSuccessful) {
                        photoDetailLiveData.value = response.body()
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<Photo>, t: Throwable) {
                    Log.d("DetailFragment", t.message.toString())
                }
            })
        }
    }

    fun insertPhoto(photo: Photo){
        viewModelScope.launch {
            try {
                savePhotoUseCase.execute(photo)
            } catch (e: Exception) {
                Log.d("DetailFragment", e.message.toString())
            }
        }
    }

    fun deletePhoto(photo: Photo){
        viewModelScope.launch {
            try {
                deletePhotoUseCase.execute(photo)
            } catch (e: Exception) {
                Log.d("DetailFragment", e.message.toString())
            }
        }
    }

    fun observePhotoDetailLiveData(): LiveData<Photo>{
        return photoDetailLiveData
    }

}