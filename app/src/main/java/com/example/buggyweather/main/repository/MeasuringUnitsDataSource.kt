package com.example.buggyweather.main.repository

import com.example.buggyweather.domain.MeasuringUnits

interface MeasuringUnitsDataSource {

	suspend fun saveUnits(units: MeasuringUnits)
	suspend fun getUnits(): MeasuringUnits
}