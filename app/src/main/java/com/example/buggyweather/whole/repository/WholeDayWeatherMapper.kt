package com.example.buggyweather.whole.repository

import androidx.arch.core.util.Function
import com.example.buggyweather.domain.WholeDayWeather
import com.example.buggyweather.core.network.KnownExceptionMessage
import com.example.buggyweather.core.network.exception.RemoteException
import retrofit2.Response
import java.net.HttpURLConnection

class WholeDayWeatherMapper: Function<Response<WholeDayWeather>, WholeDayWeather> {
	override fun apply(response: Response<WholeDayWeather>): WholeDayWeather {
		return when (response.code()) {
			HttpURLConnection.HTTP_OK -> {
				response.body() ?: throw Throwable(KnownExceptionMessage.COMMON)
			}
			else -> {
				throw RemoteException(response.message())
			}
		}
	}
}