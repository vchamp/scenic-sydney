package com.epam.scenicsydney.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Location(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        var imported: Boolean = false,
        @SerializedName("lat")
        var latitude: Double = 0.0,
        @SerializedName("lng")
        var longitude: Double = 0.0,
        var name: String = ""
) {
    constructor(latitude: Double, longitude: Double) : this(0, false, latitude, longitude, "")
}