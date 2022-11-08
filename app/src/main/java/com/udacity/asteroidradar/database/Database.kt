package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.database.dao.AsteroidDao
import com.udacity.asteroidradar.database.dao.PictureOfDayDao
import com.udacity.asteroidradar.database.entities.AsteroidEntity
import com.udacity.asteroidradar.database.entities.PictureOfDayEntity


@Database(entities = [AsteroidEntity::class, PictureOfDayEntity::class], version = 1, exportSchema = false)
abstract class AsteroidRadarDatabase: RoomDatabase() {

    abstract val pictureOfDayDao: PictureOfDayDao
    abstract val asteroidDao: AsteroidDao

    companion object{

        @Volatile
        private var INSTANCE: AsteroidRadarDatabase? = null
        fun getAsteroidDatabase(context: Context): AsteroidRadarDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidRadarDatabase::class.java,
                        "AsteroidRadarDatabase"
                    ).fallbackToDestructiveMigration().build()
                }
                return  instance
            }
        }
    }
}

