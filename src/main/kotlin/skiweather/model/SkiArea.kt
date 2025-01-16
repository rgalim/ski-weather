package skiweather.model

import skiweather.model.weather.SkiAreaWeather
import skiweather.model.weather.WeatherCondition

data class SkiArea(
    val name: String,
    val weather: SkiAreaWeather,
    val condition: WeatherCondition,
    val score: Double,
    val stars: Double
)
