package com.app.photoweatherapp.models
import com.google.gson.annotations.SerializedName


data class DataModel(
    val coord: Coord = Coord(),
    val weather: List<Weather> = listOf(),
    val base: String = "",
    val main: Main = Main(),
    val visibility: Int = 0,
    val wind: Wind = Wind(),
    val clouds: Clouds = Clouds(),
    val dt: Int = 0,
    val sys: Sys = Sys(),
    val timezone: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val cod: Int = 0
) {
    data class Coord(
        val lon: Double = 0.0,
        val lat: Double = 0.0
    )

    data class Weather(
        val id: Int = 0,
        val main: String = "",
        val description: String = "",
        val icon: String = ""
    )

    data class Main(
        val temp: Double = 0.0,
        @SerializedName("feels_like")
        val feelsLike: Double = 0.0,
        @SerializedName("temp_min")
        val tempMin: Double = 0.0,
        @SerializedName("temp_max")
        val tempMax: Double = 0.0,
        val pressure: Int = 0,
        val humidity: Int = 0
    )

    data class Wind(
        val speed: Double = 0.0,
        val deg: Int = 0
    )

    data class Clouds(
        val all: Int = 0
    )

    data class Sys(
        val type: Int = 0,
        val id: Int = 0,
        val country: String = "",
        val sunrise: Int = 0,
        val sunset: Int = 0
    )
}