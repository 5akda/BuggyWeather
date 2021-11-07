package com.example.buggyweather.whole.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.buggyweather.databinding.ItemForecastBinding
import com.example.buggyweather.core.domain.HourlyWeather
import com.example.buggyweather.core.domain.MeasuringUnits

class ForecastListAdapter : ListAdapter<HourlyWeather, ForecastListViewHolder>(ForecastListDiffCallback()) {

	var measuringUnits: MeasuringUnits? = null
	var timeShift: Long = 0

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = ItemForecastBinding.inflate(inflater, parent, false)
		return ForecastListViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ForecastListViewHolder, position: Int) {
		holder.bind(
				weather = getItem(position),
				units = measuringUnits ?: MeasuringUnits.METRIC,
				timeShift = timeShift
		)
	}
}