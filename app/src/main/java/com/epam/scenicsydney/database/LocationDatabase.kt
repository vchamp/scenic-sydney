package com.epam.scenicsydney.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.epam.scenicsydney.model.Location
import com.epam.scenicsydney.model.Note

/**
 * Defines DAO classes that are provided by this [RoomDatabase].
 */
@Database(entities = [Location::class, Note::class], version = 4, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao

    abstract fun noteDao(): NoteDao
}