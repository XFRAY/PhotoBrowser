package com.example.photobrowser.data.data_source

import androidx.lifecycle.LiveData
import com.example.photobrowser.data.LoadingState

interface PhotosDataSource {

    fun getLoadingStateData(): LiveData<LoadingState>

    fun getInitialLoadingStateData(): LiveData<LoadingState>

    fun retryLoad()

    fun clear()
}