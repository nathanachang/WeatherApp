package com.capstone.weatherapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityCache(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys
) {
    fun convertToWeatherApiResponse(cityCacheList: List<CityCache>) : WeatherApiResponse {
        val cityList = mutableListOf<City>()
        for (cityCache: CityCache in cityCacheList) {
            val city = City(
                id = cityCache.id,
                name = cityCache.name,
                coord = cityCache.coord,
                weather = cityCache.weather,
                main = cityCache.main,
                visibility = cityCache.visibility,
                wind = cityCache.wind,
                clouds = cityCache.clouds,
                dt = cityCache.dt,
                sys = cityCache.sys
            )
            cityList.add(city)
        }

        return WeatherApiResponse(cityList.size, cityList)
    }
}

@Entity
data class SingleCityCache(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: SysDetails,
    val timezone: Int,
    val cod: Int
) {
    fun convertToSingleCityResponse(singleCityCache: SingleCityCache) : SingleCityResponse {
        return SingleCityResponse(
            id = singleCityCache.id,
            name = singleCityCache.name,
            coord = singleCityCache.coord,
            weather = singleCityCache.weather,
            base = singleCityCache.base,
            main = singleCityCache.main,
            visibility = singleCityCache.visibility,
            wind = singleCityCache.wind,
            clouds = singleCityCache.clouds,
            dt = singleCityCache.dt,
            sys = singleCityCache.sys,
            timezone = singleCityCache.timezone,
            cod = singleCityCache.cod
        )
    }
}