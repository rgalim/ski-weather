package skiweather.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import skiweather.config.AppConfig
import skiweather.model.weather.WeatherForecast
import skiweather.utils.Constants.FORECAST_URL
import skiweather.utils.ErrorHandler
import skiweather.utils.logger

class WeatherApiClient(
    private val config: AppConfig,
    private val httpClient: HttpClient
) {
    private val logger = logger<WeatherApiClient>()

    suspend fun getWeatherForecast(location: String): WeatherForecast {
        val response: HttpResponse = httpClient.get {
            url(config.weatherApiUrl + FORECAST_URL)
            parameter("key", config.weatherApiKey)
            parameter("q", location)
            parameter("days", 1)
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                logger.info("Successfully fetched weather for location: $location")
                return response.body<WeatherForecast>()
            }
            else -> ErrorHandler.handleUnexpectedError("location $location", response.status.value, response.bodyAsText())
        }
    }
}