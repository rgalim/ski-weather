package skiweather.model.weather

data class DayForecast(
    val day: TotalDayData,
    val hour: List<HourData>,
)
