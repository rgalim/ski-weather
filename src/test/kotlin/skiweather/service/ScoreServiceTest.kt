package skiweather.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import skiweather.model.weather.SkiArea

class ScoreServiceTest {

    @Test
    fun `should calculate score of the ski area`() {
        val skiArea = SkiArea(
            "Sölden",
            -13.8,
            0.4,
            5.6,
            9.09,
            7.82,
            86,
            39,
            0
        )

        val scoreService = ScoreService()
        val actualScore = scoreService.scoreSkiArea(skiArea)

        assertEquals(75.0, actualScore)
    }
}