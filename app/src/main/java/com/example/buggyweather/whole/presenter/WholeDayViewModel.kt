package com.example.buggyweather.whole.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.Coordinate
import com.example.buggyweather.domain.WholeDayWeather
import com.example.buggyweather.network.exception.RemoteException
import kotlinx.coroutines.launch

class WholeDayViewModel(
		private val getWholeDayWeatherUseCase: UseCase<Coordinate, WholeDayWeather>
) : ViewModel() {

	private val _errorMessage = MutableLiveData<String>()
	val errorMessage: LiveData<String>
		get() = _errorMessage

	private val _wholeDayForecast = MutableLiveData<WholeDayWeather>()
	val wholeDayForecast: LiveData<WholeDayWeather>
		get() = _wholeDayForecast

	fun getWholeDayForecast(lat: String, lon: String) = viewModelScope.launch {
		runCatching { getWholeDayWeatherUseCase.execute(Coordinate(lon = lon, lat = lat)) }
				.onSuccess(::succeedWholeDayWeather)
				.onFailure(::failWholeDayWeather)
	}

	private fun succeedWholeDayWeather(forecast: WholeDayWeather) {
		_wholeDayForecast.postValue(forecast)
	}

	private fun failWholeDayWeather(throwable: Throwable) {
		when(throwable) {
			is RemoteException -> _errorMessage.postValue(throwable.message)
			else -> _errorMessage.postValue(throwable.message)
		}
	}
}