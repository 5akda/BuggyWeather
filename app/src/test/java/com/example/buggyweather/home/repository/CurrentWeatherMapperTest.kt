package com.example.buggyweather.home.repository

import androidx.arch.core.util.Function
import com.example.buggyweather.core.model.CurrentWeather
import com.example.buggyweather.helper.BaseTest
import com.example.buggyweather.core.network.exception.BadRequestException
import com.example.buggyweather.core.network.exception.NotFoundException
import com.example.buggyweather.core.network.exception.RemoteException
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CurrentWeatherMapperTest : BaseTest() {

	private lateinit var mapper: Function<Response<CurrentWeather>, CurrentWeather>

	@Before
	fun setUp() {
		mapper = CurrentWeatherMapper()
	}

	@Test
	fun `Map Success Response`() {
		val response = Response.success(CurrentWeather())

		val result = mapper.apply(response)

		Assert.assertEquals(CurrentWeather(), result)
	}

	@Test(expected = BadRequestException::class)
	fun `Map 400 Error to BadRequestException`() {
		val emptyBody = "".toResponseBody()
		val response = Response.error<CurrentWeather>(400, emptyBody)

		mapper.apply(response)
	}

	@Test(expected = NotFoundException::class)
	fun `Map 404 Error to NotFoundException`() {
		val emptyBody = "".toResponseBody()
		val response = Response.error<CurrentWeather>(404, emptyBody)

		mapper.apply(response)
	}

	@Test(expected = RemoteException::class)
	fun `Map 500 Error to RemoteException`() {
		val emptyBody = "".toResponseBody()
		val response = Response.error<CurrentWeather>(500, emptyBody)

		mapper.apply(response)
	}

}