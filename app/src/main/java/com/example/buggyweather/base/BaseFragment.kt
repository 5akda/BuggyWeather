package com.example.buggyweather.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

	protected open fun showLoading() {}
	protected open fun hideLoading() {}
	protected open fun showError(message: String) {}
	protected open fun hideError() {}
	protected open fun observeViewModel() {}
	protected open fun setupListener() {}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupListener()
		observeViewModel()
	}
}