package skiweather.utils

import java.math.BigDecimal
import java.math.RoundingMode

class CalculationUtils {
    companion object {
        fun roundDouble(value: Double): Double {
            return BigDecimal(value)
                .setScale(2, RoundingMode.HALF_UP)
                .toDouble()
        }
    }
}