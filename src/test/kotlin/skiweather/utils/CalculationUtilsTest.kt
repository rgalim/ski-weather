package skiweather.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import skiweather.utils.CalculationUtils.Companion.roundDouble

class CalculationUtilsTest {

    @Test
    fun `should return rounded double`() {
        val roundedDouble = roundDouble(2.3400000000001)

        assertEquals(2.34, roundedDouble)
    }
}