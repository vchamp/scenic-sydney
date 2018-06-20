package com.epam.scenicsydney.location

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.epam.scenicsydney.inject.Injector
import com.epam.scenicsydney.model.Location
import javax.inject.Inject

class LocationsViewModel : ViewModel() {

    @Inject
    lateinit var repository: LocationsRepository

    init {
        Injector.injectTo(this)
    }

    private val locationsData: LiveData<List<Location>> by lazy {
        repository.getLocations()
    }

    private var addedLocation: Location? = null

    private val addLocationMode: MutableLiveData<Boolean> = MutableLiveData()

    fun getLocations() = locationsData

    fun getAddLocationMode(): LiveData<Boolean> = addLocationMode

    fun switchAddLocationMode() {
        val currentMode = addLocationMode.value ?: false
        addLocationMode.value = !currentMode
    }

    fun isAddLocationMode(): Boolean = addLocationMode.value ?: false

    fun addLocation(latitude: Double, longitude: Double) {
        val location = Location(latitude, longitude)
        addedLocation = location
        repository.addLocation(location)
    }

    fun getAddedLocation(): Location? {
        return addedLocation
    }

    fun removeAddedLocation() {
        addedLocation = null
    }
}