package com.example.photobrowser.view.photo_browser

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.photobrowser.data.LoadingState
import com.example.photobrowser.data.data_source.PhotosDataSource
import com.example.photobrowser.data.data_source.PhotosDataSourceFactory
import com.example.photobrowser.data.model.ui.PhotoUI

class PhotoBrowserViewModel(
    private val photosDataSourceFactory: PhotosDataSourceFactory
) : ViewModel() {

    fun getPhotoViewListData(): LiveData<PagedList<PhotoUI>> {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        return LivePagedListBuilder(photosDataSourceFactory, config).build()
    }

    fun getLoadingStateData(): LiveData<LoadingState> =
        Transformations.switchMap(
            photosDataSourceFactory.getPhotosDataSourceData(),
            PhotosDataSource::getLoadingStateData
        )

    fun getInitialLoadingStateData(): LiveData<LoadingState> =
        Transformations.switchMap(
            photosDataSourceFactory.getPhotosDataSourceData(),
            PhotosDataSource::getInitialLoadingStateData
        )

    fun onRetryLoad() {
        photosDataSourceFactory.getPhotosDataSourceData().value?.retryLoad()
    }

    override fun onCleared() {
        photosDataSourceFactory.getPhotosDataSourceData().value?.clear()
    }

    companion object {
        private const val PAGE_SIZE = 5
    }
}