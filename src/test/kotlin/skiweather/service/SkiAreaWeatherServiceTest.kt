package skiweather.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import skiweather.TestConstants.WEATHER_HISTORY
import skiweather.TestConstants.HISTORY_MAP
import skiweather.model.weather.*

class SkiAreaWeatherServiceTest {

    private val skiAreaWeatherService = SkiAreaWeatherService()

    @Nested
    inner class ConvertToSkiAreaWeather {

        @Test
        fun `should convert weather forecast to ski area weather when forecast has correct format`() {
            val weatherForecast = WeatherForecast(
                WeatherLocation("Sölden", "Tirol", "Österreich"),
                ForecastList(listOf(
                    DayForecast(
                        "2025-01-11",
                        TotalDayData(0.4),
                        listOf(
                            HourData("2025-01-11 00:00", -11.9, 5.8, 95, 71, 5.0, 0.0),
                            HourData("2025-01-11 01:00", -11.7, 6.1, 95, 71, 7.0, 0.0),
                            HourData("2025-01-11 02:00", -11.8, 7.6, 95, 70, 10.0, 0.0),
                            HourData("2025-01-11 03:00", -13.4, 8.3, 95, 76, 10.0, 0.0),
                            HourData("2025-01-11 04:00", -14.8, 9.4, 97, 86, 0.0, 0.0),
                            HourData("2025-01-11 05:00", -15.5, 9.7, 97, 86, 2.0, 0.0),
                            HourData("2025-01-11 06:00", -15.3, 9.0, 97, 83, 2.0, 0.0),
                            HourData("2025-01-11 07:00", -14.8, 9.0, 97, 83, 2.0, 0.0),
                            HourData("2025-01-11 08:00", -15.0, 8.6, 97, 82, 2.0, 0.0),
                            HourData("2025-01-11 09:00", -13.8, 8.6, 95, 72, 2.0, 0.4),
                            HourData("2025-01-11 10:00", -12.6, 9.0, 92, 45, 10.0, 0.7),
                            HourData("2025-01-11 11:00", -11.5, 9.7, 82, 17, 10.0, 1.1),
                            HourData("2025-01-11 12:00", -11.0, 9.7, 75, 10, 10.0, 1.4),
                            HourData("2025-01-11 13:00", -10.9, 10.1, 71, 8, 10.0, 1.3),
                            HourData("2025-01-11 14:00", -11.2, 10.4, 73, 9, 10.0, 0.9),
                            HourData("2025-01-11 15:00", -12.1, 10.1, 82, 18, 10.0, 0.5),
                            HourData("2025-01-11 16:00", -15.4, 9.0, 94, 44, 10.0, 0.0),
                            HourData("2025-01-11 17:00", -18.8, 7.2, 94, 66, 2.0, 0.0),
                            HourData("2025-01-11 18:00", -19.5, 7.6, 93, 58, 10.0, 0.0),
                            HourData("2025-01-11 19:00", -19.9, 8.3, 91, 81, 10.0, 0.0),
                            HourData("2025-01-11 20:00", -20.5, 8.3, 91, 77, 10.0, 0.0),
                            HourData("2025-01-11 21:00", -20.8, 8.3, 68, 25, 10.0, 0.0),
                            HourData("2025-01-11 22:00", -20.5, 8.3, 80, 16, 10.0, 0.0),
                            HourData("2025-01-11 23:00", -19.9, 8.6, 62, 4, 10.0, 0.0),
                        ))
                )))

            val expectedSkiAreaWeather = SkiAreaWeather(
                "Sölden",
                -13.8,
                0.4,
                20.0,
                9.09,
                7.82,
                86,
                39,
                0.57
            )

            val skiAreaWeather = skiAreaWeatherService.convertToSkiAreaWeather(weatherForecast, 20.0005)

            assertEquals(expectedSkiAreaWeather, skiAreaWeather)
        }

        @Test
        fun `should throw exception when daily forecast is empty`() {
            val weatherForecast = WeatherForecast(
                WeatherLocation("Sölden", "Tirol", "Österreich"),
                ForecastList(listOf()))

            val exception = assertThrows<IllegalArgumentException> { skiAreaWeatherService.convertToSkiAreaWeather(weatherForecast, 0.0) }

            assertEquals("Daily forecast must not be empty", exception.message)
        }

        @Test
        fun `should throw exception when hour data list is empty`() {
            val weatherForecast = WeatherForecast(
                WeatherLocation("Sölden", "Tirol", "Österreich"),
                ForecastList(
                    listOf(
                        DayForecast(
                            "2025-01-11",
                            TotalDayData(0.4),
                            listOf()
                        )
                    )))

            val exception = assertThrows<IllegalArgumentException> { skiAreaWeatherService.convertToSkiAreaWeather(weatherForecast, 0.0) }

            assertEquals("Hour data list must not be empty", exception.message)
        }
    }

    @Nested
    inner class ConvertToHistoryMap {

        @Test
        fun `should convert to map with sum of total snow when valid weather history list`() {
            val historyMap = skiAreaWeatherService.convertToHistoryMap(WEATHER_HISTORY)

            assertEquals(HISTORY_MAP, historyMap)
        }
    }
}