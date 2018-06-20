package com.epam.scenicsydney.location.edit

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class EditLocationViewModelFactory(private val locationId: Long) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditLocationViewModel(locationId) as T
    }
}
