package com.example.buggyweather.home

import androidx.arch.core.util.Function
import com.example.buggyweather.core.base.FlowUseCase
import com.example.buggyweather.core.model.CurrentWeather
import com.example.buggyweather.core.model.MeasuringUnits
import com.example.buggyweather.home.presenter.HomeViewModel
import com.example.buggyweather.home.repository.CurrentWeatherDataSource
import com.example.buggyweather.home.repository.CurrentWeatherMapper
import com.example.buggyweather.home.repository.CurrentWeatherRepository
import com.example.buggyweather.home.repository.CurrentWeatherService
import com.example.buggyweather.home.usecase.GetCurrentWeatherUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Response
import retrofit2.Retrofit

val homeModule = module {
	single {
		createCurrentWeatherService(get())
	}
	factory <Function<Response<CurrentWeather>, CurrentWeather>> (named(CURRENT_WEATHER_MAPPER)) {
		CurrentWeatherMapper()
	}
	factory <CurrentWeatherDataSource> {
		CurrentWeatherRepository(
			service = get(),
			mapper = get(named(CURRENT_WEATHER_MAPPER))
		)
	}
	factory <FlowUseCase<Pair<String, MeasuringUnits>, CurrentWeather>> (named(CURRENT_WEATHER_USE_CASE)) {
		GetCurrentWeatherUseCase(
			repository = get(),
			ioDispatcher = Dispatchers.IO
		)
	}
	viewModel {
		HomeViewModel(
			get(named(CURRENT_WEATHER_USE_CASE))
		)
	}
}

private fun createCurrentWeatherService(retrofit: Retrofit): CurrentWeatherService {
	return retrofit.create(CurrentWeatherService::class.java)
}

private const val CURRENT_WEATHER_MAPPER = "CURRENT_WEATHER_MAPPER"
private const val CURRENT_WEATHER_USE_CASE = "CURRENT_WEATHER_USE_CASE"