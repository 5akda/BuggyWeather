package com.example.buggyweather.whole.repository

import com.example.buggyweather.domain.WholeDayWeather

interface WholeDayWeatherDataSource {
	suspend fun getWholeDayWeather(lat: String, lon: String): WholeDayWeather
}