package com.example.buggyweather.home

import androidx.arch.core.util.Function
import com.example.buggyweather.base.KoinModule
import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.home.presenter.HomeViewModel
import com.example.buggyweather.home.repository.CurrentWeatherDataSource
import com.example.buggyweather.home.repository.CurrentWeatherMapper
import com.example.buggyweather.home.repository.CurrentWeatherRepository
import com.example.buggyweather.home.repository.CurrentWeatherService
import com.example.buggyweather.home.usecase.GetCurrentWeatherUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
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
		single<Function<Response<CurrentWeather>, CurrentWeather>> {
			CurrentWeatherMapper()
		}
		single<CurrentWeatherDataSource> {
			CurrentWeatherRepository(get(), get())
		}
		single<UseCase<Pair<String, String>,CurrentWeather>> {
			GetCurrentWeatherUseCase(get())
		}
		viewModel {
			HomeViewModel(get())
		}
	}
}