package skiweather.model.weather

data class WeatherForecast(
    val location: WeatherLocation,
    val forecast: ForecastList,
)
