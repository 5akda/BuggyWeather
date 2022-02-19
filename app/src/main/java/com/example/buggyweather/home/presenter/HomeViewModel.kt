package com.example.buggyweather.home.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.buggyweather.core.base.BaseViewModel
import com.example.buggyweather.core.base.FlowUseCase
import com.example.buggyweather.core.model.CurrentWeather
import com.example.buggyweather.core.model.MeasuringUnits
import com.example.buggyweather.core.network.KnownExceptionMessage
import com.example.buggyweather.core.network.exception.BadRequestException
import com.example.buggyweather.core.network.exception.NotFoundException
import com.example.buggyweather.core.network.exception.RemoteException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
		private val getCurrentWeatherUseCase: FlowUseCase<Pair<String, MeasuringUnits>, CurrentWeather>
) : BaseViewModel() {

	private val _currentWeather = MutableLiveData<CurrentWeather>()
	val currentWeather: LiveData<CurrentWeather>
		get() = _currentWeather

	var hasObservedWeather: Boolean = false

	fun getCurrentWeather(pair: Pair<String, MeasuringUnits>) {
		viewModelScope.launch {
			getCurrentWeatherUseCase.execute(pair)
				.onStart {
					showLoading()
					clearErrorMessage()
				}
				.onCompletion {
					hideLoading()
				}
				.catch { error ->
					failCurrentWeather(error)
				}
				.collect { currentWeather ->
					succeedCurrentWeather(currentWeather)
				}
		}
	}

	private fun succeedCurrentWeather(currentWeather: CurrentWeather) {
		_currentWeather.postValue(currentWeather)
		hasObservedWeather = true
	}

	private fun failCurrentWeather(throwable: Throwable) {
		when(throwable) {
			is NotFoundException,
			is BadRequestException,
			is RemoteException -> postErrorMessage(throwable.message)
			else -> postErrorMessage(KnownExceptionMessage.COMMON)
		}
	}
}