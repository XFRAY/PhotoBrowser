package com.example.photobrowser.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.photobrowser.data.LoadingState
import com.example.photobrowser.data.data_source.PhotosDataSource
import com.example.photobrowser.data.data_source.PhotosDataSourceFactory
import com.example.photobrowser.data.model.ui.PhotoUI
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class PhotosRepositoryImpl(
    private val photosDataSourceFactory: PhotosDataSourceFactory
) : PhotosRepository {

    override fun getRecentPhotos(config: PagedList.Config): Flowable<PagedList<PhotoUI>> =
        RxPagedListBuilder(photosDataSourceFactory, config).buildFlowable(
            BackpressureStrategy.BUFFER
        )

    override fun getLoadingState(): LiveData<LoadingState> =
        Transformations.switchMap(
            photosDataSourceFactory.getPhotosDataSourceData(),
            PhotosDataSource::getLoadingStateData
        )

    override fun getInitialLoadingState(): LiveData<LoadingState> =
        Transformations.switchMap(
            photosDataSourceFactory.getPhotosDataSourceData(),
            PhotosDataSource::getInitialLoadingStateData
        )

    override fun retryLoadInitial() {
        photosDataSourceFactory.getPhotosDataSourceData().value?.retryLoadInitial()
    }

    override fun retryLoadAfter() {
        photosDataSourceFactory.getPhotosDataSourceData().value?.retryLoadAfter()
    }
}