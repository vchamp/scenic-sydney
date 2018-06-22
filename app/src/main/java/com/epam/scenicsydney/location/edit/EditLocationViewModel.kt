package com.epam.scenicsydney.location.edit

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.epam.scenicsydney.location.LocationsRepository
import com.epam.scenicsydney.model.Location
import com.epam.scenicsydney.model.Note
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Holds the location and its notes live data. Save check and close commands are for activity-fragment communication.
 */
class EditLocationViewModel @Inject constructor(private val repository: LocationsRepository) : ViewModel() {

    var locationId: Long by Delegates.notNull()

    private val locationData: LiveData<Location> by lazy {
        repository.getLocation(locationId)
    }

    private val notesData: LiveData<List<Note>> by lazy {
        repository.getNotes(locationId)
    }

    private val saveCheckCommandData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply { value = false }
    }

    private val closeCommandData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply { value = false }
    }

    fun getLocation() = locationData

    fun getNotes() = notesData

    fun addNote(note: Note) {
        repository.addNote(note)
    }

    fun getSaveCheckCommand(): LiveData<Boolean> = saveCheckCommandData

    fun getCloseCommand(): LiveData<Boolean> = closeCommandData

    fun save(title: String) {
        locationData.value?.let { location ->
            location.name = title
            repository.saveLocation(location)
        }
    }

    fun deleteLocation() {
        locationData.value?.let { location ->
            repository.deleteLocation(location)
        }
    }

    fun saveCheck() {
        saveCheckCommandData.value = true
    }

    fun close() {
        closeCommandData.value = true
    }
}
