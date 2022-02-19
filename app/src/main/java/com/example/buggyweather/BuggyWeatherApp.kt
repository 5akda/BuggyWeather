package com.example.buggyweather

import android.app.Application
import com.example.buggyweather.core.network.networkModule
import com.example.buggyweather.core.preference.sharedPreferenceModule
import com.example.buggyweather.home.homeModule
import com.example.buggyweather.main.mainModule
import com.example.buggyweather.whole.wholeDayModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class BuggyWeatherApp : Application() {

	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@BuggyWeatherApp)
			androidLogger()
			modules(
					mainModule,
					homeModule,
					wholeDayModule
			)
		}

		loadKoinModules(sharedPreferenceModule)
		loadKoinModules(networkModule)
	}
}