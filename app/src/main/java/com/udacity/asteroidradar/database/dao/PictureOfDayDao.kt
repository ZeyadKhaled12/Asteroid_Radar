package com.udacity.asteroidradar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.udacity.asteroidradar.database.entities.PictureOfDayEntity


@Dao
interface PictureOfDayDao {

    @Query("SELECT * FROM database_picture_of_day pod ORDER BY pod.date DESC LIMIT 0,1")
    fun get():  LiveData<PictureOfDayEntity>

    @Insert
    fun insert(pictureOfDay: PictureOfDayEntity)

}