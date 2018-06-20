package com.epam.scenicsydney.inject

import com.epam.scenicsydney.location.LocationsRepository
import com.epam.scenicsydney.location.LocationsViewModel
import com.epam.scenicsydney.location.edit.EditLocationViewModel

class Injector {
    companion object {
        val TAG: String = Injector::class.java.simpleName

        lateinit var APP_COMPONENT: AppComponent

        fun injectTo(locationsViewModel: LocationsViewModel) {
            APP_COMPONENT.injectTo(locationsViewModel)
        }

        fun injectTo(editLocationViewModel: EditLocationViewModel) {
            APP_COMPONENT.injectTo(editLocationViewModel)
        }

        fun injectTo(locationsRepository: LocationsRepository) {
            APP_COMPONENT.injectTo(locationsRepository)
        }
    }
}