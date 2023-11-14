package com.capstone.weatherapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val APIKEY = "6849ae760f417fb8188f4bb7fd0d92fc"
private const val UNITS = "imperial"

class DetailsViewModel(private val weatherRepo: WeatherRepository) : ViewModel() {
    val city = weatherRepo.singleCityData

    fun getSingleCityFromRepo(cityId: String) {
        viewModelScope.launch {
            weatherRepo.refreshSingleCity(cityId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTime(timestamp: Int?, timeZoneOffset: Int?): String {
        val offset = ZoneOffset.ofTotalSeconds(timeZoneOffset!!)
        val instant = Instant.ofEpochSecond(timestamp!!.toLong())
        val formatter = DateTimeFormatter.ofPattern("K:mm a", Locale.ENGLISH)
        return instant.atOffset(offset).format(formatter)
    }
}

class DetailsViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}