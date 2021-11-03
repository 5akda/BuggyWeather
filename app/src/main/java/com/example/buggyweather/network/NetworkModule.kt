package com.example.buggyweather.network

import com.example.buggyweather.base.KoinModule
import com.example.buggyweather.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule: KoinModule {

	private fun createClient(): OkHttpClient {
		val logInterceptor = HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}
		return OkHttpClient.Builder()
				.addInterceptor(logInterceptor)
				.readTimeout(5, TimeUnit.SECONDS)
				.writeTimeout(5, TimeUnit.SECONDS)
				.build()
	}

	private fun createRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
			.baseUrl(Constants.BASE_URL)
			.client(client)
			.addConverterFactory(GsonConverterFactory.create())
			.build()

	override fun provide(): Module = module {
		single { createClient() }
		single { createRetrofit(get()) }
	}
}