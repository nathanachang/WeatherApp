package com.capstone.weatherapp

data class City(val coord: Coord, val weather: List<Weather>, val base: String, val main: Main,
    val visibility: Int, val wind: Wind, val rain: Rain, val clouds: Clouds, val dt: Int,
    val sys: Sys, val timezone: Int, val id: Int, val name: String, val cod: Int) {
    // establish inner classes
    data class Coord(val lon: Double, val lat: Double)
    data class Weather(val id: Int, val main: String, val desc: String, val icon: String)
    data class Main(val temp: Double, val feelsLike: Double, val tempMin: Double, val tempMax: Double,
                    val pressure: Double, val humidity: Double, val seaLevel: Double, val groundLevel: Double)
    data class Wind(val speed: Double, val deg: Int, val gust: Double)
    data class Rain(val h: Double)
    data class Clouds(val all: Int)
    data class Sys(val type: Int, val id: Int, val country: String, val sunrise: Int, val sunset: Int)
}