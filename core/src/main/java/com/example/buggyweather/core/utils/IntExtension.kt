package com.example.buggyweather.core.utils

import android.icu.text.SimpleDateFormat

fun Int.epochToDateTime(timeShift: Long): String {
	val timeFormat = SimpleDateFormat("HH:mm")
	return timeFormat.format((this - 25200 + timeShift) * 1000)
}

fun Int.epochNumOfHoursToMidNight(timeShift: Long): Int {
	val time = this.epochToDateTime(timeShift).substring(0,2).toInt()
	return 24 - time
}