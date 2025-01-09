package skiweather.utils

class ErrorHandler {
    companion object {
        private val logger = logger<ErrorHandler>()

        fun handleUnexpectedError(
            resource: String,
            status: Int,
            body: String,
        ): Nothing {
            val errorMessage = "Unexpected status $status while fetching weather for $resource"
            logger.error("$errorMessage. Body: $body")
            throw Exception(errorMessage)
        }
    }
}
