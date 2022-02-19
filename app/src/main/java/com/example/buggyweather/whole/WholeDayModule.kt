package com.example.buggyweather.whole

import androidx.arch.core.util.Function
import com.example.buggyweather.core.base.FlowUseCase
import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.core.model.ForecastRequest
import com.example.buggyweather.core.model.WholeDayWeather
import com.example.buggyweather.whole.presenter.WholeDayViewModel
import com.example.buggyweather.whole.presenter.adapter.ForecastListAdapter
import com.example.buggyweather.whole.repository.WholeDayWeatherDataSource
import com.example.buggyweather.whole.repository.WholeDayWeatherMapper
import com.example.buggyweather.whole.repository.WholeDayWeatherRepository
import com.example.buggyweather.whole.repository.WholeDayWeatherService
import com.example.buggyweather.whole.usecase.GetWholeDayWeatherUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Response
import retrofit2.Retrofit

val wholeDayModule = module {
	single {
		createWholeDayWeatherService(get())
	}
	single<Function<Response<WholeDayWeather>, WholeDayWeather>>(named(FORECAST_MAPPER)) {
		WholeDayWeatherMapper()
	}
	single<WholeDayWeatherDataSource> {
		WholeDayWeatherRepository(
			service = get(),
			mapper = get(named(FORECAST_MAPPER))
		)
	}
	single<FlowUseCase<ForecastRequest, WholeDayWeather>>(named(FORECAST_USE_CASE)) {
		GetWholeDayWeatherUseCase(
			repository = get(),
			ioDispatcher = Dispatchers.IO
		)
	}
	viewModel {
		WholeDayViewModel(get(named(FORECAST_USE_CASE)))
	}
	single {
		ForecastListAdapter()
	}
}

private fun createWholeDayWeatherService(retrofit: Retrofit): WholeDayWeatherService {
	return retrofit.create(WholeDayWeatherService::class.java)
}

private const val FORECAST_MAPPER = "FORECAST_MAPPER"
private const val FORECAST_USE_CASE = "FORECAST_USE_CASE"