package skiweather

import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import skiweather.config.objectMapper
import skiweather.di.appModule
import skiweather.exception.WeatherApiException
import skiweather.route.skiAreas
import skiweather.service.SkiAreaService
import skiweather.utils.logger

fun main() {
    embeddedServer(Netty, port = 8000) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    val logger = logger<Application>()

    install(Koin) {
        modules(appModule)
    }

    install(ContentNegotiation) {
        register(Json, JacksonConverter(objectMapper))
    }

    install(StatusPages) {
        exception<WeatherApiException> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "Internal Weather API error")
        }
        exception<BadRequestException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "Invalid parameters")
        }
        exception<Throwable> { call, cause ->
            logger.error("Unexpected error: {}", cause.message)
            call.respond(HttpStatusCode.InternalServerError, "Unexpected error. Something went wrong")
        }
    }

    val skiAreaService by inject<SkiAreaService>()

    routing {
        skiAreas(skiAreaService)
    }
}