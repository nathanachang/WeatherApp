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

