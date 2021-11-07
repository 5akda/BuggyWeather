package com.example.buggyweather.home.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.buggyweather.R
import com.example.buggyweather.core.base.BaseFragment
import com.example.buggyweather.databinding.FragmentHomeBinding
import com.example.buggyweather.domain.Coordinate
import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.main.presenter.MainViewModel
import com.example.buggyweather.utils.hideKeyboard
import com.example.buggyweather.utils.loadWeatherIcon
import com.example.buggyweather.utils.visibleIfNotNull
import com.example.buggyweather.utils.visibleIfTrue
import com.example.buggyweather.whole.presenter.WholeDayFragment
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

	override fun observeViewModel() {
		sharedViewModel.cityNameAndUnits.observe(this) { pair ->
			binding.txtCityName.setText(pair.first)
			when(pair.second) {
				MeasuringUnits.IMPERIAL -> binding.radioGroup.check(R.id.radioImperial)
				else -> binding.radioGroup.check(R.id.radioMetric)
			}
			sharedViewModel.saveSelectedUnits()
			homeViewModel.getCurrentWeather(pair)
		}

		homeViewModel.currentWeather.observe(viewLifecycleOwner) { currentWeather ->
			bindCurrentWeather(currentWeather)
			sharedViewModel.saveLastCityName()
		}

		homeViewModel.isLoading.observe(viewLifecycleOwner) { isVisible ->
			binding.loading.visibleIfTrue(isVisible)
		}

		homeViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
			handleError(errorMessage)
		}
	}

	override fun setupListener() {
		binding.txtCityName.setOnEditorActionListener { _, _, _ ->
			clickSearch()
			return@setOnEditorActionListener false
		}

		binding.btnSearch.setOnClickListener {
			clickSearch()
		}

		binding.radioGroup.setOnCheckedChangeListener { _, id ->
			if(homeViewModel.hasObservedWeather) {
				when(id) {
					R.id.radioImperial -> sharedViewModel.setMeasuringUnits(MeasuringUnits.IMPERIAL)
					else -> sharedViewModel.setMeasuringUnits(MeasuringUnits.METRIC)
				}
			}
		}
		binding.btnWholeDay.setOnClickListener {
			homeViewModel.currentWeather.value?.coordinate?.let { coord ->
				seeWholeDayForecast(coord)
			}
		}
	}

	override fun handleError(message: String?) {
		binding.error.errorMessage.text = message
		binding.errorContainer.visibleIfNotNull(message)
	}

	private fun bindCurrentWeather(currentWeather: CurrentWeather) {
		val units = sharedViewModel.cityNameAndUnits.value?.second
		binding.apply {
			txtCurrentTemp.text = currentWeather.airCondition?.temp?.roundToInt().toString()
			txtCurrentUnit.text = units?.shortTemp
			imgCurrentIcon.loadWeatherIcon(currentWeather.weatherDescription[0].icon)
			txtMaxAndMinTemp.text = getString(R.string.data_temp_max_min,
					currentWeather.airCondition?.tempMax?.roundToInt(),
					units?.shortTemp,
					currentWeather.airCondition?.tempMin?.roundToInt())
			txtDescription.text = currentWeather.weatherDescription[0].description
			txtHumidity.text = getString(R.string.data_humidity,
					currentWeather.airCondition?.humidity)
			txtFeelLike.text = getString(R.string.data_feels_like,
					currentWeather.airCondition?.feelsLike?.roundToInt(),
					units?.shortTemp)
			txtVisibility.text = getString(R.string.data_visibility,
					currentWeather.visibility/1000)
			txtWind.text = getString(R.string.data_wind,
					currentWeather.windCondition?.speed?.roundToInt(),
					units?.speed)
		}
	}

	private fun clickSearch() {
		binding.root.hideKeyboard()
		sharedViewModel.setCityName(binding.txtCityName.text.toString())
	}

	private fun seeWholeDayForecast(coordinate: Coordinate) {
		val bundle = Bundle().apply {
			putString(WholeDayFragment.BUNDLE_KEY_LAT, coordinate.lat)
			putString(WholeDayFragment.BUNDLE_KEY_LON, coordinate.lon)
		}
		findNavController().navigate(R.id.navigateToWholeDay, bundle)
	}
}