package com.epam.scenicsydney.location.edit

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.epam.scenicsydney.inject.Injector

/**
 * Creates [EditLocationViewModel] using injection.
 */
class EditLocationViewModelFactory(private val locationId: Long) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return Injector.createEditLocationViewModel().apply { locationId = this@EditLocationViewModelFactory.locationId } as T
    }
}
