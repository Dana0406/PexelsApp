package com.example.pexelsapp.domain.models

data class Featured(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    val mediaCount: Int,
    val photosCount: Int,
    val videosCount: Int,
    var selected: Boolean? = false
)
