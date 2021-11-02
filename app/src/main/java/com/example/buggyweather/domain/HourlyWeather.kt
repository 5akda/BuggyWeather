package com.example.buggyweather.domain


import com.google.gson.annotations.SerializedName

data class HourlyWeather(
        @SerializedName("weather")
        val weatherDescription: List<WeatherDescription>,
        @SerializedName("dew_point")
        val dewPoint: Double,
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("feels_like")
        val feelsLike: Double,
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("visibility")
        val visibility: Int
)