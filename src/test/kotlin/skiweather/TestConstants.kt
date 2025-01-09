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
    val WEATHER_SOLDEN = Weather(
        WeatherLocation("Sölden", "Tirol", "Österreich"),
        CurrentWeather(-6.2, WeatherCondition("Partly Cloudy", "//icon.png"), 24.5, 92),
        WeatherForecast(listOf(
            DayForecast(
                TotalDayData(-5.4, 0.07, 73),
                listOf(
                    HourData("2025-01-08 00:00", -10.0, 16.6, 0.0),
                    HourData("2025-01-08 01:00", -10.4, 18.4, 0.0)
                ))
        ))
    )
    const val WEATHER_API_RESPONSE =
        // language=JSON
        """{
    "location": {
        "name": "Sölden",
        "region": "Tirol",
        "country": "Österreich",
        "lat": 46.9667,
        "lon": 11.0,
        "tz_id": "Europe/Vienna",
        "localtime_epoch": 1736367560,
        "localtime": "2025-01-08 21:19"
    },
    "current": {
        "temp_c": -6.2,
        "condition": {
            "text": "Partly Cloudy",
            "icon": "//icon.png"
        },
        "wind_kph": 24.5,
        "humidity": 92
    },
    "forecast": {
        "forecastday": [
            {
                "day": {
                    "avgtemp_c": -5.4,
                    "totalsnow_cm": 0.07,
                    "avghumidity": 73
                },
                "hour": [
                    {
                        "time": "2025-01-08 00:00",
                        "temp_c": -10.0,
                        "wind_kph": 16.6,
                        "snow_cm": 0.0
                    },
                    {
                        "time": "2025-01-08 01:00",
                        "temp_c": -10.4,
                        "wind_kph": 18.4,
                        "snow_cm": 0.0
                    }
                ]
            }
        ]
    }
}"""
}