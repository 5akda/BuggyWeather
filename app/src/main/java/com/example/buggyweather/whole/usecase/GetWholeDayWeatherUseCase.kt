package com.example.buggyweather.whole.usecase

import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.ForecastRequest
import com.example.buggyweather.domain.WholeDayWeather
import com.example.buggyweather.whole.repository.WholeDayWeatherDataSource

class GetWholeDayWeatherUseCase(
		private val repository: WholeDayWeatherDataSource
) : UseCase<ForecastRequest, WholeDayWeather>() {
	override suspend fun create(request: ForecastRequest): WholeDayWeather {
		return repository.getWholeDayWeather(request)
	}
}