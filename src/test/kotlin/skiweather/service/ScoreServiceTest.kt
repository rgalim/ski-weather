package skiweather.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import skiweather.model.weather.SkiAreaWeather

class ScoreServiceTest {

    @Test
    fun `should calculate score of the ski area weather`() {
        val skiAreaWeather = SkiAreaWeather(
            "Sölden",
            -13.8,
            0.4,
            5.6,
            9.09,
            7.82,
            86,
            39,
            0.0
        )

        val scoreService = ScoreService()
        val actualScore = scoreService.scoreSkiArea(skiAreaWeather)

        assertEquals(65.0, actualScore)
    }
}