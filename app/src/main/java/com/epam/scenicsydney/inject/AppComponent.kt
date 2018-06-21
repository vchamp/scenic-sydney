package com.epam.scenicsydney.inject

import com.epam.scenicsydney.location.LocationsRepository
import com.epam.scenicsydney.location.LocationsViewModel
import com.epam.scenicsydney.location.edit.EditLocationViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(editLocationViewModel: EditLocationViewModel)

    fun locationsRepository(): LocationsRepository

    fun locationsViewModel(): LocationsViewModel
}