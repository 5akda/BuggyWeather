package com.example.buggyweather.base

interface UseCase<REQUEST, RESPONSE> {
	fun execute(request: REQUEST): RESPONSE
}