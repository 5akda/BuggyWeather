package com.example.buggyweather.main.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.utils.Constants
import kotlinx.coroutines.launch

class MainViewModel(
		private val getMeasuringUnitsUseCase: UseCase<Unit, MeasuringUnits>,
		private val saveMeasuringUnitsUseCase: UseCase<MeasuringUnits, MeasuringUnits>,
		private val getLastCityUseCase: UseCase<Unit, String>,
		private val saveLastCityUseCase: UseCase<String, Unit>
) : ViewModel() {

	private val _cityName = MutableLiveData<String>()
	val cityName: LiveData<String>
		get() = _cityName

	private val _measuringUnit = MutableLiveData<MeasuringUnits>()
	val measuringUnits: LiveData<MeasuringUnits>
		get() = _measuringUnit

	fun initCityNameAndUnits() = viewModelScope.launch {
		_cityName.postValue(getLastCityUseCase.execute(Unit))
		_measuringUnit.postValue(getMeasuringUnitsUseCase.execute(Unit))
	}

	fun setCityName(cityName: String?) {
		_cityName.postValue(cityName?: "")
	}

	fun saveLastCityName(cityName: String?) = viewModelScope.launch {
		saveLastCityUseCase.execute(cityName?: Constants.DEFAULT_CITY_NAME)
	}
}