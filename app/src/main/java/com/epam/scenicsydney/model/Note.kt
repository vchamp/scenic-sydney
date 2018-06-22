package com.epam.scenicsydney.model

import android.arch.persistence.room.*

/**
 * Note entity class.
 *
 * For creation of new instances use the shorter constructor.
 */
@Entity(
        foreignKeys = [
            ForeignKey(
                    entity = Location::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("locationId"),
                    onDelete = ForeignKey.CASCADE)],
        indices = [
            Index("locationId")])
data class Note(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        var text: String,
        var locationId: Long
) {
    @Ignore
    constructor(locationId: Long) : this(0, "", locationId)
}