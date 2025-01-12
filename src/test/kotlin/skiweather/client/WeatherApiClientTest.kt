package skiweather.client

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import skiweather.TestConstants.MOCKED_APP_CONFIG
import skiweather.TestConstants.WEATHER_API_ERROR_RESPONSE
import skiweather.TestConstants.WEATHER_API_RESPONSE
import skiweather.TestUtils.Companion.createMockClient
import skiweather.exception.WeatherApiException
import skiweather.model.weather.*
import kotlin.test.assertEquals

class WeatherApiClientTest {

    @Test
    fun `should return weather object when successful response from weather api`() =
        runBlocking {
            val mockEngine =
                MockEngine { _ ->
                    respond(
                        content = WEATHER_API_RESPONSE.trimIndent(),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
                    )
                }

            val expectedLocation = WeatherLocation("Sölden", "Tirol", "Österreich")
            val expectedDayData = TotalDayData(0.4)

            val httpClient = createMockClient(mockEngine)
            val weatherApiClient = WeatherApiClient(MOCKED_APP_CONFIG, httpClient)

            val actualWeatherForecast = weatherApiClient.getWeatherForecast("Sölden")
            val actualLocation = actualWeatherForecast.location
            val dayForecast = actualWeatherForecast.forecast.forecastday.get(0)

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

            val exception = assertThrows<WeatherApiException> { weatherApiClient.getWeatherForecast("Sölden") }

            assertEquals("Unexpected status 403 while fetching weather for location Sölden", exception.message)
        }
}