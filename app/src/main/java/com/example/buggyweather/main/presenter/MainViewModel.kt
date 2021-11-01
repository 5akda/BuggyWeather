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
		runCatching { getLastCityUseCase.execute(Unit) }
				.onSuccess(::setCityName)

		runCatching { getMeasuringUnitsUseCase.execute(Unit) }
				.onSuccess(::setMeasuringUnits)
	}

	fun setCityName(cityName: String) {
		_cityName.postValue(cityName)
	}

	fun saveLastCityName() = viewModelScope.launch {
		saveLastCityUseCase.execute(cityName.value ?: Constants.DEFAULT_CITY_NAME)
	}

	fun setMeasuringUnits(units: MeasuringUnits) {
		_measuringUnit.postValue(units)
	}

	fun saveSelectedUnits() = viewModelScope.launch {
		saveMeasuringUnitsUseCase.execute(measuringUnits.value ?: MeasuringUnits.METRIC)
	}
}