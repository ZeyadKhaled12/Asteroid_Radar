package com.udacity.asteroidradar.repository

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.database.AsteroidRadarDatabase
import com.udacity.asteroidradar.database.entities.AsteroidEntity
import com.udacity.asteroidradar.database.entities.asMaping
import com.udacity.asteroidradar.network.AsteroidRadarApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class RepoAsteroid(private val database: AsteroidRadarDatabase) {

    @SuppressLint("WeekBasedYear")
    @RequiresApi(Build.VERSION_CODES.O)
    val firstApiFormat = DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT)!!

    @RequiresApi(Build.VERSION_CODES.O)
    val today: LocalDate = LocalDate.parse(Calendar.getInstance().time.toString(), firstApiFormat)

    @RequiresApi(Build.VERSION_CODES.O)
    val week: LocalDate = LocalDate.parse(addDaysToDate(7).toString(), firstApiFormat)



    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.get()) {
            it.asMaping()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    val weeklyAsteroid: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getWeekly(today.toString(), week.toString())) {
            it.asMaping()
        }

    @RequiresApi(Build.VERSION_CODES.O)
    val todayAsteroid: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getToday(today.toString())) {
            it.asMaping()
        }


    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroid = AsteroidRadarApi.retrofitService.getAsteroidAsync(Constants.API_KEY).await()
                //database.asteroidDao.insert(asteroid)
            } catch (_: Exception) {

            }
        }
    }
}




fun addDaysToDate(days: Int): Date{
    var dt = Date()
    val c = Calendar.getInstance()
    c.time = dt
    c.add(Calendar.DATE, days)
    dt = c.time
    return dt
}