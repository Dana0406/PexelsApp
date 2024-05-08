package com.example.pexelsapp.domain.models

import com.example.pexelsapp.data.models.NetworkPhoto

data class PhotoResponse(
    val page: Int,
    val perPage: Int,
    val photos: List<NetworkPhoto>,
    val nextPage: String?
)
