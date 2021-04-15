package com.example.photobrowser.data.model.dto

import com.google.gson.annotations.SerializedName

data class PhotosDTO(
    @SerializedName("photos")
    val page: PageDTO
)