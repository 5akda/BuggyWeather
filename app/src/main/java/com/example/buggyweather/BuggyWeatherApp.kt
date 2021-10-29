package com.example.buggyweather

import android.app.Application
import com.example.buggyweather.main.MainModule
import com.example.buggyweather.network.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class BuggyWeatherApp : Application() {

	private val appModule = module {
		single { applicationContext }
	}

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@BuggyWeatherApp)
			androidLogger()
			modules(
					appModule,
					NetworkModule.provide(),
					MainModule.provide()
			)
		}
	}
}