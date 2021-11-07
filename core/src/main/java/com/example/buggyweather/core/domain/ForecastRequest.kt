package com.example.buggyweather.core.domain

data class ForecastRequest(
		val coordinate: Coordinate,
		val measuringUnits: MeasuringUnits?
)