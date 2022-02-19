package com.example.buggyweather.home.repository

import androidx.arch.core.util.Function
import com.example.buggyweather.core.model.CurrentWeather
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

class CurrentWeatherRepository(
		private val service: CurrentWeatherService,
		private val mapper: Function<Response<CurrentWeather>, CurrentWeather>
) : CurrentWeatherDataSource {

	override fun getCurrentWeather(cityName: String,
	                               unitsName: String): Flow<CurrentWeather> {
		return flow {
			val response = service.getCurrentWeather(cityName, unitsName)
			delay(2000)
			emit(response)
		}.map(mapper::apply)
	}
}