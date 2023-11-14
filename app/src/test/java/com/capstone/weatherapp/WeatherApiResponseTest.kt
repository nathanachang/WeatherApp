package com.capstone.weatherapp

import org.junit.Assert
import org.junit.Test

class WeatherApiResponseTest {
    @Test
    fun `CityCache object successfully converts to a WeatherApiResponse object`() {
        val result = WeatherApiResponse(
            cnt = 1,
            listOf(City(
                id = 4772354,
                name = "McLean",
                coord = Coord(constants.DOUBLE_ZERO, constants.DOUBLE_ZERO),
                weather = emptyList(),
                main = Main(
                    temp=54.7,
                    feels_like=54.7,
                    temp_min=51.94,
                    temp_max=58.08,
                    pressure=constants.DOUBLE_ZERO,
                    humidity=55.0
                ),
                visibility = constants.INT_ZERO,
                wind = Wind(constants.DOUBLE_ZERO, constants.INT_ZERO),
                clouds = Clouds(constants.INT_ZERO),
                dt = constants.INT_ZERO,
                sys = Sys(
                    country="US",
                    timezone=constants.INT_ZERO,
                    sunrise=constants.INT_ZERO,
                    sunset=constants.INT_ZERO
                )
            ))
        )
        Assert.assertEquals(
            result,
            CityCache.convertToWeatherApiResponse(listOf(TestObjects.testCityCache))
        )
    }

    @Test
    fun `WeatherApiResponse object successfully converts to a CityCache object`() {
        val result = WeatherApiResponse(
            cnt = 1,
            list = listOf(TestObjects.testCity)
        )
        Assert.assertEquals(result.convertToCityCacheList()[0], TestObjects.testCityCache)
    }
}