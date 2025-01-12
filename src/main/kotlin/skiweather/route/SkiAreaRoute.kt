package skiweather.route

import io.ktor.server.response.*
import io.ktor.server.routing.*
import skiweather.service.SkiAreaService

fun Routing.skiAreas(skiAreaService: SkiAreaService) {

    get("/ski-area") {
        val rankedSkiAreas = skiAreaService.getSkiAreas()

        call.respond(rankedSkiAreas)
    }
}