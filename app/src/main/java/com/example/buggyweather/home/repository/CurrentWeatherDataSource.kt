package com.example.buggyweather.home.repository

import com.example.buggyweather.core.model.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface CurrentWeatherDataSource {
	fun getCurrentWeather(cityName: String, unitsName: String): Flow<CurrentWeather>
}