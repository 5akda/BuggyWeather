package com.example.buggyweather.domain


import com.google.gson.annotations.SerializedName

data class HourlyWeather(
        @SerializedName("weather")
        val weatherDescription: List<WeatherDescription> = listOf(),
        @SerializedName("dew_point")
        val dewPoint: Double = 0.0,
        @SerializedName("dt")
        val dt: Int = 0,
        @SerializedName("feels_like")
        val feelsLike: Double = 0.0,
        @SerializedName("temp")
        val temp: Double = 0.0,
        @SerializedName("visibility")
        val visibility: Int = 0
)