package com.example.buggyweather.utils

import android.icu.text.SimpleDateFormat

fun Int.epochToDateTime(timeShift: Long): String {
	val timeFormat = SimpleDateFormat("HH")
	return timeFormat.format((this - 25200 + timeShift) * 1000)
}

fun Int.epochNumOfHoursToMidNight(timeShift: Long): Int {
	val time = this.epochToDateTime(timeShift).toInt()
	return 24 - time
}