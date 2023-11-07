package com.capstone.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

private const val APIKEY = "6849ae760f417fb8188f4bb7fd0d92fc"
private const val UNITS = "imperial"

class DetailsViewModel(private val weatherRepo: WeatherRepository) : ViewModel() {
    val city = weatherRepo.singleCityData

    fun getSingleCityFromRepo(cityId: String) {
        viewModelScope.launch {
            try {
                weatherRepo.refreshSingleCity(cityId)
            } catch (e: Exception) {}
        }
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