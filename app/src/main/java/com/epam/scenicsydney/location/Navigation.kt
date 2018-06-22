package com.epam.scenicsydney.location

/**
 * Interface of the main screen navigation.
 */
interface Navigation {
    /**
     * Open the screen for a saved location edit.
     *
     * @param locationId id of the location, new locations should also be saved and have the id
     * @param isNew true if the screen should be opened for a newly added location
     */
    fun openEditLocation(locationId: Long, isNew: Boolean)
}