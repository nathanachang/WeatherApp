package com.capstone.weatherapp

class TestObjects {
    companion object {
        val testCity = City(
            coord = Coord(lon=-77.1775, lat=38.9343),
            weather = listOf(Weather(
                id=801,
                main="Clouds",
                description="few clouds",
                icon="02d"
            )),
            main = Main(
                temp=54.7,
                feels_like=52.45,
                temp_min=51.94,
                temp_max=58.08,
                pressure=1022.0,
                humidity=55.0
            ),
            visibility = 10000,
            wind = Wind(
                speed=13.8,
                deg=170
            ),
            clouds = Clouds(
                all=20
            ),
            dt = 1699904562,
            sys = Sys(
                country="US",
                timezone=-18000,
                sunrise=1699876143,
                sunset=1699912624
            ),
            id = 4772354,
            name = "McLean"
        )
        val testSingleCityResponse = SingleCityResponse(
            coord = Coord(lon=-77.1775, lat=38.9343),
            weather = listOf(Weather(
                id=801,
                main="Clouds",
                description="few clouds",
                icon="02d"
            )),
            base="stations",
            main = Main(
                temp=54.7,
                feels_like=52.45,
                temp_min=51.94,
                temp_max=58.08,
                pressure=1022.0,
                humidity=55.0
            ),
            visibility = 10000,
            wind = Wind(
                speed=13.8,
                deg=170
            ),
            clouds = Clouds(
                all=20
            ),
            dt = 1699904562,
            sys = SysDetails(
                type=2,
                country="US",
                id=2011386,
                sunrise=1699876143,
                sunset=1699912624
            ),
            timezone = -18000,
            id = 4772354,
            name = "McLean",
            cod = 200
        )
        val testCityCache = CityCache(
            id = 4772354,
            name = "McLean",
            country = "US",
            temp = 54.7,
            temp_min = 51.94,
            temp_max = 58.08,
            humidity = 55.0
        )
        val testSingleCityCache = SingleCityCache(
            id = 4772354,
            name = "McLean",
            country = "US",
            temp = 54.7,
            desc = "Clouds",
            icon = "02d",
            temp_min = 51.94,
            temp_max = 58.08,
            humidity = 55.0,
            windspeed = 13.8,
            pressure = 1022.0,
            timezone = -18000,
            sunrise = 1699876143,
            sunset = 1699912624
        )
    }
}