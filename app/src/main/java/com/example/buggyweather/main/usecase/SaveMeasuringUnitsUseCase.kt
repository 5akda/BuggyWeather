package com.example.buggyweather.main.usecase

import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.main.repository.MeasuringUnitsDataSource

class SaveMeasuringUnitsUseCase(
		private val measuringUnitsDataSource: MeasuringUnitsDataSource
) : UseCase<MeasuringUnits, Unit>() {

	override suspend fun create(request: MeasuringUnits) {
		return measuringUnitsDataSource.saveUnits(request)
	}
}