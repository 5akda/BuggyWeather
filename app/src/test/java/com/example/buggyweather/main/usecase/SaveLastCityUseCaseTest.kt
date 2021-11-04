package com.example.buggyweather.main.usecase

import com.example.buggyweather.base.UseCase
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.main.repository.LastCityDataSource
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SaveLastCityUseCaseTest : BaseTest() {

	@Mock
	private lateinit var dataSource: LastCityDataSource

	private lateinit var useCase: UseCase<String, Unit>

	@Before
	fun setUp() {
		MockitoAnnotations.initMocks(this)
		useCase = SaveLastCityUseCase(dataSource)
	}

	@ExperimentalCoroutinesApi
	@Test
	fun saveLastCity_test() = runBlockingTest {
		useCase.execute("New York")

		verify(dataSource).saveCity("New York")
	}
}