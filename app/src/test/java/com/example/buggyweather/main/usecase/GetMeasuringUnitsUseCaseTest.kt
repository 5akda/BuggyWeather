package com.example.buggyweather.main.usecase

import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.main.repository.MeasuringUnitsDataSource
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

class GetMeasuringUnitsUseCaseTest : BaseTest() {

	@Mock
	private lateinit var dataSource: MeasuringUnitsDataSource

	private lateinit var useCase: UseCase<Unit, MeasuringUnits>

	@Before
	fun setUp() {
		MockitoAnnotations.initMocks(this)
		useCase = GetMeasuringUnitsUseCase(dataSource)
	}

	@ExperimentalCoroutinesApi
	@Test
	fun getUnits_test() = runBlockingTest {
		whenever(dataSource.getUnits()) doReturn MeasuringUnits.IMPERIAL

		val result = useCase.execute(Unit)

		Assert.assertEquals(MeasuringUnits.IMPERIAL, result)
		verify(dataSource).getUnits()
	}
}