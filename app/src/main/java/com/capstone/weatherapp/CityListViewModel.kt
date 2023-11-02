package com.capstone.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

private const val CITIES = "4772354,5368361,5128581,4887398,4699066,5809844,5856195,5419384,5391959,4684888,5746545,4990729,4335045"
private const val APIKEY = "6849ae760f417fb8188f4bb7fd0d92fc"
private const val UNITS = "imperial"

class CityListViewModel(private val weatherRepo: WeatherRepository) : ViewModel() {
    private val _cityList = weatherRepo.cityListData
    val cityList: MutableLiveData<List<City>>
        get() = _cityList

    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    fun getCityListFromRepo() {
        viewModelScope.launch {
            try {
                weatherRepo.refreshCityList()
                _eventNetworkError.value = false
            } catch (e: Exception) {
                if (cityList.value.isNullOrEmpty()) {
                    _eventNetworkError.value = true
                }
            }
        }
    }
}

class CityListViewModelFactory(private val repo: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityListViewModelFactory::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CityListViewModel(repo) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}