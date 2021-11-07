package com.example.buggyweather.home

import androidx.arch.core.util.Function
import com.example.buggyweather.core.base.KoinModule
import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.home.presenter.HomeViewModel
import com.example.buggyweather.home.repository.CurrentWeatherDataSource
import com.example.buggyweather.home.repository.CurrentWeatherMapper
import com.example.buggyweather.home.repository.CurrentWeatherRepository
import com.example.buggyweather.home.repository.CurrentWeatherService
import com.example.buggyweather.home.usecase.GetCurrentWeatherUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Response
import retrofit2.Retrofit

object HomeModule : KoinModule {

	private const val CURRENT_WEATHER_MAPPER = "CURRENT_WEATHER_MAPPER"
	private const val CURRENT_WEATHER_USE_CASE = "CURRENT_WEATHER_USE_CASE"

	private fun createCurrentWeatherService(retrofit: Retrofit): CurrentWeatherService {
		return retrofit.create(CurrentWeatherService::class.java)
	}

	override fun provide(): Module = module {
		single {
			createCurrentWeatherService(get())
		}
		single<Function<Response<CurrentWeather>, CurrentWeather>>(named(CURRENT_WEATHER_MAPPER)) {
			CurrentWeatherMapper()
		}
		single<CurrentWeatherDataSource> {
			CurrentWeatherRepository(get(), get(named(CURRENT_WEATHER_MAPPER)))
		}
		single<UseCase<Pair<String, MeasuringUnits>,CurrentWeather>>(named(CURRENT_WEATHER_USE_CASE)) {
			GetCurrentWeatherUseCase(get())
		}
		viewModel {
			HomeViewModel(get(named(CURRENT_WEATHER_USE_CASE)))
		}
	}
}