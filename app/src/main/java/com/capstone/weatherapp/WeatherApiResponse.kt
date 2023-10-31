package com.capstone.weatherapp

data class City(
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val id: Int,
    val name: String
)
data class Coord(
    val lon: Double,
    val lat: Double
)
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Double,
    val humidity: Double
)
data class Wind(
    val speed: Double,
    val deg: Int
)
data class Rain(
    val h: Double
)
data class Clouds(
    val all: Int
)
data class Sys(
    val country: String,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
)
data class WeatherApiResponse(
    val cnt: Int,
    val list: List<City>
) {
    fun convertToCityCacheList(cityList: List<City>) : List<CityCache> {
        val cacheList = mutableListOf<CityCache>()
        for (city: City in cityList) {
            val cityCache =  CityCache(
                id = city.id,
                name = city.name,
                coord = city.coord,
                weather = city.weather,
                main = city.main,
                visibility = city.visibility,
                wind = city.wind,
                clouds = city.clouds,
                dt = city.dt,
                sys = city.sys
            )
            cacheList.add(cityCache)
        }

        return cacheList
    }
}