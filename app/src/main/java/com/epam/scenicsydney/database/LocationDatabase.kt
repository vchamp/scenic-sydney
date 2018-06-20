package com.epam.scenicsydney.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.epam.scenicsydney.model.Location
import com.epam.scenicsydney.model.Note

@Database(entities = [Location::class, Note::class], version = 2)
abstract class LocationDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao

    abstract fun noteDao(): NoteDao
}