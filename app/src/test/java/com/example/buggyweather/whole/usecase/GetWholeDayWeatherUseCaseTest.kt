package com.example.buggyweather.whole.usecase

import com.example.buggyweather.core.base.FlowUseCase
import com.example.buggyweather.core.model.Coordinate
import com.example.buggyweather.core.model.ForecastRequest
import com.example.buggyweather.core.model.MeasuringUnits
import com.example.buggyweather.core.model.WholeDayWeather
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.core.network.exception.RemoteException
import com.example.buggyweather.whole.repository.WholeDayWeatherDataSource
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetWholeDayWeatherUseCaseTest : BaseTest() {

	@Mock
	private lateinit var dataSource: WholeDayWeatherDataSource

	private lateinit var useCase: FlowUseCase<ForecastRequest, WholeDayWeather>

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		useCase = GetWholeDayWeatherUseCase(
			repository = dataSource,
			ioDispatcher = testCoroutineDispatcher
		)
	}

	@Test
	fun `Execute Success and Return WholeDayWeather`() = coroutineScopeTest {
		val request = ForecastRequest(Coordinate(), MeasuringUnits.IMPERIAL)
		val forecast = WholeDayWeather()
		whenever(
			dataSource.getWholeDayWeather(request)
		) doReturn flowOf(forecast)

		val result = useCase.execute(request)
			.single()

		Assert.assertEquals(forecast, result)
		verify(dataSource).getWholeDayWeather(request)
	}

	@Test(expected = RemoteException::class)
	fun `Execute Fail with RemoteException`() = coroutineScopeTest {
		val request = ForecastRequest(Coordinate(), MeasuringUnits.IMPERIAL)
		val exception = RemoteException()
		whenever(
			dataSource.getWholeDayWeather(request)
		) doReturn flow { throw exception }

		useCase.execute(request)
			.single()

		verify(dataSource).getWholeDayWeather(request)
	}
}