package com.capstone.weatherapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityCache::class, SingleCityCache::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun cityDao() : CityDao
    abstract fun singleCityDao() : SingleCityDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null
        fun getDatabase(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "appdb"
                ).allowMainThreadQueries().build()

                INSTANCE = instance
                return instance
            }
        }
    }

}