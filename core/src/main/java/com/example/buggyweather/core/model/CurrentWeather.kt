package com.example.buggyweather.core.model


import com.google.gson.annotations.SerializedName

data class CurrentWeather(
        @SerializedName("dt")
        val dt: Int = 0,
        @SerializedName("main")
        val airCondition: AirCondition? = null,
        @SerializedName("wind")
        val windCondition: WindCondition? = null,
        @SerializedName("visibility")
        val visibility: Int = 0,
        @SerializedName("weather")
        val weatherDescription: List<WeatherDescription> = listOf(),
        @SerializedName("cloud")
        val cloudCondition: CloudCondition? = null,
        @SerializedName("coord")
        val coordinate: Coordinate? = null
)