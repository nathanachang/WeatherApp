package com.capstone.weatherapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityCache(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "city") val name: String,
    val country: String,
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double,
    val humidity: Double
) {
    fun convertToWeatherApiResponse(cityCacheList: List<CityCache>) : WeatherApiResponse {
        val cityList = mutableListOf<City>()
        for (cityCache: CityCache in cityCacheList) {
            val city = City(
                id = cityCache.id,
                name = cityCache.name,
                coord = Coord(constants.DOUBLE_ZERO, constants.DOUBLE_ZERO),
                weather = emptyList(),
                main = Main(temp=temp, feels_like=temp, temp_min=temp_min, temp_max=temp_max, pressure=constants.DOUBLE_ZERO, humidity=humidity),
                visibility = constants.INT_ZERO,
                wind = Wind(constants.DOUBLE_ZERO, constants.INT_ZERO),
                clouds = Clouds(constants.INT_ZERO),
                dt = constants.INT_ZERO,
                sys = Sys(country=country, timezone=0, sunrise=0, sunset=0)
            )
            cityList.add(city)
        }

        return WeatherApiResponse(cityList.size, cityList)
    }
}

@Entity
data class SingleCityCache(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "city") val name: String,
    val country: String,
    val temp: Double,
    val base: String,
    val temp_min: Double,
    val temp_max: Double,
    val humidity: Double,
    val windspeed: Double,
    val pressure: Double,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
) {
    fun convertToSingleCityResponse() : SingleCityResponse {
        return SingleCityResponse(
            id = id,
            name = name,
            coord = Coord(constants.DOUBLE_ZERO, constants.DOUBLE_ZERO),
            weather = emptyList(),
            base = base,
            main = Main(temp=temp, feels_like=temp, temp_min=temp_min, temp_max=temp_max, pressure=pressure, humidity=humidity),
            visibility = constants.INT_ZERO,
            wind = Wind(windspeed, constants.INT_ZERO),
            clouds = Clouds(constants.INT_ZERO),
            dt = constants.INT_ZERO,
            sys = SysDetails(type=constants.INT_ZERO, id=id, country=country, sunrise=sunrise, sunset=sunset),
            timezone = timezone,
            cod = constants.INT_ZERO
        )
    }
}