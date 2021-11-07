package com.example.buggyweather.core.base

import org.koin.core.module.Module

interface KoinModule {
	fun provide(): Module
}