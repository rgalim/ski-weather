package skiweather

import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import skiweather.client.WeatherApiClient
import skiweather.config.httpClient
import skiweather.config.loadConfig
import skiweather.config.objectMapper
import skiweather.service.LocationService
import skiweather.service.WeatherService

fun main() {
    embeddedServer(Netty, port = 8000) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        register(Json, JacksonConverter(objectMapper))
    }

    val config = loadConfig()
    val weatherApiClient = WeatherApiClient(config, httpClient)
    val locationService = LocationService()
    val weatherService = WeatherService(locationService, weatherApiClient)

    routing {
        get("/weather") {

            val weather = weatherService.getWeather()

            call.respond(weather)
        }
    }
}