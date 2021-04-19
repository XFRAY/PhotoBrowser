package com.example.photobrowser.data.repository

import com.example.photobrowser.data.mapper.PhotosDTOMapper
import com.example.photobrowser.data.model.ui.PageUI
import com.example.photobrowser.data.network.ApiService
import io.reactivex.rxjava3.core.Single

class PhotosRepositoryImpl(
    private val apiService: ApiService
) : PhotosRepository {

    override fun getRecentPhotos(page: Int): Single<PageUI> =
      apiService.getRecentPhotos(page)
          .map { return@map PhotosDTOMapper.transform(it) }
}