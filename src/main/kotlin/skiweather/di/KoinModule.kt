package skiweather.di

import io.ktor.client.*
import org.koin.dsl.module
import skiweather.client.WeatherApiClient
import skiweather.config.AppConfig
import skiweather.config.createHttpClient
import skiweather.config.loadConfig
import skiweather.service.*

val appModule = module {

    single<AppConfig> { loadConfig() }

    single<HttpClient> { createHttpClient() }

    single<LocationService> { LocationService() }

    single<ScoreService> { ScoreService() }

    single<SkiAreaWeatherService> { SkiAreaWeatherService() }

    single<WeatherApiClient> { WeatherApiClient(get(), get()) }

    single<WeatherService> { WeatherService(get(), get()) }

    single<SkiAreaService> { SkiAreaService(get(), get(), get()) }
}