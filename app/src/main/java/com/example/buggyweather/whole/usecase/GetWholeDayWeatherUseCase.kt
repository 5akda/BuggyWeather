package com.example.buggyweather.whole.usecase

import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.Coordinate
import com.example.buggyweather.domain.WholeDayWeather
import com.example.buggyweather.whole.repository.WholeDayWeatherDataSource

class GetWholeDayWeatherUseCase(
		private val repository: WholeDayWeatherDataSource
) : UseCase<Coordinate, WholeDayWeather>() {
	override suspend fun create(request: Coordinate): WholeDayWeather {
		return repository.getWholeDayWeather(request.lat, request.lon)
	}
}