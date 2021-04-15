package com.example.photobrowser.data.data_source

import androidx.lifecycle.LiveData
import androidx.paging.PageKeyedDataSource
import com.example.photobrowser.data.LoadingState
import com.example.photobrowser.data.mapper.PhotosDTOMapper
import com.example.photobrowser.data.model.ui.PhotoUI
import com.example.photobrowser.data.network.ApiService
import com.example.photobrowser.extensions.live_data.SingleLiveEvent
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.functions.Action

class PhotosDataSourceImpl(
    private val apiService: ApiService,
) : PageKeyedDataSource<Int, PhotoUI>(), PhotosDataSource {

    companion object {
        private const val INITIAL_PAGE = 1
    }

    private val loadingStateData = SingleLiveEvent<LoadingState>()
    private val initialLoadingStateData = SingleLiveEvent<LoadingState>()
    private var retryLoadInitialCompletable: Completable? = null
    private var retryLoadAfterCompletable: Completable? = null

    private fun setRetryLoadInitialAction(action: Action) {
        retryLoadInitialCompletable = Completable.fromAction(action)
    }

    private fun setRetryLoadAfterAction(action: Action) {
        retryLoadAfterCompletable = Completable.fromAction(action)
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoUI>
    ) {
        initialLoadingStateData.postValue(LoadingState.LOADING)
        apiService.getRecentPhotos(INITIAL_PAGE)
            .map { return@map PhotosDTOMapper.transform(it) }
            .subscribe({
                initialLoadingStateData.postValue(LoadingState.DONE)
                val photoList = it.photoList
                val nextPageKey = it.nextPage
                callback.onResult(photoList, null, nextPageKey)
            }, {
                setRetryLoadInitialAction { loadInitial(params, callback) }
                initialLoadingStateData.postValue(LoadingState.ERROR)
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoUI>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoUI>) {
        loadingStateData.postValue(LoadingState.LOADING)
        val pageKey = params.key
        apiService.getRecentPhotos(pageKey)
            .map { return@map PhotosDTOMapper.transform(it) }
            .subscribe({
                loadingStateData.postValue(LoadingState.DONE)
                val photoList = it.photoList
                val adjacentPageKey = it.nextPage
                callback.onResult(photoList, adjacentPageKey)
            }, {
                setRetryLoadAfterAction { loadAfter(params, callback) }
                loadingStateData.postValue(LoadingState.ERROR)
            })
    }

    override fun retryLoadInitial() {
        retryLoadInitialCompletable?.subscribe()
    }

    override fun retryLoadAfter() {
        retryLoadAfterCompletable?.subscribe()
    }

    override fun getLoadingStateData(): LiveData<LoadingState> = loadingStateData

    override fun getInitialLoadingStateData(): LiveData<LoadingState> = initialLoadingStateData
}