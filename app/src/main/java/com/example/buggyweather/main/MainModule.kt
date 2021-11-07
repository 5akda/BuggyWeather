package com.example.buggyweather.main

import com.example.buggyweather.core.base.KoinModule
import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.core.domain.MeasuringUnits
import com.example.buggyweather.main.presenter.MainViewModel
import com.example.buggyweather.main.repository.LastCityDataSource
import com.example.buggyweather.main.repository.LastCityRepository
import com.example.buggyweather.main.repository.MeasuringUnitsDataSource
import com.example.buggyweather.main.repository.MeasuringUnitsRepository
import com.example.buggyweather.main.usecase.GetLastCityUseCase
import com.example.buggyweather.main.usecase.GetMeasuringUnitsUseCase
import com.example.buggyweather.main.usecase.SaveLastCityUseCase
import com.example.buggyweather.main.usecase.SaveMeasuringUnitsUseCase
import com.example.buggyweather.core.preference.SharedPreferenceModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

object MainModule : KoinModule {

	private const val GET_UNITS_USE_CASE = "GET_UNITS_USE_CASE"
	private const val SAVE_UNITS_USE_CASE = "SAVE_UNITS_USE_CASE"
	private const val GET_CITY_USE_CASE = "GET_CITY_USE_CASE"
	private const val SAVE_CITY_USE_CASE = "SAVE_CITY_USE_CASE"

	override fun provide(): Module = module {
		single<MeasuringUnitsDataSource> {
			MeasuringUnitsRepository(
					get(named(SharedPreferenceModule.FEATURE_SETTING))
			)
		}
		single<UseCase<Unit, MeasuringUnits>>(named(GET_UNITS_USE_CASE)) {
			GetMeasuringUnitsUseCase(get())
		}
		single<UseCase<MeasuringUnits, Unit>>(named(SAVE_UNITS_USE_CASE)) {
			SaveMeasuringUnitsUseCase(get())
		}

		single<LastCityDataSource> {
			LastCityRepository(
					get(named(SharedPreferenceModule.FEATURE_SETTING))
			)
		}
		single<UseCase<Unit, String>>(named(GET_CITY_USE_CASE)) {
			GetLastCityUseCase(get())
		}
		single<UseCase<String, Unit>>(named(SAVE_CITY_USE_CASE)) {
			SaveLastCityUseCase(get())
		}

		viewModel {
			MainViewModel(
					get(named(GET_UNITS_USE_CASE)),
					get(named(SAVE_UNITS_USE_CASE)),
					get(named(GET_CITY_USE_CASE)),
					get(named(SAVE_CITY_USE_CASE))
			)
		}
	}
}