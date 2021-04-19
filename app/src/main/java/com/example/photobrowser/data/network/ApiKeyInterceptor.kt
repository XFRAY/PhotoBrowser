package com.example.photobrowser.data.network

import com.example.photobrowser.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.API_KEY)
            .build()

        val request = original.newBuilder()
            .url(url).build()
        return chain.proceed(request)
    }

    companion object{
        private const val API_KEY = "api_key"
    }
}