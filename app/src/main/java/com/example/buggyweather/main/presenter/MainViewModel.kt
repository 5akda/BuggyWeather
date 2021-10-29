package com.example.buggyweather.main.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buggyweather.domain.MeasuringUnits

class MainViewModel : ViewModel() {

	private val _cityName = MutableLiveData<String>()
	val cityName: LiveData<String>
		get() = _cityName

	private val _measuringUnit = MutableLiveData<MeasuringUnits>()
	val measuringUnits: LiveData<MeasuringUnits>
		get() = _measuringUnit

	fun initCityName(cityName: String) {
		_cityName.postValue(cityName)
	}
}