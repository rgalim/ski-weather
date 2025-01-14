package skiweather

import skiweather.config.AppConfig
import skiweather.model.weather.*

object TestConstants {
    val MOCKED_APP_CONFIG =
        AppConfig(
            "http://weather-api",
            "apiKey"
        )
    val LOCATIONS = listOf("Sölden", "Kitzbühel")
    val WEATHER_FORECAST_SOLDEN = WeatherForecast(
        WeatherLocation("Sölden", "Tirol", "Österreich"),
        ForecastList(
            listOf(
                DayForecast(
                    "2025-01-11",
                    TotalDayData(0.07),
                    listOf(
                        HourData("2025-01-11 00:00", -11.9, 5.8, 95, 71, 5.0, 0.0),
                        HourData("2025-01-11 01:00", -11.7, 6.1, 95, 71, 7.0, 0.0)
                    )
                )
            )
        )
    )
    val WEATHER_FORECAST_KITZBUHEL = WeatherForecast(
        WeatherLocation("Kitzbühel", "Tirol", "Österreich"),
        ForecastList(listOf(
            DayForecast(
                "2025-01-11",
                TotalDayData(0.04),
                listOf(
                    HourData("2025-01-11 00:00", -11.3, 5.8, 81, 3, 10.0, 0.0),
                    HourData("2025-01-11 01:00", -12.4, 6.5, 84, 2, 10.0, 0.0)
                ))
        ))
    )
    val WEATHER_FORECAST_WITH_HISTORY_SOLDEN = WeatherForecast(
        WeatherLocation("Sölden", "Tirol", "Österreich"),
        ForecastList(
            listOf(
                DayForecast(
                    "2025-01-10",
                    TotalDayData(0.07),
                    listOf()
                ),
                DayForecast(
                    "2025-01-11",
                    TotalDayData(0.13),
                    listOf()
                )
            )
        )
    )
    val WEATHER_FORECAST_WITGH_HISTORY_KITZBUHEL = WeatherForecast(
        WeatherLocation("Kitzbühel", "Tirol", "Österreich"),
        ForecastList(
            listOf(
                DayForecast(
                    "2025-01-10",
                    TotalDayData(1.2),
                    listOf()
                ),
                DayForecast(
                    "2025-01-11",
                    TotalDayData(3.5),
                    listOf()
                )
            )
        )
    )
    val WEATHER_HISTORY = listOf(
        WeatherHistory(
            WeatherLocation("Sölden", "Tirol", "Österreich"),
            listOf(DayHistory("2025-01-10", 0.07), DayHistory("2025-01-11", 0.13))),
        WeatherHistory(
            WeatherLocation("Kitzbühel", "Tirol", "Österreich"),
            listOf(DayHistory("2025-01-10", 1.2), DayHistory("2025-01-11", 3.5))),
    )
    val HISTORY_MAP = mapOf(Pair("Sölden", 0.2), Pair("Kitzbühel", 4.7))
    val SKI_AREA_WEATHER_SOLDEN = SkiAreaWeather(
        "Sölden",
        -13.8,
        0.4,
        0.2,
        9.09,
        7.82,
        86,
        39,
        0.57
    )
    val SKI_AREA_WEATHER_KITZBUHEL = SkiAreaWeather(
        "Kitzbühel",
        -13.8,
        0.4,
        4.7,
        9.09,
        7.82,
        86,
        39,
        0.57
    )
    const val WEATHER_API_ERROR_RESPONSE =
        // language=JSON
        """
        {
    "error": {
        "code": 2008,
        "message": "API key has been disabled."
    }
}"""
}