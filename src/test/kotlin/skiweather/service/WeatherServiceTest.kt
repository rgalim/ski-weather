package skiweather.service

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import skiweather.TestConstants.LOCATIONS
import skiweather.TestConstants.WEATHER_FORECAST_KITZBUHEL
import skiweather.TestConstants.WEATHER_FORECAST_SOLDEN
import skiweather.TestConstants.WEATHER_HISTORY_KITZBUHEL
import skiweather.TestConstants.WEATHER_HISTORY_SOLDEN
import skiweather.client.WeatherApiClient
import skiweather.exception.WeatherApiException
import skiweather.model.weather.DayHistory
import skiweather.model.weather.WeatherHistory
import skiweather.model.weather.WeatherLocation

class WeatherServiceTest {

    private val weatherApiClient = mock(WeatherApiClient::class.java)
    private val weatherService = WeatherService(weatherApiClient)

    @Nested
    inner class GetWeatherForecast {

        @Test
        fun `should return list of weather objects when successful response from weather api`() =
            runBlocking {
                `when`(weatherApiClient.fetchWeatherForecast("Sölden")).thenReturn(WEATHER_FORECAST_SOLDEN)
                `when`(weatherApiClient.fetchWeatherForecast("Kitzbühel"))
                    .thenReturn(WEATHER_FORECAST_KITZBUHEL)

                val actualWeather = weatherService.getWeatherForecast(LOCATIONS)

                assertEquals(listOf(WEATHER_FORECAST_SOLDEN, WEATHER_FORECAST_KITZBUHEL), actualWeather)
            }

        @Test
        fun `should throw exception when error response from weather api`() =
            runBlocking {
                `when`(weatherApiClient.fetchWeatherForecast("Sölden")).thenReturn(WEATHER_FORECAST_SOLDEN)
                `when`(weatherApiClient.fetchWeatherForecast("Kitzbühel"))
                    .thenThrow(WeatherApiException("Something went wrong"))

                val exception = assertThrows<WeatherApiException> { weatherService.getWeatherForecast(LOCATIONS) }

                assertEquals("Something went wrong", exception.message)
            }
    }

    @Nested
    inner class GetWeatherHistory {

        @Test
        fun `should return list of weather history when successful response from weather api`() =
            runBlocking {
                `when`(weatherApiClient.fetchWeatherHistory("Sölden")).thenReturn(WEATHER_HISTORY_SOLDEN)
                `when`(weatherApiClient.fetchWeatherHistory("Kitzbühel"))
                    .thenReturn(WEATHER_HISTORY_KITZBUHEL)

                val actualHistory = weatherService.getWeatherHistory(LOCATIONS)

                val expectedHistory = listOf(
                    WeatherHistory(
                        WeatherLocation("Sölden", "Tirol", "Österreich"),
                        listOf(DayHistory("2025-01-10", 0.07), DayHistory("2025-01-11", 0.13))),
                    WeatherHistory(
                        WeatherLocation("Kitzbühel", "Tirol", "Österreich"),
                        listOf(DayHistory("2025-01-10", 1.2), DayHistory("2025-01-11", 3.5))),
                )

                assertEquals(expectedHistory, actualHistory)
            }

        @Test
        fun `should throw exception when error response from weather api`() =
            runBlocking {
                `when`(weatherApiClient.fetchWeatherHistory("Sölden")).thenReturn(WEATHER_HISTORY_SOLDEN)
                `when`(weatherApiClient.fetchWeatherHistory("Kitzbühel"))
                    .thenThrow(WeatherApiException("Something went wrong"))

                val exception = assertThrows<WeatherApiException> { weatherService.getWeatherHistory(LOCATIONS) }

                assertEquals("Something went wrong", exception.message)
            }
    }
}