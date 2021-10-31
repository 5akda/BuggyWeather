package com.example.buggyweather.domain


import com.google.gson.annotations.SerializedName

data class RainCondition(
    @SerializedName("1h")
    val volume: Double
)