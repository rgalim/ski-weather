package skiweather.service

import skiweather.model.weather.*
import skiweather.utils.CalculationUtils.Companion.roundDouble
import skiweather.utils.Constants.DATE_TIME_FORMATTER
import java.time.LocalDateTime
import java.time.LocalTime

class SkiAreaWeatherService {

    fun convertToSkiAreaWeather(weather: WeatherForecast, totalWeeklySnowCm: Double): SkiAreaWeather {
        val dailyForecast: List<DayForecast> = weather.forecast.forecastday
        require(dailyForecast.isNotEmpty()) { "Daily forecast must not be empty" }

        val currentDayForecast: DayForecast = dailyForecast[0]
        val hourDataList: List<HourData> = currentDayForecast.hour
        require(hourDataList.isNotEmpty()) { "Hour data list must not be empty" }

        val dayTimeHourData = hourDataList.filter { data -> isDaySkiTime(data.time) }
        val dayTimeHoursSize = dayTimeHourData.size

        val avgTemp = roundDouble(dayTimeHourData.sumOf { it.tempC } / dayTimeHoursSize)
        val avgWind = roundDouble(dayTimeHourData.sumOf { it.windKph } / dayTimeHoursSize)
        val avgVisibility = roundDouble(dayTimeHourData.sumOf { it.visKm } / dayTimeHoursSize)
        val avgHumidity = dayTimeHourData.sumOf { it.humidity } / dayTimeHoursSize
        val avgCloud = dayTimeHourData.sumOf { it.cloud } / dayTimeHoursSize
        val avgUv = roundDouble(dayTimeHourData.sumOf { it.uv } / dayTimeHoursSize)

        return SkiAreaWeather(
            weather.location.name,
            avgTemp,
            currentDayForecast.day.totalsnowCm,
            roundDouble(totalWeeklySnowCm),
            avgWind,
            avgVisibility,
            avgHumidity,
            avgCloud,
            avgUv
        )
    }

    fun convertToHistoryMap(weatherHistoryList: List<WeatherHistory>): Map<String, Double> {
        return weatherHistoryList.associate {
            weatherHistory -> weatherHistory.location.name to weatherHistory.history.sumOf { it.totalSnowCm }
        }
    }

    private fun isDaySkiTime(timeString: String): Boolean {
        val dateTime = LocalDateTime.parse(timeString, DATE_TIME_FORMATTER)
        val time = dateTime.toLocalTime()

        // The daytime interval for skiing 8:00 - 18:00
        val startTime = LocalTime.of(7, 59)
        val endTime = LocalTime.of(18, 1)

        return time.isAfter(startTime) && time.isBefore(endTime)
    }
}