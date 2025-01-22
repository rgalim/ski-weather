package skiweather.model.alert

data class Alert(
    val headline: String,
    val severity: String,
    val areas: String,
    val certainty: String,
    val event: String,
    val effective: String,
    val expires: String,
    val desc: String,
    val instruction: String
)
