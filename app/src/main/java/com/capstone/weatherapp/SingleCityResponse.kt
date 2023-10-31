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
    fun convertToSingleCityCache(singleCity: SingleCityResponse) : SingleCityCache {
        return SingleCityCache(
            id = singleCity.id,
            name = singleCity.name,
            coord = singleCity.coord,
            weather = singleCity.weather,
            base = singleCity.base,
            main = singleCity.main,
            visibility = singleCity.visibility,
            wind = singleCity.wind,
            clouds = singleCity.clouds,
            dt = singleCity.dt,
            sys = singleCity.sys,
            timezone = singleCity.timezone,
            cod = singleCity.cod
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