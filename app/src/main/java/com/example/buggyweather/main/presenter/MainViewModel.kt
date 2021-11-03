package com.example.buggyweather.main.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buggyweather.base.UseCase
import com.example.buggyweather.domain.MeasuringUnits
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
		private val getMeasuringUnitsUseCase: UseCase<Unit, MeasuringUnits>,
		private val saveMeasuringUnitsUseCase: UseCase<MeasuringUnits, Unit>,
		private val getLastCityUseCase: UseCase<Unit, String>,
		private val saveLastCityUseCase: UseCase<String, Unit>
) : ViewModel() {

	private val _cityNameAndUnits = MutableLiveData<Pair<String, MeasuringUnits>>()
	val cityNameAndUnits: LiveData<Pair<String, MeasuringUnits>>
		get() = _cityNameAndUnits

	fun initCityNameAndUnits() = viewModelScope.launch(Dispatchers.IO) {
		runCatching {
			Pair(getLastCityUseCase.execute(Unit), getMeasuringUnitsUseCase.execute(Unit))
		}.onSuccess(::setCityNameAndUnits)
	}

	private fun setCityNameAndUnits(pair: Pair<String, MeasuringUnits>) {
		_cityNameAndUnits.postValue(pair)
	}

	fun setCityName(cityName: String)  {
		cityNameAndUnits.value?.second?.let {
			_cityNameAndUnits.postValue(Pair(cityName, it))
		}
	}

	fun saveLastCityName() = viewModelScope.launch {
		cityNameAndUnits.value?.first?.let {
			saveLastCityUseCase.execute(it)
		}
	}

	fun setMeasuringUnits(units: MeasuringUnits)  {
		cityNameAndUnits.value?.first?.let {
			_cityNameAndUnits.postValue(Pair(it, units))
		}
	}

	fun saveSelectedUnits() = viewModelScope.launch {
		cityNameAndUnits.value?.second?.let {
			saveMeasuringUnitsUseCase.execute(it)
		}
	}
}