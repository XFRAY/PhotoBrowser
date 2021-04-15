package com.example.photobrowser.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.photobrowser.data.LoadingState
import com.example.photobrowser.data.model.ui.PhotoUI
import io.reactivex.Flowable

interface PhotosRepository {

    fun getRecentPhotos(config: PagedList.Config): Flowable<PagedList<PhotoUI>>

    fun getLoadingState(): LiveData<LoadingState>

    fun getInitialLoadingState(): LiveData<LoadingState>

    fun retryLoadInitial()

    fun retryLoadAfter()

}