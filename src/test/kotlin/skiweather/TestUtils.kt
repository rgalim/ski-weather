package skiweather

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import skiweather.config.objectMapper

class TestUtils {
    companion object {
        fun createMockClient(mockEngine: MockEngine) =
            HttpClient(mockEngine) {
                install(ContentNegotiation) {
                    register(ContentType.Application.Json, JacksonConverter(objectMapper))
                }
            }
    }
}
