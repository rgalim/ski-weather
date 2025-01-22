package skiweather.service

import skiweather.model.alert.Alert
import skiweather.model.weather.WeatherForecast

class AlertService {

    fun getAlerts(weatherForecastList: List<WeatherForecast>): List<Alert> {
        return weatherForecastList.asSequence()
            .flatMap { weatherForecast -> weatherForecast.alerts.alert }
            /*
                TODO: implement more gradual filtering (e.g. remove duplication EN/GER languages
                 or same alerts with different effective/expires)
             */
            .distinctBy { alert -> listOf(
                alert.headline,
                alert.severity,
                alert.areas,
                alert.certainty,
                alert.event,
                alert.effective,
                alert.expires,
                alert.desc,
                alert.instruction
            ) }
            .toList()
    }
}