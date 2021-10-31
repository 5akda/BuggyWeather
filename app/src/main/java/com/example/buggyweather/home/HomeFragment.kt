package com.example.buggyweather.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.buggyweather.R
import com.example.buggyweather.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

	private var binding: FragmentHomeBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding?.root
	}


}