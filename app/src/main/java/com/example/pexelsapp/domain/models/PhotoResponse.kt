package com.example.pexelsapp.domain.models

data class PhotoResponse(
    val page: Int,
    val perPage: Int,
    val photos: List<Photo>,
    val nextPage: String?
)
