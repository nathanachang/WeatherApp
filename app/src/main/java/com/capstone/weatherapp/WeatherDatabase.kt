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
        private var dbInstance: WeatherDatabase? = null
        fun getDatabase(context: Context): WeatherDatabase {
            val db = Room.databaseBuilder(
                context,
                WeatherDatabase::class.java,
                "appdb"
            ).build()
            dbInstance = db

            return db
        }
    }

}