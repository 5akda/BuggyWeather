package com.example.buggyweather.whole

import com.example.buggyweather.base.KoinModule
import com.example.buggyweather.whole.presenter.WholeDayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object WholeDayModule : KoinModule {
	override fun provide(): Module = module {
		viewModel { WholeDayViewModel() }
	}
}