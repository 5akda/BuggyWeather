package com.example.buggyweather.whole.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buggyweather.core.base.BaseFragment
import com.example.buggyweather.databinding.FragmentWholeDayBinding
import com.example.buggyweather.core.model.Coordinate
import com.example.buggyweather.core.model.WholeDayWeather
import com.example.buggyweather.main.presenter.MainViewModel
import com.example.buggyweather.core.utils.epochNumOfHoursToMidNight
import com.example.buggyweather.core.utils.visibleIfNotNull
import com.example.buggyweather.core.utils.visibleIfTrue
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
		wholeDayViewModel.wholeDayForecast.observe(viewLifecycleOwner) { wholeDayWeather ->
			displayWeatherList(wholeDayWeather)
		}

		wholeDayViewModel.isLoading.observe(viewLifecycleOwner) { isVisible ->
			binding.loading.visibleIfTrue(isVisible)
		}

		wholeDayViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
			handleError(errorMessage)
		}
	}

	override fun setupListener() {
		super.setupListener()
		binding.btnClose.setOnClickListener {
			activity?.onBackPressed()
		}
	}

	override fun handleError(message: String?) {
		binding.error.errorMessage.text = message
		binding.errorContainer.visibleIfNotNull(message)
	}

	private fun displayWeatherList(wholeDay: WholeDayWeather) {
		val forecastList = wholeDay.hourlyList.subList(0, 24)
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