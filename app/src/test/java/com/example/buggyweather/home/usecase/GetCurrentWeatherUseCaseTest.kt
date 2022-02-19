package com.example.buggyweather.home.usecase

import com.example.buggyweather.core.base.FlowUseCase
import com.example.buggyweather.core.model.CurrentWeather
import com.example.buggyweather.core.model.MeasuringUnits
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.home.repository.CurrentWeatherDataSource
import com.example.buggyweather.core.network.KnownExceptionMessage
import com.example.buggyweather.core.network.exception.NotFoundException
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
class GetCurrentWeatherUseCaseTest : BaseTest() {

	@Mock
	private lateinit var dataSource: CurrentWeatherDataSource

	private lateinit var useCase: FlowUseCase<Pair<String, MeasuringUnits>, CurrentWeather>

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		useCase = GetCurrentWeatherUseCase(
			repository = dataSource,
			ioDispatcher = testCoroutineDispatcher
		)
	}

	@Test
	fun `Execute Success and Return CurrentWeather`() = coroutineScopeTest {
		val currentWeather = CurrentWeather()
		whenever(
			dataSource.getCurrentWeather("Mountain View", MeasuringUnits.IMPERIAL.name)
		) doReturn flowOf(currentWeather)

		val result = useCase.execute(Pair("Mountain View", MeasuringUnits.IMPERIAL))
			.single()

		Assert.assertEquals(currentWeather, result)
		verify(dataSource).getCurrentWeather("Mountain View", MeasuringUnits.IMPERIAL.name)
	}


	@Test(expected = NotFoundException::class)
	fun `Execute Fail with NotFoundException`() = coroutineScopeTest {
		val exception = NotFoundException(KnownExceptionMessage.CITY_NAME_NOT_FOUND)
		whenever(
			dataSource.getCurrentWeather("Mountain View", MeasuringUnits.IMPERIAL.name)
		) doReturn flow { throw exception }

		useCase.execute(Pair("Mountain View", MeasuringUnits.IMPERIAL))
			.single()

		verify(dataSource).getCurrentWeather("Mountain View", MeasuringUnits.IMPERIAL.name)
	}
}