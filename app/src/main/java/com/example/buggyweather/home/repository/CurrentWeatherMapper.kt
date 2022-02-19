package com.example.buggyweather.home.repository

import androidx.arch.core.util.Function
import com.example.buggyweather.core.model.CurrentWeather
import com.example.buggyweather.core.network.KnownExceptionMessage
import com.example.buggyweather.core.network.exception.BadRequestException
import com.example.buggyweather.core.network.exception.NotFoundException
import com.example.buggyweather.core.network.exception.RemoteException
import retrofit2.Response
import java.net.HttpURLConnection

class CurrentWeatherMapper: Function<Response<CurrentWeather>, CurrentWeather> {
	override fun apply(response: Response<CurrentWeather>): CurrentWeather {
		return when (response.code()) {
			HttpURLConnection.HTTP_OK -> {
				response.body() ?: throw Throwable(KnownExceptionMessage.COMMON)
			}
			HttpURLConnection.HTTP_NOT_FOUND -> {
				throw NotFoundException(KnownExceptionMessage.CITY_NAME_NOT_FOUND)
			}
			HttpURLConnection.HTTP_BAD_REQUEST -> {
				throw BadRequestException(KnownExceptionMessage.CITY_NAME_IS_BLANK)
			}
			else -> {
				throw RemoteException(response.message())
			}
		}
	}
}