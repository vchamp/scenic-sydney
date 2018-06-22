package com.epam.scenicsydney.location

import com.epam.scenicsydney.model.Location

const val CENTER_LATITUDE = -33.8691
const val CENTER_LONGITUDE = 151.20932

/**
 * @return distance in meters from Sydney center
 */
fun getDistanceToCenter(location: Location): Float {
    val result = FloatArray(1)
    android.location.Location.distanceBetween(CENTER_LATITUDE, CENTER_LONGITUDE, location.latitude, location.longitude, result)
    return result[0]
}

/**
 * @return a new list that is sorted by locations' distance to Sydney center
 */
fun sortByDistanceFromCenter(list: List<Location>): List<Location> {
    return list.sortedWith(compareBy {
        getDistanceToCenter(it)
    })
}