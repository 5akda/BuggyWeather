package com.example.buggyweather.domain

import com.google.gson.annotations.SerializedName

data class CloudCondition(
		@SerializedName("all")
		val percent: Int
)
