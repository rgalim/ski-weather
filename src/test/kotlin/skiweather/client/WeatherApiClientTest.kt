package skiweather.client

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import skiweather.TestConstants.MOCKED_APP_CONFIG
import skiweather.TestConstants.WEATHER_API_RESPONSE
import skiweather.TestUtils.Companion.createMockClient
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

            val expectedWeather = Weather(
                WeatherLocation("Sölden", "Tirol", "Österreich"),
                CurrentWeather(-6.2, WeatherCondition("Partly Cloudy", "//icon.png"), 24.5, 92),
                WeatherForecast(listOf(
                    DayForecast(
                        TotalDayData(-5.4, 0.07, 73),
                        listOf(
                            HourData("2025-01-08 00:00", -10.0, 16.6, 0.0),
                            HourData("2025-01-08 01:00", -10.4, 18.4, 0.0)))
                ))
            )

            val httpClient = createMockClient(mockEngine)
            val weatherApiClient = WeatherApiClient(MOCKED_APP_CONFIG, httpClient)

            val actualWeather = weatherApiClient.getWeather("Sölden")

            assertEquals(expectedWeather, actualWeather)
        }
}