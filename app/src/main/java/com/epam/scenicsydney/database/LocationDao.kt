package com.epam.scenicsydney.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.epam.scenicsydney.model.Location

@Dao
interface LocationDao {
    @Query("select * from Location")
    fun loadAll(): LiveData<List<Location>>

    @Query("select count(*) from Location where imported=1")
    fun getDefaultCount(): Int

    @Insert
    fun insertAll(vararg locations: Location): List<Long>

    @Query("select * from Location where id=:id")
    fun get(id: Long): LiveData<Location>

    @Update
    fun save(location: Location)

    @Delete
    fun delete(location: Location)
}