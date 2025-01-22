package skiweather.model.weather

import skiweather.model.alert.AlertList

data class WeatherForecast(
    val location: WeatherLocation,
    val forecast: ForecastList,
    val alerts: AlertList
)
