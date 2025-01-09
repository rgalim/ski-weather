package skiweather.di

import io.ktor.client.*
import org.koin.dsl.module
import skiweather.client.WeatherApiClient
import skiweather.config.AppConfig
import skiweather.config.createHttpClient
import skiweather.config.loadConfig
import skiweather.service.LocationService
import skiweather.service.WeatherService

val appModule = module {

    single<AppConfig> { loadConfig() }

    single<HttpClient> { createHttpClient() }

    single<LocationService> { LocationService() }

    single<WeatherApiClient> { WeatherApiClient(get(), get()) }

    single<WeatherService> { WeatherService(get(), get()) }
}