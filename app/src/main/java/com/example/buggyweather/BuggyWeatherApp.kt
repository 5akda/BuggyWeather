package com.example.buggyweather

import android.app.Application
import com.example.buggyweather.home.HomeModule
import com.example.buggyweather.main.MainModule
import com.example.buggyweather.core.network.NetworkModule
import com.example.buggyweather.core.preference.SharedPreferenceModule
import com.example.buggyweather.whole.WholeDayModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BuggyWeatherApp : Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@BuggyWeatherApp)
			androidLogger()
			modules(
					MainModule.provide(),
					HomeModule.provide(),
					WholeDayModule.provide()
			)
		}

		SharedPreferenceModule.init()
		NetworkModule.init()
	}
}