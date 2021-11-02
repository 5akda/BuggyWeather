package com.example.buggyweather.domain

import com.google.gson.annotations.SerializedName

data class Coordinate(
		@SerializedName("lon")
		val lon: String,
		@SerializedName("lat")
		val lat: String,
)