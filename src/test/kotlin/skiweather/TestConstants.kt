package skiweather

import skiweather.config.AppConfig

object TestConstants {
    val MOCKED_APP_CONFIG =
        AppConfig(
            "http://weather-api",
            "apiKey"
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