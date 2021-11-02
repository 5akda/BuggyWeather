package com.example.buggyweather.whole.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buggyweather.domain.WholeDayWeather
import kotlinx.coroutines.launch

class WholeDayViewModel(

) : ViewModel() {

	private val _errorMessage = MutableLiveData<String>()
	val errorMessage: LiveData<String>
		get() = _errorMessage

	private val _wholeDayForecast = MutableLiveData<WholeDayWeather>()
	val wholedayForecast: LiveData<WholeDayWeather>
		get() = _wholeDayForecast

	fun getWholeDayForecast(lat: Double, lon: Double) = viewModelScope.launch {
		Log.e("TEST", "$lat === $lon")
	}
}