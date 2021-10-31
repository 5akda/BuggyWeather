package com.example.buggyweather.domain

import com.google.gson.annotations.SerializedName

data class SnowCondition(
		@SerializedName("1h")
		val volume: Double
)
