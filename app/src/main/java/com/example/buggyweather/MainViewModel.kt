package com.example.buggyweather

import androidx.lifecycle.ViewModel
import com.example.buggyweather.utils.Constants

class MainViewModel : ViewModel() {

	var cityName: String = Constants.DEFAULT_CITY_NAME
}