package skiweather.service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class LocationServiceTest {

    @Test
    fun `should return non empty list of locations`() {
        val locationService = LocationService()
        val actualLocations: List<String> = locationService.getLocations()

        assertTrue(actualLocations.isNotEmpty())
        assertTrue(actualLocations.contains("Sölden"))
    }
}