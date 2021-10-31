package com.example.buggyweather.home.repository

import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.network.exception.NotFoundException
import com.example.buggyweather.network.exception.OtherException
import com.example.buggyweather.network.exception.RemoteException
import retrofit2.Response
import java.net.HttpURLConnection
import androidx.arch.core.util.Function

class CurrentWeatherMapper: Function<Response<CurrentWeather>, CurrentWeather> {
	override fun apply(response: Response<CurrentWeather>): CurrentWeather {
		return when (response.code()) {
			HttpURLConnection.HTTP_OK -> {
				response.body() ?: throw OtherException()
			}
			HttpURLConnection.HTTP_NOT_FOUND -> {
				response.body() ?: throw NotFoundException(response.message())
			}
			else -> {
				throw RemoteException(response.message())
			}
		}
	}
}