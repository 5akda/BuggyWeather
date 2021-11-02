package com.example.buggyweather.domain

data class ForecastRequest(
		val coordinate: Coordinate,
		val measuringUnits: MeasuringUnits?
)