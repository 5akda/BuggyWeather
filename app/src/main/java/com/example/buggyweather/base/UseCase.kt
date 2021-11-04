package com.example.buggyweather.base

abstract class UseCase<REQUEST, RESPONSE> {

	protected abstract suspend fun create(request: REQUEST): RESPONSE

	suspend fun execute(request: REQUEST): RESPONSE {
		return create(request)
	}
}