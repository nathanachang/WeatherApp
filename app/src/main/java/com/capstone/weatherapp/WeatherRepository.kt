package com.capstone.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val CITIES = "4772354,5368361,5128581,4887398,4699066,5809844,5856195,5419384,5391959,4684888,5746545,4990729,4335045"
private const val APIKEY = "6849ae760f417fb8188f4bb7fd0d92fc"
private const val UNITS = "imperial"

class WeatherRepository(private val db: WeatherDatabase) {
    private val _cityListData = MutableLiveData<List<City>>()
    val cityListData: MutableLiveData<List<City>>
        get() = _cityListData
    private val _singleCityData = MutableLiveData<SingleCityResponse>()
    val singleCityData: MutableLiveData<SingleCityResponse>
        get() = _singleCityData

    private fun getNetworkCityList(): List<City> {
        val cacheCityList = db.cityDao().getCityWeather()
        val networkCityList = CityCache.convertToWeatherApiResponse(cacheCityList)

        return networkCityList.list
    }

    suspend fun refreshCityList() {
        withContext(Dispatchers.IO) {
            val cityList = WeatherApiClient.retrofitService.getCities(CITIES, APIKEY, UNITS)
            db.cityDao().insert(cityList.convertToCityCacheList())
        }
        _cityListData.postValue(getNetworkCityList())
    }

    suspend fun refreshSingleCity(cityId: String) {
        withContext(Dispatchers.IO) {
            val singleCity = WeatherApiClient.retrofitService.getCity(cityId, APIKEY, UNITS)
            db.singleCityDao().insert(singleCity.convertToSingleCityCache())
        }
        _singleCityData.postValue(db.singleCityDao().getSingleCityById(cityId).convertToSingleCityResponse())
    }
}