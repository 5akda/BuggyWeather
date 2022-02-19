package com.example.buggyweather.core.base

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<REQUEST, RESPONSE> {
	fun execute(request: REQUEST): Flow<RESPONSE>
}