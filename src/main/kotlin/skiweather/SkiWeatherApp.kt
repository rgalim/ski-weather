package skiweather

import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import skiweather.config.objectMapper
import skiweather.di.appModule
import skiweather.service.WeatherService

fun main() {
    embeddedServer(Netty, port = 8000) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(Koin) {
        modules(appModule)
    }

    install(ContentNegotiation) {
        register(Json, JacksonConverter(objectMapper))
    }

    val weatherService by inject<WeatherService>()

    routing {
        get("/weather") {

            val weather = weatherService.getWeather()

            call.respond(weather)
        }
    }
}