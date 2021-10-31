package com.example.buggyweather.home.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.network.exception.NotFoundException
import com.example.buggyweather.network.exception.RemoteException
import kotlinx.coroutines.launch

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
		kotlin.runCatching {
			getCurrentWeatherUseCase.execute(
					Pair(cityName, measuringUnits?.name ?: MeasuringUnits.METRIC.name)
			)
		}.onSuccess {
			_currentWeather.postValue(it)
		}.onFailure {
			when(it) {
				is NotFoundException -> _exception.postValue(it.msg)
				is RemoteException -> _exception.postValue(it.msg)
				else -> _exception.postValue(it.message)
			}
		}
	}
}