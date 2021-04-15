package com.example.photobrowser.data.network

import com.example.photobrowser.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class JsonFormatInterceptor : Interceptor {

    companion object{
        private const val FORMAT = "format"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(FORMAT, BuildConfig.RESPONSE_FORMAT)
            .build()

        val request = original.newBuilder()
            .url(url).build()
        return chain.proceed(request)
    }
}