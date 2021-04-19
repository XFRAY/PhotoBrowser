package com.example.photobrowser.data.data_source

import androidx.lifecycle.LiveData
import androidx.paging.PageKeyedDataSource
import com.example.photobrowser.data.LoadingState
import com.example.photobrowser.data.model.ui.PhotoUI
import com.example.photobrowser.data.repository.PhotosRepository
import com.example.photobrowser.extensions.live_data.SingleLiveEvent
import com.example.photobrowser.extensions.rx_java.addTo
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PhotosDataSourceImpl(
    private val photosRepository: PhotosRepository,
) : PageKeyedDataSource<Int, PhotoUI>(), PhotosDataSource {

    private val loadingStateData = SingleLiveEvent<LoadingState>()
    private val initialLoadingStateData = SingleLiveEvent<LoadingState>()
    private var retryLoadAction: (() -> Unit?)? = null
    private val compositeDisposable = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoUI>
    ) {
        initialLoadingStateData.postValue(LoadingState.LOADING)
        photosRepository.getRecentPhotos(INITIAL_PAGE)
            .subscribe({
                initialLoadingStateData.postValue(LoadingState.SUCCESS)
                val photoList = it.photoList
                val nextPageKey = it.nextPage
                callback.onResult(photoList, null, nextPageKey)
            }, {
                retryLoadAction = { loadInitial(params, callback) }
                initialLoadingStateData.postValue(LoadingState.ERROR)
            }).addTo(compositeDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoUI>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoUI>) {
        loadingStateData.postValue(LoadingState.LOADING)
        val pageKey = params.key
        photosRepository.getRecentPhotos(pageKey)
            .subscribe({
                loadingStateData.postValue(LoadingState.SUCCESS)
                val photoList = it.photoList
                val adjacentPageKey = it.nextPage
                callback.onResult(photoList, adjacentPageKey)
            }, {
                retryLoadAction = { loadAfter(params, callback) }
                loadingStateData.postValue(LoadingState.ERROR)
            }).addTo(compositeDisposable)
    }

    override fun retryLoad() {
        retryLoadAction?.invoke()
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun getLoadingStateData(): LiveData<LoadingState> = loadingStateData

    override fun getInitialLoadingStateData(): LiveData<LoadingState> = initialLoadingStateData

    companion object {
        private const val INITIAL_PAGE = 1
    }
}