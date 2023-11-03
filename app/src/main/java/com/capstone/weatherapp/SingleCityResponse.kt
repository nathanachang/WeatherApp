package com.capstone.weatherapp

data class SingleCityResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: SysDetails,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
) {
    fun convertToSingleCityCache() : SingleCityCache {
        return SingleCityCache(
            id = id,
            name = name,
            desc = weather[0].main,
            icon = weather[0].icon,
            country = sys.country,
            temp = main.temp,
            temp_min = main.temp_min,
            temp_max = main.temp_max,
            humidity = main.humidity,
            windspeed = wind.speed,
            pressure = main.pressure,
            timezone = timezone,
            sunrise = sys.sunrise,
            sunset = sys.sunset
        )
    }
}
data class SysDetails(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)