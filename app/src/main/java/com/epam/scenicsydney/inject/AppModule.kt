package com.epam.scenicsydney.inject

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import com.epam.scenicsydney.R
import com.epam.scenicsydney.database.LocationDatabase
import com.epam.scenicsydney.model.Location
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import java.io.InputStreamReader
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
open class AppModule(context: Context) {

    private val context = context.applicationContext

    @Provides
    open fun provideAppContext(): Context {
        return context
    }

    @Provides
    @Singleton
    open fun provideLocationDatabase(): LocationDatabase {
        Log.d(Injector.TAG, "create database")
        lateinit var database: LocationDatabase
        database = Room.databaseBuilder(context,
                LocationDatabase::class.java, "locations.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        importDefaultLocations(database)
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        return database
    }

    protected fun importDefaultLocations(database: LocationDatabase) {
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
}