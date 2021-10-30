package com.example.buggyweather.main.repository

import android.content.SharedPreferences
import com.example.buggyweather.utils.Constants

class LastCityRepository(
		private val settingSharedPref: SharedPreferences
) : LastCityDataSource {

	override fun saveCity(cityName: String): String {
		settingSharedPref.edit()
				.putString(KEY_LAST_CITY, cityName)
				.apply()
		return getCity()
	}

	override fun getCity(): String {
		return settingSharedPref.getString(KEY_LAST_CITY, null) ?: Constants.DEFAULT_CITY_NAME
	}

	companion object {
		private const val KEY_LAST_CITY = "key_last_city"
	}
}