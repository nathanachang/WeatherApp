package com.capstone.weatherapp

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
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
    fun test_CityDao_Insert() = runBlocking {
        db.cityDao().insert(listOf(TestObjects.testCityCache))
        assertTrue(db.cityDao().cityExists(TestObjects.testCityCache.id.toString()))
    }

    @Test
    fun test_CityDao_Delete() = runBlocking {
        db.cityDao().insert(listOf(TestObjects.testCityCache))
        db.cityDao().delete(listOf(TestObjects.testCityCache))
        assertFalse(db.cityDao().cityExists(TestObjects.testCityCache.id.toString()))
    }

    @Test
    fun test_SingleCityDao_Insert() = runBlocking {
        db.singleCityDao().insert(TestObjects.testSingleCityCache)
        assertTrue(db.singleCityDao().singleCityExists(TestObjects.testSingleCityCache.id.toString()))
    }

    @Test
    fun test_SingleCityDao_Delete() = runBlocking {
        db.singleCityDao().insert(TestObjects.testSingleCityCache)
        db.singleCityDao().delete(TestObjects.testSingleCityCache)
        assertFalse(db.singleCityDao().singleCityExists(TestObjects.testSingleCityCache.id.toString()))
    }

    @Test
    fun test_RefreshCityList_Success() = runBlocking {
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
    fun test_RefreshSingleCity_Success() = runBlocking {
        val expected = SingleCityState.Success(TestObjects.testSingleCityResponse)
        val result = Response.success(TestObjects.testSingleCityResponse)
        val cityId = TestObjects.testSingleCityResponse.id.toString()
        coEvery { WeatherApiClient.retrofitService.getCity(cityId, constants.APIKEY, constants.UNITS) } returns result

        repo.refreshSingleCity(cityId)

        assertEquals(expected, repo.singleCityData)
    }
}