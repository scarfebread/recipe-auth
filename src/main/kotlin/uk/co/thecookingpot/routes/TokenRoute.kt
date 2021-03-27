package uk.co.thecookingpot.routes

import io.ktor.application.*
import io.ktor.routing.*
import uk.co.thecookingpot.model.TokenRequest
import uk.co.thecookingpot.service.TokenService

fun Route.token(tokenService: TokenService) {
    post("/token") {
        val request = TokenRequest.validate(call.request.queryParameters)

        tokenService.generateToken(request)
    }
}