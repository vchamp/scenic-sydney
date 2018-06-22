package com.epam.scenicsydney.database

import android.content.Context
import com.epam.scenicsydney.AppExecutors
import com.epam.scenicsydney.R
import com.epam.scenicsydney.model.Location
import com.google.gson.Gson
import java.io.InputStreamReader

/**
 * Global function that imports locations to [database] by reading them from JSON contained in resources
 * of the [context]. Calls [LocationDao.insertAll] with the retrieved locations.
 */
fun importDefaultLocations(context: Context, database: LocationDatabase) {
    AppExecutors.diskIO.execute {
        val defaultLocations = Gson().fromJson(
                InputStreamReader(context.resources.openRawResource(R.raw.locations)),
                DefaultLocations::class.java)
        database.locationDao().insertAll(*defaultLocations.locations.toTypedArray())
    }
}

/**
 * Locations data to be read by [Gson].
 */
data class DefaultLocations(
        val locations: List<Location>
)
