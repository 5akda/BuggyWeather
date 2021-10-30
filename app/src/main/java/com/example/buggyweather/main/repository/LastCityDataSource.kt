package com.example.buggyweather.main.repository

interface LastCityDataSource {

	fun saveCity(cityName: String): String
	fun getCity(): String
}