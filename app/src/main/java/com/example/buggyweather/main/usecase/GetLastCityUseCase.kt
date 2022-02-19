package com.example.buggyweather.main.usecase

import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.main.repository.LastCityDataSource

class GetLastCityUseCase(
		private val lastCityDataSource: LastCityDataSource
) : UseCase<Unit, String>() {

	override suspend fun create(request: Unit): String {
		return lastCityDataSource.getCity()
	}
}