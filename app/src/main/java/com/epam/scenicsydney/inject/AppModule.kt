package com.epam.scenicsydney.inject

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import com.epam.scenicsydney.database.LocationDatabase
import com.epam.scenicsydney.database.importDefaultLocations
import dagger.Module
import dagger.Provides
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
                        importDefaultLocations(context, database)
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        return database
    }
}