package com.example.buggyweather.domain

import com.google.gson.annotations.SerializedName

data class WholeDayWeather(
		@SerializedName("time_offset")
		val numOfTimestamp: Long,
		@SerializedName("list")
		val forecastList: List<CurrentWeather>,
)
