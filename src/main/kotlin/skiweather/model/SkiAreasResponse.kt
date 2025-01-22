package skiweather.model

import skiweather.model.alert.Alert

data class SkiAreasResponse(
    val skiAreas: List<SkiArea>,
    val alerts: List<Alert>
)
