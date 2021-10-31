package com.example.buggyweather.home.usecase

import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.home.repository.CurrentWeatherDataSource

class GetCurrentWeatherUseCase(
		private val repository: CurrentWeatherDataSource
) : UseCase<Pair<String, String>, CurrentWeather>() {
	override suspend fun create(request: Pair<String, String>): CurrentWeather {
		return repository.getCurrentWeather(request.first, request.second)
	}
}