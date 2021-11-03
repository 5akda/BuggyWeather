package com.example.buggyweather.main.repository

import android.content.SharedPreferences
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.helper.BaseTest
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

class MeasuringUnitsRepositoryTest : BaseTest() {

	@Mock
	private lateinit var sharedPref: SharedPreferences

	private lateinit var repository: MeasuringUnitsDataSource

	@Before
	fun setUp() {
		MockitoAnnotations.initMocks(this)
		repository = MeasuringUnitsRepository(sharedPref)
	}

	@ExperimentalCoroutinesApi
	@Test
	fun getUnits_test() = runBlockingTest {
		whenever(
				sharedPref.getString("key_units", MeasuringUnits.METRIC.name)
		) doReturn MeasuringUnits.IMPERIAL.name

		val result = repository.getUnits()

		Assert.assertEquals(MeasuringUnits.IMPERIAL, result)
		verify(sharedPref).getString("key_units", MeasuringUnits.METRIC.name)
	}
}