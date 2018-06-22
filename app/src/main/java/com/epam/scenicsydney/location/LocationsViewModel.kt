package com.epam.scenicsydney.location

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.epam.scenicsydney.model.Location
import javax.inject.Inject

/**
 * View model for map and list fragments. Holds locations live data, manages locations addition.
 */
class LocationsViewModel @Inject constructor(
        private val repository: LocationsRepository) : ViewModel() {

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

    fun setAddLocationMode(addMode: Boolean) {
        addLocationMode.value = addMode
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