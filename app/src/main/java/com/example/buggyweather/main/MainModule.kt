package com.example.buggyweather.main

import com.example.buggyweather.base.KoinModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object MainModule : KoinModule {

	override fun provide(): Module = module {
		viewModel { MainViewModel() }
	}
}