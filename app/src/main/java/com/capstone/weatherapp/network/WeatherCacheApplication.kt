package com.capstone.weatherapp

import android.app.Application

class WeatherCacheApplication : Application() {
    val db: WeatherDatabase by lazy { WeatherDatabase.getDatabase(this) }
    val repo: WeatherRepository by lazy { WeatherRepository(db) }
}