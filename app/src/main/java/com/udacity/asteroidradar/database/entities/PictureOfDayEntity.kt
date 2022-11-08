package com.udacity.asteroidradar.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.PictureOfDay

@Entity(tableName = "database_picture_of_day")
data class PictureOfDayEntity constructor(
    @PrimaryKey
    val url : String,
    val date: String,
    val mediaType : String,
    val title : String)

fun PictureOfDayEntity.asMaping() :PictureOfDay{
    return PictureOfDay(url, mediaType, title)
}
