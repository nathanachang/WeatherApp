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

    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    fun getSingleCityFromRepo(cityId: String) {
        viewModelScope.launch {
            try {
                weatherRepo.refreshSingleCity(cityId)
                _eventNetworkError.value = false
            } catch (e: Exception) {
                if(city.value == null) {
                    _eventNetworkError.value = true
                }
            }
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