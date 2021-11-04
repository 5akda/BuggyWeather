package com.example.buggyweather.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
	val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
	inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.visibleIfTrue(visibity: Boolean) {
	this.visibility = when(visibity) {
		true -> View.VISIBLE
		else -> View.GONE
	}
}

fun View.visibleIfNotNull(data: String?) {
	data?.let {
		this.visibility = View.VISIBLE
	} ?: run {
		this.visibility = View.GONE
	}
}