package skiweather.service

import skiweather.model.weather.SkiArea
import skiweather.model.weather.SkiAreaWeather

class SkiAreaService(
    private val weatherService: WeatherService,
    private val skiAreaWeatherService: SkiAreaWeatherService,
    private val scoreService: ScoreService
) {

    suspend fun getSkiAreas(): List<SkiArea> {
        return weatherService.getWeather().asSequence()
            .map { forecast -> skiAreaWeatherService.convertToSkiAreaWeather(forecast) }
            .map { skiAreaWeather -> createSkiArea(skiAreaWeather) }
            .sortedByDescending { skiArea -> skiArea.score }
            .toList()
    }

    private fun createSkiArea(skiAreaWeather: SkiAreaWeather): SkiArea {
        return SkiArea(
            skiAreaWeather,
            scoreService.scoreSkiArea(skiAreaWeather),
            0.0 // TODO: calculate stars
        )
    }
}