package com.epam.scenicsydney.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.epam.scenicsydney.model.Location

/**
 * Room DAO interface to read and change locations in the database. Some methods, like [loadAll] and [get]
 * return [LiveData] that can be observed for any changes in the underlying data.
 */
@Dao
interface LocationDao {
    @Query("select * from Location")
    fun loadAll(): LiveData<List<Location>>

    /**
     * @return list of new ids of the inserted locations
     */
    @Insert
    fun insertAll(vararg locations: Location): List<Long>

    @Query("select * from Location where id=:id")
    fun get(id: Long): LiveData<Location>

    /**
     * Updates an existing location. For insert use [insertAll]
     */
    @Update
    fun save(location: Location)

    @Delete
    fun delete(location: Location)
}