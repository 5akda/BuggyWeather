package com.example.buggyweather.whole.presenter.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.buggyweather.databinding.ItemForecastBinding
import com.example.buggyweather.domain.HourlyWeather
import com.example.buggyweather.domain.MeasuringUnits
import com.example.buggyweather.core.utils.epochToDateTime
import com.example.buggyweather.core.utils.loadWeatherIcon
import kotlin.math.roundToInt

class ForecastListViewHolder(
		private val binding: ItemForecastBinding,
) : RecyclerView.ViewHolder(binding.root) {

	fun bind(weather: HourlyWeather, units: MeasuringUnits, timeShift: Long) {
		binding.apply {
			imgIcon.loadWeatherIcon(weather.weatherDescription[0].icon)
			txtTime.text = weather.dt.epochToDateTime(timeShift)
			txtDescription.text = weather.weatherDescription[0].description
			txtTemp.text = weather.temp.roundToInt().toString()+units.shortTemp
		}
	}
}