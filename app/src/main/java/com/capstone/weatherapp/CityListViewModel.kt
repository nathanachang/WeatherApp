package com.capstone.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

private const val CITIES = "2643741,2644688,2633352,2654675,2988507,2990969,2911298,2925535,2950159,3120501,3128760,5128581,4140963,4930956,5106834,5391959,5368361,5809844,4099974,4440906"
private const val APIKEY = "6849ae760f417fb8188f4bb7fd0d92fc"
private const val UNITS = "imperial"

class CityListViewModel : ViewModel() {
    private val _cityList = MutableLiveData<List<City>>()
    val cityList: MutableLiveData<List<City>>
        get() = _cityList

    fun getCities() {
        viewModelScope.launch {
            try {
                _cityList.value = WeatherApiClient.retrofitService.getCities(CITIES, APIKEY, UNITS).cityList
            } catch (e: Exception) {
                _cityList.value = emptyList()
            }
        }
    }
}