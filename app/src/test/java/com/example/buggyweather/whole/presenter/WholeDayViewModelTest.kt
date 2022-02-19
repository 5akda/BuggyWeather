package com.example.buggyweather.whole.presenter

import com.example.buggyweather.core.base.FlowUseCase
import com.example.buggyweather.core.model.Coordinate
import com.example.buggyweather.core.model.ForecastRequest
import com.example.buggyweather.core.model.MeasuringUnits
import com.example.buggyweather.core.model.WholeDayWeather
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.helper.getOrAwaitValue
import com.example.buggyweather.core.network.exception.RemoteException
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class WholeDayViewModelTest : BaseTest() {

	@Mock
	private lateinit var useCase: FlowUseCase<ForecastRequest, WholeDayWeather>

	private lateinit var viewModel: WholeDayViewModel

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		viewModel = WholeDayViewModel(useCase)
	}

	@Test
	fun `Get Forecast Success`() = viewModelScopeTest {
		val coord = Coordinate()
		val units = MeasuringUnits.METRIC
		val forecast = WholeDayWeather()
		whenever(
			useCase.execute(ForecastRequest(coord, units))
		) doReturn flowOf(forecast)

		viewModel.getWholeDayForecast(coord, units)

		viewModel.wholeDayForecast.getOrAwaitValue().let {
			Assert.assertEquals(forecast, it)
		}
		viewModel.errorMessage.getOrAwaitValue().let {
			Assert.assertNull(it)
		}
		viewModel.isLoading.getOrAwaitValue().let {
			Assert.assertFalse(it)
		}
		verify(useCase).execute(ForecastRequest(coord, units))
	}

	@Test
	fun `Get Forecast Fail with RemoteException`() = viewModelScopeTest  {
		val coord = Coordinate()
		val units = MeasuringUnits.METRIC
		val exception = RemoteException()
		whenever(
			useCase.execute(ForecastRequest(coord, units))
		) doReturn flow { throw exception }

		viewModel.getWholeDayForecast(coord, units)

		viewModel.errorMessage.getOrAwaitValue().let {
			Assert.assertEquals("Server error: unknown", it)
		}
		viewModel.isLoading.getOrAwaitValue().let {
			Assert.assertFalse(it)
		}
	}
}