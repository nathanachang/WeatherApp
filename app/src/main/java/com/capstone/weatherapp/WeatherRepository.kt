package com.capstone.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val CITIES = "4772354,5368361,5128581,4887398,4699066,5809844,5856195,5419384,5391959,4684888,5746545,4990729,4335045"
private const val APIKEY = "6849ae760f417fb8188f4bb7fd0d92fc"
private const val UNITS = "imperial"

class WeatherRepository(private val db: WeatherDatabase) {
    private var _cityListData = MutableLiveData<State<List<City>>>()
    val cityListData: LiveData<State<List<City>>>
        get() = _cityListData
    private var _singleCityData = MutableLiveData<State<SingleCityResponse>>()
    val singleCityData: LiveData<State<SingleCityResponse>>
        get() = _singleCityData

    private fun getNetworkCityList(): List<City> {
        val cacheCityList = db.cityDao().getCityWeather()
        val networkCityList = CityCache.convertToWeatherApiResponse(cacheCityList)

        return networkCityList.list
    }

    suspend fun refreshCityList() {
        withContext(Dispatchers.IO) {
            _cityListData.postValue(State.Loading)
            try {
                val cityListResponse = WeatherApiClient.retrofitService.getCities(CITIES, APIKEY, UNITS)
                val response = cityListResponse.body()!!
                db.cityDao().insert(response.convertToCityCacheList())
            } catch (e: Exception) {
                _cityListData.postValue(State.Error(e.toString()))
            }
            try {
                _cityListData.postValue(State.Success(getNetworkCityList()))
            } catch (e: Exception) {
                _cityListData.postValue(State.Error(e.toString()))
            }
        }
    }

    suspend fun refreshSingleCity(cityId: String) {
        withContext(Dispatchers.IO) {
            _singleCityData.postValue(State.Loading)
            try {
                val singleCity = WeatherApiClient.retrofitService.getCity(cityId, APIKEY, UNITS)
                val response = singleCity.body()!!
                db.singleCityDao().insert(response.convertToSingleCityCache())
            } catch (e: Exception) {
                _singleCityData.postValue(State.Error(e.toString()))
            }
            try {
                val singleCityCache = db.singleCityDao().getSingleCityById(cityId)
                _singleCityData.postValue(State.Success(singleCityCache.convertToSingleCityResponse()))
            } catch (e: Exception) {
                _singleCityData.postValue(State.Error(e.toString()))
            }
        }

    }
}