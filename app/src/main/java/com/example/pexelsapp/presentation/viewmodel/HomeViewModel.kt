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
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    var curatedPhotosLiveData = MutableLiveData<List<NetworkPhoto>>()
    var featuredLiveData = MutableLiveData<List<Featured>>()
    var bookmarkLiveData = getAllPhotosUseCase.execute()
    var searchPhotosLiveData = MutableLiveData<List<NetworkPhoto>>()

    fun getCuratedPhotos() {
        viewModelScope.launch {
            val call: Call<PhotoResponse> = getCuratedPhotosUseCase.execute(1, 30)
            call.enqueue(object : Callback<PhotoResponse> {
                override fun onResponse(
                    call: Call<PhotoResponse>,
                    response: Response<PhotoResponse>
                ) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        curatedPhotosLiveData.value = responseBody.photos
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                    _errorLiveData.postValue("Request failed: ${t.message}")
                    Log.d("HomeFragment", t.message.toString())
                }
            })
        }
    }

    fun getFeatured() {
        viewModelScope.launch {
            val call: Call<FeaturedResponse> = getFeaturedCollectionUseCase.execute(1, 7)
            call.enqueue(object : Callback<FeaturedResponse> {
                override fun onResponse(
                    call: Call<FeaturedResponse>,
                    response: Response<FeaturedResponse>
                ) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        featuredLiveData.value = responseBody.collections
                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<FeaturedResponse>, t: Throwable) {
                    Log.d("HomeFragment", t.message.toString())
                }
            })
        }
    }

    fun searchPhotos(searchQuery: String) {
        viewModelScope.launch {
            val call: Call<PhotoResponse> = getPhotosBySearchUseCase.execute(searchQuery, 1, 30)
            call.enqueue(
                object : Callback<PhotoResponse> {
                    override fun onResponse(
                        call: Call<PhotoResponse>,
                        response: Response<PhotoResponse>
                    ) {
                        val photoList = response.body()?.photos
                        photoList?.let {
                            searchPhotosLiveData.postValue(it)
                        }
                    }

                    override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                        Log.d("HomeFragment", t.message.toString())
                    }
                }
            )
        }
    }
}