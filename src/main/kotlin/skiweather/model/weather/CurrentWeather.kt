package skiweather.model.weather

data class CurrentWeather(
    val tempC: Double,
    val condition: WeatherCondition,
    val windKph: Double,
    val humidity: Int
)
