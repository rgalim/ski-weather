package skiweather.route

import io.ktor.server.response.*
import io.ktor.server.routing.*
import skiweather.service.WeatherService

fun Routing.forecastRoutes(weatherService: WeatherService) {

    get("/api/v1/forecast") {
        val location = call.request.queryParameters["location"]
        require(!location.isNullOrEmpty()) { "The parameter 'location' must not be empty" }

        val weatherForecast = weatherService.getWeatherForecast(location)

        call.respond(weatherForecast)
    }
}