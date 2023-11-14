package com.capstone.weatherapp

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class WeatherRepositoryUnitTest {
    val db = mockk<WeatherDatabase>(relaxed = true)
    lateinit var repo: WeatherRepository

    @Before
    fun setup() {
        mockkObject(WeatherApiClient)
        repo = WeatherRepository(db)
        coEvery { db.cityDao().insert(any()) } just Runs
        coEvery { db.singleCityDao().insert(any()) } just Runs
    }

    @Test
    fun `City DAO successfully inserts city cache into db`() = runBlocking {
        db.cityDao().insert(listOf(TestObjects.testCityCache))
        assertTrue(db.cityDao().cityExists(TestObjects.testCityCache.id.toString()))
    }

    @Test
    fun `City DAO successfully deletes city cache from db`() = runBlocking {
        db.cityDao().insert(listOf(TestObjects.testCityCache))
        db.cityDao().delete(listOf(TestObjects.testCityCache))
        assertFalse(db.cityDao().cityExists(TestObjects.testCityCache.id.toString()))
    }

    @Test
    fun `SingleCity DAO successfully inserts single city cache into db`() = runBlocking {
        db.singleCityDao().insert(TestObjects.testSingleCityCache)
        assertTrue(db.singleCityDao().singleCityExists(TestObjects.testSingleCityCache.id.toString()))
    }

    @Test
    fun `SingleCity DAO successfully deletes single city cache from db`() = runBlocking {
        db.singleCityDao().insert(TestObjects.testSingleCityCache)
        db.singleCityDao().delete(TestObjects.testSingleCityCache)
        assertFalse(db.singleCityDao().singleCityExists(TestObjects.testSingleCityCache.id.toString()))
    }

    @Test
    fun `Repo successfully refreshes city list from network`() = runBlocking {
        val expected = CityListState.Success(listOf(TestObjects.testCity))
        val result = Response.success(WeatherApiResponse(
            cnt = 1,
            list = listOf(TestObjects.testCity)
        ))
        coEvery { WeatherApiClient.retrofitService.getCities(constants.CITIES, constants.APIKEY, constants.UNITS) } returns result

        repo.refreshCityList()

        assertEquals(expected, repo.cityListData)
    }

    @Test
    fun `Repo unsuccessfully refreshes city list from network and cache is empty`() = runBlocking {
        val expected = CityListState.Error(constants.EMPTY_STRING)
        val result: Response<WeatherApiResponse> = Response.error(
            400,
            ResponseBody.create(
                MediaType.parse("application/json"),
                constants.EMPTY_STRING
            )
        )
        coEvery { WeatherApiClient.retrofitService.getCities(constants.CITIES, constants.APIKEY, constants.UNITS) } returns result

        repo.refreshCityList()

        assertEquals(expected, repo.cityListData)
    }

    @Test
    fun `Repo unsuccessfully refreshes city list from network and cache has data`() = runBlocking {
        val expected = CityListState.Success(listOf(TestObjects.testCity))
        val result: Response<WeatherApiResponse> = Response.error(
            400,
            ResponseBody.create(
                MediaType.parse("application/json"),
                constants.EMPTY_STRING
            )
        )
        coEvery { db.cityDao().getCityWeather() } returns listOf(TestObjects.testCityCache)
        coEvery { WeatherApiClient.retrofitService.getCities(constants.CITIES, constants.APIKEY, constants.UNITS) } returns result

        repo.refreshCityList()

        assertEquals(expected, repo.cityListData)
    }

    @Test
    fun `Repo successfully refreshes single city from network`() = runBlocking {
        val expected = SingleCityState.Success(TestObjects.testSingleCityResponse)
        val result = Response.success(TestObjects.testSingleCityResponse)
        val cityId = TestObjects.testSingleCityResponse.id.toString()
        coEvery { WeatherApiClient.retrofitService.getCity(cityId, constants.APIKEY, constants.UNITS) } returns result

        repo.refreshSingleCity(cityId)

        assertEquals(expected, repo.singleCityData)
    }

    @Test
    fun `Repo unsuccessfully refreshes single city from network and cache is empty`() = runBlocking {
        val expected = SingleCityState.Error(constants.EMPTY_STRING)
        val result: Response<SingleCityResponse> = Response.error(
            400,
            ResponseBody.create(
                MediaType.parse("application/json"),
                constants.EMPTY_STRING
            )
        )
        val cityId = TestObjects.testSingleCityResponse.id.toString()
        coEvery { WeatherApiClient.retrofitService.getCity(cityId, constants.APIKEY, constants.UNITS) } returns result

        repo.refreshSingleCity(cityId)

        assertEquals(expected, repo.singleCityData)
    }

    @Test
    fun `Repo unsuccessfully refreshes single city from network and cache has data`() = runBlocking {
        val expected = SingleCityState.Success(TestObjects.testSingleCityResponse)
        val result: Response<SingleCityResponse> = Response.error(
            400,
            ResponseBody.create(
                MediaType.parse("application/json"),
                constants.EMPTY_STRING
            )
        )
        val cityId = TestObjects.testSingleCityResponse.id.toString()
        coEvery { db.singleCityDao().getSingleCityById(cityId) } returns TestObjects.testSingleCityCache
        coEvery { WeatherApiClient.retrofitService.getCity(cityId, constants.APIKEY, constants.UNITS) } returns result

        repo.refreshSingleCity(cityId)

        assertEquals(expected, repo.singleCityData)
    }
}