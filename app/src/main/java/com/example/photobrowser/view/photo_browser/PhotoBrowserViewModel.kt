package com.example.photobrowser.view.photo_browser

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.paging.PagedList
import com.example.photobrowser.data.LoadingState
import com.example.photobrowser.data.model.ui.PhotoUI
import com.example.photobrowser.data.repository.PhotosRepository
import com.example.photobrowser.view.base.BaseViewModel

class PhotoBrowserViewModel(
    private val photosRepository: PhotosRepository
) : BaseViewModel() {


    fun getPhotoViewListData(): LiveData<PagedList<PhotoUI>> {
        val config = PagedList.Config.Builder()
            .setPageSize(5)
            .setEnablePlaceholders(false)
            .build()
        return LiveDataReactiveStreams.fromPublisher(photosRepository.getRecentPhotos(config))
    }

    fun getLoadingStateData(): LiveData<LoadingState> = photosRepository.getLoadingState()

    fun getInitialLoadingStateData(): LiveData<LoadingState> =
        photosRepository.getInitialLoadingState()

    fun onClickRetryInitialLoad() {
        photosRepository.retryLoadInitial()
    }

    fun onClickRetryAfterLoad() {
        photosRepository.retryLoadAfter()
    }

}