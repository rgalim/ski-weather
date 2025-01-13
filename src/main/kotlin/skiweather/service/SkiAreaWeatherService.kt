package skiweather.service

import skiweather.model.weather.DayForecast
import skiweather.model.weather.HourData
import skiweather.model.weather.SkiAreaWeather
import skiweather.model.weather.WeatherForecast
import skiweather.utils.Constants.DATE_TIME_FORMATTER
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.LocalTime

class SkiAreaWeatherService {

    fun convertToSkiAreaWeather(weather: WeatherForecast): SkiAreaWeather {
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
            10.0, // TODO: calculateWeeklySnowfall()
            avgWind,
            avgVisibility,
            avgHumidity,
            avgCloud,
            avgUv
        )
    }

    private fun isDaySkiTime(timeString: String): Boolean {
        val dateTime = LocalDateTime.parse(timeString, DATE_TIME_FORMATTER)
        val time = dateTime.toLocalTime()

        // The daytime interval for skiing 8:00 - 18:00
        val startTime = LocalTime.of(7, 59)
        val endTime = LocalTime.of(18, 1)

        return time.isAfter(startTime) && time.isBefore(endTime)
    }

    private fun roundDouble(value: Double): Double {
        return BigDecimal(value)
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()
    }
}