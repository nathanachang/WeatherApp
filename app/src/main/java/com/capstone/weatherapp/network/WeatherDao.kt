package com.capstone.weatherapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {
    @Query("SELECT EXISTS(SELECT * FROM cityCache WHERE id = :cityId)")
    fun cityExists(cityId: String) : Boolean
    @Query("SELECT * FROM cityCache")
    fun getCityWeather() : List<CityCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cityCacheList: List<CityCache>)

    @Delete
    suspend fun delete(cityCacheList: List<CityCache>)
}

@Dao
interface SingleCityDao {
    @Query("SELECT EXISTS(SELECT * FROM singleCityCache WHERE id = :cityId)")
    fun singleCityExists(cityId: String) : Boolean
    @Query("SELECT * FROM singleCityCache where id = :cityId")
    fun getSingleCityById(cityId: String) : SingleCityCache

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(singleCityCache: SingleCityCache)

    @Delete
    suspend fun delete(singleCityCache: SingleCityCache)
}