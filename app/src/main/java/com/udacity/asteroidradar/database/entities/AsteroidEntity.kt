package com.udacity.asteroidradar.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid

@Entity(tableName = "database_asteroid")
data class AsteroidEntity constructor(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean)

fun List<AsteroidEntity>.asMaping(): List<Asteroid> {
    return map {
        Asteroid(
            isPotentiallyHazardous = it.isPotentiallyHazardous,
            estimatedDiameter = it.estimatedDiameter,
            id = it.id,
            codename = it.codename,
            distanceFromEarth = it.distanceFromEarth,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            relativeVelocity = it.relativeVelocity,
           )
    }
}
