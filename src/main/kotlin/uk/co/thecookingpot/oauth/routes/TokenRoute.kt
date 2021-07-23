package uk.co.thecookingpot.oauth.routes

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import uk.co.thecookingpot.oauth.model.TokenRequest
import uk.co.thecookingpot.oauth.service.TokenService

fun Route.token(tokenService: TokenService) {
    post("/token") {

        // TODO check client secret
        val request = TokenRequest.fromParameters(call.receiveParameters())

        println(request.code)

        call.respond(
            tokenService.generateToken(request)
        )
    }
}