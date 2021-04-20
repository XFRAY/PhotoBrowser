package com.example.photobrowser.data.datasource

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.photobrowser.data.model.ui.PhotoUI
import com.example.photobrowser.data.repository.PhotosRepository
import com.example.photobrowser.extensions.livedata.SingleLiveEvent

class PhotosDataSourceFactory(
    private val photosRepository: PhotosRepository
) : DataSource.Factory<Int, PhotoUI>() {

    private val photosDataSourceData = SingleLiveEvent<PhotosDataSource>()

    override fun create(): DataSource<Int, PhotoUI> {
        with(PhotosDataSourceImpl(photosRepository)) {
            photosDataSourceData.postValue(this)
            return this
        }
    }

    fun getPhotosDataSourceData(): LiveData<PhotosDataSource> = photosDataSourceData
}