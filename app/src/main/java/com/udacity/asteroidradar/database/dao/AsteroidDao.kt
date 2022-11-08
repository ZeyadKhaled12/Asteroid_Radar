package com.udacity.asteroidradar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.udacity.asteroidradar.database.entities.AsteroidEntity


@Dao
interface AsteroidDao {

    @Query("SELECT * FROM database_asteroid pod ORDER BY closeApproachDate DESC")
    fun get(): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM database_asteroid obj WHERE obj.closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate DESC")
    fun getWeekly(startDate: String, endDate: String) : LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM database_asteroid obj WHERE obj.closeApproachDate = :today")
    fun getToday(today: String) : LiveData<List<AsteroidEntity>>

    @Insert
    fun insert(asteroidEntity: AsteroidEntity)

}