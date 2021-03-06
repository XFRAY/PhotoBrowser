package com.example.photobrowser

import android.app.Application
import com.example.photobrowser.di.networkModule
import com.example.photobrowser.di.dataModule
import com.example.photobrowser.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(networkModule, viewModelModule, dataModule)
        }
    }
}