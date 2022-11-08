package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.database.entities.asMaping
import com.udacity.asteroidradar.network.AsteroidRadarApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoPictureOfDay(private val database: AsteroidRadarDatabase){

    val pictureOfDay: LiveData<PictureOfDay> =
        Transformations.map(database.pictureOfDayDao.get()) {
            it.asMaping()
        }

    suspend fun getPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                val pictureOfDay = AsteroidRadarApi.retrofitService.getPictureAsync(Constants.API_KEY).await()
                //database.pictureOfDayDao.insert(pictureOfDay)
            } catch (_: Exception) {

            }
        }
    }

}