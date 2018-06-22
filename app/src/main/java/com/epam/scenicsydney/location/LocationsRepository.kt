package com.epam.scenicsydney.location

import android.arch.lifecycle.LiveData
import com.epam.scenicsydney.AppExecutors
import com.epam.scenicsydney.database.LocationDatabase
import com.epam.scenicsydney.model.Location
import com.epam.scenicsydney.model.Note
import javax.inject.Inject

/**
 * Single point of access to the locations database.
 */
class LocationsRepository @Inject constructor(private val database: LocationDatabase) {

    // executor for background operations
    private val executor = AppExecutors.diskIO

    fun close() {
        database.close()
    }

    fun getLocations(): LiveData<List<Location>> {
        return database.locationDao().loadAll()
    }

    fun addLocation(location: Location) {
        executor.execute {
            val ids = database.locationDao().insertAll(location)
            location.id = ids[0]
        }
    }

    fun getLocation(id: Long): LiveData<Location> {
        return database.locationDao().get(id)
    }

    fun saveLocation(location: Location) {
        executor.execute {
            database.locationDao().save(location)
        }
    }

    fun deleteLocation(location: Location) {
        executor.execute {
            database.locationDao().delete(location)
        }
    }

    fun getNotes(locationId: Long): LiveData<List<Note>> {
        return database.noteDao().loadAllForLocation(locationId)
    }

    fun addNote(note: Note) {
        executor.execute {
            database.noteDao().add(note)
        }
    }
}