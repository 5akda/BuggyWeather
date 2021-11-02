package com.example.buggyweather.whole.repository

import androidx.arch.core.util.Function
import com.example.buggyweather.domain.WholeDayWeather
import retrofit2.Response

class WholeDayWeatherRepository(
		private val service: WholeDayWeatherService,
		private val mapper: Function<Response<WholeDayWeather>, WholeDayWeather>
) : WholeDayWeatherDataSource {
	override suspend fun getWholeDayWeather(lat: String, lon: String): WholeDayWeather {
		return mapper.apply(
				service.getWholeDayWeather(lat, lon)
		)
	}
}