package skiweather.model.weather

data class WeatherDaysForecast(
    val location: String,
    val daysForecast: List<DayForecast>,
)
