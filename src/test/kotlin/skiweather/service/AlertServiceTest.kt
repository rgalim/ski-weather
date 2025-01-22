package skiweather.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import skiweather.TestConstants
import skiweather.TestConstants.ALERT_KITZBUHEL
import skiweather.TestConstants.ALERT_SOLDEN
import skiweather.model.alert.AlertList
import skiweather.model.weather.ForecastList
import skiweather.model.weather.WeatherForecast
import skiweather.model.weather.WeatherLocation

class AlertServiceTest {

    private val alertService = AlertService()

    @Test
    fun `should return alert list when weather forecast has unique alerts`() {
        val weatherForecastSolden = WeatherForecast(
            WeatherLocation("Sölden", "Tirol", "Österreich"),
            ForecastList(listOf(TestConstants.DAY_FORECAST_SOLDEN)),
            AlertList(listOf(ALERT_SOLDEN)))
        val weatherForecastKitzbuhel = WeatherForecast(
            WeatherLocation("Kitzbühel", "Tirol", "Österreich"),
            ForecastList(listOf(TestConstants.DAY_FORECAST_KITZBUHEL)),
            AlertList(listOf(ALERT_KITZBUHEL)))

        val alerts = alertService.getAlerts(listOf(weatherForecastSolden, weatherForecastKitzbuhel))

        assertEquals(2, alerts.size)
    }

    @Test
    fun `should return filtered alert list when weather forecast has duplicated alerts`() {
        val weatherForecastSolden = WeatherForecast(
            WeatherLocation("Sölden", "Tirol", "Österreich"),
            ForecastList(listOf(TestConstants.DAY_FORECAST_SOLDEN)),
            AlertList(listOf(ALERT_SOLDEN)))
        val weatherForecastKitzbuhel = WeatherForecast(
            WeatherLocation("Kitzbühel", "Tirol", "Österreich"),
            ForecastList(listOf(TestConstants.DAY_FORECAST_KITZBUHEL)),
            AlertList(listOf(ALERT_SOLDEN)))

        val alerts = alertService.getAlerts(listOf(weatherForecastSolden, weatherForecastKitzbuhel))

        assertEquals(1, alerts.size)
    }
}