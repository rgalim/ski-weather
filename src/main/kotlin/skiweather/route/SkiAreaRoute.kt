package skiweather.route

import io.ktor.server.response.*
import io.ktor.server.routing.*
import skiweather.model.SkiAreasResponse
import skiweather.service.SkiAreaService

fun Routing.skiAreaRoutes(skiAreaService: SkiAreaService) {

    get("/api/v1/ski-area") {
        val rankedSkiAreas: SkiAreasResponse = skiAreaService.getSkiAreas()

        call.respond(rankedSkiAreas)
    }
}