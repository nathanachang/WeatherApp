package com.capstone.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

private const val CITIES = "4772354,5368361,5128581,4887398,4699066,5809844,5856195,5419384,5391959,4684888,5746545,4990729,4335045"
private const val APIKEY = "6849ae760f417fb8188f4bb7fd0d92fc"
private const val UNITS = "imperial"

class CityListViewModel : ViewModel() {
    private val _cityList = MutableLiveData<List<City>>()
    val cityList: MutableLiveData<List<City>>
        get() = _cityList

    fun getCities() {
        viewModelScope.launch {
            try {
                _cityList.value = WeatherApiClient.retrofitService.getCities(CITIES, APIKEY, UNITS).list
            } catch (e: Exception) {
                _cityList.value = emptyList()
            }
        }
    }
}