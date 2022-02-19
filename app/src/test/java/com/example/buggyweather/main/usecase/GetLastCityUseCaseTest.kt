package com.example.buggyweather.main.usecase

import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.main.repository.LastCityDataSource
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

class GetLastCityUseCaseTest : BaseTest() {

	@Mock
	private lateinit var dataSource: LastCityDataSource

	private lateinit var useCase: UseCase<Unit, String>

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		useCase = GetLastCityUseCase(dataSource)
	}

	@ExperimentalCoroutinesApi
	@Test
	fun getLastCity_test() = runBlockingTest {
		whenever(dataSource.getCity()) doReturn "San Francisco"

		val result = useCase.execute(Unit)

		Assert.assertEquals("San Francisco", result)
		verify(dataSource).getCity()
	}
}