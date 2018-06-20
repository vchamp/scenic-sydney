package com.epam.scenicsydney.inject

import com.epam.scenicsydney.location.LocationsRepository
import com.epam.scenicsydney.location.LocationsViewModel
import com.epam.scenicsydney.location.edit.EditLocationViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class])
interface AppComponent {

    fun injectTo(locationsViewModel: LocationsViewModel)

    fun injectTo(editLocationViewModel: EditLocationViewModel)

    fun injectTo(locationsRepository: LocationsRepository)
}