package com.example.buggyweather.home.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buggyweather.databinding.FragmentHomeBinding
import com.example.buggyweather.main.presenter.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

	private var binding: FragmentHomeBinding? = null

	private val sharedViewModel: MainViewModel by sharedViewModel()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupListener()
		observeViewModel()
	}

	private fun observeViewModel() {
		sharedViewModel.cityName.observe(viewLifecycleOwner) { cityName ->
			binding?.txtCtiyName?.setText(cityName)
		}
	}

	private fun setupListener() {

	}
}