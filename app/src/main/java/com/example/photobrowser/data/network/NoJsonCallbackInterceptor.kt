package com.example.photobrowser.data.network

import com.example.photobrowser.BuildConfig
import com.example.photobrowser.data.network.NoJsonCallbackInterceptor.Companion.NO_JSON_CALLBACK
import okhttp3.Interceptor
import okhttp3.Response

class NoJsonCallbackInterceptor : Interceptor {

    companion object{
        private const val NO_JSON_CALLBACK = "nojsoncallback"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(NO_JSON_CALLBACK, BuildConfig.NO_JSON_CALLBACK)
            .build()

        val request = original.newBuilder()
            .url(url).build()
        return chain.proceed(request)
    }
}