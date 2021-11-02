package com.example.buggyweather.whole.repository

import com.example.buggyweather.domain.ForecastRequest
import com.example.buggyweather.domain.WholeDayWeather

interface WholeDayWeatherDataSource {
	suspend fun getWholeDayWeather(request: ForecastRequest): WholeDayWeather
}