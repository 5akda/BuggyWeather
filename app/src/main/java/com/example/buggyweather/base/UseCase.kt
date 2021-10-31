package com.example.buggyweather.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<REQUEST, RESPONSE> {

	protected abstract suspend fun create(request: REQUEST): RESPONSE

	suspend fun execute(request: REQUEST): RESPONSE {
		return withContext(Dispatchers.IO) {
			create(request)
		}
	}
}