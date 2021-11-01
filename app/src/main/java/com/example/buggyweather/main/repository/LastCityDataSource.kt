package com.example.buggyweather.main.repository

interface LastCityDataSource {

	suspend fun saveCity(cityName: String)
	suspend fun getCity(): String
}