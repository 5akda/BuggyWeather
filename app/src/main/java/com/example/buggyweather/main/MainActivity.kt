package com.example.buggyweather.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.buggyweather.databinding.ActivityMainBinding
import com.example.buggyweather.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
	private val binding: ActivityMainBinding by lazy {
		ActivityMainBinding.inflate(layoutInflater)
	}

	private val sharedViewModel: MainViewModel by viewModel()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		sharedViewModel.initCityName(Constants.DEFAULT_CITY_NAME)
	}
}