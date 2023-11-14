package com.capstone.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val CITIES = "4772354,5368361,5128581,4887398,4699066,5809844,5856195,5419384,5391959,4684888,5746545,4990729,4335045"
private const val APIKEY = "6849ae760f417fb8188f4bb7fd0d92fc"
private const val UNITS = "imperial"

class WeatherRepository(private val db: WeatherDatabase) {
    private var _cityListData = MutableLiveData<CityListState>()
    val cityListData: LiveData<CityListState>
        get() = _cityListData
    private var _singleCityData = MutableLiveData<SingleCityState>()
    val singleCityData: LiveData<SingleCityState>
        get() = _singleCityData

    private fun getNetworkCityList(): List<City> {
        val cacheCityList = db.cityDao().getCityWeather()
        val networkCityList = CityCache.convertToWeatherApiResponse(cacheCityList)

        return networkCityList.list
    }

    suspend fun refreshCityList() {
        withContext(Dispatchers.IO) {
            _cityListData.postValue(CityListState.Loading)
            try {
                val cityListResponse = WeatherApiClient.retrofitService.getCities(CITIES, APIKEY, UNITS)
                val response = cityListResponse.body()!!
                db.cityDao().insert(response.convertToCityCacheList())
            } catch (e: Exception) {
                _cityListData.postValue(CityListState.Error(e.toString()))
            }
            try {
                _cityListData.postValue(CityListState.Success(getNetworkCityList()))
            } catch (e: Exception) {
                _cityListData.postValue(CityListState.Error(e.toString()))
            }
        }
    }

    suspend fun refreshSingleCity(cityId: String) {
        withContext(Dispatchers.IO) {
            _singleCityData.postValue(SingleCityState.Loading)
            try {
                val singleCity = WeatherApiClient.retrofitService.getCity(cityId, APIKEY, UNITS)
                val response = singleCity.body()!!
                db.singleCityDao().insert(response.convertToSingleCityCache())
            } catch (e: Exception) {
                _singleCityData.postValue(SingleCityState.Error(e.toString()))
            }
            try {
                val singleCityCache = db.singleCityDao().getSingleCityById(cityId)
                _singleCityData.postValue(SingleCityState.Success(singleCityCache.convertToSingleCityResponse()))
            } catch (e: Exception) {
                _singleCityData.postValue(SingleCityState.Error(e.toString()))
            }
        }

    }
}