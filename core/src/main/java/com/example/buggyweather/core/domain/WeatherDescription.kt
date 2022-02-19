package com.example.buggyweather.core.domain


import com.google.gson.annotations.SerializedName

data class WeatherDescription(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)