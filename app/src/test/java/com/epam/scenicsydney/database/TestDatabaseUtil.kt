package com.epam.scenicsydney.database

import android.arch.persistence.room.Room
import android.content.Context

fun createInMemoryDatabase(context: Context): LocationDatabase {
    return Room.inMemoryDatabaseBuilder(context, LocationDatabase::class.java)
            .allowMainThreadQueries()
            .build().also { importDefaultLocations(context, it) }
}