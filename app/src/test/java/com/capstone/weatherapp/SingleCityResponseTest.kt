package com.capstone.weatherapp

import org.junit.Assert
import org.junit.Test

class SingleCityResponseTest {
    @Test
    fun `SingleCityCache object successfully converts to a SingleCityResponse object`() {
        val result = SingleCityResponse(
            id = 4772354,
            name = "McLean",
            coord = Coord(constants.DOUBLE_ZERO, constants.DOUBLE_ZERO),
            weather = listOf(Weather(
                id=constants.INT_ZERO,
                main="Clouds",
                description="Clouds",
                icon="02d"
            )),
            base = constants.EMPTY_STRING,
            main = Main(
                temp=54.7,
                feels_like=54.7,
                temp_min=51.94,
                temp_max=58.08,
                pressure=1022.0,
                humidity=55.0
            ),
            visibility = constants.INT_ZERO,
            wind = Wind(13.8, constants.INT_ZERO),
            clouds = Clouds(constants.INT_ZERO),
            dt = constants.INT_ZERO,
            sys = SysDetails(
                type=constants.INT_ZERO,
                id=4772354,
                country="US",
                sunrise=1699876143,
                sunset=1699912624
            ),
            timezone = -18000,
            cod = constants.INT_ZERO
        )
        Assert.assertEquals(result, TestObjects.testSingleCityCache.convertToSingleCityResponse())
    }


    @Test
    fun `SingleCityResponse object successfully converts to a SingleCityCache object`() {
        Assert.assertEquals(
            TestObjects.testSingleCityResponse.convertToSingleCityCache(),
            TestObjects.testSingleCityCache
        )
    }
}