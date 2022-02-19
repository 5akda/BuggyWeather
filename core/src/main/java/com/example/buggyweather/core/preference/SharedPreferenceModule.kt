package com.example.buggyweather.core.preference

import android.content.Context
import android.content.SharedPreferences
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedPreferenceModule = module {
	single(named(FEATURE_SETTING)) {
		createSharedPref(get())
	}
}

private fun createSharedPref(context: Context): SharedPreferences {
	return context.getSharedPreferences(FEATURE_SETTING, Context.MODE_PRIVATE)
}

const val FEATURE_SETTING = "feature_setting"