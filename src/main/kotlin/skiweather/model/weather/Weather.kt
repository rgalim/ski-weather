package skiweather.model.weather

data class Weather(
    val location: WeatherLocation,
    val current: CurrentWeather,
    val forecast: WeatherForecast,
)
