package com.example.buggyweather.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadWeatherIcon(iconId: String) {
	val drawable = resources.getIdentifier("ic_$iconId", "drawable", context.packageName)
	Glide.with(this.context)
			.load(drawable)
			.into(this)
}