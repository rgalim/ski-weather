package skiweather

import io.ktor.client.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import skiweather.client.httpClient
import skiweather.config.objectMapper

fun main() {
    embeddedServer(Netty, port = 8000) {
        module()
    }.start(wait = true)
}

fun Application.module(client: HttpClient = httpClient) {

    install(ContentNegotiation) {
        register(Json, JacksonConverter(objectMapper))
    }
}