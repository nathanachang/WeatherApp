package com.capstone.weatherapp

data class SingleCityResponse(
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
    val id: Int,
    val name: String,
    val cod: Int
)
data class SysDetails(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)