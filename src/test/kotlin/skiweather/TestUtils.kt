package skiweather

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import skiweather.config.objectMapper
import java.nio.file.Files
import java.nio.file.Paths.*

class TestUtils {
    companion object {
        fun createMockClient(mockEngine: MockEngine) =
            HttpClient(mockEngine) {
                install(ContentNegotiation) {
                    register(ContentType.Application.Json, JacksonConverter(objectMapper))
                }
            }

        fun readJson(fileName: String): String {
            val classLoader = this::class.java.classLoader
            val resource = classLoader.getResource(fileName) ?: throw IllegalArgumentException("File not found: $fileName")
            val path = get(resource.toURI())
            return Files.readString(path)
        }
    }
}
