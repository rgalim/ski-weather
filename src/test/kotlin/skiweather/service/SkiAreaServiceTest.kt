package skiweather.service

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import skiweather.TestConstants.HISTORY_MAP
import skiweather.TestConstants.LOCATIONS
import skiweather.TestConstants.SKI_AREA_WEATHER_KITZBUHEL
import skiweather.TestConstants.SKI_AREA_WEATHER_SOLDEN
import skiweather.TestConstants.WEATHER_FORECAST_KITZBUHEL
import skiweather.TestConstants.WEATHER_FORECAST_SOLDEN
import skiweather.TestConstants.WEATHER_HISTORY
import skiweather.exception.WeatherApiException
import skiweather.model.weather.*

class SkiAreaServiceTest {

    private val locationService = mock(LocationService::class.java)
    private val weatherService = mock(WeatherService::class.java)
    private val skiAreaWeatherService = mock(SkiAreaWeatherService::class.java)
    private val scoreService = mock(ScoreService::class.java)
    private val skiAreaService = SkiAreaService(locationService, weatherService, skiAreaWeatherService, scoreService)

    @Test
    fun `should return sorted ski areas when successful forecast response`() =
        runBlocking {
            `when`(locationService.getLocations()).thenReturn(LOCATIONS)
            `when`(weatherService.getWeatherHistory(LOCATIONS)).thenReturn(WEATHER_HISTORY)
            `when`(weatherService.getWeatherForecast(LOCATIONS))
                .thenReturn(listOf(WEATHER_FORECAST_SOLDEN, WEATHER_FORECAST_KITZBUHEL))
            `when`(skiAreaWeatherService.convertToHistoryMap(WEATHER_HISTORY)).thenReturn(HISTORY_MAP)
            `when`(skiAreaWeatherService.convertToSkiAreaWeather(WEATHER_FORECAST_SOLDEN, 0.2))
                .thenReturn(SKI_AREA_WEATHER_SOLDEN)
            `when`(skiAreaWeatherService.convertToSkiAreaWeather(WEATHER_FORECAST_KITZBUHEL, 4.7))
                .thenReturn(SKI_AREA_WEATHER_KITZBUHEL)
            `when`(scoreService.scoreSkiArea(SKI_AREA_WEATHER_SOLDEN)).thenReturn(70.0)
            `when`(scoreService.scoreSkiArea(SKI_AREA_WEATHER_KITZBUHEL)).thenReturn(50.0)

            val actualSkiAreas: List<SkiArea> = skiAreaService.getSkiAreas()

            val skiAreaSolden = SkiArea(SKI_AREA_WEATHER_SOLDEN, 70.0, 0.0)
            val skiAreaKitzbuhel = SkiArea(SKI_AREA_WEATHER_KITZBUHEL, 50.0, 0.0)

            assertEquals(skiAreaSolden, actualSkiAreas[0])
            assertEquals(skiAreaKitzbuhel, actualSkiAreas[1])
        }

    @Test
    fun `should throw exception when location list is empty`() =
        runBlocking {
            `when`(locationService.getLocations()).thenReturn(listOf())

            val exception = assertThrows<IllegalArgumentException> { skiAreaService.getSkiAreas() }

            assertEquals("The list of locations must not be empty", exception.message)

            verifyNoInteractions(skiAreaWeatherService)
            verifyNoInteractions(scoreService)
            verifyNoInteractions(weatherService)
        }

    @Test
    fun `should throw exception when error history response`() =
        runBlocking {
            `when`(locationService.getLocations()).thenReturn(LOCATIONS)
            `when`(weatherService.getWeatherHistory(LOCATIONS)).thenThrow(WeatherApiException("Something went wrong"))

            val exception = assertThrows<WeatherApiException> { skiAreaService.getSkiAreas() }

            assertEquals("Something went wrong", exception.message)

            verifyNoInteractions(skiAreaWeatherService)
            verifyNoInteractions(scoreService)
        }

    @Test
    fun `should throw exception when error forecast response`() =
        runBlocking {
            `when`(locationService.getLocations()).thenReturn(LOCATIONS)
            `when`(weatherService.getWeatherHistory(LOCATIONS)).thenReturn(WEATHER_HISTORY)
            `when`(skiAreaWeatherService.convertToHistoryMap(WEATHER_HISTORY)).thenReturn(HISTORY_MAP)
            `when`(weatherService.getWeatherForecast(LOCATIONS)).thenThrow(WeatherApiException("Something went wrong"))

            val exception = assertThrows<WeatherApiException> { skiAreaService.getSkiAreas() }

            assertEquals("Something went wrong", exception.message)

            verifyNoInteractions(scoreService)
        }

    @Test
    fun `should throw exception when failed to convert ski area weather`() =
        runBlocking {
            `when`(locationService.getLocations()).thenReturn(LOCATIONS)
            `when`(weatherService.getWeatherHistory(LOCATIONS)).thenReturn(WEATHER_HISTORY)
            `when`(skiAreaWeatherService.convertToHistoryMap(WEATHER_HISTORY)).thenReturn(HISTORY_MAP)
            `when`(weatherService.getWeatherForecast(LOCATIONS)).thenReturn(listOf(WEATHER_FORECAST_SOLDEN))
            `when`(skiAreaWeatherService.convertToSkiAreaWeather(WEATHER_FORECAST_SOLDEN, 0.2))
                .thenThrow(IllegalArgumentException("Daily forecast must not be empty"))

            val exception = assertThrows<IllegalArgumentException> { skiAreaService.getSkiAreas() }

            assertEquals("Daily forecast must not be empty", exception.message)

            verifyNoInteractions(scoreService)
        }

    @Test
    fun `should throw exception when failed to score ski area weather`() =
        runBlocking {
            `when`(locationService.getLocations()).thenReturn(LOCATIONS)
            `when`(weatherService.getWeatherHistory(LOCATIONS)).thenReturn(WEATHER_HISTORY)
            `when`(skiAreaWeatherService.convertToHistoryMap(WEATHER_HISTORY)).thenReturn(HISTORY_MAP)
            `when`(weatherService.getWeatherForecast(LOCATIONS)).thenReturn(listOf(WEATHER_FORECAST_SOLDEN))
            `when`(skiAreaWeatherService.convertToSkiAreaWeather(WEATHER_FORECAST_SOLDEN, 0.2))
                .thenReturn(SKI_AREA_WEATHER_SOLDEN)
            `when`(scoreService.scoreSkiArea(SKI_AREA_WEATHER_SOLDEN))
                .thenThrow(IllegalArgumentException("Something went wrong"))

            val exception = assertThrows<IllegalArgumentException> { skiAreaService.getSkiAreas() }

            assertEquals("Something went wrong", exception.message)
        }
}