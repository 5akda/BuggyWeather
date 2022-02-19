package com.example.buggyweather.core.model

import com.google.gson.annotations.SerializedName

data class CloudCondition(
		@SerializedName("all")
		val percent: Int
)
