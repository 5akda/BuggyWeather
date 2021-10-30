package com.example.buggyweather.main.repository

import com.example.buggyweather.domain.MeasuringUnits

interface MeasuringUnitsDataSource {

	fun saveUnits(units: MeasuringUnits): MeasuringUnits
	fun getUnits(): MeasuringUnits
}