package com.example.buggyweather.main.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.MeasuringUnits
import kotlinx.coroutines.launch

class MainViewModel(
		private val getMeasuringUnitsUseCase: UseCase<Unit, MeasuringUnits>,
		private val saveMeasuringUnitsUseCase: UseCase<MeasuringUnits, MeasuringUnits>
) : ViewModel() {

	private val _cityName = MutableLiveData<String>()
	val cityName: LiveData<String>
		get() = _cityName

	private val _measuringUnit = MutableLiveData<MeasuringUnits>()
	val measuringUnits: LiveData<MeasuringUnits>
		get() = _measuringUnit

	fun initCityName(city: String) = viewModelScope.launch {
		_cityName.postValue(city)
		getMeasuringUnit()
	}

	private fun getMeasuringUnit() = viewModelScope.launch {
		_measuringUnit.postValue(getMeasuringUnitsUseCase.execute(Unit))
	}
}