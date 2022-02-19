package com.example.buggyweather.home.usecase

import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.core.domain.CurrentWeather
import com.example.buggyweather.core.domain.MeasuringUnits
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.home.repository.CurrentWeatherDataSource
import com.example.buggyweather.core.network.KnownExceptionMessage
import com.example.buggyweather.core.network.exception.NotFoundException
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

class GetCurrentWeatherUseCaseTest : BaseTest() {

	@Mock
	private lateinit var dataSource: CurrentWeatherDataSource

	private lateinit var useCase: UseCase<Pair<String, MeasuringUnits>, CurrentWeather>

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		useCase = GetCurrentWeatherUseCase(dataSource)
	}

	@ExperimentalCoroutinesApi
	@Test
	fun getWeather_test_success() = runBlockingTest {
		whenever(dataSource
				.getCurrentWeather("Mountain View", MeasuringUnits.IMPERIAL.name)
		) doReturn CurrentWeather()

		val result = useCase.execute(Pair("Mountain View", MeasuringUnits.IMPERIAL))

		Assert.assertEquals(CurrentWeather(), result)
		verify(dataSource).getCurrentWeather("Mountain View", MeasuringUnits.IMPERIAL.name)
	}

	@ExperimentalCoroutinesApi
	@Test(expected = Exception::class)
	fun getWeather_test_Fail() = runBlockingTest {
		val exception = NotFoundException(KnownExceptionMessage.CITY_NAME_NOT_FOUND)
		whenever(dataSource
				.getCurrentWeather("Mountain View", MeasuringUnits.IMPERIAL.name)
		) doThrow exception

		useCase.execute(Pair("Mountain View", MeasuringUnits.IMPERIAL))

		verify(dataSource).getCurrentWeather("Mountain View", MeasuringUnits.IMPERIAL.name)
	}
}