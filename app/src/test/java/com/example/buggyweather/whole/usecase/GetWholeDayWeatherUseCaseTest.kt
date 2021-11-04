package com.example.buggyweather.whole.usecase

import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.Coordinate
import com.example.buggyweather.domain.ForecastRequest
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.domain.WholeDayWeather
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.network.KnownExceptionMessage
import com.example.buggyweather.network.exception.NotFoundException
import com.example.buggyweather.whole.repository.WholeDayWeatherDataSource
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

class GetWholeDayWeatherUseCaseTest : BaseTest() {

	@Mock
	private lateinit var dataSource: WholeDayWeatherDataSource

	private lateinit var useCase: UseCase<ForecastRequest, WholeDayWeather>

	@Before
	fun setUp() {
		MockitoAnnotations.initMocks(this)
		useCase = GetWholeDayWeatherUseCase(dataSource)
	}

	@ExperimentalCoroutinesApi
	@Test
	fun getForecasts_test_success() = runBlockingTest {
		val request = ForecastRequest(Coordinate(), MeasuringUnits.IMPERIAL)
		whenever(dataSource.getWholeDayWeather(request)) doReturn WholeDayWeather()

		val result = useCase.execute(request)

		Assert.assertEquals(WholeDayWeather(), result)
		verify(dataSource).getWholeDayWeather(request)
	}

	@ExperimentalCoroutinesApi
	@Test(expected = Exception::class)
	fun getForecasts_test_fail() = runBlockingTest {
		val request = ForecastRequest(Coordinate(), MeasuringUnits.IMPERIAL)
		val errorMessage = KnownExceptionMessage.CITY_NAME_NOT_FOUND
		whenever(dataSource.getWholeDayWeather(request)) doThrow NotFoundException(errorMessage)

		useCase.execute(request)
	}
}