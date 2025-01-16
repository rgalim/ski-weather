package skiweather.service

import skiweather.client.WeatherApiClient
import skiweather.model.weather.SkiArea
import skiweather.model.weather.SkiAreaWeather
import skiweather.model.weather.WeatherHistory
import skiweather.utils.Constants.MAX_SCORE
import skiweather.utils.logger

class SkiAreaService(
    private val locationService: LocationService,
    private val weatherService: WeatherService,
    private val skiAreaWeatherService: SkiAreaWeatherService,
    private val scoreService: ScoreService
) {

    private val logger = logger<WeatherApiClient>()

    suspend fun getSkiAreas(): List<SkiArea> {
        val locations: List<String> = locationService.getLocations()
        require(locations.isNotEmpty()) { "The list of locations must not be empty" }

        val weatherHistoryList: List<WeatherHistory> = weatherService.getWeatherHistory(locations)
        val history: Map<String, Double> = skiAreaWeatherService.convertToHistoryMap(weatherHistoryList)

        return weatherService.getWeatherForecast(locations).asSequence()
            .map { forecast ->
                skiAreaWeatherService.convertToSkiAreaWeather(
                    forecast,
                    getTotalWeeklySnow(history, forecast.location.name)
                )
            }
            .map { skiAreaWeather -> createSkiArea(skiAreaWeather) }
            .sortedByDescending { skiArea -> skiArea.score }
            .toList()
    }

    private fun getTotalWeeklySnow(history: Map<String, Double>, locationName: String): Double {
        if (history.containsKey(locationName)) {
            return history[locationName]!!
        }
        logger.warn("Location {} is missing in history map", locationName)
        return 0.0
    }

    private fun createSkiArea(skiAreaWeather: SkiAreaWeather): SkiArea {
        val score = scoreService.scoreSkiArea(skiAreaWeather)
        return SkiArea(skiAreaWeather, score, scoreService.calculateStars(score, MAX_SCORE))
    }
}