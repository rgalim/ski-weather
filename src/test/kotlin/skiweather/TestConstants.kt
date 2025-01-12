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
    val SKI_AREA_WEATHER_SOLDEN = SkiAreaWeather(
        "Sölden",
        -13.8,
        0.4,
        10.0,
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
        10.0,
        9.09,
        7.82,
        86,
        39,
        0.57
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
        "localtime_epoch": 1736600270,
        "localtime": "2025-01-11 13:57"
    },
    "current": {},
    "forecast": {
        "forecastday": [
            {
                "date": "2025-01-11",
                "day": {
                    "totalsnow_cm": 0.4
                },
                "hour": [
                    {
                        "time": "2025-01-11 00:00",
                        "temp_c": -11.9,
                        "wind_kph": 5.8,
                        "humidity": 95,
                        "cloud": 71,
                        "vis_km": 5.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 01:00",
                        "temp_c": -11.7,
                        "wind_kph": 6.1,
                        "humidity": 95,
                        "cloud": 71,
                        "vis_km": 7.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 02:00",
                        "temp_c": -11.8,
                        "wind_kph": 7.6,
                        "humidity": 95,
                        "cloud": 70,
                        "vis_km": 10.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 03:00",
                        "temp_c": -13.4,
                        "wind_kph": 8.3,
                        "humidity": 95,
                        "cloud": 76,
                        "vis_km": 10.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 04:00",
                        "temp_c": -14.8,
                        "wind_kph": 9.4,
                        "humidity": 97,
                        "cloud": 86,
                        "vis_km": 0.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 05:00",
                        "temp_c": -15.5,
                        "wind_kph": 9.7,
                        "humidity": 97,
                        "cloud": 86,
                        "vis_km": 2.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 06:00",
                        "temp_c": -15.3,
                        "wind_kph": 9.0,
                        "humidity": 97,
                        "cloud": 83,
                        "vis_km": 2.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 07:00",
                        "temp_c": -14.8,
                        "wind_kph": 9.0,
                        "humidity": 97,
                        "cloud": 83,
                        "vis_km": 2.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 08:00",
                        "temp_c": -15.0,
                        "wind_kph": 8.6,
                        "humidity": 97,
                        "cloud": 82,
                        "vis_km": 2.0,
                        "uv": 0.0
                    },
                    {
                        "time": "2025-01-11 09:00",
                        "temp_c": -13.8,
                        "wind_kph": 8.6,
                        "humidity": 95,
                        "cloud": 72,
                        "vis_km": 2.0,
                        "uv": 0.4
                    },
                    {
                        "time": "2025-01-11 10:00",
                        "temp_c": -12.2,
                        "wind_kph": 8.3,
                        "humidity": 91,
                        "cloud": 88,
                        "vis_km": 10.0,
                        "uv": 0.7
                    },
                    {
                        "time": "2025-01-11 11:00",
                        "temp_c": -10.8,
                        "wind_kph": 9.0,
                        "humidity": 82,
                        "cloud": 17,
                        "vis_km": 10.0,
                        "uv": 1.1
                    },
                    {
                        "time": "2025-01-11 12:00",
                        "temp_c": -10.4,
                        "wind_kph": 9.7,
                        "humidity": 78,
                        "cloud": 13,
                        "vis_km": 10.0,
                        "uv": 1.4
                    },
                    {
                        "time": "2025-01-11 13:00",
                        "temp_c": 1.0,
                        "wind_kph": 10.1,
                        "humidity": 55,
                        "cloud": 0,
                        "vis_km": 10.0,
                        "uv": 1.3
                    },
                    {
                        "time": "2025-01-11 14:00",
                        "temp_c": -10.5,
                        "wind_kph": 10.4,
                        "humidity": 75,
                        "cloud": 11,
                        "vis_km": 10.0,
                        "uv": 0.9
                    },
                    {
                        "time": "2025-01-11 15:00",
                        "temp_c": -11.5,
                        "wind_kph": 10.4,
                        "humidity": 83,
                        "cloud": 19,
                        "vis_km": 10.0,
                        "uv": 0.5
                    },
                    {
                        "time": "2025-01-11 16:00",
                        "temp_c": -14.7,
                        "wind_kph": 9.0,
                        "humidity": 94,
                        "cloud": 53,
                        "vis_km": 2.0,
                        "uv": 0.0
                    },
                    {
                        "time": "2025-01-11 17:00",
                        "temp_c": -18.3,
                        "wind_kph": 7.6,
                        "humidity": 94,
                        "cloud": 60,
                        "vis_km": 10.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 18:00",
                        "temp_c": -19.1,
                        "wind_kph": 7.9,
                        "humidity": 91,
                        "cloud": 83,
                        "vis_km": 10.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 19:00",
                        "temp_c": -19.4,
                        "wind_kph": 8.3,
                        "humidity": 89,
                        "cloud": 88,
                        "vis_km": 10.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 20:00",
                        "temp_c": -20.1,
                        "wind_kph": 8.6,
                        "humidity": 88,
                        "cloud": 80,
                        "vis_km": 10.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 21:00",
                        "temp_c": -20.5,
                        "wind_kph": 7.9,
                        "humidity": 89,
                        "cloud": 74,
                        "vis_km": 10.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 22:00",
                        "temp_c": -20.4,
                        "wind_kph": 8.3,
                        "humidity": 81,
                        "cloud": 17,
                        "vis_km": 10.0,
                        "uv": 0
                    },
                    {
                        "time": "2025-01-11 23:00",
                        "temp_c": -19.8,
                        "wind_kph": 8.3,
                        "humidity": 66,
                        "cloud": 5,
                        "vis_km": 10.0,
                        "uv": 0
                    }
                ]
            }
        ]
    }
}"""
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