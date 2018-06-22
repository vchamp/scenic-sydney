package com.epam.scenicsydney.inject

/**
 * Holds global static reference to Dagger component and provides convenient interface to injection framework.
 */
object Injector {
    val TAG: String = Injector::class.java.simpleName

    lateinit var APP_COMPONENT: AppComponent

    fun createLocationsViewModel() = APP_COMPONENT.locationsViewModel()

    fun createEditLocationViewModel() = APP_COMPONENT.editLocationViewModel()
}