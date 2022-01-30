package com.example.buggyweather.core.network

import com.example.buggyweather.core.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
	single {
		createInterceptors()
	}
	single {
		createClient(get())
	}
	single {
		createRetrofit(get())
	}
}

private fun createInterceptors(): List<Interceptor> {
	return listOf(
		HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
	)
}

private fun createClient(interceptors: List<Interceptor>): OkHttpClient {
	val okHttpClient = OkHttpClient.Builder()
	interceptors.forEach { okHttpClient.addInterceptor(it) }
	return okHttpClient
		.readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
		.writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
		.build()
}

private fun createRetrofit(client: OkHttpClient): Retrofit {
	return Retrofit.Builder()
		.baseUrl(Constants.BASE_URL)
		.client(client)
		.addConverterFactory(GsonConverterFactory.create())
		.build()
}

private const val NETWORK_TIMEOUT = 10L