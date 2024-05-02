package com.example.pexelsapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelsapp.data.db.PhotoDatabase
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    private var curatedPhotosLiveData = MutableLiveData<List<Photo>>()
    private var featuredLiveData = MutableLiveData<List<Featured>>()
    private var bookmarkLiveData = getAllPhotosUseCase.execute()
    private var searchPhotosLiveData = MutableLiveData<List<Photo>>()

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    @Inject
    lateinit var photoApi: PhotoApi

    fun getCuratedPhotos() {
        viewModelScope.launch {
            val call: Call<PhotoResponse> = getCuratedPhotosUseCase.execute(1,30)
            call.enqueue(object : Callback<PhotoResponse> {
                override fun onResponse(
                    call: Call<PhotoResponse>,
                    response: Response<PhotoResponse>
                ) {
                    if (response.body() != null) {
                        curatedPhotosLiveData.value = response.body()!!.photos
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
                    if (response.body() != null) {
                        featuredLiveData.value = response.body()!!.collections
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

    fun observeSearchPhotosLiveData(): LiveData<List<Photo>>{
        return searchPhotosLiveData
    }

    fun observeCuratedPhotosLiveData(): LiveData<List<Photo>> {
        return curatedPhotosLiveData
    }

    fun observeFeaturedLiveData(): LiveData<List<Featured>> {
        return featuredLiveData
    }

    fun observeBookmarkLiveData(): LiveData<List<Photo>> {
        return bookmarkLiveData
    }

}