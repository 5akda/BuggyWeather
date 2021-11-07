package com.example.buggyweather.home.usecase

import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.home.repository.CurrentWeatherDataSource

class GetCurrentWeatherUseCase(
		private val repository: CurrentWeatherDataSource
) : UseCase<Pair<String, MeasuringUnits>, CurrentWeather>() {
	override suspend fun create(request: Pair<String, MeasuringUnits>): CurrentWeather {
		return repository.getCurrentWeather(request.first, request.second.name)
	}
}