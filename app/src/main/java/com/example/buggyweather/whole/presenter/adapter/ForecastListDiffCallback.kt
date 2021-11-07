package com.example.buggyweather.whole.presenter.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.buggyweather.core.domain.HourlyWeather

class ForecastListDiffCallback : DiffUtil.ItemCallback<HourlyWeather>() {

	override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
		return oldItem == newItem
	}

	override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
		return oldItem == newItem
	}
}