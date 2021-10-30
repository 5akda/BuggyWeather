package com.example.buggyweather.main.usecase

import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.main.repository.MeasuringUnitsDataSource

class GetMeasuringUnitsUseCase(
		private val measuringUnitsDataSource: MeasuringUnitsDataSource
) : UseCase<Unit, MeasuringUnits>() {

	override fun create(request: Unit): MeasuringUnits {
		return measuringUnitsDataSource.getUnits()
	}
}