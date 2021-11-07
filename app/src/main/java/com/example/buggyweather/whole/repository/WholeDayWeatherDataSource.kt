package com.example.buggyweather.whole.repository

import com.example.buggyweather.core.domain.ForecastRequest
import com.example.buggyweather.core.domain.WholeDayWeather

interface WholeDayWeatherDataSource {
	suspend fun getWholeDayWeather(request: ForecastRequest): WholeDayWeather
}