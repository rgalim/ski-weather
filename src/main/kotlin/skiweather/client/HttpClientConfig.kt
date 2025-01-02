package skiweather.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.jackson.JacksonConverter
import skiweather.config.objectMapper

val httpClient by lazy {
    HttpClient(Java) {
        install(ContentNegotiation) {
            register(Json, JacksonConverter(objectMapper))
        }
    }
}
