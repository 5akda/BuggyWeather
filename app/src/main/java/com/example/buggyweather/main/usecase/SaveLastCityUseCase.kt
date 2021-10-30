package com.example.buggyweather.main.usecase

import com.example.buggyweather.base.UseCase
import com.example.buggyweather.main.repository.LastCityDataSource

class SaveLastCityUseCase(
		private val lastCityDataSource: LastCityDataSource
) : UseCase<String, String>() {

	override fun create(request: String): String {
		return lastCityDataSource.saveCity(request)
	}
}