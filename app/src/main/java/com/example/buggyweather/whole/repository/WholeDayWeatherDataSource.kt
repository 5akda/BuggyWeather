package com.example.buggyweather.whole.repository

import com.example.buggyweather.core.model.ForecastRequest
import com.example.buggyweather.core.model.WholeDayWeather
import kotlinx.coroutines.flow.Flow

interface WholeDayWeatherDataSource {
	fun getWholeDayWeather(request: ForecastRequest): Flow<WholeDayWeather>
}