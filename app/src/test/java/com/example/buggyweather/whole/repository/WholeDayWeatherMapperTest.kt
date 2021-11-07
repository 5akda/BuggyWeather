package com.example.buggyweather.whole.repository

import androidx.arch.core.util.Function
import com.example.buggyweather.domain.WholeDayWeather
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.core.network.exception.RemoteException
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class WholeDayWeatherMapperTest : BaseTest() {

	private lateinit var mapper: Function<Response<WholeDayWeather>, WholeDayWeather>

	@Before
	fun setUp() {
		mapper = WholeDayWeatherMapper()
	}

	@Test
	fun getForecasts_test_success() {
		val response = Response.success(WholeDayWeather())

		val result = mapper.apply(response)

		Assert.assertEquals(WholeDayWeather(), result)
	}

	@Test(expected = RemoteException::class)
	fun getWeather_test_Fail() {
		val emptyBody = "".toResponseBody()
		val response = Response.error<WholeDayWeather>(503, emptyBody)

		mapper.apply(response)
	}
}