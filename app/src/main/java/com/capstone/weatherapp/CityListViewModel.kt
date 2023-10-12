package com.capstone.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CityListViewModel : ViewModel() {
    private val _cityList = MutableLiveData<List<City>>()
    val cityList: MutableLiveData<List<City>>
        get() = _cityList

    fun getCities() {
        viewModelScope.launch {
            try {
                _cityList.value = WeatherApiClient.retrofitService.getCities().cityList
            } catch (e: Exception) {
                _cityList.value = listOf()
            }
        }
    }
}