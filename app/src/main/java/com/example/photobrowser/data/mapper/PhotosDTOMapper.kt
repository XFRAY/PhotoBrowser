package com.example.photobrowser.data.mapper

import com.example.photobrowser.BuildConfig
import com.example.photobrowser.data.model.dto.PageDTO
import com.example.photobrowser.data.model.dto.PhotoDTO
import com.example.photobrowser.data.model.dto.PhotosDTO
import com.example.photobrowser.data.model.ui.PhotoUI
import com.example.photobrowser.data.model.ui.PageUI

object PhotosDTOMapper {

    fun transform(photosDTO: PhotosDTO): PageUI {
        val pageDTO = photosDTO.page
        val photoList = pageDTO.photoList
        val page = pageDTO.page
        val pageCount = pageDTO.pages
        val perPage = pageDTO.perPage
        val nextPage = if (pageCount > page) page + 1 else null
        val photoListView = ArrayList<PhotoUI>()
        photoList.forEach {
            val serverId = it.server
            val imageId = it.id
            val secret = it.secret
            val largeUrl = getPhotoUrl(serverId, imageId, secret)
            val photoView = PhotoUI(imageId, largeUrl)
            photoListView.add(photoView)
        }
        return PageUI(page, nextPage, pageCount, perPage, photoListView)
    }

    private fun getPhotoUrl(
        serverId: Int,
        imageId: String,
        secret: String
    ): String {
        return "${BuildConfig.PHOTO_URL}$serverId/${imageId}_${secret}_b.jpg"
    }
}