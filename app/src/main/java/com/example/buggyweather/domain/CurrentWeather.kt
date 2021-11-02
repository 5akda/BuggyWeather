package com.example.buggyweather.domain


import com.google.gson.annotations.SerializedName

data class CurrentWeather(
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("main")
        val airCondition: AirCondition,
        @SerializedName("wind")
        val windCondition: WindCondition,
        @SerializedName("visibility")
        val visibility: Int,
        @SerializedName("weather")
        val weatherDescription: List<WeatherDescription>,
        @SerializedName("cloud")
        val cloudCondition: CloudCondition,
        @SerializedName("coord")
        val coordinate: Coordinate
)