package com.example.buggyweather.whole.presenter

import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.core.domain.Coordinate
import com.example.buggyweather.core.domain.ForecastRequest
import com.example.buggyweather.core.domain.MeasuringUnits
import com.example.buggyweather.core.domain.WholeDayWeather
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

class WholeDayViewModelTest : BaseTest() {

	@Mock
	private lateinit var useCase: UseCase<ForecastRequest, WholeDayWeather>

	private lateinit var viewModel: WholeDayViewModel

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		viewModel = WholeDayViewModel(useCase)
	}

	@ExperimentalCoroutinesApi
	@Test
	fun getForecasts_test_success() = runBlockingTest {
		val coord = Coordinate()
		val units = MeasuringUnits.METRIC
		whenever(useCase.execute(ForecastRequest(coord, units))) doReturn WholeDayWeather()

		viewModel.getWholeDayForecast(coord, units)

		viewModel.wholeDayForecast.getOrAwaitValue().let {
			Assert.assertEquals(WholeDayWeather(), it)
		}
		viewModel.errorMessage.getOrAwaitValue().let {
			Assert.assertEquals(null, it)
		}
		viewModel.isLoading.getOrAwaitValue().let {
			Assert.assertFalse(it)
		}
		verify(useCase).execute(ForecastRequest(coord, units))
	}

	@ExperimentalCoroutinesApi
	@Test(expected = Exception::class)
	fun getForecasts_test_fail() = runBlockingTest {
		val coord = Coordinate()
		val units = MeasuringUnits.METRIC
		val errorMessage = KnownExceptionMessage.CITY_NAME_IS_BLANK
		whenever(useCase.execute(ForecastRequest(coord, units))) doThrow BadRequestException(errorMessage)

		viewModel.getWholeDayForecast(coord, units)
	}
}