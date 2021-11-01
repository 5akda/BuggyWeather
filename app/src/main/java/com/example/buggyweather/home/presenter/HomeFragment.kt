package com.example.buggyweather.home.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buggyweather.R
import com.example.buggyweather.base.BaseFragment
import com.example.buggyweather.databinding.FragmentHomeBinding
import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.main.presenter.MainViewModel
import com.example.buggyweather.utils.hideKeyboard
import com.example.buggyweather.utils.loadWeatherIcon
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class HomeFragment : BaseFragment() {

	private lateinit var binding: FragmentHomeBinding

	private val homeViewModel: HomeViewModel by viewModel()
	private val sharedViewModel: MainViewModel by sharedViewModel()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View {
		binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		showLoading()
	}

	override fun observeViewModel() {
		sharedViewModel.cityName.observe(viewLifecycleOwner) { cityName ->
			binding.txtCityName.setText(cityName)
			homeViewModel.getCurrentWeather(
					cityName = cityName,
					measuringUnits = sharedViewModel.measuringUnits.value
			)
		}

		sharedViewModel.measuringUnits.observe(viewLifecycleOwner) { units ->
			when(units) {
				MeasuringUnits.IMPERIAL -> binding.radioGroup.check(R.id.radioImperial)
				else -> binding.radioGroup.check(R.id.radioMetric)
			}
			homeViewModel.getCurrentWeather(
					cityName = sharedViewModel.cityName.value,
					measuringUnits = units
			)
			sharedViewModel.saveSelectedUnits()
		}

		homeViewModel.currentWeather.observe(viewLifecycleOwner) { currentWeather ->
			bindCurrentWeather(currentWeather)
			sharedViewModel.saveLastCityName()
			hideLoading()
		}

		homeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
			hideLoading()
			showError(errorMessage)
		}
	}

	override fun setupListener() {
		binding.txtCityName.setOnEditorActionListener { _, _, _ ->
			processSearch()
			return@setOnEditorActionListener false
		}

		binding.btnSearch.setOnClickListener {
			processSearch()
		}

		binding.radioGroup.setOnCheckedChangeListener { _, i ->
			when(i) {
				R.id.radioImperial -> sharedViewModel.setMeasuringUnits(MeasuringUnits.IMPERIAL)
				else -> sharedViewModel.setMeasuringUnits(MeasuringUnits.METRIC)
			}
		}
	}

	override fun showLoading() {
		binding.loading.visibility = View.VISIBLE
	}

	override fun hideLoading() {
		binding.loading.visibility = View.GONE
	}

	override fun showError(message: String) {
		binding.error.errorMessage.text = message
		binding.errorContainer.visibility = View.VISIBLE
	}

	override fun hideError() {
		binding.errorContainer.visibility = View.GONE
	}

	private fun bindCurrentWeather(currentWeather: CurrentWeather) {
		val units = sharedViewModel.measuringUnits.value
		binding.apply {
			txtCurrentTemp.text = currentWeather.airCondition.temp.roundToInt().toString()
			txtCurrentUnit.text = units?.shortTemp
			imgCurrentIcon.loadWeatherIcon(currentWeather.weatherDescription[0].icon)
			txtMaxAndMinTemp.text = getString(R.string.data_temp_max_min,
					currentWeather.airCondition.tempMax.roundToInt(),
					units?.shortTemp,
					currentWeather.airCondition.tempMin.roundToInt())
			txtDescription.text = currentWeather.weatherDescription[0].description
			txtHumidity.text = getString(R.string.data_humidity,
					currentWeather.airCondition.humidity)
			txtFeelLike.text = getString(R.string.data_feels_like,
					currentWeather.airCondition.feelsLike.roundToInt(),
					units?.shortTemp)
			txtVisibility.text = getString(R.string.data_visibility,
					currentWeather.visibility/1000)
			txtWind.text = getString(R.string.data_wind,
					currentWeather.windCondition.speed.roundToInt(),
					units?.speed)
		}
	}

	private fun processSearch() {
		binding.root.hideKeyboard()
		hideError()
		showLoading()
		sharedViewModel.setCityName(binding.txtCityName.text.toString())
	}
}