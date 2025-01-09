package skiweather.model.weather

data class HourData(
    val time: String,
    val tempC: Double,
    val windKph: Double,
    val snowCm: Double
)
