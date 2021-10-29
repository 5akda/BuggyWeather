package com.example.buggyweather.domain

enum class MeasuringUnits(
		val format: String,
		val temp: String,
		val shortTemp: String,
		val speed: String) {

	METRIC(
			format = "metric",
			temp = "Celsius",
			shortTemp = "°C",
			speed = "m/s"
	),
	IMPERIAL(
			format = "imperial",
			temp = "farenheit",
			shortTemp = "°F",
			speed = "mph"
	)
}