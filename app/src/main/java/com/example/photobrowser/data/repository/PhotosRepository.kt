package com.example.photobrowser.data.repository

import com.example.photobrowser.data.model.ui.PageUI
import io.reactivex.rxjava3.core.Single

interface PhotosRepository {

    fun getRecentPhotos(page: Int): Single<PageUI>
}