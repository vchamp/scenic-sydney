package com.epam.scenicsydney.inject

import com.epam.scenicsydney.location.edit.EditLocationViewModel

/**
 * Holds global static reference to Dagger component and provides convenient interface to injection framework.
 */
object Injector {
    val TAG: String = Injector::class.java.simpleName

    lateinit var APP_COMPONENT: AppComponent

    fun injectTo(editLocationViewModel: EditLocationViewModel) {
        APP_COMPONENT.inject(editLocationViewModel)
    }

    fun createLocationsRepository() = APP_COMPONENT.locationsRepository()

    fun createLocationsViewModel() = APP_COMPONENT.locationsViewModel()
}