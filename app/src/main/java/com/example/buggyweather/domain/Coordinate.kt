package com.example.buggyweather.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinate(
		@SerializedName("lon")
		val lon: Double,
		@SerializedName("lat")
		val lat: Double,
): Parcelable