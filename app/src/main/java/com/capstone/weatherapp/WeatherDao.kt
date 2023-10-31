package com.capstone.weatherapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CityDao {
    @Query("SELECT * FROM cityCache")
    fun getCityWeather() : List<CityCache>

    @Insert
    suspend fun insert(cityCache: CityCache)

    @Delete
    suspend fun delete(cityCache: CityCache)
}

@Dao
interface SingleCityDao {
    @Query("SELECT * FROM singleCityCache where id = :cityId")
    fun getSingleCityById(cityId: String) : SingleCityCache

    @Insert
    suspend fun insert(singleCityCache: SingleCityCache)

    @Delete
    suspend fun delete(singleCityCache: SingleCityCache)
}