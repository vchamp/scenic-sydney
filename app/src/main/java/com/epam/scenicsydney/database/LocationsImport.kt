package com.epam.scenicsydney.database

import android.content.Context
import com.epam.scenicsydney.R
import com.epam.scenicsydney.model.Location
import com.google.gson.Gson
import java.io.InputStreamReader
import java.util.concurrent.Executors

fun importDefaultLocations(context: Context, database: LocationDatabase) {
    Executors.newSingleThreadExecutor().execute {
        val defaultLocations = Gson().fromJson(
                InputStreamReader(context.resources.openRawResource(R.raw.locations)),
                DefaultLocations::class.java)
        defaultLocations.locations.forEach { it.imported = true; }
        database.locationDao().insertAll(*defaultLocations.locations.toTypedArray())
    }
}

data class DefaultLocations(
        val locations: List<Location>
)
