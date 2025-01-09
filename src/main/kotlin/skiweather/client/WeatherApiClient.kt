package skiweather.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import skiweather.config.AppConfig
import skiweather.model.weather.Weather
import skiweather.utils.ErrorHandler
import skiweather.utils.logger

class WeatherApiClient(
    private val config: AppConfig,
    private val httpClient: HttpClient
) {
    private val logger = logger<WeatherApiClient>()

    suspend fun getWeather(location: String): Weather {
        val response: HttpResponse = httpClient.get {
            url(config.weatherApiUrl)
            parameter("key", config.weatherApiKey)
            parameter("q", location)
            parameter("aqi", "no")
            parameter("alerts", "no")
            parameter("days", 1)
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                logger.info("Successfully fetched weather for location: $location")
                return response.body<Weather>()
            }
            else -> ErrorHandler.handleUnexpectedError("location $location", response.status.value, response.bodyAsText())
        }
    }
}