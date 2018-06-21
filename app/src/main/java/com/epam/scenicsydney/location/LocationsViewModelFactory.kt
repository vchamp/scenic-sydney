package com.epam.scenicsydney.location

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.epam.scenicsydney.inject.Injector

/**
 * Creates LocationsViewModel using injection.
 */
class LocationsViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Injector.createLocationsViewModel() as T
    }
}