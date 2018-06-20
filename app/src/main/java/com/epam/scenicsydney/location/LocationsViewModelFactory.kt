package com.epam.scenicsydney.location

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class LocationsViewModelFactory(private val repository: LocationsRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationsViewModel() as T
    }
}