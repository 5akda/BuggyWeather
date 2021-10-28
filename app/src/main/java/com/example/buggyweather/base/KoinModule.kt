package com.example.buggyweather.base

import org.koin.core.module.Module

interface KoinModule {
	fun provide(): Module
}