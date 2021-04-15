package com.example.photobrowser.data.network

import com.example.photobrowser.data.model.dto.PhotosDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("services/rest/")
    fun getRecentPhotos(
        @Query("page")
        page: Int,
        @Query("method")
        method: String = "flickr.photos.getRecent"
    ): Single<PhotosDTO>
}