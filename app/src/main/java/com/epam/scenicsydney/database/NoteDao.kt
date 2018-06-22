package com.epam.scenicsydney.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.epam.scenicsydney.model.Note

/**
 * Location notes DAO. Returns location notes in the observable [LiveData]. Inserts new notes.
 */
@Dao
interface NoteDao {
    @Query("select * from Note where locationId = :locationId")
    fun loadAllForLocation(locationId: Long): LiveData<List<Note>>

    @Insert
    fun add(note: Note)
}