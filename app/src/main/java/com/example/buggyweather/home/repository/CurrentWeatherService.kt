package com.example.buggyweather.home.repository

import com.example.buggyweather.core.model.CurrentWeather
import com.example.buggyweather.core.model.MeasuringUnits
import com.example.buggyweather.core.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherService {

	@GET("weather")
	suspend fun getCurrentWeather(
			@Query("q") cityName: String,
			@Query("units") unitsName: String = MeasuringUnits.METRIC.name,
			@Query("appid") tokenId: String = Constants.TOKEN_ID
	): Response<CurrentWeather>
}