package com.epam.scenicsydney.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Location entity class.
 *
 * For creation of new instances use the shorter constructor.
 */
@Entity
data class Location(
        @PrimaryKey(autoGenerate = true)
        var id: Long,
        @SerializedName("lat")
        var latitude: Double,
        @SerializedName("lng")
        var longitude: Double,
        var name: String
) {
    @Ignore
    constructor(latitude: Double, longitude: Double) : this(0, latitude, longitude, "")
}