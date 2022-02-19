package com.example.buggyweather.core.model

import com.google.gson.annotations.SerializedName

data class WholeDayWeather(
		@SerializedName("timezone_offset")
		val timeOffset: Long = 0,
		@SerializedName("hourly")
		val hourlyList: List<HourlyWeather> = listOf(),
)
