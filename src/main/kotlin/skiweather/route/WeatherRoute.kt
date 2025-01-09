package skiweather.route

import io.ktor.server.response.*
import io.ktor.server.routing.*
import skiweather.service.WeatherService

fun Routing.weatherRoute(weatherService: WeatherService) {

    get("/weather") {
        val weather = weatherService.getWeather()

        call.respond(weather)
    }
}