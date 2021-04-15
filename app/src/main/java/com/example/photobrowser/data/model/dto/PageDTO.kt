package com.example.photobrowser.data.model.dto

import com.google.gson.annotations.SerializedName

data class PageDTO(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("perpage")
    val perPage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("photo")
    val photoList: List<PhotoDTO>
)