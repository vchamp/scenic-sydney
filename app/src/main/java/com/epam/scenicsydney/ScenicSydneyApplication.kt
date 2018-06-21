package com.epam.scenicsydney

import android.app.Application
import com.epam.scenicsydney.inject.AppModule
import com.epam.scenicsydney.inject.DaggerAppComponent
import com.epam.scenicsydney.inject.Injector

class ScenicSydneyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.APP_COMPONENT = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}