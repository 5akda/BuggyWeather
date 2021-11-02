package com.example.buggyweather.whole.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buggyweather.base.BaseFragment
import com.example.buggyweather.databinding.FragmentWholeDayBinding
import com.example.buggyweather.domain.Coordinate
import com.example.buggyweather.domain.WholeDayWeather
import com.example.buggyweather.main.presenter.MainViewModel
import com.example.buggyweather.utils.epochNumOfHoursToMidNight
import com.example.buggyweather.whole.presenter.adapter.ForecastListAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WholeDayFragment : BaseFragment() {

	private lateinit var binding: FragmentWholeDayBinding

	private val wholeDayViewModel: WholeDayViewModel by viewModel()
	private val sharedViewModel: MainViewModel by sharedViewModel()

	private val listAdapter: ForecastListAdapter by inject()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View {
		binding = FragmentWholeDayBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.recyclerView.adapter = listAdapter

		showLoading()

		arguments?.let { bundle ->
			val coordinate = Coordinate(
					lat = bundle.getString(BUNDLE_KEY_LAT) ?: "",
					lon = bundle.getString(BUNDLE_KEY_LON) ?: "",
			)
			wholeDayViewModel.getWholeDayForecast(
					coordinate = coordinate,
					units = sharedViewModel.cityNameAndUnits.value?.second
			)
		}
	}

	override fun observeViewModel() {
		super.observeViewModel()
		wholeDayViewModel.wholeDayForecast.observe(this) { wholeDayWeather ->
			displayWeatherList(wholeDayWeather)
			hideLoading()
		}

		wholeDayViewModel.errorMessage.observe(this) { errorMessage ->
			hideLoading()
			showError(errorMessage)
		}
	}

	override fun setupListener() {
		super.setupListener()
		binding.btnClose.setOnClickListener {
			activity?.onBackPressed()
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

	private fun displayWeatherList(wholeDay: WholeDayWeather) {
		val numOfHour = wholeDay.hourlyList[0].dt.epochNumOfHoursToMidNight(wholeDay.timeOffset)
		val forecastList = wholeDay.hourlyList.subList(0, numOfHour)
		listAdapter.apply {
			submitList(forecastList)
			timeShift = wholeDay.timeOffset
			measuringUnits = sharedViewModel.cityNameAndUnits.value?.second
			notifyDataSetChanged()
		}
	}

	companion object {
		const val BUNDLE_KEY_LAT = "bundle_key_lat"
		const val BUNDLE_KEY_LON = "bundle_key_lon"
	}
}