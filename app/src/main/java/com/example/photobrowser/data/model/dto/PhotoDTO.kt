package com.example.photobrowser.data.model.dto

import com.google.gson.annotations.SerializedName

data class PhotoDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("server")
    val server: Int,
    @SerializedName("farm")
    val farm: Int,
    @SerializedName("title")
    val title: String
)