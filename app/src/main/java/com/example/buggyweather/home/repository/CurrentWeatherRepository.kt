package com.example.buggyweather.home.repository

import androidx.arch.core.util.Function
import com.example.buggyweather.core.domain.CurrentWeather
import retrofit2.Response

class CurrentWeatherRepository(
		private val service: CurrentWeatherService,
		private val mapper: Function<Response<CurrentWeather>, CurrentWeather>
) : CurrentWeatherDataSource {

	override suspend fun getCurrentWeather(cityName: String,
	                                       unitsName: String): CurrentWeather {
		return mapper.apply(
				service.getCurrentWeather(cityName, unitsName)
		)
	}
}