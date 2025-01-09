package skiweather.service

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import skiweather.TestConstants.LOCATIONS
import skiweather.client.WeatherApiClient
import skiweather.TestConstants.WEATHER_SOLDEN
import skiweather.model.weather.*

class WeatherServiceTest {

    private val weatherApiClient = mock(WeatherApiClient::class.java)
    private val locationService = mock(LocationService::class.java)
    private val weatherService = WeatherService(locationService, weatherApiClient)

    @Test
    fun `should return list of weather objects when successful response from weather api`() =
        runBlocking {
            val weatherKitzbuhel = Weather(
                WeatherLocation("Kitzbühel", "Tirol", "Österreich"),
                CurrentWeather(-8.0, WeatherCondition("Cloudy", "//icon.png"), 14.3, 87),
                WeatherForecast(listOf(
                    DayForecast(
                        TotalDayData(-0.4, 5.69, 90),
                        listOf(
                            HourData("2025-01-08 00:00", -2.2, 10.6, 0.0),
                            HourData("2025-01-08 01:00", -2.6, 10.1, 0.0)
                        ))
                ))
            )

            `when`(locationService.getLocations()).thenReturn(LOCATIONS)
            `when`(weatherApiClient.getWeather("Sölden")).thenReturn(WEATHER_SOLDEN)
            `when`(weatherApiClient.getWeather("Kitzbühel")).thenReturn(weatherKitzbuhel)

            val actualWeather = weatherService.getWeather()

            assertEquals(listOf(WEATHER_SOLDEN, weatherKitzbuhel), actualWeather)
        }

    @Test
    fun `should throw exception when list of locations is empty`() =
        runBlocking {
            `when`(locationService.getLocations()).thenReturn(listOf())

            val exception = assertThrows<IllegalArgumentException> { weatherService.getWeather() }

            assertEquals("The list of locations must not be empty", exception.message)

            verifyNoInteractions(weatherApiClient)
        }
}