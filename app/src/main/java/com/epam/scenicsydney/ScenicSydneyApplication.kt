package com.epam.scenicsydney

import android.app.Application
import com.epam.scenicsydney.inject.AppModule
import com.epam.scenicsydney.inject.DaggerAppComponent
import com.epam.scenicsydney.inject.Injector

/**
 * The purpose of this application class is to create the singleton Dagger component each time
 * the application is created and to provide the application context to the component.
 */
class ScenicSydneyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.APP_COMPONENT = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}