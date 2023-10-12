package com.capstone.weatherapp

data class WeatherApiResponse(val cod: Int, val cityList: List<City>)