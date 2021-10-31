package com.example.buggyweather.home.repository

import com.example.buggyweather.domain.CurrentWeather

interface CurrentWeatherDataSource {
	suspend fun getCurrentWeather(cityName: String, unitsName: String): CurrentWeather
}