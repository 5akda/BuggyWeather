package com.example.buggyweather.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.buggyweather.core.base.KoinModule
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

object SharedPreferenceModule : KoinModule {

	const val FEATURE_SETTING = "feature_setting"

	private fun createSharedPreference(context: Context, feature: String): SharedPreferences {
		return context.getSharedPreferences(feature, Context.MODE_PRIVATE)
	}

	override fun provide(): Module = module {
		single(named(FEATURE_SETTING)) { createSharedPreference(get(), FEATURE_SETTING) }
	}
}