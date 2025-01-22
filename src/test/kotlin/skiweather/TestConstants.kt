package skiweather

import skiweather.config.AppConfig
import skiweather.model.alert.Alert
import skiweather.model.alert.AlertList
import skiweather.model.weather.*

object TestConstants {
    val MOCKED_APP_CONFIG =
        AppConfig(
            "http://weather-api",
            "apiKey"
        )
    val LOCATIONS = listOf("Sölden", "Kitzbühel")
    val WEATHER_CONDITION = WeatherCondition("Sunny", "//icon")
    val DAY_FORECAST_SOLDEN = DayForecast(
        "2025-01-11",
        DayData(0.07, WEATHER_CONDITION),
        listOf(
            HourData("2025-01-11 00:00", -11.9, 5.8, 95, 71, 5.0, 0.0),
            HourData("2025-01-11 01:00", -11.7, 6.1, 95, 71, 7.0, 0.0)
        )
    )
    val DAY_FORECAST_KITZBUHEL = DayForecast(
        "2025-01-11",
        DayData(0.04, WEATHER_CONDITION),
        listOf(
            HourData("2025-01-11 00:00", -11.3, 5.8, 81, 3, 10.0, 0.0),
            HourData("2025-01-11 01:00", -12.4, 6.5, 84, 2, 10.0, 0.0)
        ))
    val WEATHER_FORECAST_SOLDEN = WeatherForecast(
        WeatherLocation("Sölden", "Tirol", "Österreich"),
        ForecastList(listOf(DAY_FORECAST_SOLDEN)),
        AlertList(listOf())
    )
    val WEATHER_FORECAST_KITZBUHEL = WeatherForecast(
        WeatherLocation("Kitzbühel", "Tirol", "Österreich"),
        ForecastList(listOf(DAY_FORECAST_KITZBUHEL)),
        AlertList(listOf())
    )
    val WEATHER_FORECAST_HISTORY_SOLDEN = WeatherForecastHistory(
        WeatherLocation("Sölden", "Tirol", "Österreich"),
        ForecastList(
            listOf(
                DayForecast(
                    "2025-01-10",
                    DayData(0.07, WEATHER_CONDITION),
                    listOf()
                ),
                DayForecast(
                    "2025-01-11",
                    DayData(0.13, WEATHER_CONDITION),
                    listOf()
                )
            )
        )
    )
    val WEATHER_FORECAST_HISTORY_KITZBUHEL = WeatherForecastHistory(
        WeatherLocation("Kitzbühel", "Tirol", "Österreich"),
        ForecastList(
            listOf(
                DayForecast(
                    "2025-01-10",
                    DayData(1.2, WEATHER_CONDITION),
                    listOf()
                ),
                DayForecast(
                    "2025-01-11",
                    DayData(3.5, WEATHER_CONDITION),
                    listOf()
                )
            )
        )
    )
    val ALERT_SOLDEN = Alert(
        "(Alert) Official WARNING of FROST",
        "Minor",
        "",
        "Likely",
        "frost",
        "2025-01-22T12:55:00+01:00",
        "2025-01-23T12:00:00+01:00",
        "Description1",
        "Instruction1"
    )
    val ALERT_KITZBUHEL = Alert(
        "(Alert) Official WARNING of ICY SURFACES",
        "Minor",
        "",
        "Likely",
        "icy surfaces",
        "2025-01-22T12:55:00+01:00",
        "2025-01-23T12:00:00+01:00",
        "Description2",
        "Instruction2"
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