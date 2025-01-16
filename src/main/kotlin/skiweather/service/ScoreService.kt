package skiweather.service

import skiweather.model.weather.SkiAreaWeather
import kotlin.math.round

class ScoreService {

    fun scoreSkiArea(weather: SkiAreaWeather): Double {
        var score = 0.0

        score += calculateTemperature(weather.temperature)
        score += calculateDailySnowfall(weather.dailySnowfall)
        score += calculateWeeklySnowfall(weather.weeklySnowfall)
        score += calculateWind(weather.wind)
        score += calculateVisibility(weather.visibility)
        score += calculateHumidity(weather.humidity)
        score += calculateCloud(weather.cloud)
        score += calculateUv(weather.uv)

        return score
    }

    private fun calculateTemperature(temperature: Double): Double {
        return when (temperature) {
            in -6.0..-2.0 -> 30.0
            in -10.0..-6.0 -> 15.0
            in -15.0..-10.0 -> 5.0
            in 0.0..5.0 -> 5.0
            else -> 0.0
        }
    }

    private fun calculateDailySnowfall(dailySnowfall: Double): Double {
        return when (dailySnowfall) {
            in 10.0..20.0 -> 25.0
            in 5.0..30.0 -> 15.0
            else -> 0.0
        }
    }

    private fun calculateWeeklySnowfall(weeklySnowfall: Double): Double {
        return when (weeklySnowfall) {
            in 30.0..50.0 -> 20.0
            in 20.0..70.0 -> 10.0
            else -> 0.0
        }
    }

    private fun calculateWind(wind: Double): Double {
        return when (wind) {
            in 0.0..30.0 -> 20.0
            in 30.0..50.0 -> 10.0
            else -> 0.0
        }
    }

    private fun calculateVisibility(visibility: Double): Double {
        return round((visibility / 10.0) * 25.0)
    }

    private fun calculateHumidity(humidity: Int): Double {
        return when (humidity) {
            in 30..60 -> 10.0
            in 20..70 -> 5.0
            else -> 0.0
        }
    }

    private fun calculateCloud(cloud: Int): Double {
        return when (cloud) {
            in 0..40 -> 10.0
            in 40..70 -> 5.0
            else -> 0.0
        }
    }

    private fun calculateUv(uv: Double): Double {
        return when (uv) {
            in 0.0..3.0 -> 10.0
            in 3.0..5.0 -> 5.0
            else -> 0.0
        }
    }
}