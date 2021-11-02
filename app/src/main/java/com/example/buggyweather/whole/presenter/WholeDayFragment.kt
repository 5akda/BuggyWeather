package com.example.buggyweather.whole.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.buggyweather.base.BaseFragment
import com.example.buggyweather.databinding.FragmentWholeDayBinding
import com.example.buggyweather.main.presenter.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WholeDayFragment : BaseFragment() {

	private lateinit var binding: FragmentWholeDayBinding

	private val wholeDayViewModel: WholeDayViewModel by viewModel()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View {
		binding = FragmentWholeDayBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		showLoading()
		arguments?.let { bundle ->
			wholeDayViewModel.getWholeDayForecast(
					lat = bundle.getDouble(BUNDLE_KEY_LAT),
					lon = bundle.getDouble(BUNDLE_KEY_LON)
			)
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

	companion object {
		const val BUNDLE_KEY_LAT = "bundle_key_lat"
		const val BUNDLE_KEY_LON = "bundle_key_lon"
	}
}