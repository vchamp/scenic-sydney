package com.epam.scenicsydney.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
            entity = Location::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("locationId"),
            onDelete = ForeignKey.CASCADE)])
data class Note(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        var text: String = "",
        var locationId: Long = 0
) {
    constructor(locationId: Long) : this(0, "", locationId)
}