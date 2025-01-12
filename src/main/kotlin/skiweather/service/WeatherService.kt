package skiweather.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import skiweather.client.WeatherApiClient
import skiweather.model.weather.WeatherForecast

class WeatherService(
    private val locationService: LocationService,
    private val weatherApiClient: WeatherApiClient
) {

    suspend fun getWeather(): List<WeatherForecast> = coroutineScope {
        val locations: List<String> = locationService.getLocations()
        require(locations.isNotEmpty()) { "The list of locations must not be empty" }

        val weatherDeferred = locations.map { location ->
            async { weatherApiClient.getWeatherForecast(location) }
        }
        weatherDeferred.awaitAll()
    }
}