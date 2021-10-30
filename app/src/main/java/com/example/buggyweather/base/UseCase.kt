package com.example.buggyweather.base

interface UseCase<REQUEST, RESPONSE> {
	suspend fun execute(request: REQUEST): RESPONSE
}