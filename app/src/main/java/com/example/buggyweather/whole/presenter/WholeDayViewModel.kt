package com.example.buggyweather.whole.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.buggyweather.core.base.BaseViewModel
import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.domain.Coordinate
import com.example.buggyweather.domain.ForecastRequest
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.domain.WholeDayWeather
import com.example.buggyweather.network.exception.RemoteException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WholeDayViewModel(
		private val getWholeDayWeatherUseCase: UseCase<ForecastRequest, WholeDayWeather>
) : BaseViewModel() {

	private val _wholeDayForecast = MutableLiveData<WholeDayWeather>()
	val wholeDayForecast: LiveData<WholeDayWeather>
		get() = _wholeDayForecast

	fun getWholeDayForecast(coordinate: Coordinate, units: MeasuringUnits?) = viewModelScope.launch(Dispatchers.IO) {
		stateLoading.postValue(true)
		stateErrorMessage.postValue(null)
		val request = ForecastRequest(coordinate, units)
		runCatching { getWholeDayWeatherUseCase.execute(request) }
				.onSuccess(::succeedWholeDayWeather)
				.onFailure(::failWholeDayWeather)
	}

	private fun succeedWholeDayWeather(forecast: WholeDayWeather) {
		stateLoading.postValue(false)
		_wholeDayForecast.postValue(forecast)
	}

	private fun failWholeDayWeather(throwable: Throwable) {
		stateLoading.postValue(false)
		when(throwable) {
			is RemoteException -> stateErrorMessage.postValue(throwable.message)
			else -> stateErrorMessage.postValue(throwable.message)
		}
	}
}