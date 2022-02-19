package com.example.buggyweather.home.presenter

import com.example.buggyweather.core.base.FlowUseCase
import com.example.buggyweather.core.model.CurrentWeather
import com.example.buggyweather.core.model.MeasuringUnits
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.helper.getOrAwaitValue
import com.example.buggyweather.core.network.KnownExceptionMessage
import com.example.buggyweather.core.network.exception.BadRequestException
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
class HomeViewModelTest : BaseTest() {

	@Mock
	private lateinit var useCase: FlowUseCase<Pair<String, MeasuringUnits>, CurrentWeather>

	private lateinit var viewModel: HomeViewModel

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		viewModel = HomeViewModel(useCase)
	}

	@Test
	fun `Get CurrentWeather Success`() = viewModelScopeTest {
		val request = Pair("Seoul", MeasuringUnits.METRIC)
		val result = CurrentWeather()
		whenever(
			useCase.execute(request)
		) doReturn flowOf(result)

		viewModel.getCurrentWeather(request)

		viewModel.currentWeather.getOrAwaitValue().let {
			Assert.assertEquals(result, it)
		}
		viewModel.errorMessage.getOrAwaitValue().let {
			Assert.assertNull(it)
		}
		viewModel.isLoading.getOrAwaitValue().let {
			Assert.assertFalse(it)
		}
		verify(useCase).execute(request)
	}

	@Test
	fun `Get CurrentWeather Fail with BadRequestException`() = viewModelScopeTest {
		val request = Pair("Seoul", MeasuringUnits.METRIC)
		val exception = BadRequestException(KnownExceptionMessage.CITY_NAME_IS_BLANK)
		whenever(
			useCase.execute(request)
		) doReturn flow { throw exception }

		viewModel.getCurrentWeather(request)

		viewModel.errorMessage.getOrAwaitValue().let {
			Assert.assertEquals(KnownExceptionMessage.CITY_NAME_IS_BLANK, it)
		}
		viewModel.isLoading.getOrAwaitValue().let {
			Assert.assertFalse(it)
		}
		verify(useCase).execute(request)
	}
}