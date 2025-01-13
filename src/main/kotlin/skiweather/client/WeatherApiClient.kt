package skiweather.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import skiweather.config.AppConfig
import skiweather.model.weather.WeatherForecast
import skiweather.utils.Constants.DATE_FORMATTER
import skiweather.utils.Constants.FORECAST_URL
import skiweather.utils.Constants.HISTORY_URL
import skiweather.utils.ErrorHandler
import skiweather.utils.logger
import java.time.LocalDate

class WeatherApiClient(
    private val config: AppConfig,
    private val httpClient: HttpClient
) {
    private val logger = logger<WeatherApiClient>()

    suspend fun fetchWeatherForecast(location: String): WeatherForecast {
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

    suspend fun fetchWeatherHistory(location: String): WeatherForecast {
        val now: LocalDate = LocalDate.now()
        /*
            The history is collected starting from one day before now
            because the snowfall for today is provided in forecast
         */
        val endDate = now.minusDays(1)
        val startDate = endDate.minusDays(7)

        val response: HttpResponse = httpClient.get {
            url(config.weatherApiUrl + HISTORY_URL)
            parameter("key", config.weatherApiKey)
            parameter("q", location)
            parameter("dt", startDate.format(DATE_FORMATTER))
            parameter("end_dt", endDate.format(DATE_FORMATTER))
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