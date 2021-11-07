package com.example.buggyweather.main.usecase

import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.main.repository.MeasuringUnitsDataSource
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SaveMeasuringUnitUseCaseTest : BaseTest() {

	@Mock
	private lateinit var dataSource: MeasuringUnitsDataSource

	private lateinit var useCase: UseCase<MeasuringUnits, Unit>

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		useCase = SaveMeasuringUnitsUseCase(dataSource)
	}

	@ExperimentalCoroutinesApi
	@Test
	fun saveSelectedUnit_test() = runBlockingTest {
		useCase.execute(MeasuringUnits.METRIC)

		verify(dataSource).saveUnits(MeasuringUnits.METRIC)
	}
}