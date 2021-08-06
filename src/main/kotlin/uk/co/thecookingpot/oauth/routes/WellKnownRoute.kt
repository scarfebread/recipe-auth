package uk.co.thecookingpot.oauth.routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import uk.co.thecookingpot.oauth.service.JwtService

fun Route.wellKnown(jwtService: JwtService) {
    route("/.well-known") {
        get("/jwks.json") {
            call.respond(
                mapOf(
                    "keys" to listOf(
                        jwtService.getPublicKey().toJSONObject()
                    )
                )
            )
        }
    }
}