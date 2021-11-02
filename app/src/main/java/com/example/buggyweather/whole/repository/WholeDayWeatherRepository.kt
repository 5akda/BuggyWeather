package com.example.buggyweather.whole.repository

import androidx.arch.core.util.Function
import com.example.buggyweather.domain.ForecastRequest
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.domain.WholeDayWeather
import retrofit2.Response

class WholeDayWeatherRepository(
		private val service: WholeDayWeatherService,
		private val mapper: Function<Response<WholeDayWeather>, WholeDayWeather>
) : WholeDayWeatherDataSource {
	override suspend fun getWholeDayWeather(request: ForecastRequest): WholeDayWeather {
		return mapper.apply(
				service.getWholeDayWeather(
						lat = request.coordinate.lat,
						lon = request.coordinate.lon,
						unitsName = request.measuringUnits?.name ?: MeasuringUnits.METRIC.name
				)
		)
	}
}