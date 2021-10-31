package com.example.buggyweather.main.repository

interface LastCityDataSource {

	fun saveCity(cityName: String)
	fun getCity(): String
}