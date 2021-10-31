package com.example.buggyweather.domain


import com.google.gson.annotations.SerializedName

data class SunCondition(
    @SerializedName("country")
    val country: String,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
)