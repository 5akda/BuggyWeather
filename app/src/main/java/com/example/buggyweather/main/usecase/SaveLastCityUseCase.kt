package com.example.buggyweather.main.usecase

import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.main.repository.LastCityDataSource

class SaveLastCityUseCase(
		private val lastCityDataSource: LastCityDataSource
) : UseCase<String, Unit>() {

	override suspend fun create(request: String) {
		lastCityDataSource.saveCity(request)
	}
}