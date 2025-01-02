package skiweather.config

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule

val objectMapper =
    jsonMapper {
        propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
        addModule(kotlinModule())
    }
