package com.example.buggyweather.network

import com.example.buggyweather.base.KoinModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule: KoinModule {
	private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

	private val logInterceptor = HttpLoggingInterceptor().apply {
		level = HttpLoggingInterceptor.Level.BODY
	}

	private fun createClient(): OkHttpClient = OkHttpClient.Builder()
			.addInterceptor(logInterceptor)
			.readTimeout(60, TimeUnit.SECONDS)
			.writeTimeout(60, TimeUnit.SECONDS)
			.build()

	private fun createRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(client)
			.addConverterFactory(GsonConverterFactory.create())
			.build()

	override fun provide(): Module = module {
		single { createClient() }
		single { createRetrofit(get()) }
	}
}