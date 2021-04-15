package com.example.photobrowser.di

import com.example.photobrowser.BuildConfig
import com.example.photobrowser.data.data_source.PhotosDataSourceFactory
import com.example.photobrowser.data.mapper.PhotosDTOMapper
import com.example.photobrowser.data.network.ApiKeyInterceptor
import com.example.photobrowser.data.network.ApiService
import com.example.photobrowser.data.network.JsonFormatInterceptor
import com.example.photobrowser.data.network.NoJsonCallbackInterceptor
import com.example.photobrowser.data.repository.PhotosRepository
import com.example.photobrowser.data.repository.PhotosRepositoryImpl
import com.example.photobrowser.view.photo_browser.PhotoBrowserViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single { provideRetrofit(get()).create(ApiService::class.java) }
    single { provideOkHttpClient(get(), get(), get(), get()) }
    single { ApiKeyInterceptor() }
    single { NoJsonCallbackInterceptor() }
    single { JsonFormatInterceptor() }
    single { provideHttpLoggingInterceptor() }
}

val viewModelModule = module {
    viewModel { PhotoBrowserViewModel(get()) }
}

val dataModule = module {
    single<PhotosRepository> { PhotosRepositoryImpl(get()) }
    single { PhotosDataSourceFactory(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
}

fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    apiKeyInterceptor: ApiKeyInterceptor,
    noJsonCallbackInterceptor: NoJsonCallbackInterceptor,
    jsonFormatInterceptor: JsonFormatInterceptor
) = OkHttpClient.Builder().apply {
    addInterceptor(httpLoggingInterceptor)
    addInterceptor(apiKeyInterceptor)
    addInterceptor(noJsonCallbackInterceptor)
    addInterceptor(jsonFormatInterceptor)
}.build()

fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().also {
    it.level = HttpLoggingInterceptor.Level.BODY
}
