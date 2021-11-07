package com.example.buggyweather.home.presenter

import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.helper.getOrAwaitValue
import com.example.buggyweather.core.network.KnownExceptionMessage
import com.example.buggyweather.core.network.exception.BadRequestException
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomeViewModelTest : BaseTest() {

	@Mock
	private lateinit var useCase: UseCase<Pair<String, MeasuringUnits>, CurrentWeather>

	private lateinit var viewModel: HomeViewModel

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		viewModel = HomeViewModel(useCase)
	}

	@ExperimentalCoroutinesApi
	@Test
	fun getWeather_test_success() = runBlockingTest {
		val request = Pair("Seoul", MeasuringUnits.METRIC)
		whenever(useCase.execute(request)) doReturn CurrentWeather()

		viewModel.getCurrentWeather(request)

		viewModel.currentWeather.getOrAwaitValue().let {
			Assert.assertEquals(CurrentWeather(), CurrentWeather())
		}
		viewModel.errorMessage.getOrAwaitValue().let {
			Assert.assertEquals(null, it)
		}
		viewModel.isLoading.getOrAwaitValue().let {
			Assert.assertFalse(it)
		}
		verify(useCase).execute(request)
	}

	@ExperimentalCoroutinesApi
	@Test(expected = Exception::class)
	fun getWeather_test_Fail() = runBlockingTest {
		val request = Pair("Seoul", MeasuringUnits.METRIC)
		val errorMessage = KnownExceptionMessage.CITY_NAME_NOT_FOUND
		whenever(useCase.execute(request)) doThrow BadRequestException(errorMessage)

		viewModel.getCurrentWeather(request)
	}
}