package com.example.pexelsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.data.retrofir.PhotoApi
import com.example.pexelsapp.domain.models.Featured
import com.example.pexelsapp.domain.models.FeaturedResponse
import com.example.pexelsapp.domain.models.Photo
import com.example.pexelsapp.domain.models.PhotoResponse
import com.example.pexelsapp.domain.usecases.GetAllPhotosUseCase
import com.example.pexelsapp.domain.usecases.GetCuratedPhotosUseCase
import com.example.pexelsapp.domain.usecases.GetFeaturedCollectionUseCase
import com.example.pexelsapp.domain.usecases.GetPhotosBySearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCuratedPhotosUseCase: GetCuratedPhotosUseCase,
    private val getFeaturedCollectionUseCase: GetFeaturedCollectionUseCase,
    private val getPhotosBySearchUseCase: GetPhotosBySearchUseCase,
    private val getAllPhotosUseCase: GetAllPhotosUseCase
) : ViewModel() {

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData
    val curatedPhotosLiveData = MutableLiveData<List<NetworkPhoto>>()
    val featuredLiveData = MutableLiveData<List<Featured>>()
    val bookmarkLiveData = getAllPhotosUseCase.execute()
    val searchPhotosLiveData = MutableLiveData<List<NetworkPhoto>>()

    fun getCuratedPhotos() {
        viewModelScope.launch {
            try {
                val response: Response<PhotoResponse> = coroutineScope {
                    getCuratedPhotosUseCase.execute(1, 30)
                }
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    curatedPhotosLiveData.value = responseBody.photos
                } else {
                    Log.d("HomeFragment", "Error")
                }
            } catch (e: IOException) {
                Log.d("HomeFragment", e.message.toString())
            }
        }
    }

    fun getFeatured() {
        viewModelScope.launch {
            try {
                val response: Response<FeaturedResponse> = coroutineScope {
                    getFeaturedCollectionUseCase.execute(1, 7)
                }
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    featuredLiveData.value = responseBody.collections
                } else {
                    Log.d("HomeFragment", "Error")
                }
            } catch (e: IOException) {
                Log.d("HomeFragment", e.message.toString())
            }
        }
    }

    fun searchPhotos(searchQuery: String) {
        viewModelScope.launch {
            try {
                val response: Response<PhotoResponse> = coroutineScope {
                    getPhotosBySearchUseCase.execute(searchQuery, 1, 30)
                }
                val photoList = response.body()?.photos
                photoList?.let {
                    searchPhotosLiveData.postValue(it)
                }
            } catch (e: IOException) {
                Log.d("HomeFragment", e.message.toString())
            }
        }
    }
}