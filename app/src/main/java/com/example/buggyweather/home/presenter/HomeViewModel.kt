package com.example.buggyweather.home.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.buggyweather.core.base.BaseViewModel
import com.example.buggyweather.core.base.UseCase
import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.core.network.exception.BadRequestException
import com.example.buggyweather.core.network.exception.NotFoundException
import com.example.buggyweather.core.network.exception.RemoteException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
		private val getCurrentWeatherUseCase: UseCase<Pair<String, MeasuringUnits>, CurrentWeather>
) : BaseViewModel() {

	private val _currentWeather = MutableLiveData<CurrentWeather>()
	val currentWeather: LiveData<CurrentWeather>
		get() = _currentWeather

	var hasObservedWeather: Boolean = false

	fun getCurrentWeather(pair: Pair<String, MeasuringUnits>) = viewModelScope.launch(Dispatchers.IO) {
		stateLoading.postValue(true)
		stateErrorMessage.postValue(null)
		runCatching { getCurrentWeatherUseCase.execute(pair) }
				.onSuccess(::succeedCurrentWeather)
				.onFailure(::failCurrentWeather)
	}

	private fun succeedCurrentWeather(currentWeather: CurrentWeather) {
		_currentWeather.postValue(currentWeather)
		hasObservedWeather = true
		stateLoading.postValue(false)
	}

	private fun failCurrentWeather(throwable: Throwable) {
		when(throwable) {
			is NotFoundException,
			is BadRequestException,
			is RemoteException -> stateErrorMessage.postValue(throwable.message)
			else -> stateErrorMessage.postValue(throwable.message)
		}
		stateLoading.postValue(false)
	}
}