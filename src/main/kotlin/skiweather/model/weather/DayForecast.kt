package skiweather.model.weather

data class DayForecast(
    val date: String,
    val day: TotalDayData,
    val hour: List<HourData>,
)
