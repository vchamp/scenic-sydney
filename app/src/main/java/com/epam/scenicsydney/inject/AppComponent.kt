package com.epam.scenicsydney.inject

import com.epam.scenicsydney.AppExecutors
import com.epam.scenicsydney.location.LocationsRepository
import com.epam.scenicsydney.location.LocationsViewModel
import com.epam.scenicsydney.location.edit.EditLocationViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Dagger application component.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun locationsRepository(): LocationsRepository

    fun locationsViewModel(): LocationsViewModel

    fun editLocationViewModel(): EditLocationViewModel
}