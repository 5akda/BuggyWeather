package com.example.buggyweather.home.usecase

import com.example.buggyweather.core.base.FlowUseCase
import com.example.buggyweather.core.model.CurrentWeather
import com.example.buggyweather.core.model.MeasuringUnits
import com.example.buggyweather.home.repository.CurrentWeatherDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetCurrentWeatherUseCase(
		private val repository: CurrentWeatherDataSource,
		private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Pair<String, MeasuringUnits>, CurrentWeather> {
	override fun execute(request: Pair<String, MeasuringUnits>): Flow<CurrentWeather> {
		return repository.getCurrentWeather(
			cityName = request.first,
			unitsName = request.second.name
		).flowOn(ioDispatcher)
	}
}