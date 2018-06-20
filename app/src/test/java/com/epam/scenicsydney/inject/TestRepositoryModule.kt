package com.epam.scenicsydney.inject

import com.epam.scenicsydney.location.LocationsRepository

class TestRepositoryModule(private val locationsRepositoryMock: LocationsRepository) : RepositoryModule() {

    override fun provideLocationsRepository(): LocationsRepository {
        return locationsRepositoryMock
    }
}