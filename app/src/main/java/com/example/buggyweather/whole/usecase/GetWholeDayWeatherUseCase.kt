package com.example.buggyweather.whole.usecase

import com.example.buggyweather.core.base.FlowUseCase
import com.example.buggyweather.core.model.ForecastRequest
import com.example.buggyweather.core.model.WholeDayWeather
import com.example.buggyweather.whole.repository.WholeDayWeatherDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetWholeDayWeatherUseCase(
		private val repository: WholeDayWeatherDataSource,
		private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<ForecastRequest, WholeDayWeather> {
	override fun execute(request: ForecastRequest): Flow<WholeDayWeather> {
		return repository.getWholeDayWeather(request)
			.flowOn(ioDispatcher)
	}
}