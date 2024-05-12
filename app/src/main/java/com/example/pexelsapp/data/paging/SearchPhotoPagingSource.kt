package com.example.pexelsapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.domain.repository.AppRepository
import com.example.pexelsapp.domain.usecases.GetPhotosBySearchUseCase

class SearchPhotoPagingSource(
    private val getPhotosBySearchUseCase: GetPhotosBySearchUseCase,
    private val query: String
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
            val response = getPhotosBySearchUseCase.execute(query, currentPage, params.loadSize)
            val data = response.body()?.photos.orEmpty()

            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (data.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}