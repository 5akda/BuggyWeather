package com.example.buggyweather

import android.app.Application
import com.example.buggyweather.home.HomeModule
import com.example.buggyweather.main.MainModule
import com.example.buggyweather.network.NetworkModule
import com.example.buggyweather.preference.SharedPreferenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class BuggyWeatherApp : Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@BuggyWeatherApp)
			androidLogger()
			modules(
					NetworkModule.provide(),
					MainModule.provide(),
					SharedPreferenceModule.provide(),
					HomeModule.provide()
			)
		}
	}
}