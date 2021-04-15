package com.example.photobrowser.data.data_source

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.photobrowser.data.model.ui.PhotoUI
import com.example.photobrowser.data.network.ApiService
import com.example.photobrowser.extensions.live_data.SingleLiveEvent

class PhotosDataSourceFactory(
    private val apiService: ApiService
) : DataSource.Factory<Int, PhotoUI>() {

    private val photosDataSourceData = SingleLiveEvent<PhotosDataSource>()

    override fun create(): DataSource<Int, PhotoUI> {
        with(PhotosDataSourceImpl(apiService)) {
            photosDataSourceData.postValue(this)
            return this
        }
    }

    fun getPhotosDataSourceData(): LiveData<PhotosDataSource> = photosDataSourceData
}