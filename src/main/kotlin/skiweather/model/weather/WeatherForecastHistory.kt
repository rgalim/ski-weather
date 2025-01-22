package skiweather.model.weather


data class WeatherForecastHistory(
    val location: WeatherLocation,
    val forecast: ForecastList
)
