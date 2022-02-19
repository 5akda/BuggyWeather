package com.example.buggyweather.main.usecase

import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.core.domain.MeasuringUnits
import com.example.buggyweather.main.repository.MeasuringUnitsDataSource

class GetMeasuringUnitsUseCase(
		private val measuringUnitsDataSource: MeasuringUnitsDataSource
) : UseCase<Unit, MeasuringUnits>() {

	override suspend fun create(request: Unit): MeasuringUnits {
		return measuringUnitsDataSource.getUnits()
	}
}