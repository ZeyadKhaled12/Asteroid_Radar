package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Asteroid


import com.udacity.asteroidradar.database.AsteroidRadarDatabase.Companion.getAsteroidDatabase
import com.udacity.asteroidradar.network.AsteroidRadarApiFilter
import com.udacity.asteroidradar.network.AsteroidRadarApiFilter.*

import com.udacity.asteroidradar.repository.RepoAsteroid
import com.udacity.asteroidradar.repository.RepoPictureOfDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val asteroidDatabase = getAsteroidDatabase(application)
    private val repoPictureOfDay = RepoPictureOfDay(asteroidDatabase)
    private val repoAsteroid = RepoAsteroid(asteroidDatabase)
    val navigateToSelectedAsteroid = MutableLiveData<Asteroid?>()



    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val filter = MutableLiveData(SHOW)

    init {
        coroutineScope.launch {
            repoAsteroid.getAsteroids()
            repoPictureOfDay.getPictureOfDay()

        }
    }

    val pictureOfDay = repoPictureOfDay.pictureOfDay
    var asteroids = repoAsteroid.asteroids

    fun displayPropertyDetails(asteroid: Asteroid) {
        navigateToSelectedAsteroid.value = asteroid
    }
    fun displayPropertyDetailsComplete() {
        navigateToSelectedAsteroid.value = null
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun updateFilter(asteroidRadarApiFilter: AsteroidRadarApiFilter) {
        asteroids = when (filter.value) {
            SHOW_TODAY -> {
                repoAsteroid.todayAsteroid
            }
            SHOW_WEEK -> {
                repoAsteroid.weeklyAsteroid
            }
            else -> {
                repoAsteroid.asteroids
            }
        }
    }



}