package skiweather.service

import skiweather.model.weather.SkiArea
import kotlin.math.round

class ScoreService {

    fun scoreSkiArea(area: SkiArea): Double {
        var score = 0.0

        score += calculateTemperature(area.temperature)
        score += calculateDailySnowfall(area.dailySnowfall)
        score += calculateWeeklySnowfall(area.weeklySnowfall)
        score += calculateWind(area.wind)
        score += calculateVisibility(area.visibility)
        score += calculateHumidity(area.humidity)
        score += calculateCloud(area.cloud)
        score += calculateUv(area.uv)

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
            in 0..40 -> 20.0
            in 40..70 -> 10.0
            else -> 0.0
        }
    }

    private fun calculateUv(uv: Int): Double {
        return when (uv) {
            in 0..3 -> 10.0
            in 3..5 -> 5.0
            else -> 0.0
        }
    }
}