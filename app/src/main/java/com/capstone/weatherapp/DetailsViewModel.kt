package com.capstone.weatherapp

import androidx.lifecycle.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

private const val APIKEY = "6849ae760f417fb8188f4bb7fd0d92fc"
private const val UNITS = "imperial"

class DetailsViewModel : ViewModel() {
    private val _city = MutableLiveData<SingleCityResponse?>()
    val city: MutableLiveData<SingleCityResponse?>
        get() = _city

    fun getCity(cityId: String) {
        viewModelScope.launch {
            try {
                println("calling weather api with id $cityId")
                _city.value = WeatherApiClient.retrofitService.getCity(cityId, APIKEY, UNITS)
                println(city.value?.name)
            } catch (e: Exception) {
                println("error in api call: $e")
                _city.value = null
            }
        }
    }
}