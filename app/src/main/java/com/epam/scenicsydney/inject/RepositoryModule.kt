package com.epam.scenicsydney.inject

import android.util.Log
import com.epam.scenicsydney.location.LocationsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class RepositoryModule {

    @Provides
    @Singleton
    open fun provideLocationsRepository(): LocationsRepository {
        Log.d(Injector.TAG, "create repository")
        return LocationsRepository()
    }
}