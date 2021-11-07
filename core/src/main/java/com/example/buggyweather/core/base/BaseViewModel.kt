package com.example.buggyweather.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

	protected val stateErrorMessage = MutableLiveData<String>()
	val errorMessage: LiveData<String>
		get() = stateErrorMessage

	protected val stateLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean>
		get() = stateLoading
}