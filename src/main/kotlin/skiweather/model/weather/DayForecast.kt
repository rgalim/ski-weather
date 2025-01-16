package skiweather.model.weather

data class DayForecast(
    val date: String,
    val day: DayData,
    val hour: List<HourData>,
)
