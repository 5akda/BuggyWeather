package com.example.buggyweather.home.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.network.exception.BadRequestException
import com.example.buggyweather.network.exception.NotFoundException
import com.example.buggyweather.network.exception.RemoteException
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class HomeViewModel(
		private val getCurrentWeatherUseCase: UseCase<Pair<String, String>, CurrentWeather>
) : ViewModel() {

	private val _currentWeather = MutableLiveData<CurrentWeather>()
	val currentWeather: LiveData<CurrentWeather>
		get() = _currentWeather

	private val _exception = MutableLiveData<String>()
	val exception: LiveData<String>
		get() = _exception

	fun getCurrentWeather(cityName: String,
	                      measuringUnits: MeasuringUnits?) = viewModelScope.launch {
		val request = Pair(cityName, measuringUnits?.name ?: MeasuringUnits.METRIC.name)
		runCatching { getCurrentWeatherUseCase.execute(request) }
				.onSuccess(::succeedCurrentWeather)
				.onFailure(::failCurrentWeather)
	}

	private fun succeedCurrentWeather(currentWeather: CurrentWeather) {
		_currentWeather.postValue(currentWeather)
	}

	private fun failCurrentWeather(throwable: Throwable) {
		when(throwable) {
			is NotFoundException,
			is BadRequestException,
			is RemoteException -> _exception.postValue(throwable.message)
			else -> _exception.postValue(throwable.message)
		}
	}
}