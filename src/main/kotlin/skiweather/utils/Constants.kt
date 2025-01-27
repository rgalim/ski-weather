package skiweather.utils

import java.time.format.DateTimeFormatter

object Constants {
    const val FORECAST_URL = "/forecast.json"
    const val HISTORY_URL = "/history.json"
    const val MAX_SCORE = 150.0
    const val ONE_DAY = 1
    const val THREE_DAYS = 3

    val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
}