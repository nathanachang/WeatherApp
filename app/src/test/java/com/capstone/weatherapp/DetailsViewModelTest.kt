package com.capstone.weatherapp

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {
    private val repo = mockk<WeatherRepository>(relaxed = true)
    private lateinit var viewModel: DetailsViewModel

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
        viewModel = DetailsViewModelFactory(repo).create(DetailsViewModel::class.java)
    }

    @Test
    fun `ViewModel factory successfully creates ViewModel`() = runTest {
        val result  = DetailsViewModelFactory(repo).create(DetailsViewModel::class.java)

        Assert.assertEquals(result::class.java, DetailsViewModel::class.java)
    }

    @Test
    fun `viewModel successfully refreshes singleCity from the repo`() = runTest {
        val expected = SingleCityState.Success(TestObjects.testSingleCityResponse)
        val cityId = TestObjects.testSingleCityResponse.id.toString()
        coEvery { repo.refreshSingleCity(cityId) } answers {
            every { repo.singleCityData.value } returns expected
        }
        viewModel.getSingleCityFromRepo(cityId)

        Assert.assertEquals(expected, viewModel.city.value)
    }

    @Test
    fun `viewModel unsuccessfully refreshes singleCity from the repo`() = runTest {
        val expected = SingleCityState.Error(constants.EMPTY_STRING)
        coEvery { repo.refreshSingleCity(constants.EMPTY_STRING) } answers {
            every { repo.singleCityData.value } returns expected
        }
        viewModel.getSingleCityFromRepo(constants.EMPTY_STRING)

        Assert.assertEquals(expected, viewModel.city.value)
    }

    @Test
    fun `ViewModel successfully converts time with timezone offset`() = runTest {
        val result = "6:50 AM"
        val expected = viewModel.convertTime(1699962610, -18000)

        Assert.assertEquals(expected, result)
    }
}