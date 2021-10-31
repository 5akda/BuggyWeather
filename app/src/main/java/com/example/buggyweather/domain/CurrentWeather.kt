package com.example.buggyweather.domain


import com.google.gson.annotations.SerializedName

data class CurrentWeather(
        @SerializedName("cod")
        val cod: Int,
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("main")
        val airCondition: AirCondition,
        @SerializedName("rain")
        val rainCondition: RainCondition? = null,
        @SerializedName("snow")
        val snowCondition: SnowCondition? = null,
        @SerializedName("sys")
        val sunCondition: SunCondition,
        @SerializedName("visibility")
        val visibility: Int,
        @SerializedName("weather")
        val weatherDescription: List<WeatherDescription>,
        @SerializedName("cloud")
        val cloudCondition: CloudCondition
)