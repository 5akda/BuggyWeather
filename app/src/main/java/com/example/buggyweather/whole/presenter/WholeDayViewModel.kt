package com.example.buggyweather.whole.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.buggyweather.core.base.BaseViewModel
import com.example.buggyweather.core.base.FlowUseCase
import com.example.buggyweather.core.model.Coordinate
import com.example.buggyweather.core.model.ForecastRequest
import com.example.buggyweather.core.model.MeasuringUnits
import com.example.buggyweather.core.model.WholeDayWeather
import com.example.buggyweather.core.network.KnownExceptionMessage
import com.example.buggyweather.core.network.exception.RemoteException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WholeDayViewModel(
		private val getWholeDayWeatherUseCase: FlowUseCase<ForecastRequest, WholeDayWeather>
) : BaseViewModel() {

	private val _wholeDayForecast = MutableLiveData<WholeDayWeather>()
	val wholeDayForecast: LiveData<WholeDayWeather>
		get() = _wholeDayForecast

	fun getWholeDayForecast(coordinate: Coordinate, units: MeasuringUnits?) {
		viewModelScope.launch {
			val request = ForecastRequest(coordinate, units)
			getWholeDayWeatherUseCase.execute(request)
				.onStart {
					showLoading()
					clearErrorMessage()
				}
				.onCompletion {
					hideLoading()
				}
				.catch { error ->
					failWholeDayWeather(error)
				}
				.collect { forecast ->
					succeedWholeDayWeather(forecast)
				}
		}
	}

	private fun succeedWholeDayWeather(forecast: WholeDayWeather) {
		_wholeDayForecast.postValue(forecast)
	}

	private fun failWholeDayWeather(throwable: Throwable) {
		when(throwable) {
			is RemoteException -> postErrorMessage(throwable.message)
			else -> postErrorMessage(KnownExceptionMessage.COMMON)
		}
	}
}