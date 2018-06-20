package com.epam.scenicsydney.inject

import android.arch.persistence.room.Room
import android.content.Context
import com.epam.scenicsydney.database.LocationDatabase

class AndroidTestAppModule(context: Context) : AppModule(context) {

    override fun provideLocationDatabase(): LocationDatabase {
        return Room.inMemoryDatabaseBuilder(provideAppContext().applicationContext, LocationDatabase::class.java)
                .allowMainThreadQueries()
                .build().also { importDefaultLocations(it) }
    }
}
