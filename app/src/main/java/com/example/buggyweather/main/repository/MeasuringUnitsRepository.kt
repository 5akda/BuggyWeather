package com.example.buggyweather.main.repository

import android.content.SharedPreferences
import com.example.buggyweather.domain.MeasuringUnits

class MeasuringUnitsRepository(
		private val settingSharedPref: SharedPreferences
) : MeasuringUnitsDataSource {

	override fun saveUnits(units: MeasuringUnits): MeasuringUnits {
		settingSharedPref.edit()
				.putString(KEY_UNITS, units.name)
				.apply()
		return getUnits()
	}

	override fun getUnits(): MeasuringUnits {
		val savedData = settingSharedPref.getString(KEY_UNITS, MeasuringUnits.METRIC.name)
		return MeasuringUnits.valueOf(savedData ?: MeasuringUnits.METRIC.name)
	}

	companion object {
		private const val KEY_UNITS = "key_units"
	}
}