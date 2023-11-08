package com.capstone.weatherapp

sealed class CityListState {
    data class Success (val result: List<City>) : CityListState()
    data class Error(val message: String) : CityListState()
    object Loading: CityListState()
}
sealed class SingleCityState {
    data class Success (val result: SingleCityResponse) : SingleCityState()
    data class Error(val message: String) : SingleCityState()
    object Loading: SingleCityState()
}