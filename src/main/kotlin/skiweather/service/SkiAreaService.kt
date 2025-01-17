package skiweather.service

import skiweather.client.WeatherApiClient
import skiweather.model.SkiArea
import skiweather.model.weather.*
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
            .map { weatherForecast -> createSkiArea(weatherForecast, history)}
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

    private fun createSkiArea(weatherForecast: WeatherForecast, history: Map<String, Double>): SkiArea {
        val name = weatherForecast.location.name

        val dayForecastList = weatherForecast.forecast.forecastday
        require(dayForecastList.isNotEmpty()) { "Daily forecast must not be empty" }

        val currentDayForecast: DayForecast = dayForecastList[0]
        val condition: WeatherCondition = currentDayForecast.day.condition

        val skiAreaWeather: SkiAreaWeather = skiAreaWeatherService.getSkiAreaWeather(currentDayForecast, getTotalWeeklySnow(history, name))
        val score: Double = scoreService.scoreSkiArea(skiAreaWeather)
        val stars: Double = scoreService.calculateStars(score, MAX_SCORE)

        return SkiArea(name, skiAreaWeather, condition, score, stars)
    }
}