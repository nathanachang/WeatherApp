package com.capstone.weatherapp

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CityListViewModelUnitTest {
    private val repo = mockk<WeatherRepository>(relaxed = true)
    private lateinit var viewModel: CityListViewModel

    @Before
    fun setUp() {
        viewModel = CityListViewModelFactory(repo).create(CityListViewModel::class.java)
    }

    @Test
    fun `viewModel successfully refreshes cityList from the repo`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val expected = CityListState.Success(listOf(TestObjects.testCity))
        coEvery { repo.refreshCityList() } answers {
            every { repo.cityListData.value } returns expected
        }
        viewModel.getCityListFromRepo()

        assertEquals(expected, viewModel.cityList.value)
    }

    @Test
    fun `viewModel unsuccessfully refreshes cityList from the repo`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val expected = CityListState.Error(constants.EMPTY_STRING)
        coEvery { repo.refreshCityList() } answers {
            every { repo.cityListData.value } returns expected
        }
        viewModel.getCityListFromRepo()

        assertEquals(expected, viewModel.cityList.value)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelUnitTest {
    private val repo = mockk<WeatherRepository>(relaxed = true)
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setUp() {
        viewModel = DetailsViewModelFactory(repo).create(DetailsViewModel::class.java)
    }

    @Test
    fun `viewModel successfully refreshes singleCity from the repo`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val expected = SingleCityState.Success(TestObjects.testSingleCityResponse)
        val cityId = TestObjects.testSingleCityResponse.id.toString()
        coEvery { repo.refreshSingleCity(cityId) } answers {
            every { repo.singleCityData.value } returns expected
        }
        viewModel.getSingleCityFromRepo(cityId)

        assertEquals(expected, viewModel.city.value)
    }

    @Test
    fun `viewModel unsuccessfully refreshes singleCity from the repo`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher(testScheduler))
        val expected = SingleCityState.Error(constants.EMPTY_STRING)
        coEvery { repo.refreshSingleCity(constants.EMPTY_STRING) } answers {
            every { repo.singleCityData.value } returns expected
        }
        viewModel.getSingleCityFromRepo(constants.EMPTY_STRING)

        assertEquals(expected, viewModel.city.value)
    }
}
