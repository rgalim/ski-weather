package skiweather.service

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import skiweather.TestConstants
import skiweather.TestConstants.DAY_FORECAST_SOLDEN
import skiweather.TestConstants.LOCATION
import skiweather.TestConstants.LOCATIONS
import skiweather.TestConstants.WEATHER_FORECAST_KITZBUHEL
import skiweather.TestConstants.WEATHER_FORECAST_SOLDEN
import skiweather.TestConstants.WEATHER_FORECAST_HISTORY_KITZBUHEL
import skiweather.TestConstants.WEATHER_FORECAST_HISTORY_SOLDEN
import skiweather.TestConstants.WEATHER_HISTORY
import skiweather.client.WeatherApiClient
import skiweather.exception.WeatherApiException
import skiweather.model.alert.AlertList
import skiweather.model.weather.*

class WeatherServiceTest {

    private val weatherApiClient = mock(WeatherApiClient::class.java)
    private val weatherService = WeatherService(weatherApiClient)

    @Nested
    inner class GetWeatherForecasts {

        @Test
        fun `should return list of weather objects when successful response from weather api`() =
            runBlocking {
                `when`(weatherApiClient.fetchWeatherForecast("Sölden", 1)).thenReturn(WEATHER_FORECAST_SOLDEN)
                `when`(weatherApiClient.fetchWeatherForecast("Kitzbühel", 1))
                    .thenReturn(WEATHER_FORECAST_KITZBUHEL)

                val actualWeather = weatherService.getWeatherForecasts(LOCATIONS)

                assertEquals(listOf(WEATHER_FORECAST_SOLDEN, WEATHER_FORECAST_KITZBUHEL), actualWeather)
            }

        @Test
        fun `should throw exception when error response from weather api`() =
            runBlocking {
                `when`(weatherApiClient.fetchWeatherForecast("Sölden", 1)).thenReturn(WEATHER_FORECAST_SOLDEN)
                `when`(weatherApiClient.fetchWeatherForecast("Kitzbühel", 1))
                    .thenThrow(WeatherApiException("Something went wrong"))

                val exception = assertThrows<WeatherApiException> { weatherService.getWeatherForecasts(LOCATIONS) }

                assertEquals("Something went wrong", exception.message)
            }
    }

    @Nested
    inner class GetWeatherForecast {

        @Test
        fun `should return weather days forecast when successful response from weather api`() =
            runBlocking {
                val secondDayForecastSolden = DayForecast(
                    "2025-01-12",
                    DayData(10.7, TestConstants.WEATHER_CONDITION),
                    listOf(
                        HourData("2025-01-11 00:00", -11.9, 5.8, 95, 71, 5.0, 0.0, 5.3),
                        HourData("2025-01-11 01:00", -11.7, 6.1, 95, 71, 7.0, 0.0, 5.4)
                    )
                )
                val weatherForecast = WeatherForecast(
                    WeatherLocation("Sölden", "Tirol", "Österreich"),
                    ForecastList(listOf(DAY_FORECAST_SOLDEN, secondDayForecastSolden)),
                    AlertList(listOf())
                )

                `when`(weatherApiClient.fetchWeatherForecast("Sölden", 3)).thenReturn(weatherForecast)

                val actualForecast: WeatherDaysForecast = weatherService.getWeatherForecast(LOCATION)


                val expectedDaysForecast = WeatherDaysForecast(LOCATION, listOf(DAY_FORECAST_SOLDEN, secondDayForecastSolden))

                assertEquals(expectedDaysForecast, actualForecast)
            }

        @Test
        fun `should throw exception when error response from weather api`() =
            runBlocking {
                `when`(weatherApiClient.fetchWeatherForecast("Sölden", 3))
                    .thenThrow(WeatherApiException("Something went wrong"))

                val exception = assertThrows<WeatherApiException> { weatherService.getWeatherForecast(LOCATION) }

                assertEquals("Something went wrong", exception.message)
            }
    }

    @Nested
    inner class GetWeatherHistory {

        @Test
        fun `should return list of weather history when successful response from weather api`() =
            runBlocking {
                `when`(weatherApiClient.fetchWeatherHistory("Sölden")).thenReturn(WEATHER_FORECAST_HISTORY_SOLDEN)
                `when`(weatherApiClient.fetchWeatherHistory("Kitzbühel"))
                    .thenReturn(WEATHER_FORECAST_HISTORY_KITZBUHEL)

                val actualHistory = weatherService.getWeatherHistory(LOCATIONS)

                assertEquals(WEATHER_HISTORY, actualHistory)
            }

        @Test
        fun `should throw exception when error response from weather api`() =
            runBlocking {
                `when`(weatherApiClient.fetchWeatherHistory("Sölden")).thenReturn(WEATHER_FORECAST_HISTORY_SOLDEN)
                `when`(weatherApiClient.fetchWeatherHistory("Kitzbühel"))
                    .thenThrow(WeatherApiException("Something went wrong"))

                val exception = assertThrows<WeatherApiException> { weatherService.getWeatherHistory(LOCATIONS) }

                assertEquals("Something went wrong", exception.message)
            }
    }
}