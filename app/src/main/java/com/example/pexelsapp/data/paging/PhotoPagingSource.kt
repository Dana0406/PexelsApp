package com.example.pexelsapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.domain.repository.AppRepository
import com.example.pexelsapp.domain.usecases.AddPhotosToCacheUseCase
import com.example.pexelsapp.domain.usecases.GetAllCachePhotosUseCase
import com.example.pexelsapp.domain.usecases.GetCuratedPhotosUseCase

class PhotoPagingSource(
    private val getCuratedPhotosUseCase: GetCuratedPhotosUseCase,
    private val getAllCachePhotosUseCase: GetAllCachePhotosUseCase,
    private val addPhotosToCacheUseCase: AddPhotosToCacheUseCase
) : PagingSource<Int, NetworkPhoto>() {

    override fun getRefreshKey(state: PagingState<Int, NetworkPhoto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetworkPhoto> {
        return try {
            val currentPage = params.key ?: 1
            val cachedPhotos = getAllCachePhotosUseCase.execute()

            if (cachedPhotos.isNotEmpty()) {
                return LoadResult.Page(
                    data = cachedPhotos,
                    prevKey = null,
                    nextKey = null
                )
            } else {
                val response = getCuratedPhotosUseCase.execute(currentPage, params.loadSize)
                val data = response.body()?.photos.orEmpty()

                addPhotosToCacheUseCase.execute(data)

                LoadResult.Page(
                    data = data,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (data.isEmpty()) null else currentPage + 1
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}