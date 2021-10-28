package com.example.buggyweather

import android.app.Application
import com.example.buggyweather.network.NetworkModule
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
					NetworkModule.provide(),
			)
		}
	}
}