package com.example.buggyweather.whole.repository

import androidx.arch.core.util.Function
import com.example.buggyweather.core.model.ForecastRequest
import com.example.buggyweather.core.model.MeasuringUnits
import com.example.buggyweather.core.model.WholeDayWeather
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

class WholeDayWeatherRepository(
		private val service: WholeDayWeatherService,
		private val mapper: Function<Response<WholeDayWeather>, WholeDayWeather>
) : WholeDayWeatherDataSource {
	override fun getWholeDayWeather(request: ForecastRequest): Flow<WholeDayWeather> {
		return flow {
			val response = service.getWholeDayWeather(
				lat = request.coordinate.lat,
				lon = request.coordinate.lon,
				unitsName = request.measuringUnits?.name ?: MeasuringUnits.METRIC.name
			)
			delay(2000)
			emit(response)
		}.map(mapper::apply)
	}
}