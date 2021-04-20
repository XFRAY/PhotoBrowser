package com.example.photobrowser.data.datasource

import androidx.lifecycle.LiveData
import com.example.photobrowser.data.LoadingState

interface PhotosDataSource {

    fun getLoadingStateData(): LiveData<LoadingState>

    fun getInitialLoadingStateData(): LiveData<LoadingState>

    fun retryLoad()

    fun clear()
}