package com.example.buggyweather.main.presenter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buggyweather.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
	private val binding: ActivityMainBinding by lazy {
		ActivityMainBinding.inflate(layoutInflater)
	}

	private val sharedViewModel: MainViewModel by viewModel()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		sharedViewModel.initCityNameAndUnits()
	}
}