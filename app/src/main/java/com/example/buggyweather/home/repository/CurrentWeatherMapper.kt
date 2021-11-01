package com.example.buggyweather.home.repository

import android.util.Log
import androidx.arch.core.util.Function
import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.network.KnownExceptionMessage
import com.example.buggyweather.network.exception.BadRequestException
import com.example.buggyweather.network.exception.NotFoundException
import com.example.buggyweather.network.exception.RemoteException
import retrofit2.Response
import java.net.HttpURLConnection

class CurrentWeatherMapper: Function<Response<CurrentWeather>, CurrentWeather> {
	override fun apply(response: Response<CurrentWeather>): CurrentWeather {
		Log.i("ERROR ${response.code()}", response.message())
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