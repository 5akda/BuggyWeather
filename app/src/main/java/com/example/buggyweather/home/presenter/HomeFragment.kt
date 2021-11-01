package com.example.buggyweather.home.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buggyweather.base.BaseFragment
import com.example.buggyweather.databinding.FragmentHomeBinding
import com.example.buggyweather.domain.CurrentWeather
import com.example.buggyweather.main.presenter.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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
			binding.txtCtiyName.setText(cityName)
			homeViewModel.getCurrentWeather(
					cityName = cityName,
					measuringUnits = sharedViewModel.measuringUnits.value
			)
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
		binding.btnSearch.setOnClickListener {
			hideError()
			showLoading()
			sharedViewModel.setCityName(binding.txtCtiyName.text.toString())
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
		
	}
}