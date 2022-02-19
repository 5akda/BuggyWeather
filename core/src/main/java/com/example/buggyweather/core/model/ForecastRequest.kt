package com.example.buggyweather.core.model

data class ForecastRequest(
		val coordinate: Coordinate,
		val measuringUnits: MeasuringUnits?
)