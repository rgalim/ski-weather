package skiweather.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import skiweather.model.weather.SkiAreaWeather

class ScoreServiceTest {

    @Nested
    inner class ScoreSkiArea {

        @Test
        fun `should calculate score of the ski area weather`() {
            val skiAreaWeather = SkiAreaWeather(
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

    @Nested
    inner class CalculateStars {

        @Test
        fun `should calculate max five stars when the score is max`() {

            val scoreService = ScoreService()
            val actualStars = scoreService.calculateStars(150.0, 150.0)

            assertEquals(5.0, actualStars)
        }

        @Test
        fun `should calculate stars when the score is not max`() {

            val scoreService = ScoreService()
            val actualStars = scoreService.calculateStars(95.0, 150.0)

            assertEquals(3.17, actualStars)
        }
    }
}