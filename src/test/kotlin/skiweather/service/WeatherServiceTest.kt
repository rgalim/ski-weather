package skiweather.service

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import skiweather.TestConstants.LOCATIONS
import skiweather.client.WeatherApiClient
import skiweather.exception.WeatherApiException
import skiweather.model.weather.*

class WeatherServiceTest {

    private val weatherApiClient = mock(WeatherApiClient::class.java)
    private val locationService = mock(LocationService::class.java)
    private val weatherService = WeatherService(locationService, weatherApiClient)

    @Test
    fun `should return list of weather objects when successful response from weather api`() =
        runBlocking {
            val weatherForecastSolden = WeatherForecast(
                WeatherLocation("Sölden", "Tirol", "Österreich"),
                ForecastList(listOf(
                    DayForecast(
                        "2025-01-11",
                        TotalDayData(0.07),
                        listOf(
                            HourData("2025-01-11 00:00", -11.9, 5.8, 95, 71, 5.0, 0),
                            HourData("2025-01-11 01:00", -11.7, 6.1, 95, 71, 7.0, 0)
                        ))
                ))
            )
            val weatherForecastKitzbuhel = WeatherForecast(
                WeatherLocation("Kitzbühel", "Tirol", "Österreich"),
                ForecastList(listOf(
                    DayForecast(
                        "2025-01-11",
                        TotalDayData(0.04),
                        listOf(
                            HourData("2025-01-11 00:00", -11.3, 5.8, 81, 3, 10.0, 0),
                            HourData("2025-01-11 01:00", -12.4, 6.5, 84, 2, 10.0, 0)
                        ))
                ))
            )

            `when`(locationService.getLocations()).thenReturn(LOCATIONS)
            `when`(weatherApiClient.getWeatherForecast("Sölden")).thenReturn(weatherForecastSolden)
            `when`(weatherApiClient.getWeatherForecast("Kitzbühel")).thenReturn(weatherForecastKitzbuhel)

            val actualWeather = weatherService.getWeather()

            assertEquals(listOf(weatherForecastSolden, weatherForecastKitzbuhel), actualWeather)
        }

    @Test
    fun `should throw exception when list of locations is empty`() =
        runBlocking {
            `when`(locationService.getLocations()).thenReturn(listOf())

            val exception = assertThrows<IllegalArgumentException> { weatherService.getWeather() }

            assertEquals("The list of locations must not be empty", exception.message)

            verifyNoInteractions(weatherApiClient)
        }

    @Test
    fun `should throw exception when error response from weather api`() =
        runBlocking {
            val weatherForecastSolden = WeatherForecast(
                WeatherLocation("Sölden", "Tirol", "Österreich"),
                ForecastList(listOf(
                    DayForecast(
                        "2025-01-11",
                        TotalDayData(0.07),
                        listOf(
                            HourData("2025-01-11 00:00", -11.9, 5.8, 95, 71, 5.0, 0),
                            HourData("2025-01-11 01:00", -11.7, 6.1, 95, 71, 7.0, 0)
                        ))
                ))
            )

            `when`(locationService.getLocations()).thenReturn(LOCATIONS)
            `when`(weatherApiClient.getWeatherForecast("Sölden")).thenReturn(weatherForecastSolden)
            `when`(weatherApiClient.getWeatherForecast("Kitzbühel")).thenThrow(WeatherApiException("Something went wrong"))

            val exception = assertThrows<WeatherApiException> { weatherService.getWeather() }

            assertEquals("Something went wrong", exception.message)
        }
}