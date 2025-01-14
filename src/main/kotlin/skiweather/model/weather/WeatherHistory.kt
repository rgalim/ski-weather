package skiweather.model.weather

data class WeatherHistory(
    val location: WeatherLocation,
    val history: List<DayHistory>
)
