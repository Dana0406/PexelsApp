package com.example.pexelsapp.domain.models

data class FeaturedResponse(
    val collections: List<Featured>,
    val page: Int,
    val perPage: Int,
    val totalResults: Int,
    val nextPage: String?,
    val prevPage: String?
)
