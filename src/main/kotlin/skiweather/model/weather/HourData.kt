package skiweather.model.weather

data class HourData(
    val time: String,
    val tempC: Double,
    val windKph: Double,
    val humidity: Int,
    val cloud: Int,
    val visKm: Double,
    val uv: Int
)
