package com.example.buggyweather.core.domain


import com.google.gson.annotations.SerializedName

data class WindCondition(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val degree: Int
)