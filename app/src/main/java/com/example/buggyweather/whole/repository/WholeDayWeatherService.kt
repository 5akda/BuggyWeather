package com.example.buggyweather.whole.repository

import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.domain.WholeDayWeather
import com.example.buggyweather.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WholeDayWeatherService {

	@GET("onecall")
	suspend fun getWholeDayWeather(
			@Query("lat") lat: String,
			@Query("lon") lon: String,
			@Query("units") unitsName: String = MeasuringUnits.METRIC.name,
			@Query("appid") tokenId: String = Constants.TOKEN_ID,
			@Query("exclude") exclude: String = EXCLUDE_PARAMS
	): Response<WholeDayWeather>

	companion object {
		private const val EXCLUDE_PARAMS = "current,minutely,daily,alerts"
	}
}