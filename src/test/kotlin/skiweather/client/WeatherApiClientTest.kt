package skiweather.client

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import skiweather.TestConstants.MOCKED_APP_CONFIG
import skiweather.TestConstants.WEATHER_API_ERROR_RESPONSE
import skiweather.TestUtils.Companion.createMockClient
import skiweather.TestUtils.Companion.readJson
import skiweather.exception.WeatherApiException
import skiweather.model.weather.*
import kotlin.test.assertEquals

class WeatherApiClientTest {

    @Nested
    inner class GetWeatherForecast {

        @Test
        fun `should return weather object when successful response from weather api`() =
            runBlocking {
                val weatherForecastResponse = readJson("weatherForecastResponse.json")
                val mockEngine =
                    MockEngine { _ ->
                        respond(
                            content = weatherForecastResponse.trimIndent(),
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
                        )
                    }

                val httpClient = createMockClient(mockEngine)
                val weatherApiClient = WeatherApiClient(MOCKED_APP_CONFIG, httpClient)

                val actualWeatherForecast = weatherApiClient.fetchWeatherForecast("Sölden")
                val actualLocation = actualWeatherForecast.location
                val dayForecast = actualWeatherForecast.forecast.forecastday.get(0)

                val expectedLocation = WeatherLocation("Sölden", "Tirol", "Österreich")
                val expectedDayData = DayData(0.4, WeatherCondition("Sunny", "//icon"))

                assertEquals(expectedLocation, actualLocation)
                assertEquals(expectedDayData, dayForecast.day)
                assertEquals("2025-01-11", dayForecast.date)
                assertEquals(24, dayForecast.hour.size)
            }

        @Test
        fun `should return error when failed response from weather api`() =
            runBlocking {
                val mockEngine =
                    MockEngine { _ ->
                        respond(
                            content = WEATHER_API_ERROR_RESPONSE.trimIndent(),
                            status = HttpStatusCode.Forbidden,
                            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
                        )
                    }

                val httpClient = createMockClient(mockEngine)
                val weatherApiClient = WeatherApiClient(MOCKED_APP_CONFIG, httpClient)

                val exception = assertThrows<WeatherApiException> { weatherApiClient.fetchWeatherForecast("Sölden") }

                assertEquals("Unexpected status 403 while fetching weather for location Sölden", exception.message)
            }
    }

    @Nested
    inner class GetWeatherHistory {

        @Test
        fun `should return weather object when successful history response from weather api`() =
            runBlocking {
                val weatherHistoryResponse = readJson("weatherHistoryResponse.json")
                val mockEngine =
                    MockEngine { _ ->
                        respond(
                            content = weatherHistoryResponse.trimIndent(),
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
                        )
                    }

                val httpClient = createMockClient(mockEngine)
                val weatherApiClient = WeatherApiClient(MOCKED_APP_CONFIG, httpClient)

                val actualWeatherHistory = weatherApiClient.fetchWeatherHistory("Sölden")

                val expectedLocation = WeatherLocation("Sölden", "Tirol", "Österreich")
                val expectedDayData = DayData(1.32, WeatherCondition("Sunny", "//icon"))

                assertEquals(expectedLocation, actualWeatherHistory.location)
                assertEquals(7, actualWeatherHistory.forecast.forecastday.size)
                assertEquals(expectedDayData, actualWeatherHistory.forecast.forecastday[0].day)
            }

        @Test
        fun `should return error when failed history response from weather api`() =
            runBlocking {
                val mockEngine =
                    MockEngine { _ ->
                        respond(
                            content = WEATHER_API_ERROR_RESPONSE.trimIndent(),
                            status = HttpStatusCode.Forbidden,
                            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
                        )
                    }

                val httpClient = createMockClient(mockEngine)
                val weatherApiClient = WeatherApiClient(MOCKED_APP_CONFIG, httpClient)

                val exception = assertThrows<WeatherApiException> { weatherApiClient.fetchWeatherHistory("Sölden") }

                assertEquals("Unexpected status 403 while fetching weather for location Sölden", exception.message)
            }
    }
}