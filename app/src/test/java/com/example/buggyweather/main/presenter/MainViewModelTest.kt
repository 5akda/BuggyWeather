package com.example.buggyweather.main.presenter

import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.helper.getOrAwaitValue
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainViewModelTest : BaseTest() {

	@Mock
	private lateinit var getLastCityUseCase: UseCase<Unit, String>
	@Mock
	private lateinit var saveLastCityUseCase: UseCase<String, Unit>
	@Mock
	private lateinit var getSelectedUnitsUseCase: UseCase<Unit, MeasuringUnits>
	@Mock
	private lateinit var saveSelectedUnitsUseCase: UseCase<MeasuringUnits, Unit>

	private lateinit var viewModel: MainViewModel

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		viewModel = MainViewModel(
				getSelectedUnitsUseCase,
				saveSelectedUnitsUseCase,
				getLastCityUseCase,
				saveLastCityUseCase
		)
	}

	@ExperimentalCoroutinesApi
	@Test
	fun getStoredCityNameAndUnits_test() = runBlockingTest {
		whenever(getLastCityUseCase.execute(Unit)) doReturn "Samut Prakan"
		whenever(getSelectedUnitsUseCase.execute(Unit)) doReturn MeasuringUnits.IMPERIAL

		viewModel.initCityNameAndUnits()

		viewModel.cityNameAndUnits.getOrAwaitValue().let {
			Assert.assertEquals("Samut Prakan", it.first)
			Assert.assertEquals(MeasuringUnits.IMPERIAL, it.second)
		}
		verify(getLastCityUseCase).execute(Unit)
		verify(getSelectedUnitsUseCase).execute(Unit)
	}
}