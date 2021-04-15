package com.example.photobrowser.data.model.ui

data class PageUI(
    val page: Int,
    val nextPage: Int? = null,
    val pageCount: Int,
    val perPage: Int,
    val photoList: List<PhotoUI>
)