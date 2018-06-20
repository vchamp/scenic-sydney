package com.epam.scenicsydney.inject

import android.arch.persistence.room.Room
import android.content.Context
import com.epam.scenicsydney.database.LocationDatabase

class TestAppModule(context: Context) : AppModule(context) {

    override fun provideLocationDatabase(): LocationDatabase {
        return Room.inMemoryDatabaseBuilder(provideAppContext(), LocationDatabase::class.java)
                .allowMainThreadQueries()
                .build().also { importDefaultLocations(it) }
    }
}
