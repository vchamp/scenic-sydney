package com.epam.scenicsydney

import android.app.Application
import com.epam.scenicsydney.inject.AppModule
import com.epam.scenicsydney.inject.DaggerAppComponent
import com.epam.scenicsydney.inject.Injector
import com.epam.scenicsydney.inject.RepositoryModule

class ScenicSydneyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.APP_COMPONENT = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .repositoryModule(RepositoryModule())
                .build()
    }
}