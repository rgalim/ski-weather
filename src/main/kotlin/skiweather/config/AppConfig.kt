package skiweather.config

data class AppConfig(
    val weatherApiUrl: String,
    val weatherApiKey: String,
)

fun loadConfig(): AppConfig {
    return AppConfig(
        weatherApiUrl = System.getenv("WEATHER_API_URL"),
        weatherApiKey = System.getenv("WEATHER_API_KEY"),
    )
}
