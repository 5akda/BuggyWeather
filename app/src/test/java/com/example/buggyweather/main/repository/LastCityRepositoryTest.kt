package com.example.buggyweather.main.repository

import android.content.SharedPreferences
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

class LastCityRepositoryTest : BaseTest() {

	@Mock
	private lateinit var sharedPref: SharedPreferences

	private lateinit var repository: LastCityDataSource

	@Before
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		repository = LastCityRepository(sharedPref)
	}

	@ExperimentalCoroutinesApi
	@Test
	fun getCity_test() = runBlockingTest {
		whenever(sharedPref.getString("key_last_city", null)) doReturn "Phuket"

		val cityName = repository.getCity()

		Assert.assertEquals("Phuket", cityName)
		verify(sharedPref).getString("key_last_city", null)
	}
}