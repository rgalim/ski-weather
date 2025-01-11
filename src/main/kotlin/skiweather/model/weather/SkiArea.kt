package skiweather.model.weather

data class SkiArea(
    val location: String,
    val temperature: Double,
    val dailySnowfall: Double,
    val weeklySnowfall: Double,
    val wind: Double,
    val visibility: Double,
    val humidity: Int,
    val cloud: Int,
    val uv: Int
)
