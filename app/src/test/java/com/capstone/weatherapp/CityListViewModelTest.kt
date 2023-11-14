package com.capstone.weatherapp

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class CityListViewModelUnitTest {
    private val repo = mockk<WeatherRepository>(relaxed = true)
    private lateinit var viewModel: CityListViewModel

    class MainDispatcherRule(
        val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
    ) : TestWatcher() {
        override fun starting(description: Description) {
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description) {
            Dispatchers.resetMain()
        }
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = CityListViewModelFactory(repo).create(CityListViewModel::class.java)
    }

    @Test
    fun `ViewModel factory successfully creates ViewModel`() = runTest {
        val result  = CityListViewModelFactory(repo).create(CityListViewModel::class.java)

        assertEquals(result::class.java, CityListViewModel::class.java)
    }

    @Test
    fun `viewModel successfully refreshes cityList from the repo`() = runTest {
        val expected = CityListState.Success(listOf(TestObjects.testCity))
        coEvery { repo.refreshCityList() } answers {
            every { repo.cityListData.value } returns expected
        }
        viewModel.getCityListFromRepo()

        assertEquals(expected, viewModel.cityList.value)
    }

    @Test
    fun `viewModel unsuccessfully refreshes cityList from the repo`() = runTest {
        val expected = CityListState.Error(constants.EMPTY_STRING)
        coEvery { repo.refreshCityList() } answers {
            every { repo.cityListData.value } returns expected
        }
        viewModel.getCityListFromRepo()

        assertEquals(expected, viewModel.cityList.value)
    }
}

