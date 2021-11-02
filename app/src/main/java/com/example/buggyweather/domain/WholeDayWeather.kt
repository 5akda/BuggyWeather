package com.example.buggyweather.domain

import com.google.gson.annotations.SerializedName

data class WholeDayWeather(
		@SerializedName("timezone_offset")
		val timeOffset: Long,
		@SerializedName("hourly")
		val hourlyList: List<CurrentWeather>,
)
