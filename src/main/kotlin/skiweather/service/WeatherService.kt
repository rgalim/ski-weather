package skiweather.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import skiweather.client.WeatherApiClient
import skiweather.model.weather.*

class WeatherService(
    private val weatherApiClient: WeatherApiClient
) {

    suspend fun getWeatherForecast(locations: List<String>): List<WeatherForecast> = coroutineScope {
        // TODO: replace with batch request
        val weatherForecastDeferred = locations.map { location ->
            async { weatherApiClient.fetchWeatherForecast(location) }
        }
        weatherForecastDeferred.awaitAll()
    }

    suspend fun getWeatherHistory(locations: List<String>): List<WeatherHistory> = coroutineScope {
        // TODO: replace with batch request
        val weatherHistory = locations.map { location ->
            async { weatherApiClient.fetchWeatherHistory(location) }
        }.awaitAll()

        weatherHistory.map { convertToWeatherHistory(it) }
    }

    private fun convertToWeatherHistory(weatherForecastHistory: WeatherForecastHistory): WeatherHistory {
        val dayForecastList: List<DayForecast> = weatherForecastHistory.forecast.forecastday
        require(dayForecastList.isNotEmpty()) { "The list of day forecasts must not be empty" }

        val dayHistoryList: List<DayHistory> = dayForecastList.map { forecast ->
            DayHistory(forecast.date, forecast.day.totalsnowCm)
        }.toList()

        return WeatherHistory(weatherForecastHistory.location, dayHistoryList)
    }
}