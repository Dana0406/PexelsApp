package com.example.pexelsapp.presentation.converters

import com.example.pexelsapp.data.models.DBPhoto
import com.example.pexelsapp.data.models.NetworkPhoto
import com.example.pexelsapp.domain.models.Photo

object ModelConverter {

    fun networkPhotoToDomainModel(networkPhoto: NetworkPhoto): Photo {
        return Photo(
            id = networkPhoto.id,
            width = networkPhoto.width,
            height = networkPhoto.height,
            url = networkPhoto.url,
            photographer = networkPhoto.photographer,
            photographerUrl = networkPhoto.photographerUrl,
            photographerId = networkPhoto.photographerId,
            avgColor = networkPhoto.avgColor,
            src = networkPhoto.src,
            liked = networkPhoto.liked,
            alt = networkPhoto.alt
        )
    }

    fun dbPhotoToDomainModel(dbPhoto: DBPhoto): Photo {
        return Photo(
            id = dbPhoto.id,
            width = dbPhoto.width,
            height = dbPhoto.height,
            url = dbPhoto.url,
            photographer = dbPhoto.photographer,
            photographerUrl = dbPhoto.photographerUrl,
            photographerId = dbPhoto.photographerId,
            avgColor = dbPhoto.avgColor,
            src = dbPhoto.src,
            liked = dbPhoto.liked,
            alt = dbPhoto.alt
        )
    }

    fun photoToDBPhoto(photo: Photo): DBPhoto {
        return DBPhoto(
            id = photo.id,
            width = photo.width,
            height = photo.height,
            url = photo.url,
            photographer = photo.photographer,
            photographerUrl = photo.photographerUrl,
            photographerId = photo.photographerId,
            avgColor = photo.avgColor,
            src = photo.src,
            liked = photo.liked,
            alt = photo.alt
        )
    }

    fun dbPhotoToPhoto(dbPhoto: DBPhoto): Photo {
        return Photo(
            id = dbPhoto.id,
            width = dbPhoto.width,
            height = dbPhoto.height,
            url = dbPhoto.url,
            photographer = dbPhoto.photographer,
            photographerUrl = dbPhoto.photographerUrl,
            photographerId = dbPhoto.photographerId,
            avgColor = dbPhoto.avgColor,
            src = dbPhoto.src,
            liked = dbPhoto.liked,
            alt = dbPhoto.alt
        )
    }
}